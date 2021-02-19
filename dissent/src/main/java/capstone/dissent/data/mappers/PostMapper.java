package capstone.dissent.data.mappers;


import capstone.dissent.models.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

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
        return post;
    }

}
