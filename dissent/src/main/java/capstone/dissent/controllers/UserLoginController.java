package capstone.dissent.controllers;

import capstone.dissent.domain.UserLoginService;
import capstone.dissent.models.UserLogin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user-login")
public class UserLoginController {

    private final UserLoginService service;

    public UserLoginController(UserLoginService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserLogin> findAll() {
        return service.findAll();
    }
}
