package capstone.dissent.domain;

import capstone.dissent.data.PostRepository;
import capstone.dissent.data.TopicRepository;
import capstone.dissent.models.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TopicServiceTest {

    @Autowired
    TopicService service;

    @MockBean
    TopicRepository repository;

    @Test
    void shouldFindALl() {
        List<Topic> expected = List.of(
            new Topic(1, "Science"),
            new Topic(2, "Philosophy"),
            new Topic(3, "Economics"),
            new Topic(4, "Politics")
        );
        when(repository.findAll()).thenReturn(expected);
        List<Topic> actual = repository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindPostById() {
        List<Topic> expected = List.of(
                new Topic(1, "Science"),
                new Topic(2, "Philosophy"),
                new Topic(3, "Economics"),
                new Topic(4, "Politics")
        );
        when(repository.findAll()).thenReturn(expected);
        List<Topic> actual = repository.findAll();

        assertEquals(expected, actual);
    }

    private Topic makeTopic() {
        Topic topic = new Topic();
        topic.setTopicId(5);
        topic.setTopicName("Sports");
        return topic;
    }

}