package capstone.dissent.models;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTagTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    public void tagNameShouldNotBeNull(){
        FeedbackTag tag = new FeedbackTag();
        tag.setColorHex("#000000");
        Set<ConstraintViolation<FeedbackTag>> violations = validator.validate(tag);
        ConstraintViolation<FeedbackTag> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Feedback Tag name cannot be blank", first.getMessage());
    }

    @Test
    public void tagNameShouldNotBeBlank(){
        FeedbackTag tag = new FeedbackTag();
        tag.setColorHex("#000000");
        Set<ConstraintViolation<FeedbackTag>> violations = validator.validate(tag);
        ConstraintViolation<FeedbackTag> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Feedback Tag name cannot be blank", first.getMessage());
    }

    @Test
    public void ShouldPass(){
        FeedbackTag tag = new FeedbackTag("12112121", "#000000");
        Set<ConstraintViolation<FeedbackTag>> violations = validator.validate(tag);
        assertEquals(0, violations.size());
    }

}