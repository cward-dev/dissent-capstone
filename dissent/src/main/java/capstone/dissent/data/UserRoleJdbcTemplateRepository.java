package capstone.dissent.data;

import capstone.dissent.data.mappers.UserRoleMapper;
import capstone.dissent.data.mappers.UserMapper;
import capstone.dissent.models.User;
import capstone.dissent.models.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRoleJdbcTemplateRepository implements UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRoleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // methods
    @Override
    public UserRole add(UserRole userRole) {

        final String sql = "insert into user_role " +
                "(`name`) " +
                "values (?);";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userRole.getName());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return userRole;
    }

    @Override
    public List<UserRole> findAll() {
        final String sql = "select * from user_role limit 1000;";
        return jdbcTemplate.query(sql, new UserRoleMapper());
    }

    @Override
    public UserRole findById(int userRoleId) {

        final String sql = "select * " +
                "from user_role " +
                "where user_role_id = ?;";

        return jdbcTemplate.query(sql, new UserRoleMapper(), userRoleId).stream()
                .findAny().orElse(null);

    }

    @Override
    public boolean edit(UserRole userRole) {
        final String sql = "update user_role set " +
                "`name` = ? " +
                "where user_role_id = ?;";

        return jdbcTemplate.update(sql, userRole.getName(), userRole.getUserRoleId()) > 0;
    }

}
