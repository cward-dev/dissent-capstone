package capstone.dissent.domain;

import capstone.dissent.data.TopicRepository;
import capstone.dissent.models.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TopicServiceTest {

    @Autowired
    TopicService service;

    @MockBean
    TopicRepository repository;

    @Test
    void shouldFindALl() {
        List<Topic> expected = List.of(
            new Topic(1, "Science", true),
            new Topic(2, "Philosophy", true),
            new Topic(3, "Economics", true),
            new Topic(4, "Politics", true)
        );
        when(repository.findAll()).thenReturn(expected);
        List<Topic> actual = repository.findAll();

        assertEquals(expected, actual);
    }


    @Test
    void shouldFindById(){
        Topic topicIn = makeTopic();

        when(repository.findById(topicIn.getTopicId())).thenReturn(topicIn);

        Topic actual = service.findById(topicIn.getTopicId());
       assertEquals(actual.getTopicName(),topicIn.getTopicName());
    }

    @Test
    void shouldNotFindByID(){
        Topic topic = service.findById(9999);
        assertNull(topic);
    }

    @Test
    void shouldFindByTopicName(){
        Topic topicIn = makeTopic();

        when(repository.findByTopicName(topicIn.getTopicName())).thenReturn(topicIn);
        Topic actual = service.findByTopicName(topicIn.getTopicName());

        assertEquals(actual.getTopicId(),topicIn.getTopicId());
    }

    @Test
    void shouldNotFindByTopicsName(){
        Topic topic = service.findByTopicName("Bogus");
        assertNull(topic);
    }

    @Test
    void shouldAddNullTopic(){
        Result<Topic> result = service.add(null);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Topic cannot be null"));
    }



    @Test
    void shouldFindALlInactive() {
        List<Topic> expected = List.of(new Topic(5, "Healthcare", false));
        when(repository.findAllWithInactive()).thenReturn(expected);
        List<Topic> actual = repository.findAllWithInactive();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTopicById() {
        Topic expected = makeTopic();
        when(repository.findById(5)).thenReturn(expected);
        Topic actual = repository.findById(5);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindTopicByIdNotPresent() {
        when(repository.findById(45)).thenReturn(null);
        Topic actual = repository.findById(5);

        assertNull(actual);
    }

    @Test
    void shouldAddNewTopic() {
        Topic expected = makeTopic();
        Topic actual = makeTopic();
        actual.setTopicId(0);

        when(repository.add(actual)).thenReturn(expected);
        Result<Topic> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNewTopicWithId() {
        Topic actual = makeTopic();
        Result<Topic> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic ID cannot be set for `add` operation", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNewTopicWithoutName() {
        Topic actual = makeTopic();
        actual.setTopicId(0);
        actual.setTopicName(null);
        Result<Topic> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic name cannot be blank", result.getMessages().get(0));

        actual.setTopicName("     ");
        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic name cannot be blank", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNewTopicNameTooLong() {
        Topic actual = makeTopic();
        actual.setTopicId(0);
        actual.setTopicName("text ".repeat(52));
        Result<Topic> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic name must be between 1 and 255 characters", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicate() {
        Topic actual = makeTopic();
        actual.setTopicId(0);

        when(repository.findAllWithInactive()).thenReturn(List.of(makeTopic()));

        Result<Topic> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic Name: Sports, already exists", result.getMessages().get(0));
    }

    @Test
    void shouldEditExistingTopic() {
        Topic actual = makeTopic();

        when(repository.edit(actual)).thenReturn(true);
        Result<Topic> result = service.edit(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotEditWithoutId() {
        Topic actual = makeTopic();
        actual.setTopicId(0);

        Result<Topic> result = service.edit(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Topic ID must be set for `add` operation", result.getMessages().get(0));
    }

    @Test
    void shouldNotEditInvalidTopic(){
        Topic topic = makeTopic();
        topic.setTopicId(9999);

        Result<Topic> result = service.edit(topic);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Topic ID: 9999, not found"));
    }

    @Test
    void shouldActivateById(){
        when(repository.activateById(1)).thenReturn(true);

        boolean success = service.activateById(1);

        assertTrue(success);
    }

    @Test
    void shouldNotActivateByInvalidId(){
        when(repository.activateById(1)).thenReturn(false);

        boolean success = service.activateById(999);

        assertFalse(success);
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

    private Topic makeTopic() {
        Topic topic = new Topic();
        topic.setTopicId(1);
        topic.setTopicName("Sports");
        return topic;
    }
}