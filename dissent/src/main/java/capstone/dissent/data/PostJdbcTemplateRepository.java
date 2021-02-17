package capstone.dissent.data;

import capstone.dissent.data.mappers.PostMapper;
import capstone.dissent.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class PostJdbcTemplateRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    @Override
    public List<Post> findAll() {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content from post limit 1000;";
        return jdbcTemplate.query(sql, new PostMapper());
    }

    @Override
    public List<Post> findByParentPostId(String parentPostId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content"
                + "from post "
                + "where parent_post_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), parentPostId);
    }

    @Override
    public List<Post> findByArticleId(String articleId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content"
                + "from post "
                + "where article_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), articleId);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content"
                + "from post "
                + "where user_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    @Override
    public List<Post> findByDateRange(LocalDate startDate, LocalDate endDate) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content"
                + "from post "
                + "where date_posted between ? and ?;";

        return jdbcTemplate.query(sql, new PostMapper(), startDate, endDate);
    }

    @Override
    public Post findById(String postId) {
        final String sql = "select post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content"
                + "from post "
                + "where post_id = ?;";

        return jdbcTemplate.query(sql, new PostMapper(), postId).stream()
                .findAny().orElse(null);
    }

    @Override
    public Post add(Post post) {
        final String sql = "insert into post " +
                "(post_id, parent_post_id, article_id, user_id, is_dissenting, date_posted, content) " +
                "values (?,?,?,?,?,?,?);";

        post.setPostId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getPostId());
            ps.setString(2, post.getParentPostId());
            ps.setString(3, post.getArticleId());
            ps.setString(4, post.getUserId());
            ps.setBoolean(5, post.isDissenting());
            ps.setDate(6, Date.valueOf(post.getDatePosted()));
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
                + "is_dissenting = ? "
                + "date_posted = ? "
                + "content = ? "
                + "where post_id = ?";

        return jdbcTemplate.update(sql, post.isDissenting(), post.getDatePosted(), post.getContent()) > 0;
    }

    @Override
    public boolean deleteById(int postId) {
        return jdbcTemplate.update("delete from post where post_id = ?", postId) > 0;
    }
}
