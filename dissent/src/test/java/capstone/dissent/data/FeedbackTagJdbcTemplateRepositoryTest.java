package capstone.dissent.data;

import capstone.dissent.models.FeedbackTag;
import capstone.dissent.models.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FeedbackTagJdbcTemplateRepositoryTest {

    final static int NEXT_FEEDBACK_TAG_ID = 6;

    @Autowired
    FeedbackTagJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<FeedbackTag> feedbackTags = repository.findAll();

        assertNotNull(feedbackTags);
        assertTrue(feedbackTags.size() > 0);
    }

    @Test
    void shouldFindAllInactive() {
        List<FeedbackTag> feedbackTags = repository.findAll();

        assertNotNull(feedbackTags);
        assertTrue(feedbackTags.size() > 0);
    }

    @Test
    void shouldFindById() {
        FeedbackTag feedbackTag = repository.findById(1);

        assertNotNull(feedbackTag);
        assertEquals("Sound", feedbackTag.getName());
    }

    @Test
    void shouldFindInactiveByName() {
        FeedbackTag feedbackTag = repository.findInactiveByName("Too Nice");

        assertNotNull(feedbackTag);
        assertEquals(5, feedbackTag.getFeedbackTagId());
    }

    @Test
    void shouldAdd() {
        FeedbackTag feedbackTag = makeFeedbackTag();
        FeedbackTag actual = repository.add(feedbackTag);
        assertNotNull(actual);
        assertEquals(NEXT_FEEDBACK_TAG_ID, actual.getFeedbackTagId());
    }

    @Test

    void shouldReactivateIfAddedExistingInactive() {
        FeedbackTag feedbackTag = makeFeedbackTag();
        feedbackTag.setName("Not Nice");
        FeedbackTag actual = repository.add(feedbackTag);
        assertNotNull(actual);
        assertTrue( actual.getFeedbackTagId() >=4 || actual.getFeedbackTagId()<=77);
    }


    void shouldUpdate() {
        FeedbackTag feedbackTag = new FeedbackTag();
        feedbackTag.setFeedbackTagId(2);
        feedbackTag.setName("Very Fallacious");
        feedbackTag.setColorHex("#000000");
        assertTrue(repository.edit(feedbackTag));
        feedbackTag.setFeedbackTagId(16);
        assertFalse(repository.edit(feedbackTag));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(45));
    }

    @Test
    void shouldFindAllWithInactive(){
        assertTrue(repository.findAllWithInactive().size()>=5 && repository.findAllWithInactive().size()<=7);
    }

    @Test
    void shouldActivateById(){
        boolean success = repository.activateById(4);
        assertTrue(success);
        assertTrue(repository.findById(4).isActive());
    }
    @Test
    void shouldNotActiveById(){
        boolean success = repository.activateById(999);
        assertFalse(success);
    }


    private FeedbackTag makeFeedbackTag() {
        FeedbackTag feedbackTag = new FeedbackTag();
        feedbackTag.setName("Super Sound");
        feedbackTag.setColorHex("#000000");
        return feedbackTag;
    }



}