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

    User findById(String userId);

    User findByUsername(String username);

    // update
    boolean edit(User user);

    // delete
    boolean deleteById(String userId);


}
