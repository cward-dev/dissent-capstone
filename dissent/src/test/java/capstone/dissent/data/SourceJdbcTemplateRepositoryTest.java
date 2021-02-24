package capstone.dissent.data;

import capstone.dissent.models.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SourceJdbcTemplateRepositoryTest {

    @Autowired

    SourceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<Source> all = repository.findAll();
        assertTrue(all.size()>=2&&all.size()<=4);
        System.out.println(all.size());
    }

    @Test
    void shouldAdd() {
        Source source = new Source("d","Added Source","www.ADD.com","I added this");

        Source addedSource = repository.add(source);

        assertEquals(source, addedSource);
        assertEquals(source.getDescription(),addedSource.getDescription());

    }

    @Test
    void shouldFindById() {
        Source source = repository.findById("d293ae18-63e0-49b7-87fd-9856bcf52884");
        assertTrue(source.getSourceName().equals("European Southern Observatory"));
    }

    @Test
    void shouldNotFindById() {
        Source source = repository.findById("Q");
        assertNull(source);
    }

    @Test
    void shouldEdit() {
        Source source = new Source("fsd67a8s-a512-dfb2-saf6-fsadfas76dfa" ,"Associated Press","https://www.apnews.com/", "I was updated!");

        boolean success = repository.edit(source);
        assertTrue(success);
        assertEquals(repository.findById("fsd67a8s-a512-dfb2-saf6-fsadfas76dfa").getDescription(),"I was updated!");

    }

    @Test
    void shouldNotEditBogusSource(){

        Source source = new Source("XXX" ,"updated","www.u.com",null);

        boolean success = repository.edit(source);
        assertFalse(success);
        assertEquals(repository.findById("XXX"),null);

    }

    @Test
    void shouldDeleteById() {
        boolean success = repository.deleteById("fsdafas8-fsad-fsd8-fsda-413h1hj1a90s");
        assertTrue(success);
        assertEquals(null, repository.findById("fsdafas8-fsad-fsd8-fsda-413h1hj1a90s"));
    }

    @Test
    void shouldNotDeleteByBogusId(){
        boolean success = repository.deleteById("X");
        assertFalse(success);
        assertEquals(null, repository.findById("X"));

    }

    @Test
    void shouldFindSourceByNameAndUrl(){
        Source source = repository.findBySourceNameAndUrl("European Southern Observatory","https://www.eso.org/");
        assertEquals("d293ae18-63e0-49b7-87fd-9856bcf52884", source.getSourceId());
    }

    @Test
    void shouldNotFindSourceByNameAndUrl(){
        Source source = repository.findBySourceNameAndUrl("???","www.?.com");
        assertNull(source);
    }
}