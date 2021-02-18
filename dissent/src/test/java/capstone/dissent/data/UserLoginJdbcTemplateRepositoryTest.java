package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserLoginJdbcTemplateRepositoryTest {

    @Autowired
    UserLoginJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAll() {
        List<UserLogin> userLogin = repository.findAll();

        assertNotNull(userLogin);
        assertTrue(userLogin.size() > 0);
    }

    @Test
    void shouldFindById() {
        UserLogin userLogin = repository.findById("103a7d9b-f72b-4469-b1a3-bdba2f6356b4");
        String expected = "user@dissent.com";
        String actual = userLogin.getEmail();
        assertEquals(expected, actual);

        UserLogin invalidUserLogin = repository.findById("...");
        assertNull(invalidUserLogin);
    }

    @Test
    void shouldAdd() {
        UserLogin expected = makeUserLogin();
        UserLogin actual = repository.add(expected);

        assertNotNull(actual);
        assertEquals(expected.getEmail(), actual.getEmail());

    }

    @Test
    void shouldUpdate() {
        UserLogin userLogin = makeUserLogin();
        userLogin = repository.add(userLogin);
        userLogin.setEmail("newEmail@gmail.com");
        assertTrue(repository.edit(userLogin));
        userLogin.setPassword("newPassword");
        assertTrue(repository.edit(userLogin));
        userLogin.setUserLoginId("not-going-to-work");
        assertFalse(repository.edit(userLogin));
    }

    @Test
    void shouldDelete() {

        boolean actual = repository.deleteById("103a7d9b-f72b-4469-b1a3-bdba2f6356b4");
        assertTrue(actual);

    }

    @Test
    void shouldFindUserFromLoginId() {
        String loginId = "103a7d9b-f72b-4469-b1a3-bdba2f6356b4";
        User user = repository.getUserFromUserLoginId(loginId);

        String expected = "dffec086-b1e9-455a-aab4-ff6c6611fef0";
        String actual = user.getUserId();

        assertEquals(expected, actual);

    }


    private UserLogin makeUserLogin() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserLoginId("PLACE-HOLDER");
        userLogin.setEmail("admin@dissent.com");
        userLogin.setPassword("p4ssw0rd");
        return userLogin;
    }
}