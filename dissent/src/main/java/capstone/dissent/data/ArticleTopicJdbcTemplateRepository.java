package capstone.dissent.data;

import capstone.dissent.models.ArticleTopic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleTopicJdbcTemplateRepository implements ArticleTopicRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleTopicJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(ArticleTopic articleTopic){
        final String sql = "insert into article_topic (topic_id , article_id) values "
                        + "(?, ?);";
        return jdbcTemplate.update(sql,
                articleTopic.getTopicId(),
                articleTopic.getArticleId()) >0;
    }

    @Override
    public boolean deleteByKey(String articleId, int topicId){

        final String sql = "delete from article_topic "
                    + "where article_id = ? and topic_id = ?;";
        return jdbcTemplate.update(sql,articleId,topicId)>0;
    }


}
