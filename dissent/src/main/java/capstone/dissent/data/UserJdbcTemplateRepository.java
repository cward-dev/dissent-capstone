package capstone.dissent.data;

import capstone.dissent.data.mappers.UserMapper;
import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // create
    @Override
    public User add(User user) {

        final String sql = "insert into user " +
                "(user_id, user_login_id, username, user_role, photo_url, country, bio) " +
                "values (?,?,?,?,?,?,?);";

        user.setUserId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserLoginId());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPhotoUrl());
            ps.setString(6, user.getCountry());
            ps.setString(7, user.getBio());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return user;
    }


    // read
    @Override
    public List<User> findAll() {
        final String sql = "select * from `user` limit 1000;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(String userId) {
        final String sql = "select * " +
                "from `user`" +
                "where user_id = ?;";

        return jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findAny().orElse(null);
    }


    // update
    @Override
    public boolean edit(User user) {

        final String sql = "update user set " +
                "username = ? " +
                "photo_url = ? " +
                "country = ? " +
                "bio = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql, user.getUsername(), user.getPhotoUrl(), user.getCountry(), user.getBio(), user.getUserId()) > 0;

    }



    // delete
    @Override
    public boolean deleteById(String userLoginId) {
        return false;
    }


    // helper
    @Override
    public HashMap<FeedbackTag, Integer> getTagData(User user) {
        return null;
    }

}
