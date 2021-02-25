package capstone.dissent.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class User {

    // properties
    private final int MAX_CHARACTERS = 255;

    private String userId;

    private List<String> roles = new ArrayList<>();

    @NotBlank(message = "Email cannot be null")
    @Email
    private String email;

    private String password;

    @NotBlank(message = "Username cannot be null")
    @Size(max = MAX_CHARACTERS, message = "Username must not exceed ${MAX_CHARACTERS} characters.")
    private String username;

    private String photoUrl;

    @Size(max = MAX_CHARACTERS, message = "Country must not exceed ${MAX_CHARACTERS} characters.")
    private String country;

    @Size(max = MAX_CHARACTERS, message = "Bio must not exceed ${MAX_CHARACTERS} characters.")
    private String bio;

    private boolean isActive;

    // constructor(s)
    public User() {

    }

    public User(String userId, List<String> roles, String email, String password, String username) {
        this.userId = userId;
        this.roles = roles;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(List<String> roles, String username) {
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean hasRole(String role) {
        if (roles == null) return false;
        return roles.contains(role);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
