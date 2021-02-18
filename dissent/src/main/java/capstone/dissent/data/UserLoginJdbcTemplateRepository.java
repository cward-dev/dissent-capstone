package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return null;
    }

    @Override
    public List<UserLogin> findAll() {
        return null;
    }

    @Override
    public UserLogin findById(String userLoginId) {
        return null;
    }

    @Override
    public boolean edit(UserLogin userLogin) {
        return false;
    }

    @Override
    public boolean deleteById(String userLoginId) {
        return false;
    }

    @Override
    public List<User> getUserFromLogin(UserLogin userLogin) {
        return null;
    }
}
