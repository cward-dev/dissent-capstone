package capstone.dissent.data.mappers;

import capstone.dissent.models.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ArticleMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet resultSet, int i) throws SQLException{
        Article article = new Article();
        article.setArticleId(resultSet.getString("article_id"));
        article.setTitle(resultSet.getString("title"));
        article.setDescription(resultSet.getString("description"));
        article.setSource_id(resultSet.getString("source_id"));
        article.setAuthor(resultSet.getString("author"));
        article.setArticleUrl(resultSet.getString("article_url"));
        article.setArticleImageUrl(resultSet.getString("article_image_url"));
        article.setDatePublished(resultSet.getTimestamp("date_published").toLocalDateTime());
        article.setDatePosted(resultSet.getTimestamp("date_posted").toLocalDateTime());
//        article.setActive(resultSet.getBoolean("is_Active"));

        return article;

    }


}
