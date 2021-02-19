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
        article.setAuthor(resultSet.getString("author"));
        article.setArticleUrl(resultSet.getString("article_url"));
        article.setArticleImageUrl(resultSet.getString("article_image_url"));
        article.setDatePublished(resultSet.getTimestamp("date_published").toLocalDateTime());
        article.setDatePosted(resultSet.getTimestamp("date_posted").toLocalDateTime());
        article.setActive(resultSet.getBoolean("is_active"));

        SourceMapper sourceMapper = new SourceMapper();
        article.setSource(sourceMapper.mapRow(resultSet, i));

        return article;
    }


}
