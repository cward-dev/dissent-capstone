package capstone.dissent.data.mappers;

import capstone.dissent.models.UserLogin;
import org.springframework.jdbc.core.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginMapper implements RowMapper<UserLogin> {

    @Override
    public UserLogin mapRow(ResultSet resultSet, int i) throws SQLException {

        UserLogin userLogin = new UserLogin();

        userLogin.setUserLoginId(resultSet.getString("userLogin_id"));
        userLogin.setEmail(resultSet.getString("email"));

        // TODO: discuss how passwords are handled (security)
        userLogin.setPassword(resultSet.getString("password"));

        return userLogin;
    }
}