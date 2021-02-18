package capstone.dissent.data;

import capstone.dissent.data.mappers.FeedbackTagMapper;
import capstone.dissent.models.FeedbackTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class FeedbackTagJdbcTemplateRepository implements FeedbackTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FeedbackTag> findAll() {
        final String sql = "select feedback_tag_id, feedback_tag_name, is_active from feedback_tag where is_active = true limit 1000;";
        return jdbcTemplate.query(sql, new FeedbackTagMapper());
    }

    @Override
    public List<FeedbackTag> findAllInactive() {
        final String sql = "select feedback_tag_id, feedback_tag_name, is_active from feedback_tag where is_active = false limit 1000;";
        return jdbcTemplate.query(sql, new FeedbackTagMapper());
    }

    @Override
    public FeedbackTag findById(int feedbackTagId) {
        final String sql = "select feedback_tag_id, feedback_tag_name, is_active from feedback_tag where feedback_tag_id = ?;";

        return jdbcTemplate.query(sql, new FeedbackTagMapper(), feedbackTagId).stream()
                .findAny().orElse(null);
    }

    @Override
    public FeedbackTag add(FeedbackTag feedbackTag) {
        final String sql = "insert into feedback_tag (feedback_tag_name) values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, feedbackTag.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        feedbackTag.setFeedbackTagId(keyHolder.getKey().intValue());
        return feedbackTag;
    }

    @Override
    public boolean edit(FeedbackTag feedbackTag) {

        final String sql = "update feedback_tag set "
                + "feedback_tag_name = ? "
                + "where feedback_tag_id = ? and is_active = true;";

        return jdbcTemplate.update(sql, feedbackTag.getName(), feedbackTag.getFeedbackTagId()) > 0;
    }

    @Override
    public boolean deleteById(int feedbackTagId) {

        final String sql = "update feedback_tag set "
                + "is_active = false "
                + "where feedback_tag_id = ? and is_active = true;";

        return jdbcTemplate.update(sql, feedbackTagId) > 0;
    }
}
