package capstone.dissent.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Article {

    private final int MAX_CHARACTERS = 255;


    String articleId;
    @NotNull(message = "Title cannot be blank!")
    @Size(max = MAX_CHARACTERS, min = 1, message = "Title must be at least 1 character and cannot exceed 255")
    String title;
    @NotNull(message = "Description cannot be blank!")
    @Size(max = MAX_CHARACTERS, min =1, message = "Description must be at least 1 character and cannot exceed 255")
    String description;
    @NotNull(message = "Source cannot be blank!")
    @Size(max = MAX_CHARACTERS, min =1, message = "Source must be at least 1 character and cannot exceed 255")
    String source_id;
    @NotNull(message = "Author cannot be blank!")
    @Size(max = MAX_CHARACTERS, min = 1, message = "Author must be at least 1 character and cannot exceed 255")
    String author;
    @NotNull(message = "Article Url cannot be blank")
    @Size(max = MAX_CHARACTERS, min = 1, message = "Article Url must be at least 1 character and cannot exceed 255")
    String articleUrl;
    String articleImageUrl;
    @NotNull(message = "Article must have published date!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Article cannot have a future published date")
    LocalDate datePublished;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Article cannot have a future post date")
    LocalDate datePosted;
    List<Topic> topics = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    HashMap<FeedbackTag, Integer> feedbackTags = new HashMap<>();

    public Article( String title, String description, String source, String author,
                   String articleUrl,String articleImageUrl, LocalDate datePublished, LocalDate datePosted) {
        this.title = title;
        this.description = description;
        this.source_id = source;
        this.author = author;
        this.articleUrl = articleUrl;
        this.articleImageUrl = articleImageUrl;
        this.datePublished = datePublished;
        this.datePosted = datePosted;
    }

    public Article(){}

    public void addFeedbackTagToArticle(FeedbackTag feedbackTag){

        if(feedbackTags.get(feedbackTag)==null){
            feedbackTags.put(feedbackTag,1);
        } else{
            int value = feedbackTags.get(feedbackTag).intValue();
            feedbackTags.replace(feedbackTag,value+1);
        }

        setFeedbackTags(feedbackTags);
    }

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

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
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

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished = datePublished;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
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
    public HashMap<FeedbackTag, Integer> getFeedbackTags() {
        return feedbackTags;
    }

    public void setFeedbackTags(HashMap<FeedbackTag, Integer> feedbackTags) {
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
