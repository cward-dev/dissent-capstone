package capstone.dissent.data;

import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.PostFeedbackTag;

import java.util.List;

public interface ArticleFeedbackTagRepository {

    List<ArticleFeedbackTag> findByArticleId(String articleId);

    boolean add(ArticleFeedbackTag articleFeedbackTag);

    boolean deleteByKey(String articleId, String userId, int feedbackTagId);
}
