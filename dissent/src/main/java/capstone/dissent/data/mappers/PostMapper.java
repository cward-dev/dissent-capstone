package capstone.dissent.data.mappers;


import capstone.dissent.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

    private final JdbcTemplate jdbcTemplate;

    public PostMapper(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post = new Post();
        post.setPostId(resultSet.getString("post_id"));
        post.setParentPostId(resultSet.getString("parent_post_id"));
        post.setArticleId(resultSet.getString("article_id"));
        post.setDissenting(resultSet.getBoolean("is_dissenting"));
        post.setTimestamp(resultSet.getTimestamp("date_posted").toLocalDateTime());
        post.setContent(resultSet.getString("content"));
        post.setActive(resultSet.getBoolean("is_active"));

        UserMapper userMapper = new UserMapper();
        post.setUser(userMapper.mapRow(resultSet, i));

        addChildPosts(post);

        return post;
    }

    private void addChildPosts(Post post) {

        final String sql = "select cp.post_id, cp.parent_post_id, cp.article_id, cp.user_id, cp.is_dissenting, cp.date_posted, cp.content, cp.is_active, "
                + "cu.username as username, cu.email, cu.password_hash, cu.photo_url, cu.country, cu.bio, cu.is_active "
                + "from post cp "
                + "inner join `user` cu on cp.user_id = cu.user_id "
                + "where (cp.parent_post_id = ?);";

        var childPosts = jdbcTemplate.query(sql, new PostMapper(jdbcTemplate), post.getPostId());

        post.setChildPosts(childPosts);
    }

}
