package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleFeedbackTagMapper;
import capstone.dissent.models.Article;
import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTagHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleFeedbackTagJdbcTemplateRepository implements ArticleFeedbackTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleFeedbackTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FeedbackTagHelper> findByArticleId(String articleId) {
        final String sql = "select "
                + "aft.article_id as article_id, "
                + "aft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from article_feedback_tag aft "
                + "inner join feedback_tag ft on aft.feedback_tag_id = ft.feedback_tag_id "
                + "where aft.article_id = ? and ft.is_active = true;";

        var articleFeedbackTags = jdbcTemplate.query(
                sql, new ArticleFeedbackTagMapper(), articleId);

        List<FeedbackTagHelper> list = new ArrayList<>();
        if (articleFeedbackTags.size() > 0) {
            for (ArticleFeedbackTag i : articleFeedbackTags) {
                if (list.size() == 0) {
                    list.add(new FeedbackTagHelper(i.getFeedbackTag().getName(), 1, i.getFeedbackTag().getColorHex()));
                    continue;
                }
                boolean found = false;
                for (FeedbackTagHelper feedbackTagHelper : list) {
                    if (feedbackTagHelper.getTitle().equalsIgnoreCase(i.getFeedbackTag().getName())) {
                        feedbackTagHelper.setValue(feedbackTagHelper.getValue() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    list.add(new FeedbackTagHelper(i.getFeedbackTag().getName(), 1, i.getFeedbackTag().getColorHex()));
                }
            }
        }

        return list;
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
