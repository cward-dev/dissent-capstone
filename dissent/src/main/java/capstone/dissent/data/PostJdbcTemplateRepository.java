package capstone.dissent.data;

import capstone.dissent.data.mappers.PostMapper;
import capstone.dissent.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostJdbcTemplateRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    @Override
    public List<Post> findAll() {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content, is_active from post limit 1000;";
        return jdbcTemplate.query(sql, new PostMapper());
    }

    @Override
    public List<Post> findByArticleId(String articleId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content, is_active "
                + "from post "
                + "where article_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), articleId);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content, is_active "
                + "from post "
                + "where user_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    @Override
    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content, is_active "
                + "from post "
                + "where (date_posted between ? and ?);";

        return jdbcTemplate.query(sql, new PostMapper(), start, end);
    }

    @Override
    public Post findById(String postId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content, is_active "
                + "from post "
                + "where post_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), postId).stream()
                .findAny().orElse(null);
    }

    @Override
    public Post add(Post post) {
        final String sql = "insert into post " +
                "(post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content) "
                + "values (?,?,?,?,?,?,?);";

        post.setPostId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getPostId());
            ps.setString(2, post.getParentPostId());
            ps.setString(3, post.getArticleId());
            ps.setString(4, post.getUserId());
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
}
