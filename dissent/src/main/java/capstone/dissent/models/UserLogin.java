package capstone.dissent.models;

public class UserLogin {

    // properties
    private String userLoginId;
    private String email;
    private String password;


    // constructor(s)
    public UserLogin() {
    }


    // getters/setters
    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
