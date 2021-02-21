package capstone.dissent.data;

import capstone.dissent.models.User;
import capstone.dissent.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserRoleJdbcTemplateRepositoryTest {

    @Autowired
    UserRoleJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAll() {
        List<UserRole> userRole = repository.findAll();

        assertNotNull(userRole);
        assertTrue(userRole.size() > 0);
    }

    @Test
    void shouldFindById() {
        UserRole userRole = repository.findById(1);
        String expected = "ADMIN";
        String actual = userRole.getName();
        assertEquals(expected, actual);

        UserRole invalidUserRole = repository.findById(777);
        assertNull(invalidUserRole);
    }

    @Test
    void shouldAdd() {
        UserRole expected = makeUserRole();
        UserRole actual = repository.add(expected);

        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());

    }

    @Test
    void shouldUpdate() {
        UserRole userRole = makeUserRole();
        userRole = repository.add(userRole);
        userRole.setName("NEW_ROLE_PREMIUM");
        assertFalse(repository.edit(userRole));
    }


    private UserRole makeUserRole() {
        UserRole userRole = new UserRole();
        userRole.setName("DISSENT_PREMIUM");
        return userRole;
    }
}