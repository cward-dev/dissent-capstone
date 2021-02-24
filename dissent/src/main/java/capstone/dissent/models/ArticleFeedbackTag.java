package capstone.dissent.models;

import javax.validation.constraints.NotBlank;

public class ArticleFeedbackTag {

    @NotBlank(message = "Must have an article Id")
    private String articleId;

    @NotBlank(message = "Must have an user Id")
    private String userId;

    private FeedbackTag feedbackTag;

    public ArticleFeedbackTag(String articleId, String userId) {
        this.articleId = articleId;
        this.userId = userId;
    }

    public ArticleFeedbackTag(){}

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
