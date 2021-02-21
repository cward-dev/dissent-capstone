package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserRole;

import java.util.List;

public interface UserRoleRepository {

    // create
    UserRole add(UserRole userRole);

    // read
    List<UserRole> findAll();
    UserRole findById(int userRoleId);

    // update
    boolean edit(UserRole userRole);


}
