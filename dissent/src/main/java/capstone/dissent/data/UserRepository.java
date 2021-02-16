package capstone.dissent.data;

import capstone.dissent.models.FeedbackTag;
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
    HashMap<FeedbackTag, Integer> getTagData(User user);

}
