package capstone.dissent.domain;

import capstone.dissent.data.UserRepository;
import capstone.dissent.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    final User USER_1 = new User(1, "user101");
    final User USER_2 = new User(2, "user102");
    final User USER_3 = new User(3, "user103");
    List<User> users = new ArrayList<>(Arrays.asList(USER_1, USER_2, USER_3));

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(users);

        List<User> results = service.findAll();

        assertNotNull(results);
        assertEquals(3,results.size());
    }


    @Test
    void shouldFindById() {
        User userIn = USER_1;
        when(repository.findById("1")).thenReturn(USER_1);
        User userOut = service.findById("1");
        assertEquals(userIn,userOut);
    }

    @Test
    void shouldNotAddEmptyUsernameAndPassword() {
        User userIn = USER_2;
        userIn.setUserId(null);
        User userOut = new User();

        when(repository.add(userIn)).thenReturn(userOut);
        Result<User> result = service.add(userIn);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAdd() {
        User userIn = USER_2;
        userIn.setUserId(null);
        User userOut = new User();
        userIn.setEmail("test@email.com");
        userIn.setPassword("Passwrod");

        when(repository.add(userIn)).thenReturn(userOut);
        Result<User> result = service.add(userIn);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddWithSetId(){
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("Passwrod");
        user.setUserId("TEST_USER_ID");
        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("User ID cannot be set for `add` operation"));
    }

    @Test
    void shouldEdit() {
        User user = USER_1;
        user.setUsername("newUser1Update");
        user.setEmail("test@email.com");
        user.setPassword("Passwrod");
        user.setUserId("TEST_USER_ID");

        when(repository.edit(user)).thenReturn(true);
        when(repository.findById(user.getUserId())).thenReturn(user);

        Result<User> result = service.edit(user);
        assertEquals(ResultType.SUCCESS,result.getType());
    }

    @Test
    void shouldDelete() {
        User user = USER_3;
        user.setUserId("TEST_USERID");

        when(repository.deleteById(user.getUserId())).thenReturn(true);

        boolean success = service.deleteById(user.getUserId());
        assertTrue(success);
    }
}