package capstone.dissent.data;

import capstone.dissent.models.Post;
import capstone.dissent.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<User> user = repository.findAll();

        assertNotNull(user);
        assertTrue(user.size() >= 2 || user.size() >= 4);
    }


    @Test
    void shouldFindById() {
        User user = repository.findById("dffec086-b1e9-455a-aab4-ff6c6611fef0");
        String expected = "dffec086-b1e9-455a-aab4-ff6c6611fef0";
        String actual = user.getUserId();
        assertEquals(expected, actual);

        User invalidUser = repository.findById("...");
        assertNull(invalidUser);
    }

    @Test
    void shouldNotFindById(){
        assertNull(repository.findById("X"));
    }

    @Test
    void shouldFindByUsername(){
        User user = repository.findByUsername("dissenter101");
        assertNotNull(user);
        assertEquals("milan@stoj.com", user.getEmail());

    }
    @Test
    void shouldNotFindByUserName(){
        assertNull(repository.findByUsername("Santa"));
    }


    @Test
    void shouldAdd() {
        User expected = makeUser();
        User actual = repository.add(expected);

        assertNotNull(actual);
        assertEquals(expected.getUsername(), actual.getUsername());

    }


    @Test
    void shouldUpdate() {
        User user = makeUser();
        user = repository.add(user);
        user.setCountry("United States");
        assertTrue(repository.edit(user));
        user.setUserId("this-leads-to-nothing");
        assertFalse(repository.edit(user));
    }

    @Test
    void shouldNotUpdate(){
        User user = new User();
        user.setUserId("XXX");

        boolean success = repository.edit(user);
        assertFalse(success);
    }

    @Test
    void shouldDelete() {
        boolean actual = repository.deleteById("b");
        assertTrue(actual);
        assertEquals("deleted",repository.findById("b").getEmail());
    }


    @Test
    void shouldFindUserRoles(){
        User user = repository.findById("dffec086-b1e9-455a-aab4-ff6c6611fef0");
        System.out.println(user.getRoles());
        assertTrue(user.getRoles().size()==2);
    }

    @Test
    void shouldFindByUserName(){
        User user = repository.findByUsername("dissenter101");
        assertEquals("milan@stoj.com",user.getEmail());
    }




    private User makeUser() {
        User user = new User();
        user.setUsername("accepter101");
        user.setEmail("new@gmail.com");
        user.setPassword("test_pass");

        return user;
    }

}