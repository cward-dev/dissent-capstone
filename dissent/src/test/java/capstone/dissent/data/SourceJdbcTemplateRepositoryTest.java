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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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
        Source source = new Source("a" ,"updated","www.u.com", "I was updated!");

        boolean success = repository.edit(source);
        assertTrue(success);
        assertEquals(repository.findById("a").getDescription(),"I was updated!");

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
        boolean success = repository.deleteById("b");
        assertTrue(success);
        assertEquals(null, repository.findById("b"));
    }

    @Test
    void shouldNotDeleteByBogusId(){
        boolean success = repository.deleteById("X");
        assertFalse(success);
        assertEquals(null, repository.findById("X"));

    }
}