package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    String topicId;
    String topicName;
    List<Article> articles = new ArrayList<>();

    public Topic(String topicId, String topicName, List<Article> articles) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.articles = articles;
    }

    public Topic(){

    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
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
