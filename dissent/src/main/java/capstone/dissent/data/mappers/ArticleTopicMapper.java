package capstone.dissent.data.mappers;

import capstone.dissent.models.ArticleTopic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleTopicMapper implements RowMapper {


    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        ArticleTopic articleTopic = new ArticleTopic();
        articleTopic.setArticleId(resultSet.getString("article_id"));
        articleTopic.setTopicId(resultSet.getInt("topic_id"));

        TopicMapper topicMapper = new TopicMapper();
        articleTopic.setTopic(topicMapper.mapRow(resultSet,i));

        return articleTopic;
    }


}
