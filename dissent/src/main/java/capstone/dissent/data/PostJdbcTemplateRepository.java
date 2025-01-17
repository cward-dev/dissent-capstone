package capstone.dissent.data;

import capstone.dissent.data.mappers.PostFeedbackTagMapper;
import capstone.dissent.data.mappers.PostMapper;
import capstone.dissent.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class PostJdbcTemplateRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    @Override
    public List<Post> findAll() {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email as email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id limit 1000;";
        List<Post> result = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate));

        if (result.size() > 0) {
            for(Post post : result) {
                addFeedbackTags(post);
                postChildCounter(post);
            }
        }

        return result;
    }

    @Override
    public List<Post> findByArticleId(String articleId) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email as email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where p.article_id = ? and p.parent_post_id IS NULL;";

        List<Post> result = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate), articleId);

        if (result.size() > 0) {
            for(Post post : result) {
                addFeedbackTags(post);
                postChildCounter(post);
            }
        }

        return result;
    }

    @Override
    public List<Post> findByUserId(String userId) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where p.user_id = ?;";

        List<Post> result = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate), userId);

        if (result.size() > 0) {
            for(Post post : result) {
                addFeedbackTags(post);
                postChildCounter(post);
            }
        }

        return result;
    }

    @Override
    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where (p.date_posted between ? and ?);";

        List<Post> result = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate), start, end);

        if (result.size() > 0) {
            for(Post post : result) {
                addFeedbackTags(post);
                postChildCounter(post);
            }
        }

        return result;
    }

    @Override
    public Post findById(String postId) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email as email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where (p.post_id = ?);";

        Post result = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate), postId).stream()
                .findAny().orElse(null);

        if (result != null) {
            addFeedbackTags(result);
            postChildCounter(result);
        }

        return result;
    }

    @Override
    public Post add(Post post) {
        final String sql = "insert into post " +
                "(post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content) "
                + "values (?,?,?,?,?,?,?);";

        post.setPostId(java.util.UUID.randomUUID().toString());
        post.setTimestamp(LocalDateTime.now());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getPostId());
            ps.setString(2, post.getParentPostId());
            ps.setString(3, post.getArticleId());
            ps.setString(4, post.getUser().getUserId());
            ps.setBoolean(5, post.isDissenting());
            ps.setTimestamp(6, Timestamp.valueOf(post.getTimestamp()));
            ps.setString(7, post.getContent());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return post;
    }

    @Override
    public boolean edit(Post post) {

        final String sql = "update post set "
                + "is_dissenting = ?, "
                + "date_posted = ?, "
                + "content = ? "
                + "where post_id = ? and is_active = true;";

        return jdbcTemplate.update(sql, post.isDissenting(), Timestamp.valueOf(post.getTimestamp()), post.getContent(), post.getPostId()) > 0;
    }

    @Override
    public boolean deleteById(String postId) {
        final String sql = "update post set "
                + "content = ?, "
                + "is_active = ? "
                + "where post_id = ? and is_active = true;";

        return jdbcTemplate.update(sql, "This post has been deleted.", false, postId) > 0;
    }

    public void addFeedbackTags(Post post) {

        final String sql = "select pft.post_id, pft.user_id, pft.feedback_tag_id, "
                + "ft.feedback_tag_id, ft.feedback_tag_name, ft.color_hex, ft.is_active "
                + "from post_feedback_tag pft "
                + "inner join feedback_tag ft on pft.feedback_tag_id = ft.feedback_tag_id "
                + "where pft.post_id = ?";

        var feedbackTags = jdbcTemplate.query(sql, new PostFeedbackTagMapper(), post.getPostId());

        List<FeedbackTagHelper> list = new ArrayList<>();
        if (feedbackTags.size() > 0) {
            for (PostFeedbackTag i : feedbackTags) {
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

        post.setFeedbackTags(list);
    }

    private int postChildCounter(Post post) {
        int counter = 0;
        int newCounter;

        for (Post p : post.getChildPosts()) {
            if (p.isActive()) {
                counter++;
                if (p.getChildPosts().size() > 0) {
                    newCounter = postChildCounter(p);
                    counter += newCounter;
                }
            }
        }

        post.setChildPostCount(counter);
        return counter;
    }
}
