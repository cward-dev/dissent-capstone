package capstone.dissent.data;

import capstone.dissent.models.ArticleFeedbackTag;
import capstone.dissent.models.FeedbackTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ArticleFeedbackTagJdbcTemplateRepositoryTest {

    @Autowired
    ArticleFeedbackTagJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindArticleFeedbackTagsForArticleId() {
        List<ArticleFeedbackTag> articleFeedbackTags = repository.findByArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");
        assertTrue(articleFeedbackTags.size() > 0);
    }

    @Test
    void shouldAdd() {
        ArticleFeedbackTag articleFeedbackTag = makeArticleFeedbackTag();
        assertTrue(repository.add(articleFeedbackTag));

        try {
            repository.add(articleFeedbackTag); // must fail
            fail("cannot add the same article/user/feedback tag combination twice.");
        } catch (DataAccessException ex) {
            // this is expected
        }
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteByKey("c32bec11-b9a0-434b-bda7-08b9cf2007e2", "dffec086-b1e9-455a-aab4-ff6c6611fef0", 1));
        assertFalse(repository.deleteByKey("this-doesnt-exist", "dffec086-b1e9-455a-aab4-ff6c6611fef0", 1));
    }

    ArticleFeedbackTag makeArticleFeedbackTag() {
        ArticleFeedbackTag articleFeedbackTag = new ArticleFeedbackTag();
        articleFeedbackTag.setArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");
        articleFeedbackTag.setUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0");

        FeedbackTag feedbackTag = new FeedbackTag(2, "Sound", "#000000", true);

        articleFeedbackTag.setFeedbackTag(feedbackTag);

        return articleFeedbackTag;
    }

    @Test
    void shouldFindByKey(){
        ArticleFeedbackTag tag = repository.findByKey("a", "b", 1);

        assertNotNull(tag);
    }

    @Test
    void shouldNotFindByKey(){
        ArticleFeedbackTag tag = repository.findByKey("a","b",-1);
        assertNull(tag);
    }
}