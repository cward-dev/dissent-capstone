package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;

import java.util.List;

public class UserLoginJdbcTemplateRepository implements UserLoginRepository {
    @Override
    public UserLogin add(UserLogin userLogin) {
        return null;
    }

    @Override
    public List<UserLogin> findAll() {
        return null;
    }

    @Override
    public UserLogin findById(int userLoginId) {
        return null;
    }

    @Override
    public boolean edit(UserLogin userLogin) {
        return false;
    }

    @Override
    public boolean deleteById(int userLoginId) {
        return false;
    }

    @Override
    public List<User> getUserFromLogin(UserLogin userLogin) {
        return null;
    }
}
