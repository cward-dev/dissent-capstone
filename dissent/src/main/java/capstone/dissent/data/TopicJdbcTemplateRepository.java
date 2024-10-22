package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleMapper;
import capstone.dissent.data.mappers.TopicMapper;
import capstone.dissent.models.Article;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TopicJdbcTemplateRepository implements TopicRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ArticleRepository articleRepository;

    public TopicJdbcTemplateRepository(JdbcTemplate jdbcTemplate, ArticleRepository articleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Topic> findAll() {
        final String sql = "select topic_id, topic_name, is_active from topic where is_active = true limit 1000;";
        List<Topic> result = jdbcTemplate.query(sql, new TopicMapper());

        if (result.size() > 0) {
            for (Topic topic : result) {
                addArticles(topic);
            }
        }

        return result;
    }

    @Override
    public List<Topic> findAllWithInactive() {
        final String sql = "select topic_id, topic_name, is_active from topic limit 1000;";
        List<Topic> result = jdbcTemplate.query(sql, new TopicMapper());

        if (result.size() > 0) {
            for (Topic topic : result) {
                addArticles(topic);
            }
        }

        return result;
    }

    @Override
    public Topic findById(int topicId) {
        final String sql = "select topic_id, topic_name, is_active from topic where topic_id = ?;";

        Topic result = jdbcTemplate.query(sql, new TopicMapper(), topicId).stream()
                .findAny().orElse(null);

        if (result != null) {
            addArticles(result);
        }

        return result;
    }

    @Override
    public Topic findByTopicName(String topicName) {
        final String sql = "select topic_id, topic_name, is_active from topic where UPPER(topic_name) = UPPER(?) and is_active = true;";

        Topic result = jdbcTemplate.query(sql, new TopicMapper(), topicName).stream()
                .findAny().orElse(null);

        if (result != null) {
            addArticles(result);
        }

        return result;
    }

    @Override
    public Topic findInactiveByName(String topicName) {
        final String sql = "select topic_id, topic_name, is_active from topic where UPPER(topic_name) = UPPER(?) and is_active = false;";

        Topic result = jdbcTemplate.query(sql, new TopicMapper(), topicName).stream()
                .findAny().orElse(null);

        if (result != null) {
            addArticles(result);
        }

        return result;
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
    public boolean activateById(int topicId) {

        final String sql = "update topic set "
                + "is_active = true "
                + "where topic_id = ?;";

        return jdbcTemplate.update(sql, topicId) > 0;
    }

    @Override
    public boolean deleteById(int topicId) {

        final String sql = "update topic set "
                + "is_active = false "
                + "where topic_id = ?;";

        return jdbcTemplate.update(sql, topicId) > 0;
    }

    private void addArticles(Topic topic) {
        topic.setArticles(articleRepository.findArticleByTopicId(topic.getTopicId()));
    }
}
