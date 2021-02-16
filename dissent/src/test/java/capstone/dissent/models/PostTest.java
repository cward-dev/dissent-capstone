package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;




class PostTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final Post TEST_POST = new Post(null,"testId", "testId", true, LocalDate.of(2020,10,10),"dsjdjdj");
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
        post.setUserId(null);
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post must have a user", first.getMessage());
    }

    @Test
    void postMustBeMadeInFuture(){
        Post post = TEST_POST;
        post.setDatePosted(LocalDate.of(9999,10,10));
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Date posted must not be in future", first.getMessage());
    }

    @Test
    void contentCannotBeBlank(){
        Post post = TEST_POST;
        post.setContent("");
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        ConstraintViolation<Post> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Post content must between 1 and 255 characters", first.getMessage());

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