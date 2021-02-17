package capstone.dissent.data;

import capstone.dissent.models.Article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ArticleJdbcTemplateRepositoryTest {

    @Autowired
    ArticleJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
//    void setup(){
//       knownGoodState.set();
//    }

    @Test
    void findAllArticles() {
        List<Article> all = repository.findAllArticles();
        assertNotNull(all);
        assertTrue(all.size()==1);

        Article article = all.get(0);
        System.out.println(article.getArticleId() + article.getTitle() + article.getAuthor());
        System.out.println(article.getDatePosted());
    }

    @Test
    void findArticleByArticleId() {
    }
}