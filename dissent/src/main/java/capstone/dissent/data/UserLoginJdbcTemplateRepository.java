package capstone.dissent.data;

import capstone.dissent.data.mappers.UserLoginMapper;
import capstone.dissent.data.mappers.UserMapper;
import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserLoginJdbcTemplateRepository implements UserLoginRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserLoginJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // methods
    @Override
    public UserLogin add(UserLogin userLogin) {

        final String sql = "insert into user_login " +
                "(user_login_id, email, `password`) " +
                "values (?,?,?);";

        userLogin.setUserLoginId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userLogin.getUserLoginId());
            ps.setString(2, userLogin.getEmail());
            ps.setString(3, userLogin.getPassword());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return userLogin;

    }

    @Override
    public List<UserLogin> findAll() {
        final String sql = "select * from user_login limit 1000;";
        return jdbcTemplate.query(sql, new UserLoginMapper());
    }

    @Override
    public UserLogin findById(String userLoginId) {

        final String sql = "select * " +
                "from user_login " +
                "where user_login_id = ?;";

        return jdbcTemplate.query(sql, new UserLoginMapper(), userLoginId).stream()
                .findAny().orElse(null);

    }

    @Override
    public boolean edit(UserLogin userLogin) {
        final String sql = "update user_login set " +
                "email = ?, " +
                "password = ? " +
                "where user_login_id = ?;";

        return jdbcTemplate.update(sql, userLogin.getEmail(), userLogin.getPassword(), userLogin.getUserLoginId()) > 0;
    }

    @Override
    public boolean deleteById(String userLoginId) {

        final String sqlUser = "update user set "
                + "username = 'deleted', "
                + "user_role = 'deleted', "
                + "photo_url = null, "
                + "country = null, "
                + "bio = null "
                + "where user_login_id = ?";
        jdbcTemplate.update(sqlUser, userLoginId);

        final String sqlUserLogin = "update user_login set " +
                "email = 'deleted', " +
                "password = 'deleted' " +
                "where user_login_id = ?";
        return jdbcTemplate.update(sqlUserLogin, userLoginId) > 0;
    }

    @Override
    public User getUserFromUserLoginId(String userLoginId) {
        final String sql = "select * " +
                "from user " +
                "where user_login_id = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), userLoginId).stream()
                .findAny().orElse(null);
    }
}
