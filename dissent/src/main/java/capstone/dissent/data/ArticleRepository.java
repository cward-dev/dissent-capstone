package capstone.dissent.data;

import capstone.dissent.models.Article;
import capstone.dissent.models.FeedbackTag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface ArticleRepository {
    List<Article> findAllArticles();

    Article findArticleByArticleId(String articleId);

//    List<Article> findArticleByTopicId(int topicId);

    List<Article> findByPostedDateRange(LocalDateTime d1, LocalDateTime d2);

    Article addArticle(Article article);

    boolean updateArticle(Article article);

    boolean inactivateArticle(String articleId);

//    HashMap<String, Integer> getTagData(Article article);
}
