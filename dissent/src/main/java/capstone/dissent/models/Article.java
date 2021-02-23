package capstone.dissent.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

public class Article {

    private final int MAX_CHARACTERS = 255;


    String articleId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Title must be at least 1 character and cannot exceed 255")
    String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Description must be at least 1 character and cannot exceed 255")
    String description;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Author must be at least 1 character and cannot exceed 255")
    String author;

    @NotBlank(message = "Article Url cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Article Url must be at least 1 character and cannot exceed 255")
    String articleUrl;
    String articleImageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Article must have published date")
    @PastOrPresent(message = "Article cannot have a future published date")
    LocalDateTime datePublished;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Article cannot have a future post date")
    LocalDateTime datePosted;

    @Min(value = 0, message = "Discussion length cannot be less than zero.")
    int discussionLength;

    boolean isActive = true;

    Source source;
    List<Topic> topics = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    List<FeedbackTagHelper> feedbackTags = new ArrayList<>();

    public Article(String title, String description, String author,
                   String articleUrl, String articleImageUrl,
                   LocalDateTime datePublished, LocalDateTime datePosted,
                   Source source) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.articleUrl = articleUrl;
        this.articleImageUrl = articleImageUrl;
        this.datePublished = datePublished;
        this.datePosted = datePosted;
        this.source = source;
    }

    public Article() {}

//    public void addFeedbackTagToArticle(String feedbackTag){
//        if(feedbackTags.get(feedbackTag)==null){
//            feedbackTags.put(feedbackTag,1);
//        } else {
//            int value = feedbackTags.get(feedbackTag).intValue();
//            feedbackTags.replace(feedbackTag,value+1);
//        }
//
//        setFeedbackTags(feedbackTags);
//    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setArticleImageUrl(String articleImageUrl) {
        this.articleImageUrl = articleImageUrl;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDateTime datePublished) {
        this.datePublished = datePublished;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDiscussionLength() {
        return discussionLength;
    }

    public void setDiscussionLength(int discussionLength) {
        this.discussionLength = discussionLength;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getArticleId(), article.getArticleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId());
    }
}
