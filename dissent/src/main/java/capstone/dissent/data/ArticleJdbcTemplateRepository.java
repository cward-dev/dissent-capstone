package capstone.dissent.data;

import capstone.dissent.data.mappers.*;

import capstone.dissent.models.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class ArticleJdbcTemplateRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final PostRepository postRepository;

    public ArticleJdbcTemplateRepository(JdbcTemplate jdbcTemplate, PostRepository postRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRepository = postRepository;
    }

    @Override
    public List<Article> findAllArticles() {
        final String sql = "select a.article_id, a.title, a.`description`, a.author, a.article_url," +
                " a.article_image_url, a.date_published, a.date_posted, a.is_active," +
                " s.source_id, s.source_name, s.website_url, s.`description`" +
                " from article a" +
                " left outer join `source` s on a.source_id = s.source_id" +
                " where a.is_active = true;";

        List<Article> result = jdbcTemplate.query(sql, new ArticleMapper());

        if (result.size() > 0) {
            for(Article article : result) {
                addFeedbackTags(article);
                addTopics(article);
                addPosts(article);
                getArticleDiscussionLength(article);
            }
        }

        return result;
    }

    @Override
    public Article findArticleByArticleId(String articleId) {
        final String sql = "select a.article_id, a.title, a.`description`, a.author, a.article_url," +
                " a.article_image_url, a.date_published, a.date_posted, a.is_active," +
                " s.source_id, s.source_name, s.website_url, s.`description`" +
                " from article a" +
                " left outer join `source` s on a.source_id = s.source_id" +
                " where a.article_id = ?;";

        Article article = jdbcTemplate.query(sql, new ArticleMapper(), articleId).stream()
                .findFirst().orElse(null);


        if (article != null) {
            addFeedbackTags(article);
            addTopics(article);
            addPosts(article);
            getArticleDiscussionLength(article);
        }

        return article;
    }

    @Override
    public List<Article> findArticleByTopicId(int topicId) {
        final String sql = "select a.article_id, a.title, a.`description`, a.author, a.article_url," +
                " a.article_image_url, a.date_published, a.date_posted, a.is_active," +
                " s.source_id, s.source_name, s.website_url, s.`description`" +
                " from article a" +
                " left outer join `source` s on a.source_id = s.source_id" +
                " inner join article_topic ar on a.article_id = ar.article_id" +
                " where ar.topic_id = ? and a.is_active = true;";

        var articles = jdbcTemplate.query(sql, new ArticleMapper(), topicId);

        if (articles.size() > 0) {
            for(Article article : articles) {
                addFeedbackTags(article);
                addTopics(article);
                addPosts(article);
                getArticleDiscussionLength(article);
            }
        }

        return articles;
    }

    @Override
    public List<Article> findByPostedDateRange(LocalDateTime d1, LocalDateTime d2) {
        final String sql = "select a.article_id, a.title, a.`description`, a.author, a.article_url," +
                " a.article_image_url, a.date_published, a.date_posted, a.is_active," +
                " s.source_id, s.source_name, s.website_url, s.`description`" +
                " from article a" +
                " left outer join `source` s on a.source_id = s.source_id" +
                " where (a.date_posted between ? AND ?) and is_active = true;";

        List<Article> result = jdbcTemplate.query(sql, new ArticleMapper(), d1, d2);

        if (result.size() > 0) {
            for (Article article : result) {
                addFeedbackTags(article);
                addTopics(article);
                addPosts(article);
                getArticleDiscussionLength(article);
            }
        }

        return result;
    }

    @Override
    public Article addArticle(Article article){
        final String sql = "insert into article (article_id, source_id, title, author, `description`, article_url, "
        +" article_image_url, date_published, date_posted, is_active) "
        +" values(?,?,?,?,?,?,?,?,?,?);";

        article.setArticleId(java.util.UUID.randomUUID().toString());

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            ps.setString(1, article.getArticleId());
            ps.setString(2, article.getSource().getSourceId());
            ps.setString(3, article.getTitle());
            ps.setString(4,article.getAuthor());
            ps.setString(5,article.getDescription());
            ps.setString(6,article.getArticleUrl());
            ps.setString(7,article.getArticleImageUrl());
            ps.setTimestamp(8, Timestamp.valueOf(article.getDatePublished()));
            ps.setTimestamp(9,Timestamp.valueOf(article.getDatePosted()));
            ps.setBoolean(10,article.isActive());
            return ps;
        });
        if(rowsAffected <= 0){
            return null;
        }

       return article;
    }

    @Override
    public boolean updateArticle(Article article){
        final String sql = "update article set "
                +" title = ? , description = ? ,source_id = ? , author = ? , article_url = ?,"
                + " article_image_url = ?, date_published = ?, date_posted =?, is_active = ? "
                + " where article_id = ?;";
        return jdbcTemplate.update(sql,
                    article.getTitle(),
                    article.getDescription(),
                    article.getSource().getSourceId(),
                    article.getAuthor(),
                    article.getArticleUrl(),
                    article.getArticleImageUrl(),
                    article.getDatePublished(),
                    article.getDatePosted(),
                    article.isActive(),
                    article.getArticleId()) >0;
    }

    @Override
    public boolean inactivateArticle(String articleId){
        final String sql = "update article set "
                    + "is_active = ? "
                    + "where article_id = ?;";

        return jdbcTemplate.update(sql, false, articleId)>0;
    }

    private void addFeedbackTags(Article article) {

        final String sql = "select aft.article_id, aft.user_id, aft.feedback_tag_id, "
                + "ft.feedback_tag_id, ft.feedback_tag_name, ft.color_hex, ft.is_active "
                + "from article_feedback_tag aft "
                + "inner join feedback_tag ft on aft.feedback_tag_id = ft.feedback_tag_id "
                + "where aft.article_id = ?";

        var feedbackTags = jdbcTemplate.query(sql, new ArticleFeedbackTagMapper(), article.getArticleId());

        List<FeedbackTagHelper> list = new ArrayList<>();
        if (feedbackTags.size() > 0) {
            for (ArticleFeedbackTag i : feedbackTags) {
                if (list.size() == 0) {
                    list.add(new FeedbackTagHelper(i.getFeedbackTag().getName(), 1, i.getFeedbackTag().getColorHex()));
                    continue;
                }

                boolean found = false;

                for (FeedbackTagHelper feedbackTagHelper : list) {
                    if (feedbackTagHelper.getTitle().equalsIgnoreCase(i.getFeedbackTag().getName())) {
                        feedbackTagHelper.setValue(feedbackTagHelper.getValue() + 1);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    list.add(new FeedbackTagHelper(i.getFeedbackTag().getName(), 1, i.getFeedbackTag().getColorHex()));
                }
            }
        }

        article.setFeedbackTags(list);
    }

    private void addTopics(Article article) {

        final String sql = "select t.topic_id, t.topic_name, t.is_active " +
                " from topic t inner join article_topic ta on t.topic_id = ta.topic_id "
                + "where ta.article_id = ? and t.is_active = true;";
      
        var topics = jdbcTemplate.query(sql,new TopicMapper(),article.getArticleId());

        article.setTopics(topics);
    }

    private void addPosts(Article article) {

        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where p.article_id = ?;"; // fixed issue where only top-level post reply was being added to article.

        List<Post> posts = postRepository.findByArticleId(article.getArticleId());
        article.setPosts(posts);
    }


    private void getArticleDiscussionLength(Article article) {
        final String sql = "select p.post_id, p.parent_post_id, p.article_id, p.user_id, p.is_dissenting, p.date_posted, p.content, p.is_active, "
                + "u.username as username, u.email, u.password_hash, u.photo_url, u.country, u.bio, u.is_active "
                + "from post p "
                + "inner join `user` u on p.user_id = u.user_id "
                + "where p.article_id = ?;";

        List<Post> posts = postRepository.findByArticleId(article.getArticleId());

        article.setDiscussionLength(postCounterHelper(posts, 0));
    }

    private int postCounterHelper(List<Post> posts, int counter) {
        for (Post p : posts) {
            if (p.isActive()) {
                counter++;
                counter = postCounterHelper(p.getChildPosts(), counter);
            }
        }

        return counter;
    }
}
