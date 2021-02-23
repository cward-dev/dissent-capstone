package capstone.dissent.data;

import capstone.dissent.models.FeedbackTagHelper;
import capstone.dissent.models.PostFeedbackTag;

import java.util.List;

public interface PostFeedbackTagRepository {

    List<FeedbackTagHelper> findByPostId(String postId);

    List<FeedbackTagHelper> findByUserId(String userId);

    PostFeedbackTag findByKey(String postId, String userId, int feedbackTagId);

    boolean add(PostFeedbackTag postFeedbackTag);

    boolean deleteByKey(String postId, String userId, int feedbackTagId);
}
