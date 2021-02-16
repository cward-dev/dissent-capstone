package capstone.dissent.data;

import capstone.dissent.models.Tag;
import capstone.dissent.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // methods
    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(int userId) {
        return null;
    }

    @Override
    public boolean edit(User user) {
        return false;
    }

    @Override
    public boolean deleteById(int userLoginId) {
        return false;
    }

    @Override
    public HashMap<Tag, Integer> getTagData(User user) {
        return null;
    }

}
