package capstone.dissent.domain;

import capstone.dissent.data.*;
import capstone.dissent.models.*;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ArticleServiceTest {

    @Autowired
    ArticleService service;

    @Autowired
    SourceService sourceService;

    @MockBean
    ArticleFeedbackTagRepository articleFeedbackTagRepository;

    @MockBean
    ArticleTopicRepository articleTopicRepository;

    @MockBean
    ArticleRepository repository;

    @MockBean
    SourceRepository sourceRepository;

    final LocalDateTime DAY1 = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
    final LocalDateTime DAY2 = LocalDateTime.of(2021, 2, 17, 12, 0, 0);
    final Source TEST_SOURCE = new Source(
            "fsdafas8-fsad-fsd8-fsda-413h1hj1a90s",
            "CNN",
            "https://www.cnn.com/",
            "Cable News Network is a multinational news-based pay television channel headquartered in Atlanta.");

    @Test
    void shouldFindAll() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article());

        when(repository.findAllArticles()).thenReturn(articles);

        List<Article> results = service.findAll();

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void shouldFindById() {
        List<Article> articles = new ArrayList<>();
        Article articleIn = makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findArticleByArticleId("a")).thenReturn(articleIn);

        Article articleOut = service.findById("a");
        assertEquals(articleIn, articleOut);
        assertEquals(articleIn.getAuthor(), articleOut.getAuthor());

    }

    @Test
    void shouldNotFindById() {
        List<Article> articles = new ArrayList<>();
        Article articleIn = makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findArticleByArticleId("a")).thenReturn(articleIn);
        Article articleOut = service.findById("x");
        assertNull(articleOut);

    }


    @Test
    void shouldFindArticleByTopicId() {
        when(repository.findArticleByTopicId(1)).thenReturn(List.of(makeArticle()));
        List<Article> articles = service.findArticleByTopicId(1);

        assertEquals("Yolo", articles.get(0).getTitle());

    }

    @Test
    void shouldFindByArticleFeedbackTagByKey() {
        ArticleFeedbackTag articleFeedbackTagIn = new ArticleFeedbackTag("abc", "1");
        when(articleFeedbackTagRepository.findByKey("abc", "1", 1)).thenReturn(articleFeedbackTagIn);
        ArticleFeedbackTag articleFeedbackTagActual = service.findArticleFeedbackTagByKey("abc", "1", 1);

        assertEquals(articleFeedbackTagActual, articleFeedbackTagIn);
    }

    @Test
    void shouldNOTFindByArticleFeedbackTagByKey() {
        ArticleFeedbackTag articleFeedbackTagIn = new ArticleFeedbackTag("abc", "999999");
        when(articleFeedbackTagRepository.findByKey("abc", "1", 1)).thenReturn(null);
        ArticleFeedbackTag articleFeedbackTagActual = service.findArticleFeedbackTagByKey("abc", "1", 1);

        assertNull(articleFeedbackTagActual);
    }

    @Test
    void shouldFindArticleFeedbackTagsByArticleId() {

        FeedbackTagHelper tagHelper = new FeedbackTagHelper("Biased", 1, "#0FFF0");
        when(articleFeedbackTagRepository.findByArticleId("1")).thenReturn(List.of(tagHelper));
        List<FeedbackTagHelper> feedbackTagHelpers = service.findArticleFeedbackTagsByArticleId("1");

        assertEquals(1, feedbackTagHelpers.size());
    }

    @Test
    void shouldNotFindArticleFeedaclTagsByArticleId() {
        List<FeedbackTagHelper> feedbackTagHelpers = new ArrayList<>();
        when(articleFeedbackTagRepository.findByArticleId("1")).thenReturn(feedbackTagHelpers);
        List<FeedbackTagHelper> feedbackTagList = service.findArticleFeedbackTagsByArticleId("1");

        assertEquals(0, feedbackTagList.size());

    }


    @Test
    void shouldNotFindArticleByTopicId() {
        List<Article> articlesOut = new ArrayList<>();
        when(repository.findArticleByTopicId(1)).thenReturn(articlesOut);
        List<Article> articles = service.findArticleByTopicId(1);

        assertEquals(0, articles.size());
    }

    @Test
    void shouldNotBeAbleToAddWithInvalidSource() {
        Article article = makeArticle();

        when(sourceService.findBySourceNameAndUrl("a", "b")).thenReturn(null);
        Result<Article> result = service.add(article);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Unable to attach source to article."));
    }

    @Test
    void shouldNotUpdateInvalidArticle() {
        Article article = makeArticle();
        article.setArticleUrl(null);

        Result<Article> result = service.update(article);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Article Url cannot be blank"));
    }

    @Test
    void shouldNotAddInvalidArticleTopic() {
        ArticleTopic articleTopic = new ArticleTopic("1", 1);

        when(articleTopicRepository.add(articleTopic)).thenReturn(false);
        Result<Void> result = service.addArticleTopic(articleTopic);

        assertFalse(result.isSuccess());
        System.out.println(result.getMessages());
        assertTrue(result.getMessages().contains("Article topic not found"));

    }

    @Test
    void shouldNotAddNullArticleTopic() {
        ArticleTopic articleTopic = new ArticleTopic();
        when(articleTopicRepository.add(articleTopic)).thenReturn(false);
        Result<Void> result = service.addArticleTopic(articleTopic);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("topicId and articleId cannot be blank or missing!"));
    }

    @Test
    void shouldNotDeleteInvalidArticleTopic() {
        ArticleTopic articleTopic = new ArticleTopic();
        when(articleTopicRepository.deleteByKey("1", 2)).thenReturn(false);
        Result<Void> result = service.deleteArticleTopicByKey("1", 2);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Article topic not found"));
    }

    @Test
    void shouldNotAddInvalidArticleFeedBackTag() {
        ArticleFeedbackTag articleFeedbackTag = new ArticleFeedbackTag();
        Result<Void> result = service.addFeedbackTag(articleFeedbackTag);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Feedback Tag cannot be null"));
    }

    @Test
    void shouldNotAddArticleFeedbackTagFeedBackDueToRepoError() {
        FeedbackTag feedbackTag = new FeedbackTag("xx","xx");
        ArticleFeedbackTag tag = new ArticleFeedbackTag("2", "2");
        tag.setFeedbackTag(feedbackTag);

        when(articleFeedbackTagRepository.add(tag)).thenReturn(false);
        Result<Void> result = service.addFeedbackTag(tag);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Article feedback tag not added"));
    }


    @Test
    void shouldFindByPostedDateRange() {
        final LocalDateTime DAY3 = LocalDateTime.of(2019, 01, 01, 12, 00, 00);
        final LocalDateTime DAY4 = LocalDateTime.of(2021, 02, 17, 17, 00, 00);
        List<Article> articles = new ArrayList<>();

        Article articleIn = makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findByPostedDateRange(DAY3, DAY4)).thenReturn(articles);

        List<Article> articlesByDate = service.findByPostedDateRange(DAY3, DAY4);

        assertEquals(articles, articlesByDate);
    }

    @Test
    void shouldNotFindByPostedDateRange() {
        List<Article> articles = service.findByPostedDateRange(DAY1, DAY2);
        assertEquals(articles.size(), 0);
    }

    @Test
    void shouldAdd() {
        Article articleIn = makeArticle();
        Article articleOut = makeArticle();
        articleOut.setArticleId("test-id");

        when(repository.addArticle(articleIn)).thenReturn(articleOut);
        when(sourceRepository.findBySourceNameAndUrl("CNN", "https://www.cnn.com/")).thenReturn(TEST_SOURCE);
        Result<Article> result = service.add(articleIn);

        assertTrue(result.isSuccess()); // missing source in test-database
        assertNotNull(result.getPayload());
        assertEquals(articleOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<Article> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Article cannot be blank"));

    }

    @Test
    void shouldNotAddWithID() {
        Article article = makeArticle();
        article.setArticleId("a");

        Result<Article> result = service.add(article);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Id cannot be set for `add` operation"));

    }

    @Test
    void shouldNotAddDuplicate() {
        Article article = makeArticle();
        List<Article> articles = List.of(article);

        when(repository.findAllArticles()).thenReturn(articles);
        when(sourceRepository.findBySourceNameAndUrl("CNN", "https://www.cnn.com/")).thenReturn(TEST_SOURCE);

        Result<Article> result = service.add(makeArticle());

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Duplicate Articles are not allowed"));

    }

    @Test
    void shouldUpdate() {
        Article articleIn = makeArticle();
        articleIn.setArticleId("a");

        when(repository.updateArticle(articleIn)).thenReturn(true);
        when(repository.findArticleByArticleId(articleIn.getArticleId())).thenReturn(articleIn);

        Result<Article> result = service.update(articleIn);
        assertEquals(ResultType.SUCCESS, result.getType());

    }

    @Test
    void shouldNotUpdateWithNullId() {
        Article article = makeArticle();

        Result<Article> result = service.update(article);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Must have a valid Id to update Article!"));

    }

    @Test
    void shouldNotUpdateWithDifferentUrl() {
        Article original = makeArticle();
        original.setArticleId("a");
        Article article = new Article("Yolo", "Young Idiots", "Jesus",
                "XXXXXX", "www.u.com", DAY1, DAY2, TEST_SOURCE);

        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(original);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Cannot update Article URL"));

    }

    @Test
    void shouldNotUpdateArticleThatDoesNotExist() {
        Article article = makeArticle();
        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(makeArticle());
        when(repository.updateArticle(article)).thenReturn(false);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.NOT_FOUND, result.getType());

    }

    @Test
    void shouldNotUpdateArticleWhenYouCannotFindOriginalArticle() {

        Article article = makeArticle();
        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(null);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.NOT_FOUND, result.getType());
    }


    @Test
    void inactivateArticle() {
        Article articleIn = makeArticle();
        articleIn.setArticleId("a");

        when(repository.inactivateArticle("a")).thenReturn(true);

        boolean result = service.inactivateArticle("a");

        assertTrue(result);
    }

    @Test
    void shouldNotInactiveBogusArticle() {
        boolean result = service.inactivateArticle("XXXXX");

        assertFalse(result);
    }

    @Test
    void shouldNotAddArticleWithFuturePostDate() {
        Article article = makeArticle();
        article.setDatePosted(LocalDateTime.of(2999, 01, 01, 12, 00, 00));

        Result<Article> result = service.add(article);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        System.out.println(result.getMessages().get(0));
        assertTrue(result.getMessages().contains("Article cannot have a future post date"));
    }

    Article makeArticle() {
        Article article = new Article();
        article.setTitle("Yolo");
        article.setDescription("Young Idiots");
        article.setAuthor("Jesus");
        article.setArticleUrl("www.u.com");
        article.setArticleImageUrl("www.u.com");
        article.setDatePublished(DAY1);
        article.setDatePosted(DAY2);
        article.setSource(TEST_SOURCE);

        return article;
    }
}