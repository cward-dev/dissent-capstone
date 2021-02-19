package capstone.dissent.data;

import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.PostFeedbackTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostFeedbackTagJdbcTemplateRepositoryTest {

    @Autowired
    PostFeedbackTagJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindPostFeedbackTagsForPostId() {
        List<PostFeedbackTag> postFeedbackTags = repository.findByPostId("a7db5cb6-446a-4c8e-836e-006d9ff239b5");
        assertTrue(postFeedbackTags.size() > 0);
    }

    @Test
    void shouldAdd() {
        PostFeedbackTag postFeedbackTag = makePostFeedbackTag();
        assertTrue(repository.add(postFeedbackTag));

        try {
            repository.add(postFeedbackTag); // must fail
            fail("cannot add the same post/user/feedback tag combination twice.");
        } catch (DataAccessException ex) {
            // this is expected
        }
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteByKey("a7db5cb6-446a-4c8e-836e-006d9ff239b5", "dffec086-b1e9-455a-aab4-ff6c6611fef0", 3));
        assertFalse(repository.deleteByKey("this-doesnt-exist", "dffec086-b1e9-455a-aab4-ff6c6611fef0", 3));
    }

    PostFeedbackTag makePostFeedbackTag() {
        PostFeedbackTag postFeedbackTag = new PostFeedbackTag();
        postFeedbackTag.setPostId("a7db5cb6-446a-4c8e-836e-006d9ff239b5");
        postFeedbackTag.setUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0");

        FeedbackTag feedbackTag = new FeedbackTag(1, "Sound", true);

        postFeedbackTag.setFeedbackTag(feedbackTag);

        return postFeedbackTag;
    }
}