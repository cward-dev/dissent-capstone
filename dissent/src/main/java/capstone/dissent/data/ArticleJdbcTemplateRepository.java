package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleMapper;
import capstone.dissent.models.Article;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Article> findAllArticles() {
        final String sql = "select article_id, title, description,source_id, author, article_url, " +
                " article_image_url, date_published, date_posted from article;";

        return jdbcTemplate.query(sql, new ArticleMapper());
    }

    public Article findArticleByArticleId(String articleId){
        final String sql = "select article_id, title, description,source_id, author, article_url, " +
                " article_image_url, date_published, date_posted from article" +
                "where article_id =  ?;";

        Article article = jdbcTemplate.query(sql, new ArticleMapper(),articleId).stream()
                .findFirst().orElse(null);

        // TODO: 2/16/2021 adding topics and posts ... also Hashmap

        return article;
    }

    public List<Article> findArticleByTopic(Topic topic){
        return null;
    }


    private void addTopics(){
        final String sql = "select * from";
    }



}
