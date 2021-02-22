package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleFeedbackTagMapper;
import capstone.dissent.models.ArticleFeedbackTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleFeedbackTagJdbcTemplateRepository implements ArticleFeedbackTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleFeedbackTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ArticleFeedbackTag> findByArticleId(String articleId) {
        final String sql = "select "
                + "aft.article_id as article_id, "
                + "aft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from article_feedback_tag aft "
                + "inner join feedback_tag ft on aft.feedback_tag_id = ft.feedback_tag_id "
                + "where aft.article_id = ?;";

        return jdbcTemplate.query(
                sql, new ArticleFeedbackTagMapper(), articleId);
    }

    @Override
    public ArticleFeedbackTag findByKey(String articleId, String userId, int feedbackTagId) {
        final String sql = "select "
                + "aft.article_id as article_id, "
                + "aft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from article_feedback_tag aft "
                + "inner join feedback_tag ft on aft.feedback_tag_id = ft.feedback_tag_id "
                + "where aft.article_id = ? and aft.user_id = ? and aft.feedback_tag_id = ?;";

        return jdbcTemplate.query(
                sql, new ArticleFeedbackTagMapper(), articleId, userId, feedbackTagId).stream().findAny().orElse(null);
    }

    @Override
    public boolean add(ArticleFeedbackTag articleFeedbackTag) {

        final String sql = "insert into article_feedback_tag (article_id, user_id, feedback_tag_id) values "
                + "(?,?,?);";

        return jdbcTemplate.update(sql,
                articleFeedbackTag.getArticleId(),
                articleFeedbackTag.getUserId(),
                articleFeedbackTag.getFeedbackTag().getFeedbackTagId()) > 0;
    }

    @Override
    public boolean deleteByKey(String articleId, String userId, int feedbackTagId) {

        final String sql = "delete from article_feedback_tag "
                + "where article_id = ? and user_id = ? and feedback_tag_id = ?;";

        return jdbcTemplate.update(sql, articleId, userId, feedbackTagId) > 0;
    }
}
