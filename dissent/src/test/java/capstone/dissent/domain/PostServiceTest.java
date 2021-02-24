package capstone.dissent.domain;

import capstone.dissent.data.PostFeedbackTagRepository;
import capstone.dissent.data.PostRepository;
import capstone.dissent.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;

import java.security.Provider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostServiceTest {

    @Autowired
    PostService service;

    @MockBean
    PostRepository repository;

    @MockBean
    PostFeedbackTagRepository postFeedbackTagRepository;

    @Test
    void shouldFindAll() {
        List<Post> expected = List.of(
            new Post("a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                    null,
                    "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                    true,
                    LocalDateTime.of(2021, 2, 15, 12, 0, 0),
                    "I'll have to see this black-hole to believe it!", true,
                    new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
            new Post("d7e12582-6f81-4f02-9e6e-18190f622264",
                    "a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                    "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                    false,
                    LocalDateTime.of(2021, 2, 16, 12, 0, 0),
                    "Wait --- Nevermind, because science.", true,
                    new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
            new Post("dfdsf67s-fd67-580f-f678-44120dsfa873",
                    "d7e12582-6f81-4f02-9e6e-18190f622264",
                    "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                    false,
                    LocalDateTime.of(2021, 2, 16, 12, 10, 0),
                    "Science never lies!", true,
                    new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")));
        when(repository.findAll()).thenReturn(expected);
        List<Post> actual = service.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindPostById() {
        Post expected = makePost();
        when(repository.findById("a7db5cb6-446a-4c8e-836e-006d9ff239b5")).thenReturn(expected);

        Post actual = service.findById("a7db5cb6-446a-4c8e-836e-006d9ff239b5");

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByArticleId() {
        List<Post> expected = List.of(
                new Post("a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                        null,
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        true,
                        LocalDateTime.of(2021, 2, 15, 12, 0, 0),
                        "I'll have to see this black-hole to believe it!", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
                new Post("d7e12582-6f81-4f02-9e6e-18190f622264",
                        "a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        false,
                        LocalDateTime.of(2021, 2, 16, 12, 0, 0),
                        "Wait --- Nevermind, because science.", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
                new Post("dfdsf67s-fd67-580f-f678-44120dsfa873",
                        "d7e12582-6f81-4f02-9e6e-18190f622264",
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        false,
                        LocalDateTime.of(2021, 2, 16, 12, 10, 0),
                        "Science never lies!", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")));
        when(repository.findByArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2")).thenReturn(expected);
        List<Post> actual = service.findByArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByUserId() {
        List<Post> expected = List.of(
                new Post("a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                        null,
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        true,
                        LocalDateTime.of(2021, 2, 15, 12, 0, 0),
                        "I'll have to see this black-hole to believe it!", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
                new Post("d7e12582-6f81-4f02-9e6e-18190f622264",
                        "a7db5cb6-446a-4c8e-836e-006d9ff239b5",
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        false,
                        LocalDateTime.of(2021, 2, 16, 12, 0, 0),
                        "Wait --- Nevermind, because science.", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")),
                new Post("dfdsf67s-fd67-580f-f678-44120dsfa873",
                        "d7e12582-6f81-4f02-9e6e-18190f622264",
                        "c32bec11-b9a0-434b-bda7-08b9cf2007e2",
                        false,
                        LocalDateTime.of(2021, 2, 16, 12, 10, 0),
                        "Science never lies!", true,
                        new User("dffec086-b1e9-455a-aab4-ff6c6611fef0")));
        when(repository.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0")).thenReturn(expected);
        List<Post> actual = service.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0");

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddNewPost() {
        Post expected = makePost();
        expected.setPostId(java.util.UUID.randomUUID().toString());
        Post actual = makePost();
        actual.setPostId(null);

        when(repository.add(actual)).thenReturn(expected);
        Result<Post> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNewPostWithin30SecondsOfLast() {
        Post lastPost = makePost();
        lastPost.setPostId(java.util.UUID.randomUUID().toString());
        lastPost.setTimestamp(LocalDateTime.now());

        Post actual = makePost();
        actual.setPostId(null);

        when(repository.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0")).thenReturn(List.of(lastPost));
        Result<Post> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Cannot submit another post so quickly, please try again soon", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<Post> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post cannot be null", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithId() {
        Post post = makePost();
        post.setPostId("fd788ds8-fsdf-4ghd-438a-d6ds8a7sdf91");
        Result<Post> result = service.add(post);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post ID cannot be set for `add` operation", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddBlankArticleId() {
        Post actual = makePost();
        actual.setPostId(null);
        actual.setArticleId(null);

        Result<Post> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post must belong to an article", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setArticleId("   ");

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post must belong to an article", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

//    @Test
//    void shouldNotAddBlankUserId() {
//        Post actual = makePost();
//        actual.setPostId(null);
//        actual.setUserId(null);
//
//        Result<Post> result = service.add(actual);
//
//        assertEquals(ResultType.INVALID, result.getType());
//        assertEquals("Post must have a user", result.getMessages().get(0));
//        assertNull(result.getPayload());
//
//        actual.setUserId("   ");
//
//        result = service.add(actual);
//
//        assertEquals(ResultType.INVALID, result.getType());
//        assertEquals("Post must have a user", result.getMessages().get(0));
//        assertNull(result.getPayload());
//    }

    @Test
    void shouldNotAddFutureTimestamp() {
        Post actual = makePost();
        actual.setPostId(null);
        actual.setTimestamp(LocalDateTime.now().plusMinutes(1));

        Result<Post> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Time posted must not be in future", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddBlankContent() {
        Post actual = makePost();
        actual.setPostId(null);
        actual.setContent(null);

        Result<Post> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post content cannot be blank", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setContent("   ");

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post content cannot be blank", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddContentTooLong() {
        Post actual = makePost();
        actual.setPostId(null);
        actual.setContent("text ".repeat(8001));

        Result<Post> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals(40005, actual.getContent().length());
        assertEquals("Post content must between 1 and 40000 characters", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldEditExistingPost() {
        Post actual = makePost();

        when(repository.edit(actual)).thenReturn(true);
        when(repository.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0")).thenReturn(List.of(makePost()));
        Result<Post> result = service.edit(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotEditWithoutId() {
        Post actual = makePost();
        actual.setPostId(null);

        when(repository.findByUserId("dffec086-b1e9-455a-aab4-ff6c6611fef0")).thenReturn(List.of(makePost()));
        Result<Post> result = service.edit(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Post ID must be set for `edit` operation", result.getMessages().get(0));
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById("fd788ds8-fsdf-4ghd-438a-d6ds8a7sdf91")).thenReturn(true);
        assertTrue(service.deleteById("fd788ds8-fsdf-4ghd-438a-d6ds8a7sdf91"));
    }

    @Test
    void shouldNotDeleteByIdNotPresent() {
        when(repository.deleteById("this-leads-to-nothing")).thenReturn(false);
        assertFalse(repository.deleteById("this-leads-to-nothing"));
    }

    @Test
    void  shouldFindByTime(){
        LocalDateTime day1 = LocalDateTime.of(2021, 2, 15, 12, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        when(repository.findByTimestampRange(day1,now)).thenReturn(List.of(makePost()));
        List<Post> post = service.findByTimestampRange(day1,now);

        assertEquals(post.size(),1);
    }

    @Test
    void shouldNotFindByTime(){
        LocalDateTime day1 = LocalDateTime.of(2021, 2, 15, 12, 0, 0);
        LocalDateTime now = LocalDateTime.now();

        when(repository.findByTimestampRange(day1,now)).thenReturn(null);
        List<Post> posts = service.findByTimestampRange(day1,now);

       assertNull(posts);
    }

    @Test
    void shouldFindPostFeedbackByKey(){
        PostFeedbackTag tag = new PostFeedbackTag("1","1");

        when(postFeedbackTagRepository.findByKey("1","1", 1)).thenReturn(tag);
        PostFeedbackTag actual = service.findPostFeedbackTagByKey("1","1",1);

        assertEquals(tag, actual);

    }
    @Test
    void shouldNotFindPostFeedbackByKey(){
        PostFeedbackTag actual = service.findPostFeedbackTagByKey("999","999",3);
        assertNull(actual);
    }

    @Test
    void shouldFindFeedBackTagHelperByPostId(){
        List<FeedbackTagHelper> feedbackTagHelpers = List.of(new FeedbackTagHelper("A",1,"111d"));

        when(postFeedbackTagRepository.findByPostId("1")).thenReturn(feedbackTagHelpers);
        List<FeedbackTagHelper> tags = service.findPostFeedbackTagByPostId("1");

        assertEquals(1, tags.size());
        assertEquals("A", tags.get(0).getTitle());
    }

    @Test
    void shouldNotFindFeedBackTagHelperByPostId(){
        List<FeedbackTagHelper> tags = service.findPostFeedbackTagByPostId("1");

        assertEquals(0, tags.size());
    }

    @Test
    void shouldFindFeedbackTagHelperByUserId(){
        List<FeedbackTagHelper> feedbackTagHelpers = List.of(new FeedbackTagHelper("A",1,"111d"));

        when(postFeedbackTagRepository.findByUserId("1")).thenReturn(feedbackTagHelpers);
        List<FeedbackTagHelper> tags = service.findPostFeedbackTagByUserId("1");

        assertEquals(1, tags.size());
        assertEquals("A", tags.get(0).getTitle());
    }

    @Test
    void shouldNotFindFeedBackTagHelperByUserId(){
        List<FeedbackTagHelper> tags = service.findPostFeedbackTagByPostId("1");

        assertEquals(0, tags.size());
    }

    @Test
    void shouldNotEditByNullPost(){
        Result<Post> result = service.edit(null);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Post cannot be null"));
    }

    @Test
    void shouldNotEditInvalidPost(){
        Post post = makePost();
        Result<Post> result = service.edit(post);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Post ID: fd788ds8-fsdf-4ghd-438a-d6ds8a7sdf91, not found"));
    }

    @Test
    void shouldAddFeedBackTag(){
        PostFeedbackTag postFeedbackTag = new PostFeedbackTag("1","1");
        postFeedbackTag.setFeedbackTag(new FeedbackTag(1,"12212","1", true));

        when(postFeedbackTagRepository.add(postFeedbackTag)).thenReturn(true);
        Result<Void> result = service.addFeedbackTag(postFeedbackTag);

        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddNullFeedBackTag(){
        Result<Void> result = service.addFeedbackTag(null);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Post Feedback Tag cannot be null"));
    }

    @Test
    void shouldNotAddWithNullFeedbackTag(){
        PostFeedbackTag postFeedbackTag = new PostFeedbackTag("1","1");

        Result<Void> result = service.addFeedbackTag(postFeedbackTag);

        assertFalse(result.isSuccess());
        System.out.println(result.getMessages());
        assertTrue(result.getMessages().contains("Feedback Tag cannot be null"));
    }

    @Test
    void shouldAddNotFeedBackTagWithErrorFromRepo(){
        PostFeedbackTag postFeedbackTag = new PostFeedbackTag("1","1");
        postFeedbackTag.setFeedbackTag(new FeedbackTag(1,"12212","1", true));

        when(postFeedbackTagRepository.add(postFeedbackTag)).thenReturn(false);
        Result<Void> result = service.addFeedbackTag(postFeedbackTag);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Post feedback tag not added"));
    }

    @Test
    void shouldDeleteByKey(){
        when(postFeedbackTagRepository.deleteByKey("a","a",1)).thenReturn(true);
        boolean success = service.deleteFeedbackTagByKey("a","a",1);

        assertTrue(success);

    }

    @Test
    void shouldNotDeleteByKey(){
        when(postFeedbackTagRepository.deleteByKey("a","a",1)).thenReturn(false);
        boolean success = service.deleteFeedbackTagByKey("a","a",1);

        assertFalse(success);

    }












    private Post makePost() {
        Post post = new Post();
        post.setPostId("fd788ds8-fsdf-4ghd-438a-d6ds8a7sdf91");
        post.setParentPostId("a7db5cb6-446a-4c8e-836e-006d9ff239b5");
        post.setArticleId("c32bec11-b9a0-434b-bda7-08b9cf2007e2");
        post.setUser(new User("dffec086-b1e9-455a-aab4-ff6c6611fef0"));
        post.setDissenting(true);
        post.setTimestamp(LocalDateTime.now());
        post.setContent("Wait, I take that back. There's no such thing as black holes!");
        return post;
    }
}