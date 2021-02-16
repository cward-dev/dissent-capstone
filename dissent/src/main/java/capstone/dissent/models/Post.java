package capstone.dissent.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Post {

    String postId;
    Post parentPost;
    @NotNull(message = "post must belong to an article")
    Article article;
    @NotNull(message = "post must have a user")
    User user;
    @NotNull(message = "post must have a stance [accepting / dissenting]")
    boolean isDissenting;
    @PastOrPresent(message = "Date Posted must not be in future")
    LocalDate datePosted;
    @NotNull(message = "Post content cannot be blank")
    @Size(max = 255, min = 1, message =  "Post content must between 1 and 255 characters")
    String content;
    HashMap<FeedbackTag, Integer> feedbackTags = new HashMap<>();


    public Post( Post parentPost, Article article, User user, boolean isDissenting, LocalDate datePosted, String content) {
        this.parentPost = parentPost;
        this.article = article;
        this.user = user;
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

    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
