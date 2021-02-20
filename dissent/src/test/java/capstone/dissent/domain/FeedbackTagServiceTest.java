package capstone.dissent.domain;

import capstone.dissent.data.FeedbackTagRepository;
import capstone.dissent.models.FeedbackTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FeedbackTagServiceTest {

    @Autowired
    FeedbackTagService service;

    @MockBean
    FeedbackTagRepository repository;

    @Test
    void shouldFindALl() {
        List<FeedbackTag> expected = List.of(
                new FeedbackTag(1, "Sound", "#000000", true),
                new FeedbackTag(2, "Fallacious", "#000000", true),
                new FeedbackTag(3, "Biased", "#000000", true)
        );
        when(repository.findAll()).thenReturn(expected);
        List<FeedbackTag> actual = repository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindALlInactive() {
        List<FeedbackTag> expected = List.of(new FeedbackTag(5, "Not Nice", "#000000", false));
        when(repository.findAllInactive()).thenReturn(expected);
        List<FeedbackTag> actual = repository.findAllInactive();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFeedbackTagById() {
        FeedbackTag expected = makeFeedbackTag();
        when(repository.findById(5)).thenReturn(expected);
        FeedbackTag actual = repository.findById(5);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindFeedbackTagByIdNotPresent() {
        when(repository.findById(45)).thenReturn(null);
        FeedbackTag actual = repository.findById(5);

        assertNull(actual);
    }

    @Test
    void shouldAddNewFeedbackTag() {
        FeedbackTag expected = makeFeedbackTag();
        FeedbackTag actual = makeFeedbackTag();
        actual.setFeedbackTagId(0);

        when(repository.add(actual)).thenReturn(expected);
        Result<FeedbackTag> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNewFeedbackTagWithId() {
        FeedbackTag actual = makeFeedbackTag();
        Result<FeedbackTag> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag ID cannot be set for `add` operation", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNewFeedbackTagWithoutName() {
        FeedbackTag actual = makeFeedbackTag();
        actual.setFeedbackTagId(0);
        actual.setName(null);
        Result<FeedbackTag> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag name cannot be blank", result.getMessages().get(0));

        actual.setName("     ");
        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag name cannot be blank", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNewFeedbackTagNameTooLong() {
        FeedbackTag actual = makeFeedbackTag();
        actual.setFeedbackTagId(0);
        actual.setName("text ".repeat(52));
        Result<FeedbackTag> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag name must be between 1 and 255 characters", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicate() {
        FeedbackTag actual = makeFeedbackTag();
        actual.setFeedbackTagId(0);

        when(repository.findAll()).thenReturn(List.of(makeFeedbackTag()));

        Result<FeedbackTag> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag Name: Super Sound, already exists", result.getMessages().get(0));
    }

    @Test
    void shouldEditExistingFeedbackTag() {
        FeedbackTag actual = makeFeedbackTag();

        when(repository.edit(actual)).thenReturn(true);
        Result<FeedbackTag> result = service.edit(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotEditWithoutId() {
        FeedbackTag actual = makeFeedbackTag();
        actual.setFeedbackTagId(0);

        Result<FeedbackTag> result = service.edit(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Feedback Tag ID must be set for `add` operation", result.getMessages().get(0));
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteByIdNotPresent() {
        when(repository.deleteById(45)).thenReturn(false);
        assertFalse(service.deleteById(1));
    }

    private FeedbackTag makeFeedbackTag() {
        FeedbackTag feedbackTag = new FeedbackTag();
        feedbackTag.setFeedbackTagId(1);
        feedbackTag.setName("Super Sound");
        feedbackTag.setColorHex("#000000");
        return feedbackTag;
    }
}