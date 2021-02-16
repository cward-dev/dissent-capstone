package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    private final int MAX_CHARACTERS = 255;

    int topicId;
    @NotNull(message = "Topic name cannot be blank!")
    @Size(max = MAX_CHARACTERS, min =1, message = "Name must be between 1 and 255 characters")
    String topicName;
    List<Article> articles = new ArrayList<>();

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Topic(){

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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


}
