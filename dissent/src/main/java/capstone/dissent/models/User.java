package capstone.dissent.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    // properties
    private final int MAX_CHARACTERS = 255;

    private String userId;
    private int userRoleId;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Username cannot be null")
    @Size(max = MAX_CHARACTERS, message = "Username must not exceed ${MAX_CHARACTERS} characters.")
    private String username;

    private String photoUrl;

    @Size(max = MAX_CHARACTERS, message = "Country must not exceed ${MAX_CHARACTERS} characters.")
    private String country;

    @Size(max = MAX_CHARACTERS, message = "Bio must not exceed ${MAX_CHARACTERS} characters.")
    private String bio;

    // TODO: isActive tag to mirror database.


    // constructor(s)
    public User() {

    }

    public User(int userRoleId, String username) {
        this.userRoleId = userRoleId;
        this.username = username;
    }

    public User(String userId) {
        this.userId = userId;
    }

    // getters/setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
