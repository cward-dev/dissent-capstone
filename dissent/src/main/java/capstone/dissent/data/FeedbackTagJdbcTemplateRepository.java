package capstone.dissent.data;

import capstone.dissent.data.mappers.FeedbackTagMapper;
import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.Topic;
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
        final String sql = "select feedback_tag_id, feedback_tag_name, color_hex, is_active from feedback_tag where is_active = true limit 1000;";
        return jdbcTemplate.query(sql, new FeedbackTagMapper());
    }

    @Override
    public List<FeedbackTag> findAllWithInactive() {
        final String sql = "select feedback_tag_id, feedback_tag_name, color_hex, is_active from feedback_tag limit 1000;";
        return jdbcTemplate.query(sql, new FeedbackTagMapper());
    }

    @Override
    public FeedbackTag findById(int feedbackTagId) {
        final String sql = "select feedback_tag_id, feedback_tag_name, color_hex, is_active from feedback_tag where feedback_tag_id = ?;";

        return jdbcTemplate.query(sql, new FeedbackTagMapper(), feedbackTagId).stream()
                .findAny().orElse(null);
    }

    @Override
    public FeedbackTag findInactiveByName(String feedbackTagName) {
        final String sql = "select feedback_tag_id, feedback_tag_name, color_hex, is_active from feedback_tag where UPPER(feedback_tag_name) = UPPER(?);";

        return jdbcTemplate.query(sql, new FeedbackTagMapper(), feedbackTagName).stream()
                .findAny().orElse(null);
    }

    @Override
    public FeedbackTag add(FeedbackTag feedbackTag) {
        final String sql = "insert into feedback_tag (feedback_tag_name, color_hex) values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, feedbackTag.getName());
            ps.setString(2, feedbackTag.getColorHex());
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
                + "feedback_tag_name = ?, "
                + "color_hex = ? "
                + "where feedback_tag_id = ?;";

        return jdbcTemplate.update(sql, feedbackTag.getName(), feedbackTag.getColorHex(), feedbackTag.getFeedbackTagId()) > 0;
    }

    @Override
    public boolean activateById(int feedbackTagId) {

        final String sql = "update feedback_tag set "
                + "is_active = true "
                + "where feedback_tag_id = ? and is_active = false;";

        return jdbcTemplate.update(sql, feedbackTagId) > 0;
    }

    @Override
    public boolean deleteById(int feedbackTagId) {

        final String sql = "update feedback_tag set "
                + "is_active = false "
                + "where feedback_tag_id = ? and is_active = true;";

        return jdbcTemplate.update(sql, feedbackTagId) > 0;
    }
}
