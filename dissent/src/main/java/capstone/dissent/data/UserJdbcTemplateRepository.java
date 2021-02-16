package capstone.dissent.data;

import capstone.dissent.models.Tag;
import capstone.dissent.models.User;

import java.util.HashMap;
import java.util.List;

public class UserJdbcTemplateRepository implements UserRepository {

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
