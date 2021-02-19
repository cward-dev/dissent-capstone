package capstone.dissent.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    // properties
    private final int MAX_CHARACTERS = 255;

    private String userId;
    private String userLoginId;

    @NotNull(message = "Username cannot be null")
    @Size(max = MAX_CHARACTERS, message = "Username must not exceed ${MAX_CHARACTERS} characters.")
    private String username;

    //    private Role role;
    //    TODO: discuss making Role a model

    private String role;
    private String photoUrl;

    @Size(max = MAX_CHARACTERS, message = "Country must not exceed ${MAX_CHARACTERS} characters.")
    private String country;

    @Size(max = MAX_CHARACTERS, message = "Bio must not exceed ${MAX_CHARACTERS} characters.")
    private String bio;


    // constructor(s)
    public User() {

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

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLogin) {
        this.userLoginId = userLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
