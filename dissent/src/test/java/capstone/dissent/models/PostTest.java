package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;




class PostTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final Post TEST_POST = new Post(null,"testId", true, LocalDateTime.now(),"dsjdjdj", true, new User("testId"));
    @Test
    void postShouldPass(){
        Post post = TEST_POST;
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(0, violations.size());
    }

    @Test
    void postMustNotHaveNullArticle(){
        Post post = TEST_POST;
        post.setArticleId(null);
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post must belong to an article", first.getMessage());
    }

    @Test
    void postMustNotHaveNullUser(){
        Post post = TEST_POST;
        post.setUser(null);
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post must have a user", first.getMessage());
    }

    @Test
    void postCannotBeMadeInFuture(){
        Post post = TEST_POST;
        post.setTimestamp(LocalDateTime.now().plusDays(1));
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Time posted must not be in future", first.getMessage());
    }

    @Test
    void contentCannotBeBlank(){
        Post post = TEST_POST;
        post.setContent("");
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post content cannot be blank", first.getMessage());

    }

    @Test
    void contentCannotBeNull(){
        Post post = TEST_POST;
        post.setContent(null);
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post content cannot be blank", first.getMessage());

    }

}