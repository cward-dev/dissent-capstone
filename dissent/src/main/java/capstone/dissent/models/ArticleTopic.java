package capstone.dissent.models;

public class ArticleTopic {

    private String articleId;
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


