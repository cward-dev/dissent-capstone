package capstone.dissent.data;

import capstone.dissent.models.Article;

import java.util.List;

public interface ArticleRepository {
    List<Article> findAllArticles();

    Article findArticleByArticleId(String articleId);



}
