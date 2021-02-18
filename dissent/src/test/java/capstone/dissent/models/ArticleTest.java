package capstone.dissent.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

   private final  LocalDateTime someday = LocalDateTime.of(2020,01,01,12,00,00);
    @Test
    void articleShouldPass(){
        Article article = new Article("Yolo","Young Idiots","CNN","Jesus",
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        for(ConstraintViolation<Article>violation : violations){
            System.out.println(violation.getMessage());
        }
        assertEquals(0, violations.size());
    }

    @Test
    void titleShouldNotBeNull(){
        Article article = new Article( null,"Young Idiots","CNN","Jesus",
                "www.u.com","www.u.com", someday, someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Title cannot be blank!", first.getMessage());

    }
    @Test
    void titleShouldNotBeBlank(){
        Article article = new Article( "","Young Idiots","CNN","Jesus",
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Title must be at least 1 character and cannot exceed 255", first.getMessage());

    }

    @Test
    void descriptionShouldNotBeNull(){
        Article article = new Article( "xxx",null,"CNN","Jesus",
                "www.u.com","www.u.com",someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank!", first.getMessage());

    }

    @Test
    void descriptionShouldNotBeBlank(){
        Article article = new Article( "xx","","CNN","Jesus",
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Description must be at least 1 character and cannot exceed 255", first.getMessage());

    }

    @Test
    void sourceShouldNotBeNull(){
        Article article = new Article( "xx","xxx",null,"Jesus",
                "www.u.com","www.u.com",someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Source cannot be blank!", first.getMessage());

    }
    @Test
    void sourceShouldNotBeBlank(){
        Article article = new Article( "xx","xxx","","Jesus",
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Source must be at least 1 character and cannot exceed 255", first.getMessage());

    }

    @Test
    void authorShouldNotBeNull(){
        Article article = new Article( "xx","xxx","yyy",null,
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Author cannot be blank!", first.getMessage());

    }
    @Test
    void authorShouldNotBeBlank(){
        Article article = new Article( "xx","xxx","zzzz","",
                "www.u.com","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Author must be at least 1 character and cannot exceed 255", first.getMessage());
    }

    @Test
    void articleUrlShouldNotBeNull(){
        Article article = new Article( "xx","xxx","sss","Jesus",
                null,"www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Article Url cannot be blank", first.getMessage());

    }
    @Test
    void articleUrlShouldNotBeBlank(){
        Article article = new Article( "xx","xxx","ccc","Jesus",
                "","www.u.com", someday,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);
        assertEquals(1, violations.size());
        assertEquals("Article Url must be at least 1 character and cannot exceed 255", first.getMessage());

    }

    @Test
    void datePublishedShouldNotBeNull(){
        Article article = new Article( "xx","xxx","xxx","Jesus",
                "www.u.com","www.u.com", null,someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);

        assertEquals(1, violations.size());
        assertEquals("Article must have published date!", first.getMessage());
    }

    @Test
    void datePublishedShouldNotBeInFuture(){
        Article article = new Article( "xx","xxx","sjsjs","Jesus",
                "www.u.com","www.u.com",LocalDateTime.of(2029,01,01,12,00,00),someday);
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);

        assertEquals(1, violations.size());
        assertEquals("Article cannot have a future published date", first.getMessage());

    }

    @Test
    void datePostedShouldNotBeInFuture(){
        Article article = new Article( "xx","xxx","sjsjs","Jesus",
                "www.u.com","www.u.com", someday,LocalDateTime.of(2029,01,01,12,00,00));
        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        ConstraintViolation<Article> first = violations.stream().findFirst().orElse(null);

        assertEquals(1, violations.size());
        assertEquals("Article cannot have a future post date", first.getMessage());

    }






}