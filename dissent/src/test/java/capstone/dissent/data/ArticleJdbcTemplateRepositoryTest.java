package capstone.dissent.data;

import capstone.dissent.models.Article;

import capstone.dissent.models.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ArticleJdbcTemplateRepositoryTest {

    @Autowired
    ArticleJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
       knownGoodState.set();
    }

    final LocalDateTime someday = LocalDateTime.of(2020,01,01,12,00,00);
    final LocalDateTime someday2 = LocalDateTime.of(2021,02,17,12,00,00);

    @Test
    void findAllArticles() {
        List<Article> all = repository.findAllArticles();
        assertNotNull(all);
        assertTrue(all.size()>1 && all.size()<=4);
        Article article = all.get(0);
        System.out.println(article.getArticleId() + article.getTitle() + article.getAuthor());
        System.out.println(article.getDatePosted());
    }

    @Test
    void shouldFindArticleByArticleId() {
       Article article = repository.findArticleByArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");
       assertNotNull(article);
       assertTrue(article.getAuthor().equalsIgnoreCase("Michael Douglas"));
       assertEquals(article.getFeedbackTags().size(), 1);

    }
    @Test
    void shouldNotFindArticleByArticleId() {
        Article article = repository.findArticleByArticleId("X");
        assertNull(article);
    }

//    @Test  // TODO maybe delete?
//    void shouldFindArticleByTopicId(){
//        List<Article> articles = repository.findArticleByTopicId(1);
//        assertTrue(articles.size()==1 || articles.size()==2);
//    }
//
//    @Test  // TODO maybe delete?
//    void shouldNOTFindArticleByTopicId(){
//        List<Article> articles = repository.findArticleByTopicId(99);
//        assertTrue(articles.size()==0);
//    }

    @Test
    void shouldFindByPostedDateRange(){
        List<Article> articles = repository.findByPostedDateRange(someday, someday2);
        for(Article article : articles){
            System.out.println(article);
        }
        assertTrue(articles.size()>=1 && articles.size()<=3);

    }
    @Test
    void shouldNotFindByPostedDateRange(){
        final LocalDateTime someday = LocalDateTime.of(1980,01,01,12,00,00);
        final LocalDateTime someday2 = LocalDateTime.of(1981,02,17,12,00,00);
        List<Article> articles = repository.findByPostedDateRange(someday, someday2);
        assertTrue(articles.size()==0);

    }

    @Test
    void shouldAddArticle(){
        Article article = new Article("Yolo","Young Idiots","d293ae18-63e0-49b7-87fd-9856bcf52884","Jesus",
                "www.u.com","www.u.com", someday,someday2);
        article.setArticleId("abc");

        Article addedArticle = repository.addArticle(article);

        assertEquals(addedArticle,article);
        assertTrue(addedArticle.getAuthor().equalsIgnoreCase("Jesus"));

    }

    @Test
    void shouldUpdateArticle(){
        Article article = new Article("Yolo","Young Idiots","d293ae18-63e0-49b7-87fd-9856bcf52884","Jesus",
                "www.u.com","www.u.com", someday,someday2);
        article.setArticleId("b");

        boolean isUpdated = repository.updateArticle(article);
        assertTrue(isUpdated);
        assertTrue(repository.findArticleByArticleId("b").getAuthor().equalsIgnoreCase("Jesus"));
    }

    @Test
    void shouldNotUpdateArticle(){
        Article article = new Article("Yolo","Young Idiots","d293ae18-63e0-49b7-87fd-9856bcf52884","Jesus",
                "www.u.com","www.u.com", someday,someday2);
        article.setArticleId("c");

        boolean isUpdated = repository.updateArticle(article);
        assertFalse(isUpdated);
    }

    @Test
    void shouldDeleteArticle(){
      Article article = new Article();
      article.setArticleId("b");
      article.setActive(true);

        boolean success = repository.inactivateArticle(article.getArticleId());
        assertTrue(success);
        assertEquals(repository.findArticleByArticleId("b").isActive(), false);

    }





}