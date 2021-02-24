package capstone.dissent.data.mappers;

import capstone.dissent.models.Post;
import capstone.dissent.models.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements RowMapper<User> {

    private final JdbcTemplate jdbcTemplate;

    public UserMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setUserId(resultSet.getString("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setActive(resultSet.getBoolean("is_active"));

        if (resultSet.getString("email") != null) {
            user.setEmail(resultSet.getString("email"));
        }

        if (resultSet.getString("password_hash") != null) {
            user.setPassword(resultSet.getString("password_hash"));
        }

        if (resultSet.getString("bio") != null) {
            user.setBio(resultSet.getString("bio"));
        }

        if (resultSet.getString("country") != null) {
            user.setCountry(resultSet.getString("country"));
        }

        if (resultSet.getString("photo_url") != null) {
            user.setPhotoUrl(resultSet.getString("photo_url"));
        }

        addRoles(user);

        return user;
    }

    private void addRoles(User user) {

        final String sql = "select r.`name` " +
                "from `role` r " +
                "inner join user_role ur on ur.role_id = r.role_id " +
                "where user_id = ?;";

        List<String> roles = jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
                }
            },
            user.getUserId()
        );

        user.setRoles(roles);
    }
}
