package capstone.dissent.data.mappers;

import capstone.dissent.models.ArticleFeedbackTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleFeedbackTagMapper implements RowMapper<ArticleFeedbackTag> {

    @Override
    public ArticleFeedbackTag mapRow(ResultSet resultSet, int i) throws SQLException {

        ArticleFeedbackTag articleFeedbackTag = new ArticleFeedbackTag();
        articleFeedbackTag.setArticleId(resultSet.getString("article_id"));
        articleFeedbackTag.setUserId(resultSet.getString("user_id"));

        FeedbackTagMapper feedbackTagMapper = new FeedbackTagMapper();
        articleFeedbackTag.setFeedbackTag(feedbackTagMapper.mapRow(resultSet, i));

        return articleFeedbackTag;
    }
}
