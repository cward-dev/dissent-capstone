package capstone.dissent.models;

public class ArticleFeedbackTag {

    private String articleId;
    private String userId;

    private FeedbackTag feedbackTag;

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

    public FeedbackTag getFeedbackTag() {
        return feedbackTag;
    }

    public void setFeedbackTag(FeedbackTag feedbackTag) {
        this.feedbackTag = feedbackTag;
    }
}
