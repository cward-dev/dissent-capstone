package capstone.dissent.models;

public class UserRole {

    // properties
    private int userRoleId;
    private String name;

    // constructor(s)
    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    // getters/setters
    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
