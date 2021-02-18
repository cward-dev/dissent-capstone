package capstone.dissent.models;

public class PostFeedbackTag {

    private String postId;
    private String userId;

    private FeedbackTag feedbackTag;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
