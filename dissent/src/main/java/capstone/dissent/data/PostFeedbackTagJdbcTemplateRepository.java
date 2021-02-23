package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleFeedbackTagMapper;
import capstone.dissent.data.mappers.PostFeedbackTagMapper;
import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTagHelper;
import capstone.dissent.models.PostFeedbackTag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostFeedbackTagJdbcTemplateRepository implements PostFeedbackTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostFeedbackTagJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FeedbackTagHelper> findByPostId(String postId) {
        final String sql = "select "
                + "pft.post_id as post_id, "
                + "pft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from post_feedback_tag pft "
                + "inner join feedback_tag ft on pft.feedback_tag_id = ft.feedback_tag_id "
                + "where pft.post_id = ?;";

        var postFeedbackTags = jdbcTemplate.query(
                sql, new PostFeedbackTagMapper(), postId);

        List<FeedbackTagHelper> list = new ArrayList<>();
        if (postFeedbackTags.size() > 0) {
            for (PostFeedbackTag i : postFeedbackTags) {
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
    public List<FeedbackTagHelper> findByUserId(String userId) {
        final String sql = "select "
                + "pft.post_id as post_id, "
                + "pft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from post_feedback_tag pft "
                + "inner join feedback_tag ft on pft.feedback_tag_id = ft.feedback_tag_id "
                + "where pft.user_id = ?;";

        var postFeedbackTags = jdbcTemplate.query(
                sql, new PostFeedbackTagMapper(), userId);

        List<FeedbackTagHelper> list = new ArrayList<>();
        if (postFeedbackTags.size() > 0) {
            for (PostFeedbackTag i : postFeedbackTags) {
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
    public PostFeedbackTag findByKey(String postId, String userId, int feedbackTagId) {
        final String sql = "select "
                + "pft.post_id as post_id, "
                + "pft.user_id as user_id, "
                + "ft.feedback_tag_id as feedback_tag_id, "
                + "ft.feedback_tag_name as feedback_tag_name, "
                + "ft.color_hex as color_hex, "
                + "ft.is_active as is_active "
                + "from post_feedback_tag pft "
                + "inner join feedback_tag ft on pft.feedback_tag_id = ft.feedback_tag_id "
                + "where pft.post_id = ? and pft.user_id = ? and pft.feedback_tag_id = ?;";

        return jdbcTemplate.query(
                sql, new PostFeedbackTagMapper(), postId, userId, feedbackTagId).stream().findAny().orElse(null);
    }

    @Override
    public boolean add(PostFeedbackTag postFeedbackTag) {

        final String sql = "insert into post_feedback_tag (post_id, user_id, feedback_tag_id) values "
                + "(?,?,?);";

        return jdbcTemplate.update(sql,
                postFeedbackTag.getPostId(),
                postFeedbackTag.getUserId(),
                postFeedbackTag.getFeedbackTag().getFeedbackTagId()) > 0;
    }

    @Override
    public boolean deleteByKey(String postId, String userId, int feedbackTagId) {

        final String sql = "delete from post_feedback_tag "
                + "where post_id = ? and user_id = ? and feedback_tag_id = ?;";

        return jdbcTemplate.update(sql, postId, userId, feedbackTagId) > 0;
    }
}
