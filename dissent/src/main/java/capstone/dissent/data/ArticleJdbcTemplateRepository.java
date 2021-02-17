package capstone.dissent.data;

import capstone.dissent.data.mappers.ArticleMapper;
import capstone.dissent.data.mappers.PostMapper;
import capstone.dissent.models.Article;
import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.Post;
import capstone.dissent.models.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public class ArticleJdbcTemplateRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Article> findAllArticles() {
        final String sql = "select article_id, title, description,source_id, author, article_url, " +
                " article_image_url, date_published, date_posted from article;";

        return jdbcTemplate.query(sql, new ArticleMapper());
    }

    @Override
    public Article findArticleByArticleId(String articleId) {
        final String sql = "select article_id, title, description, source_id, author, article_url, " +
                " article_image_url, date_published, date_posted from article " +
                " where article_id =  ?;";

        Article article = jdbcTemplate.query(sql, new ArticleMapper(), articleId).stream()
                .findFirst().orElse(null);


        if (article != null) {
            addTopics(article);
            addPosts(article);
        }

        return article;
    }

    public List<Article> findArticleByTopicId(int topicId) {
        final String sql = "select a.article_id, a.title,a.description, a.source_id, a.author, a.article_url, " +
                " a.article_image_url, a.date_published, a.date_posted from article  a inner join article_topic ar on a.article_id = ar.article_id"
                + " where ar.topic_id = ?;";
        var articles = jdbcTemplate.query(sql, new ArticleMapper(), topicId);

        return articles;
    }

    public List<Article> findByPostedDateRange(LocalDateTime d1, LocalDateTime d2) {
        final String sql = "select * from article where date_posted between ? AND ? ";
        return jdbcTemplate.query(sql, new ArticleMapper(), d1, d2);
    }

    public Article addArticle(Article article){
        final String sql = "insert into article (article_id, source_id, title, author, `description`, article_url, "
        +" article_image_url, date_published, date_posted) "
        +" values(?,?,?,?,?,?,?,?,?);";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            ps.setString(1, article.getArticleId());
            ps.setString(2, article.getSource_id());
            ps.setString(3, article.getTitle());
            ps.setString(4,article.getAuthor());
            ps.setString(5,article.getDescription());
            ps.setString(6,article.getArticleUrl());
            ps.setString(7,article.getArticleImageUrl());
            ps.setTimestamp(8, Timestamp.valueOf(article.getDatePublished()));
            ps.setTimestamp(9,Timestamp.valueOf(article.getDatePosted()));
            // TODO: 2/17/2021  add isActiveBoolean from DB 
            return ps;
        });
        if(rowsAffected <= 0){
            return null;
        }

       return article;

    }

    public boolean updateArticle(Article article){
        String sql = "update article set "
                +"title = ? , description = ? ,source_id = ? , author = ? , article_url = ?,"
                + "article_image_url = ?, date_published = ?, date_posted =? "
                + " where article_id = ?;";
        return jdbcTemplate.update(sql,
                    article.getTitle(),
                    article.getDescription(),
                    article.getSource_id(),
                    article.getAuthor(),
                    article.getArticleUrl(),
                    article.getArticleImageUrl(),
                    article.getDatePublished(),
                    article.getDatePosted(),
                    article.getArticleId()) >0;

    }

    public boolean inActivateArticle(String articleId){
        final String sql = "update article set "
                    + "is_active = ? "
                    + "where article_id = ?;";

        return jdbcTemplate.update(sql, false, articleId)>0;
    }



    public HashMap<FeedbackTag, Integer> getTagData(Article article) {
        final String sql = "select ft.feedback_tag_id, ft.name" +
                " from article_feedback_tag aft inner join feedback_tag ft on  aft.feedback_tag_id = ft.feedback_tag_id"
                + " where aft.article_id = ?;";

//           var allFeedBackTags = jdbcTemplate(sql,new FeedBackTagMapper(),article.getArticleId());
//
//           for(FeedbackTag tag : allFeedBackTags){
//               article.addFeedbackTagToArticle(tag);
//           }
        return null;

    }

    private void addTopics(Article article) {
        final String sql = "select t.topic_id, t.topic_name" +
                "from topic t inner join article_topic ta on t.topic_id = ta.topic_id "
                + "where ta.article_id = ?;";
//        var topics = jdbcTemplate.query(sql,new TopicMapper(),article.getArticleId());

//        article.setTopics(topics);
    }

    private void addPosts(Article article) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id,user_id,p.is_dissenting,p.date_posted,p.content "
                + "from post p inner join article a where p.article_id= ?;";

        var posts = jdbcTemplate.query(sql, new PostMapper(), article.getArticleId());
        article.setPosts(posts);
    }


}
