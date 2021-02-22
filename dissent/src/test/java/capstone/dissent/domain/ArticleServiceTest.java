package capstone.dissent.domain;

import capstone.dissent.data.ArticleRepository;
import capstone.dissent.data.SourceRepository;
import capstone.dissent.models.Article;
import capstone.dissent.models.Source;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ArticleServiceTest {

    @Autowired
    ArticleService service;

    @Autowired
    SourceService sourceService;

    @MockBean
    ArticleRepository repository;

    @MockBean
    SourceRepository sourceRepository;

    final LocalDateTime DAY1 = LocalDateTime.of(2020,1,1,12,0,0);
    final LocalDateTime DAY2 = LocalDateTime.of(2021,2,17,12,0,0);
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
        assertEquals(1,results.size());
    }

    @Test
    void shouldFindById() {
        List<Article> articles = new ArrayList<>();
        Article articleIn =  makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findArticleByArticleId("a")).thenReturn(articleIn);

        Article articleOut = service.findById("a");
        assertEquals(articleIn,articleOut);
        assertEquals(articleIn.getAuthor(),articleOut.getAuthor());

    }

    @Test
    void shouldNotFindById(){
        List<Article> articles = new ArrayList<>();
        Article articleIn =  makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findArticleByArticleId("a")).thenReturn(articleIn);
        Article articleOut = service.findById("x");
        assertNull(articleOut);

    }
    @Test
    void test(){
        LocalDateTime test = LocalDateTime.parse("2021-01-01T12:00");
        System.out.println(test);
//        System.out.println(DAY1);

    }

//    @Test  // TODO maybe delete?
//    void findArticleByTopicId() {
//        // TODO: 2/17/2021 How do I test this with MockBean??
//
//    }

    @Test
    void shouldFindByPostedDateRange() {
        final LocalDateTime DAY3 = LocalDateTime.of(2019,01,01,12,00,00);
        final LocalDateTime DAY4 = LocalDateTime.of(2021,02,17,17,00,00);
        List<Article> articles = new ArrayList<>();

        Article articleIn = makeArticle();
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findByPostedDateRange(DAY3,DAY4)).thenReturn(articles);

        List<Article>articlesByDate = service.findByPostedDateRange(DAY3,DAY4);

        assertEquals(articles,articlesByDate);
    }

    @Test
    void shouldNotFindByPostedDateRange() {
      List<Article> articles = service.findByPostedDateRange(DAY1,DAY2);
      assertEquals(articles.size(),0);
    }

    @Test
    void shouldAdd() {
        Article articleIn = makeArticle();
        Article articleOut = makeArticle();
        articleOut.setArticleId("test-id");

        when(repository.addArticle(articleIn)).thenReturn(articleOut);
        when(sourceRepository.findBySourceNameAndUrl("CNN", "https://www.cnn.com/")).thenReturn(TEST_SOURCE);
        Result<Article> result = service.add(articleIn);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(articleOut,result.getPayload());
    }

    @Test
    void shouldNotAddNull(){
        Result<Article> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Article cannot be blank"));

    }
    @Test
    void shouldNotAddWithID(){
        Article article = makeArticle();
        article.setArticleId("a");

        Result<Article> result = service.add(article);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Id cannot be set for `add` operation"));

    }

    @Test
    void shouldNotAddDuplicate(){
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
    void shouldNotUpdateWithNullId(){
        Article article = makeArticle();

        Result<Article> result = service.update(article);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Must have a valid Id to update Article!"));

    }

    @Test
    void shouldNotUpdateWithDifferentUrl(){
        Article original = makeArticle();
        original.setArticleId("a");
        Article article = new Article("Yolo","Young Idiots","Jesus",
                "XXXXXX","www.u.com", DAY1, DAY2, TEST_SOURCE);

        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(original);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Cannot update Article URL"));

    }

    @Test
    void shouldNotUpdateArticleThatDoesNotExist(){
        Article article = makeArticle();
        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(makeArticle());
        when(repository.updateArticle(article)).thenReturn(false);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.NOT_FOUND, result.getType());

    }
    @Test
    void shouldNotUpdateArticleWhenYouCannotFindOriginalArticle(){

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
    void shouldNotInactiveBogusArticle(){
        boolean result = service.inactivateArticle("XXXXX");

        assertFalse(result);
    }

    @Test
    void shouldNotAddArticleWithFuturePostDate(){
        Article article = makeArticle();
        article.setDatePosted(LocalDateTime.of(2999,01,01,12,00,00));

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