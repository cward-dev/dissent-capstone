package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTopicTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void shouldAdd(){
        ArticleTopic articleTopic = new ArticleTopic("a",3);
        Set<ConstraintViolation<ArticleTopic>> violations = validator.validate(articleTopic);
        assertEquals(0, violations.size());
    }

    @Test
    void shouldNotHaveNullArticleId(){
        ArticleTopic articleTopic = new ArticleTopic(null,3);
        Set<ConstraintViolation<ArticleTopic>> violations = validator.validate(articleTopic);
        assertEquals(1, violations.size());
        ConstraintViolation<ArticleTopic> first = violations.stream().findFirst().orElse(null);
        assertEquals("article Id cannot be blank", first.getMessage());
    }

    @Test
    void shouldNotHaveBlankArticleId(){
        ArticleTopic articleTopic = new ArticleTopic("",3);
        Set<ConstraintViolation<ArticleTopic>> violations = validator.validate(articleTopic);
        assertEquals(1, violations.size());
        ConstraintViolation<ArticleTopic> first = violations.stream().findFirst().orElse(null);
        assertEquals("article Id cannot be blank", first.getMessage());
    }

    @Test
    void shouldNotHaveNegativeArticleId(){
        ArticleTopic articleTopic = new ArticleTopic("a",-3);
        Set<ConstraintViolation<ArticleTopic>> violations = validator.validate(articleTopic);
        assertEquals(1, violations.size());
        ConstraintViolation<ArticleTopic> first = violations.stream().findFirst().orElse(null);
        assertEquals("topic Id must be greater than 1", first.getMessage());
    }


}