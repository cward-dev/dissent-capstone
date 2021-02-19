package capstone.dissent.data.mappers;

import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackTagMapper implements RowMapper<FeedbackTag> {

    @Override
    public FeedbackTag mapRow(ResultSet resultSet, int i) throws SQLException {
        FeedbackTag feedbackTag = new FeedbackTag();
        feedbackTag.setFeedbackTagId(resultSet.getInt("feedback_tag_id"));
        feedbackTag.setName(resultSet.getString("feedback_tag_name"));
        feedbackTag.setActive(resultSet.getBoolean("is_active"));

        return feedbackTag;
    }



}