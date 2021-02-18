package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SourceTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final Source TEST_SOURCE = new Source(null,"CNN", "CNN.com", "Who knows what CNN stands for");
    @Test
    void sourceShouldPass(){
        Source source = TEST_SOURCE;
        Set<ConstraintViolation<Source>> violations = validator.validate(source);
        assertEquals(0,violations.size());
    }

    @Test
    void sourceNameShouldNotBeNull(){
        Source source = TEST_SOURCE;
        source.setSourceName(null);
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Source name cannot be blank", first.getMessage());

    }

    @Test
    void sourceNameShouldNotBeLessThan2Characters(){
        Source source = TEST_SOURCE;
        source.setSourceName("A");
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Source name must between 2 and 255 characters", first.getMessage());

    }
    @Test
    void sourceNameShouldNotBeMoreThan255Characters(){
        Source source = TEST_SOURCE;
        source.setSourceName("A".repeat(256));
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Source name must between 2 and 255 characters", first.getMessage());

    }

    @Test
    void websiteUrlShouldNotBeLessThan4Characters(){
        Source source = TEST_SOURCE;
        source.setWebsiteUrl("aaa");
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("URL must between 4 and 255 characters", first.getMessage());

    }

    @Test
    void websiteUrlShouldNotBeNull(){
        Source source = TEST_SOURCE;
        source.setWebsiteUrl(null);
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Website Url cannot be blank", first.getMessage());

    }

    @Test
    void websiteUrlShouldNotBeMoreThan255Characters(){
        Source source = TEST_SOURCE;
        source.setWebsiteUrl("A".repeat(256));
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("URL must between 4 and 255 characters", first.getMessage());

    }

    @Test
    void descriptionShouldNotBeMoreThan255Characters(){
        Source source = TEST_SOURCE;
        source.setDescription("A".repeat(256));
        Set<ConstraintViolation<Source>> violations = validator.validate(source);

        ConstraintViolation<Source> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Description should be less than 255 characters", first.getMessage());

    }









}