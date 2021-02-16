package capstone.dissent.data.mappers;

import capstone.dissent.models.User;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();

        // TODO: discuss user.setUserLogin

        user.setUserId(resultSet.getString("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setRole(resultSet.getString("user_role"));

        if (resultSet.getString("bio") != null) {
            user.setBio(resultSet.getString("bio"));
        }

        if (resultSet.getString("country") != null) {
            user.setCountry(resultSet.getString("country"));
        }

        if (resultSet.getString("photo_url") != null) {
            user.setPhotoUrl(resultSet.getString("photo_url"));
        }

        return user;
    }
}
