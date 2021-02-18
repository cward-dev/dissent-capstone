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
        assertTrue(user.size() > 0);
    }


    @Test
    void shouldFindById() {
        User user = repository.findById("dffec086-b1e9-455a-aab4-ff6c6611fef0");
        String expected = "dissenter101";
        String actual = user.getUsername();
        assertEquals(expected, actual);

        User invalidUser = repository.findById("...");
        assertNull(invalidUser);
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
    void shouldDelete() {
        User user = makeUser();
        user = repository.add(user);

        boolean actual = repository.deleteById(user.getUserId());

        assertTrue(actual);
    }


    private User makeUser() {
        User user = new User();
        user.setUserLoginId("103a7d9b-f72b-4469-b1a3-bdba2f6356b4");
        user.setUsername("accepter101");
        user.setRole("user");
        return user;
    }

}