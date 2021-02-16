package capstone.dissent.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Post {

    String postId;
    String parentPostId;
    @NotBlank(message = "Post must belong to an article")
    String articleId;
    @NotBlank(message = "Post must have a user")
    String userId;
    @NotNull(message = "Post must have a stance [accepting / dissenting]")
    boolean isDissenting;
    @PastOrPresent(message = "Date posted must not be in future")
    LocalDate datePosted;
    @NotNull(message = "Post content cannot be blank")
    @Size(max = 255, min = 1, message =  "Post content must between 1 and 255 characters")
    String content;
    HashMap<FeedbackTag, Integer> feedbackTags = new HashMap<>();


    public Post( String parentPost, String article, String user, boolean isDissenting, LocalDate datePosted, String content) {
        this.parentPostId = parentPost;
        this.articleId = article;
        this.userId = user;
        this.isDissenting = isDissenting;
        this.datePosted = datePosted;
        this.content = content;
    }

    public Post() {

    }

    public void addFeedbackTagToPost(FeedbackTag feedbackTag){

        if(feedbackTags.get(feedbackTag)==null){
            feedbackTags.put(feedbackTag,1);
        } else{
            int value = feedbackTags.get(feedbackTag);
            feedbackTags.replace(feedbackTag,value+1);
        }

        setFeedbackTags(feedbackTags);
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDissenting() {
        return isDissenting;
    }

    public void setDissenting(boolean dissenting) {
        isDissenting = dissenting;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<FeedbackTag, Integer> getFeedbackTags() {
        return feedbackTags;
    }

    public void setFeedbackTags(HashMap<FeedbackTag, Integer> feedbackTags) {
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
