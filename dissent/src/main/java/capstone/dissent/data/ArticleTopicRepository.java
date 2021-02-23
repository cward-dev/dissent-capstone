package capstone.dissent.data;

import capstone.dissent.models.ArticleTopic;

public interface ArticleTopicRepository {

    boolean add(ArticleTopic articleTopic);

    boolean deleteByKey(String articleId, int topicId);
}
