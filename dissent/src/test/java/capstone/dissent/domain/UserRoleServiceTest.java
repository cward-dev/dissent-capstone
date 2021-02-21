package capstone.dissent.domain;

import capstone.dissent.data.UserRoleRepository;
import capstone.dissent.models.UserRole;
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
class UserRoleServiceTest {

    @Autowired
    UserRoleService service;

    @MockBean
    UserRoleRepository repository;

    final UserRole ADMIN = new UserRole("ADMIN");
    final UserRole USER = new UserRole("USER");
    final UserRole GUEST = new UserRole("GUEST");
    List<UserRole> userRoles = new ArrayList<>(Arrays.asList(ADMIN, USER, GUEST));

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(userRoles);

        List<UserRole> results = service.findAll();

        assertNotNull(results);
        assertEquals(3,results.size());
    }

    @Test
    void shouldFindById() {
        UserRole userRoleIn = ADMIN;
        when(repository.findById(1)).thenReturn(ADMIN);
        UserRole userRoleOut = service.findById(1);
        assertEquals(userRoleIn, userRoleOut);
    }

    @Test
    void shouldAdd() {
        UserRole userRoleIn = USER;
        UserRole userRoleOut = new UserRole();

        when(repository.add(userRoleIn)).thenReturn(userRoleOut);
        Result<UserRole> result = service.add(userRoleIn);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(userRoleOut, result.getPayload());

    }

    @Test
    void shouldNotAddWithSetId(){
        UserRole userRole = GUEST;
        userRole.setUserRoleId(3);
        Result<UserRole> result = service.add(userRole);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("UserRole ID cannot be set for `add` operation"));
    }

    @Test
    void shouldEdit() {
        UserRole userRole = ADMIN;
        userRole.setName("ROLE_ADMIN");
        userRole.setUserRoleId(1);

        when(repository.edit(userRole)).thenReturn(true);
        when(repository.findById(userRole.getUserRoleId())).thenReturn(userRole);

        Result<UserRole> result = service.edit(userRole);
        assertEquals(ResultType.SUCCESS,result.getType());
    }
}