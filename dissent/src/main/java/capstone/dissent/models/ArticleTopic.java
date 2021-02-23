package capstone.dissent.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ArticleTopic {

    @NotBlank(message = "article Id cannot be blank")
    private String articleId;
    @Min(value = 1, message = "topic Id must be greater than 1")
    private int topicId;

    private Topic topic;

    public ArticleTopic(String articleId, int topicId) {
        this.articleId = articleId;
        this.topicId = topicId;
    }
    public ArticleTopic(){}

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}


