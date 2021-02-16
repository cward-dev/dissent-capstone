package capstone.dissent.data;

import capstone.dissent.models.Tag;
import capstone.dissent.models.User;

import java.util.HashMap;
import java.util.List;

public interface UserRepository {

    // create
    User add(User user);

    // read
    List<User> findAll();
    User findById(int userId);

    // update
    boolean edit(User user);

    // delete
    boolean deleteById(int userLoginId);

    // helpers
    HashMap<Tag, Integer> getTagData(User user);

}
