package capstone.dissent.domain;


import capstone.dissent.data.ArticleRepository;
import capstone.dissent.models.Article;
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

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    public List<Article> findAll() {
        return repository.findAllArticles();
    }

    public Article findById(String articleId) {
        return repository.findArticleByArticleId(articleId);
    }

    public List<Article> findArticleByTopicId(int topicId) {
        return repository.findArticleByTopicId(topicId);
    }

    public List<Article> findByPostedDateRange(LocalDateTime d1, LocalDateTime d2) {
        return repository.findByPostedDateRange(d1, d2);
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

        result = checkForDuplicates(article);
        if(!result.isSuccess()){
            return result;
        }

        UUID uuid = UUID.randomUUID();
        article.setArticleId(uuid.toString());

        article = repository.addArticle(article);
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

        Article originalArticle = repository.findArticleByArticleId(article.getArticleId());
        if(originalArticle == null){
            String msg = String.format("Article:  %s  by %s, was not found! ",article.getTitle(),article.getAuthor());
            result.addMessage(msg,ResultType.NOT_FOUND);
            return result;
        }
        if(originalArticle.getArticleUrl() != article.getArticleUrl()){
            result.addMessage("Cannot update Article URL", ResultType.INVALID);
            return result;
        }
        if(!repository.updateArticle(article)){
            String msg = String.format("Article:  %s  by %s, was not found! ",article.getTitle(),article.getAuthor());
            result.addMessage(msg,ResultType.NOT_FOUND);
        }
        return result;

    }

    public boolean inactivateArticle(String articleId){

        return repository.inactivateArticle(articleId);
    }

    // Get Tag Data can be called from the object...

    private Result<Article> checkForDuplicates(Article article){
        Result<Article> result = new Result<>();
        List<Article>all = findAll();
        for(Article a : all){
            if(a.getTitle() == article.getTitle() && a.getAuthor() == article.getAuthor()){
                result.addMessage("Duplicate Articles are not allowed", ResultType.INVALID);
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


}
