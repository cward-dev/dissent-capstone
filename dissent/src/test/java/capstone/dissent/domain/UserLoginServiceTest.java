package capstone.dissent.domain;

import capstone.dissent.data.UserLoginRepository;
import capstone.dissent.models.UserLogin;
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
class UserLoginServiceTest {

    @Autowired
    UserLoginService service;

    @MockBean
    UserLoginRepository repository;

    final UserLogin LOGIN_1 = new UserLogin("email@test", "password");
    final UserLogin LOGIN_2 = new UserLogin("emails@test2", "password");
    final UserLogin LOGIN_3 = new UserLogin("TestLoginID3", "userLogin103");
    List<UserLogin> userLogins = new ArrayList<>(Arrays.asList(LOGIN_1, LOGIN_2, LOGIN_3));

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(userLogins);

        List<UserLogin> results = service.findAll();

        assertNotNull(results);
        assertEquals(3,results.size());
    }

    @Test
    void shouldFindById() {
        UserLogin userLoginIn = LOGIN_1;
        when(repository.findById("1")).thenReturn(LOGIN_1);
        UserLogin userLoginOut = service.findById("1");
        assertEquals(userLoginIn,userLoginOut);
    }

    @Test
    void shouldAdd() {
        UserLogin userLoginIn = LOGIN_2;
        userLoginIn.setUserLoginId(null);
        UserLogin userLoginOut = new UserLogin();

        when(repository.add(userLoginIn)).thenReturn(userLoginOut);
        Result<UserLogin> result = service.add(userLoginIn);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(userLoginOut, result.getPayload());

    }

    @Test
    void shouldNotAddWithSetId(){
        UserLogin userLogin = LOGIN_2;
        userLogin.setUserLoginId("TEST_LOGIN_ID");
        Result<UserLogin> result = service.add(userLogin);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("UserLogin ID cannot be set for `add` operation"));
    }

    @Test
    void shouldEdit() {
        UserLogin userLogin = LOGIN_1;
        userLogin.setEmail("newEmail@gmail.com");
        userLogin.setUserLoginId("TEST_LOGIN_ID");

        when(repository.edit(userLogin)).thenReturn(true);
        when(repository.findById(userLogin.getUserLoginId())).thenReturn(userLogin);

        Result<UserLogin> result = service.edit(userLogin);
        assertEquals(ResultType.SUCCESS,result.getType());
    }

    @Test
    void shouldDelete() {
        UserLogin userLogin = LOGIN_3;
        userLogin.setUserLoginId("TEST_LOGINID");

        when(repository.deleteById(userLogin.getUserLoginId())).thenReturn(true);

        boolean success = service.deleteById(userLogin.getUserLoginId());
        assertTrue(success);
    }
}