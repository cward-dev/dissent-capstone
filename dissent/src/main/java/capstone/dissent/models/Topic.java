package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Topic {

    int topicId;

    private final int MAX_CHARACTERS = 255;
    @NotBlank(message = "Topic name cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Topic name must be between 1 and 255 characters")
    String topicName;

    boolean isActive;

    List<Article> articles = new ArrayList<>();

    public Topic(){
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Topic(int topicId, String topicName, boolean isActive) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.isActive = isActive;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


}
