package capstone.dissent.data.mappers;

import capstone.dissent.models.PostFeedbackTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostFeedbackTagMapper implements RowMapper<PostFeedbackTag> {

    @Override
    public PostFeedbackTag mapRow(ResultSet resultSet, int i) throws SQLException {

        PostFeedbackTag postFeedbackTag = new PostFeedbackTag();
        postFeedbackTag.setPostId(resultSet.getString("post_id"));
        postFeedbackTag.setUserId(resultSet.getString("user_id"));

        FeedbackTagMapper feedbackTagMapper = new FeedbackTagMapper();
        postFeedbackTag.setFeedbackTag(feedbackTagMapper.mapRow(resultSet, i));

        return postFeedbackTag;
    }
}
