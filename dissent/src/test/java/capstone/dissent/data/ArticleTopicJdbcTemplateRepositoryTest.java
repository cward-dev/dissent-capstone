package capstone.dissent.data;

import capstone.dissent.models.ArticleTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ArticleTopicJdbcTemplateRepositoryTest {

    @Autowired
    ArticleTopicJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldAdd() {
        ArticleTopic articleTopic = new ArticleTopic("a", 2);
        boolean success = repository.add(articleTopic);
        assertTrue(success);

    }

    @Test
    void shouldNotAdd() {
        ArticleTopic articleTopic = new ArticleTopic("zzz", 99);
        boolean success = false;
        try {
            repository.add(articleTopic);
            success = true;
        } catch (Exception ex) {
            //
        }
        assertFalse(success);
    }

    @Test
    void shouldDeleteByKey() {
        boolean success = repository.deleteByKey("b", 3);
        assertTrue(success);

    }

    @Test
    void shouldNotDeleteByKey() {
        boolean success = repository.deleteByKey("abc", 123);
    }
}


