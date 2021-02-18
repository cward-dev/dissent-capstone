package capstone.dissent.data.mappers;

import capstone.dissent.models.Post;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicMapper implements RowMapper<Topic> {

    @Override
    public Topic mapRow(ResultSet resultSet, int i) throws SQLException {
        Topic topic = new Topic();
        topic.setTopicId(resultSet.getInt("topic_id"));
        topic.setTopicName(resultSet.getString("topic_name"));
        topic.setActive(resultSet.getBoolean("is_active"));

        return topic;
    }

}