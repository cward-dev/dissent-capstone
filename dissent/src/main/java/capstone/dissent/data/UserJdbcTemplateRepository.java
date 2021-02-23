package capstone.dissent.data;

import capstone.dissent.data.mappers.UserMapper;
import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private static final User DEFAULT_USER = new User();


    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // create
    @Override
    @Transactional
    public User add(User user) {

        final String sql = "insert into user " +
                "(user_id, email, password_hash, username, photo_url, country, bio) " +
                "values (?,?,?,?,?,?,?);";

        user.setUserId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPhotoUrl());
            ps.setString(6, user.getCountry());
            ps.setString(7, user.getBio());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        // updates user_role bridge table
        updateRoles(user);

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

        User user = jdbcTemplate.query(sql, new UserMapper(), userId).stream()
                .findAny().orElse(null);

        if (user != null) {
            user.setRoles(getRolesByUserId(user.getUserId()));
        }

        return user;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        final String sql = "select * " +
                "from `user`" +
                "where username = ?;";

        User user = jdbcTemplate.query(sql, new UserMapper(), username).stream()
                .findAny().orElse(null);

        if (user != null) {
            user.setRoles(getRolesByUserId(user.getUserId()));
        }

        return user;
    }



    // update
    @Override
    public boolean edit(User user) {

        final String sql = "update user set " +
                "username = ?, " +
                "photo_url = ?, " +
                "country = ?, " +
                "bio = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql, user.getUsername(), user.getPhotoUrl(), user.getCountry(), user.getBio(), user.getUserId()) > 0;

    }


    // TODO: Need to discuss how to handle deletes for users
    // delete
    @Override
    @Transactional
    public boolean deleteById(String userId) {
        final String sql = "update user set "
                + "username = 'deleted', "
                + "email = 'deleted', "
                + "password_hash = 'deleted', "
                + "photo_url = null, "
                + "country = null, "
                + "bio = null "
                + "where user_id = ?";

        // remove role relationships
        deleteRoles(findById(userId));

        return jdbcTemplate.update(sql, userId) > 0;
    }


    // helper
    @Override
    public HashMap<FeedbackTag, Integer> getTagData(User user) {
        return null;
    }

    private void updateRoles(User user) {

        // delete all roles, then re-add (scorched-earth method {safe})
        deleteRoles(user);

        if (user.getRoles() == null) return;

        for (String role : user.getRoles()) {
            String sql = "insert into user_role (user_id, role_id) " +
                    "select ?, role_id from `role` where `name` = ?;";

            jdbcTemplate.update(sql, user.getUserId(), role);
        }

    }

    private void deleteRoles(User user) {
        if (user == null) return;
        jdbcTemplate.update("delete from user_role where user_id = ?;", user.getUserId());
    }

    private List<String> getRolesByUserId(String userId) {
        final String sql = "select r.name "
                + "from user_role ur "
                + "inner join role r on ur.role_id = r.role_id "
                + "where ur.user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), userId);
    }

}
