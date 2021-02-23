package capstone.dissent.data;

import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTagHelper;
import capstone.dissent.models.PostFeedbackTag;

import java.util.List;

public interface ArticleFeedbackTagRepository {

    List<FeedbackTagHelper> findByArticleId(String articleId);

    ArticleFeedbackTag findByKey(String articleId, String userId, int feedbackTagId);

    boolean add(ArticleFeedbackTag articleFeedbackTag);

    boolean deleteByKey(String articleId, String userId, int feedbackTagId);
}
