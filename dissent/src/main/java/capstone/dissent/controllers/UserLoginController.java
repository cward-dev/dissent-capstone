package capstone.dissent.controllers;

import capstone.dissent.domain.Result;
import capstone.dissent.domain.UserLoginService;
import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/id/{userLoginId}")
    public ResponseEntity<UserLogin> findById(@PathVariable String userLoginId){
        UserLogin userLogin = service.findById(userLoginId);
        if(userLogin == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userLogin);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody UserLogin userLogin) {
        Result<UserLogin> result = service.add(userLogin);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userLoginId}")
    public ResponseEntity<Object> edit(@PathVariable String userLoginId, @RequestBody UserLogin userLogin) {
        if (!userLoginId.equals(userLogin.getUserLoginId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<UserLogin> result = service.edit(userLogin);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userLoginId}")
    public ResponseEntity<UserLogin> deleteById(@PathVariable String userLoginId) {
        if (service.deleteById(userLoginId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
