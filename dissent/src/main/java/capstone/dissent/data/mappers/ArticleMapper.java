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
        article.setArticleImageUrl(resultSet.getString("article_url"));
        article.setArticleImageUrl(resultSet.getString("article_image_url"));
        article.setDatePublished(resultSet.getDate("date_published").toLocalDate());
        article.setDatePosted(resultSet.getDate("date_posted").toLocalDate());

        return article;

    }


}
