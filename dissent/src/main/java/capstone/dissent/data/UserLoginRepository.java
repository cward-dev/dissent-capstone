package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;

import java.util.HashMap;
import java.util.List;

public interface UserLoginRepository {

    // create
    UserLogin add(UserLogin userLogin);

    // read
    List<UserLogin> findAll();
    UserLogin findById(String userLoginId);

    // update
    boolean edit(UserLogin userLogin);

    // delete
    boolean deleteById(String userLoginId);

    // helpers
    List<User> getUserFromLogin(UserLogin userLogin);

}
