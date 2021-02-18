package capstone.dissent.domain;

import capstone.dissent.data.ArticleRepository;
import capstone.dissent.models.Article;
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

    @MockBean
    ArticleRepository repository;


    final LocalDateTime DAY1 = LocalDateTime.of(2020,01,01,12,00,00);
    final LocalDateTime DAY2 = LocalDateTime.of(2021,02,17,12,00,00);
    final Article TEST_ARTICLE = new Article("Yolo","Young Idiots","d293ae18-63e0-49b7-87fd-9856bcf52884","Jesus",
            "www.u.com","www.u.com", DAY1,DAY2);

    @Test
    void findAll() {
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
        Article articleIn =  TEST_ARTICLE;
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
        Article articleIn =  TEST_ARTICLE;
        articleIn.setArticleId("a");
        articles.add(articleIn);

        when(repository.findArticleByArticleId("a")).thenReturn(articleIn);
        Article articleOut = service.findById("x");
        assertNull(articleOut);

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

        Article articleIn =  TEST_ARTICLE;
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
        Article articleIn = TEST_ARTICLE;
        Article articleOut = new Article();

        when(repository.addArticle(articleIn)).thenReturn(articleOut);
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
        Article article = TEST_ARTICLE;
        article.setArticleId("a");

        Result<Article> result = service.add(article);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Id cannot be set for `add` operation"));

    }

    @Test
    void shouldNotAddDuplicate(){
        Article article = TEST_ARTICLE;
        List<Article> articles = List.of(article);

        when(service.findAll()).thenReturn(articles);

        Result<Article> result = service.add(TEST_ARTICLE);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().contains("Duplicate Articles are not allowed"));

    }

    @Test
    void shouldUpdate() {
        Article articleIn = TEST_ARTICLE;
        articleIn.setArticleId("a");

        when(repository.updateArticle(articleIn)).thenReturn(true);
        when(repository.findArticleByArticleId(articleIn.getArticleId())).thenReturn(articleIn);

        Result<Article> result = service.update(articleIn);
        assertEquals(ResultType.SUCCESS, result.getType());

    }

    @Test
    void shouldNotUpdateWithNullId(){
        Article article = TEST_ARTICLE;

        Result<Article> result = service.update(article);
        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Must have a valid Id to update Article!"));

    }

    @Test
    void shouldNotUpdateWithDifferentUrl(){
        Article original = TEST_ARTICLE;
        original.setArticleId("a");
        Article article = new Article("Yolo","Young Idiots","d293ae18-63e0-49b7-87fd-9856bcf52884","Jesus",
                "XXXXXX","www.u.com", DAY1,DAY2);

        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(original);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.INVALID, result.getType());
        assertTrue(result.getMessages().contains("Cannot update Article URL"));

    }

    @Test
    void shouldNotUpdateArticleThatDoesNotExist(){
        Article article = TEST_ARTICLE;
        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(TEST_ARTICLE);
        when(repository.updateArticle(article)).thenReturn(false);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.NOT_FOUND, result.getType());

    }
    @Test
    void shouldNotUpdateArticleWhenYouCannotFindOriginalArticle(){
        Article article = TEST_ARTICLE;
        article.setArticleId("a");

        when(repository.findArticleByArticleId(article.getArticleId())).thenReturn(null);
        Result<Article> result = service.update(article);

        assertEquals(ResultType.NOT_FOUND, result.getType());
    }



    @Test
    void inactivateArticle() {
        Article articleIn = TEST_ARTICLE;
        articleIn.setArticleId("a");

        Article articleOut = TEST_ARTICLE;

        when(repository.inactivateArticle("a")).thenReturn(true);

        boolean result = service.inactivateArticle("a");

        assertTrue(result);
    }

    @Test
    void shouldNotInactiveBogusArticle(){
        boolean result = service.inactivateArticle("XXXXX");

        assertFalse(result);
    }

}