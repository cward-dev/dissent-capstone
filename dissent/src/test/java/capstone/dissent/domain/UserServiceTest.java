package capstone.dissent.domain;

import capstone.dissent.data.UserRepository;
import capstone.dissent.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    final User USER_1 = new User(Arrays.asList("USER", "ADMIN"), "user101");
    final User USER_2 = new User(Collections.singletonList("USER"), "user102");
    final User USER_3 = new User(Collections.singletonList("USER"), "user103");
    List<User> users = new ArrayList<>(Arrays.asList(USER_1, USER_2, USER_3));

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(users);

        List<User> results = service.findAll();

        assertNotNull(results);
        assertEquals(3, results.size());
    }


    @Test
    void shouldFindById() {
        User userIn = USER_1;
        when(repository.findById("1")).thenReturn(USER_1);
        User userOut = service.findById("1");
        assertEquals(userIn, userOut);
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
        userIn.setPassword("Pa55w()rd");

        when(repository.add(userIn)).thenReturn(userOut);
        Result<User> result = service.add(userIn);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddWithSetId() {
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("P@55word");
        user.setUserId("TEST_USER_ID");
        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
        System.out.println(result.getMessages().get(0));
        assertTrue(result.getMessages().contains("User ID cannot be set for `add` operation"));
    }

    @Test
    void shouldNotAddWithMissingSpecial() {
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("P555word");
        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Password must contain at least one special character [!@#$%^&-+=()]"));
    }

    @Test
    void shouldNotAddWithPasswordLength() {
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("P1p$");
        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Password must be between 8 and 20 characters with no white space."));
    }

    @Test
    void shouldNotAddWithNoUppers() {
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("pa55w0r#");
        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Password must contain an upper and lowercase letter."));
    }

    @Test
    void shouldNotAddWithNoLowers() {
        User user = USER_2;
        user.setEmail("test@email.com");
        user.setPassword("PA55W0R#");
        Result<User> result = service.add(user);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Password must contain an upper and lowercase letter."));
    }

    @Test
    void shouldEdit() {
        User user = USER_1;
        user.setUsername("newUser1Update");
        user.setEmail("test@email.com");
        user.setPassword("Pa55w()rd");
        user.setUserId("TEST_USER_ID");

        when(repository.edit(user)).thenReturn(true);
        when(repository.findById(user.getUserId())).thenReturn(user);

        Result<User> result = service.edit(user);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldDelete() {
        User user = USER_3;
        user.setUserId("TEST_USERID");

        when(repository.deleteById(user.getUserId())).thenReturn(true);

        boolean success = service.deleteById(user.getUserId());
        assertTrue(success);
    }

    @Test
    void shouldNotHaveWithUserId() {
        User user = USER_1;
        user.setUserId("XXXX");
        user.setPassword("Abc34dvb!");
        user.setEmail("abcd123@aol.com");

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("User ID cannot be set for `add` operation"));

    }

    @Test
    void passwordShouldNotBeNull() {
        User user = USER_1;
        user.setUserId("XXXX");
        user.setPassword(null);
        user.setEmail("abcd123@aol.com");

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        System.out.println(result.getMessages());
        assertTrue(result.getMessages().contains("Password cannot be null"));

    }

    @Test
    void passwordShouldHaveADigit() {
        User user = USER_1;
        user.setUserId("XXXX");
        user.setPassword("Abcdvb!");
        user.setEmail("abcd123@aol.com");

        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        System.out.println(result.getMessages());
        assertTrue(result.getMessages().contains("Password must contain a digit 0-9."));
    }

    @Test
    void userCannotBeNull() {
        User user = null;
        Result<User> result = service.add(user);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("User cannot be null"));

    }

    @Test
    void shouldNotAddDuplicateUser() {
        User userIn = new User(null, List.of("ADMIN"), "abcd@gmail.com", "Abcd123!!", "test");
        User userOut = new User("AAAA", List.of("User"), "D@gmail.com", "Abcd123!!", "test");

        userOut.setUserId("xxx");

        when(repository.findAll()).thenReturn(List.of(userOut));
        Result<User> result = service.add(userIn);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Username already exists."));
    }

    @Test
    void shouldNotEditInvalidUser(){
        User userIn = new User("XXXX", List.of("ADMIN"), "abcd@gmail.com", "Abcd123!!", "test");

        Result<User> result = service.edit(userIn);

        assertFalse(result.isSuccess());
        System.out.println(result.getMessages());
        assertTrue(result.getMessages().contains("User ID: XXXX, not found"));

    }

    @Test
    void shouldNotEditWithNullPassword(){
        User userIn = new User("XXXX", List.of("ADMIN"), "abcd@gmail.com", null, "test");

        Result<User> result = service.edit(userIn);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("Password cannot be null"));
    }

    @Test
    void shouldFindByUserName(){
       User user = USER_1;
        when(repository.findById(user.getUserId())).thenReturn(USER_1);

        User userOut = service.findById(user.getUserId());

        assertTrue(user.equals(userOut));
    }




}