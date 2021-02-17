package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TopicTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void topicNameShouldNotBeNull(){
        Topic topic = new Topic();
        Set<ConstraintViolation<Topic>> violations = validator.validate(topic);
        ConstraintViolation<Topic> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Topic name cannot be blank", first.getMessage());
    }
    @Test
    public void topicNameShouldNotBeBlank(){
        Topic topic = new Topic("");
        Set<ConstraintViolation<Topic>> violations = validator.validate(topic);
        ConstraintViolation<Topic> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Topic name must be between 1 and 255 characters", first.getMessage());
    }


    @Test
    public void topicShouldPass(){
        Topic topic = new Topic( "2131321231");
        Set<ConstraintViolation<Topic>> violations = validator.validate(topic);
        assertEquals(0, violations.size());
    }

}