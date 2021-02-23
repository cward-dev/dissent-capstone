package capstone.dissent.domain;


import capstone.dissent.data.ArticleFeedbackTagRepository;
import capstone.dissent.data.ArticleRepository;
import capstone.dissent.data.ArticleTopicRepository;
import capstone.dissent.models.*;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTopicRepository articleTopicRepository;
    private final ArticleFeedbackTagRepository articleFeedbackTagRepository;
    private final SourceService sourceService;

    Validator validator;

    public ArticleService(ArticleRepository articleRepository, ArticleTopicRepository articleTopicRepository,
                          ArticleFeedbackTagRepository articleFeedbackTagRepository, SourceService sourceService) {
        this.articleTopicRepository = articleTopicRepository;
        this.articleRepository = articleRepository;
        this.articleFeedbackTagRepository = articleFeedbackTagRepository;
        this.sourceService = sourceService;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Article> findAll() {
        return articleRepository.findAllArticles();
    }

    public Article findById(String articleId) {
        return articleRepository.findArticleByArticleId(articleId);
    }

    public List<Article> findArticleByTopicId(int topicId) {  // TODO maybe delete?
        return articleRepository.findArticleByTopicId(topicId);
    }

    public List<Article> findByPostedDateRange(LocalDateTime d1, LocalDateTime d2) {
        return articleRepository.findByPostedDateRange(d1, d2);
    }

    public ArticleFeedbackTag findArticleFeedbackTagByKey(String articleId, String userId, int feedbackTagId) {
        return articleFeedbackTagRepository.findByKey(articleId, userId, feedbackTagId);
    }

    public List<FeedbackTagHelper> findArticleFeedbackTagsByArticleId(String articleId) {
        return articleFeedbackTagRepository.findByArticleId(articleId);
    }

    public Result<Article> add(Article article) {
        Result<Article> result = validate(article);

        if(!result.isSuccess()){
            return result;
        }

        if(article.getArticleId()!= null){
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        Result<Source> sourceResult = addSource(article);
        if(!sourceResult.isSuccess()){
            result.addMessage("Unable to attach source to article.", ResultType.INVALID);
            return result;
        }
        article.setSource(sourceResult.getPayload());

        result = checkForDuplicates(article);
        if(!result.isSuccess()){
            return result;
        }

        article = articleRepository.addArticle(article);
        result.setPayload(article);
        return result;

    }

    public Result<Article> update(Article article){
        Result<Article> result = validate(article);

        if (!result.isSuccess()) {
            return result;
        }

        if(article.getArticleId()==null){
            result.addMessage("Must have a valid Id to update Article!", ResultType.INVALID);
            return result;
        }

        Article originalArticle = articleRepository.findArticleByArticleId(article.getArticleId());
        if(originalArticle == null){
            String msg = String.format("Article:  %s  by %s, was not found! ",article.getTitle(),article.getAuthor());
            result.addMessage(msg,ResultType.NOT_FOUND);
            return result;
        }
        if(!originalArticle.getArticleUrl().equalsIgnoreCase(article.getArticleUrl())){
            result.addMessage("Cannot update Article URL", ResultType.INVALID);
            return result;
        }
        if(!articleRepository.updateArticle(article)){
            String msg = String.format("Article:  %s  by %s, was not found! ",article.getTitle(),article.getAuthor());
            result.addMessage(msg,ResultType.NOT_FOUND);
        }
        article.setDatePosted(LocalDateTime.now());
        return result;

    }

    public boolean inactivateArticle(String articleId){
        return articleRepository.inactivateArticle(articleId);
    }


    public Result<Void> addArticleTopic(ArticleTopic articleTopic){
        Result<Void> result = validateArticleTopic(articleTopic);
        if(!result.isSuccess()){
            return result;
        }
        if(!articleTopicRepository.add(articleTopic)){
            result.addMessage("Article topic not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Void> deleteArticleTopicByKey(String articleId, int topicId){
        Result<Void> result = new Result<>();
        if (!articleTopicRepository.deleteByKey(articleId, topicId)) {
            return result;
        }
        result.addMessage("Article topic not found", ResultType.NOT_FOUND);
        return result;
    }

    public Result<Void> addFeedbackTag(ArticleFeedbackTag articleFeedbackTag) {
        Result<Void> result = validate(articleFeedbackTag);
        if (!result.isSuccess()) {
            return result;
        }

        if (!articleFeedbackTagRepository.add(articleFeedbackTag)) {
            result.addMessage("Article feedback tag not added", ResultType.INVALID);
        }

        return result;
    }

    public boolean deleteFeedbackTagByKey(String articleId, String userId, int feedbackTagId) {
        return articleFeedbackTagRepository.deleteByKey(articleId, userId, feedbackTagId);
    }


    private Result<Article> checkForDuplicates(Article article){
        Result<Article> result = new Result<>();
        List<Article>all = findAll();
        for(Article a : all){
            if(a.getTitle().equalsIgnoreCase(article.getTitle()) && a.getAuthor().equalsIgnoreCase( article.getAuthor())){
                result.addMessage("Duplicate Articles are not allowed", ResultType.INVALID);
                return result;
            }
        }
        return result;
    }

    private Result<Article> validate(Article article) {
        Result<Article> result = new Result<>();

        if (article == null) {
            result.addMessage("Article cannot be blank", ResultType.INVALID);
            return result;
        }

        if (result.isSuccess()) {
            Set<ConstraintViolation<Article>> violations = validator.validate(article);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<Article> violation : violations) {
                    result.addMessage(violation.getMessage(), ResultType.INVALID);
                }
            }

        }
        return result;
    }


    private Result<Void> validateArticleTopic(ArticleTopic articleTopic){
        Result<Void> result = new Result<>();

        if(articleTopic == null){
            result.addMessage("articleTopic cannot be null", ResultType.INVALID);
            return result;
        }
        if(articleTopic.getArticleId() == null || articleTopic.getTopicId()<=0){
            result.addMessage("topicId and articleId cannot be blank or missing!", ResultType.INVALID);
        }
        return result;
    }


    private Result<Void> validate(ArticleFeedbackTag articleFeedbackTag) {
        Result<Void> result = new Result<>();
        if (articleFeedbackTag == null) {
            result.addMessage("Article Feedback Tag cannot be null", ResultType.INVALID);
            return result;
        }

        if (articleFeedbackTag.getFeedbackTag() == null) {
            result.addMessage("Feedback Tag cannot be null", ResultType.INVALID);
        }

        return result;
    }

    private Result<Source> addSource(Article article) {
        Result<Source> result = new Result<>();

        result.setPayload(sourceService.findBySourceNameAndUrl(
                article.getSource().getSourceName(), article.getSource().getWebsiteUrl()));
        if (result.getPayload() != null) {
            return result;
        }

        return sourceService.add(article.getSource());
    }


}
