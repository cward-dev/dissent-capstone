package capstone.dissent.data;

import capstone.dissent.models.Post;
import capstone.dissent.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostJdbcTemplateRepositoryTest {

    @Autowired
    PostJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Post> posts = repository.findAll();

        assertNotNull(posts);
        assertTrue(posts.size() > 0);
    }

    @Test
    void shouldFindById() {
        Post post = repository.findById("a7db5cb6-446a-4c8e-836e-006d9ff239b5");
        assertEquals("I'll have to see this black-hole to believe it!", post.getContent());
        assertEquals(post.getFeedbackTags().size(), 2);

        Post invalidPost = repository.findById("this-leads-to-nothing");
        assertNull(invalidPost);
    }

    @Test
    void shouldFindByArticleId() {
        List<Post> posts = repository.findByArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");

        assertTrue(posts.size() > 0);
    }

    @Test
    void shouldFindByUserId() {
        List<Post> posts = repository.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0");

        assertTrue(posts.size() > 0);
    }

    @Test
    void shouldAdd() {
        Post post = makePost();
        Post actual = repository.add(post);
        assertNotNull(actual);
        assertNotNull(actual.getPostId());
    }

    @Test
    void shouldUpdate() {
        Post post = makePost();
        post.setPostId("d7e12582-6f81-4f02-9e6e-18190f622264");
        assertTrue(repository.edit(post));
        post.setPostId("this-leads-to-nothing");
        assertFalse(repository.edit(post));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById("dfdsf67s-fd67-580f-f678-44120dsfa873"));
        assertFalse(repository.findById("dfdsf67s-fd67-580f-f678-44120dsfa873").isActive());

        assertFalse(repository.deleteById("this-leads-to-nothing"));
    }

    private Post makePost() {
        Post post = new Post();
        post.setParentPostId("a7db5cb6-446a-4c8e-836e-006d9ff239b5");
        post.setArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");
        post.setUser(new User("dffec086-b1e9-455a-aab4-ff6c6611fef0"));
        post.setDissenting(true);
        post.setTimestamp(LocalDateTime.now());
        post.setContent("Wait, I take that back. There's no such thing as black holes!");
        return post;
    }

}