package capstone.dissent.data;

import capstone.dissent.data.mappers.TopicMapper;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class TopicJdbcTemplateRepository implements TopicRepository {

    private final JdbcTemplate jdbcTemplate;

    public TopicJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<Topic> findAll() {
        final String sql = "select topic_id, topic_name from topic limit 1000;";
        return jdbcTemplate.query(sql, new TopicMapper());
    }

    @Override
    public Topic findById(String topicId) {
        final String sql = "select topic_id, topic_name, from topic where topic_id = ?;";

        return jdbcTemplate.query(sql, new TopicMapper(), topicId).stream()
                .findAny().orElse(null);
    }

    @Override
    public Topic add(Topic topic) {
        final String sql = "insert into topic (topic_name) values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, topic.getTopicName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        topic.setTopicId(keyHolder.getKey().intValue());
        return topic;
    }

    @Override
    public boolean edit(Topic topic) {

        final String sql = "update topic set "
                + "topic_name = ? "
                + "where topic_id = ?;";

        return jdbcTemplate.update(sql, topic.getTopicName(), topic.getTopicId()) > 0;
    }

    @Override
    public boolean deleteById(String topicId) {
        return jdbcTemplate.update("delete from topic where topic_id = ?;", topicId) > 0;
    }
}
