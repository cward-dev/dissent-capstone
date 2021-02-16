package capstone.dissent.data;

import capstone.dissent.data.mappers.PostMapper;
import capstone.dissent.models.Article;
import capstone.dissent.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public List<Post> findByArticleId(int articleId) {
        return null;
    }

    @Override
    public Post findById(int postId) {
        return null;
    }

    @Override
    public List<Post> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Post add(Post post) {
        return null;
    }

    @Override
    public boolean edit(Post post) {
        return false;
    }

    @Override
    public boolean deleteById(int postId) {
        return false;
    }
}
