package capstone.dissent.models;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Post {

    String postId;

    String parentPostId;

    @NotBlank(message = "Post must belong to an article")
    String articleId;

    boolean isDissenting;

    @PastOrPresent(message = "Time posted must not be in future")
    LocalDateTime timestamp;

    private final int MAX_CHARACTERS = 40000;
    @NotBlank(message = "Post content cannot be blank")
    @Size(max = MAX_CHARACTERS, message =  "Post content must between 1 and 40000 characters")
    String content;

    boolean isActive;

    // TODO: Discuss User vs userId.
    @NotNull(message = "Post must have a user")
    User user;

    @Min(value = 0, message =  "Post cannot have less than zero children")
    int childPostCount;

    List<Post> childPosts = new ArrayList<>();
    List<FeedbackTagHelper> feedbackTags = new ArrayList<>();

    public Post() {
        this.isActive = true;
    }

    public Post(String parentPost, String article, boolean isDissenting, LocalDateTime timestamp, String content, boolean isActive, User user) {
        this.parentPostId = parentPost;
        this.articleId = article;
        this.isDissenting = isDissenting;
        this.timestamp = timestamp;
        this.content = content;
        this.isActive = isActive;
        this.user = user;
    }

    public Post(String postId, String parentPostId, String articleId, boolean isDissenting, LocalDateTime timestamp, String content, boolean isActive, User user) {
        this.postId = postId;
        this.parentPostId = parentPostId;
        this.articleId = articleId;
        this.isDissenting = isDissenting;
        this.timestamp = timestamp;
        this.content = content;
        this.isActive = isActive;
        this.user = user;
    }

//    public void addFeedbackTagToPost(FeedbackTag feedbackTag){
//
//        if(feedbackTags.get(feedbackTag)==null){
//            feedbackTags.put(feedbackTag,1);
//        } else{
//            int value = feedbackTags.get(feedbackTag);
//            feedbackTags.replace(feedbackTag,value+1);
//        }
//
//        setFeedbackTags(feedbackTags);
//    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(String parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public boolean isDissenting() {
        return isDissenting;
    }

    public void setDissenting(boolean dissenting) {
        isDissenting = dissenting;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getChildPostCount() {
        return childPostCount;
    }

    public void setChildPostCount(int childPostCount) {
        this.childPostCount = childPostCount;
    }

    public List<Post> getChildPosts() {
        return childPosts;
    }

    public void setChildPosts(List<Post> childPosts) {
        this.childPosts = childPosts;
    }

    public List<FeedbackTagHelper> getFeedbackTags() {
        return feedbackTags;
    }

    public void setFeedbackTags(List<FeedbackTagHelper> feedbackTags) {
        this.feedbackTags = feedbackTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(getPostId(), post.getPostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId());
    }
}
