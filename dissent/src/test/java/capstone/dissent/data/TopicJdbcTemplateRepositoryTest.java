package capstone.dissent.data;

import capstone.dissent.models.Post;
import capstone.dissent.models.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TopicJdbcTemplateRepositoryTest {

    final static int NEXT_TOPIC_ID = 5;

    @Autowired
    TopicJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Topic> topics = repository.findAll();

        assertNotNull(topics);
        assertTrue(topics.size() > 0);
    }

    @Test
    void shouldFindById() {
        Topic topic = repository.findById(1);

        assertNotNull(topic);
        assertEquals("Science", topic.getTopicName());
    }

    @Test
    void shouldAdd() {
        Topic topic = makeTopic();
        Topic actual = repository.add(topic);
        assertNotNull(actual);
        assertEquals(NEXT_TOPIC_ID, actual.getTopicId());
    }

    @Test
    void shouldUpdate() {
        Topic topic = new Topic();
        topic.setTopicId(2);
        topic.setTopicName("Ethics");
        assertTrue(repository.edit(topic));
        topic.setTopicId(16);
        assertFalse(repository.edit(topic));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(45));
    }

    private Topic makeTopic() {
        Topic topic = new Topic();
        topic.setTopicName("Sports");
        return topic;
    }
}