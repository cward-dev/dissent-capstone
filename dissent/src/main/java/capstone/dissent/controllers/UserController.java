package capstone.dissent.controllers;

import capstone.dissent.domain.Result;
import capstone.dissent.domain.UserService;
import capstone.dissent.models.Source;
import capstone.dissent.models.Topic;
import capstone.dissent.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> findById(@PathVariable String userId){
        User user = service.findById(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user = service.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> add(@RequestBody User user) {

        user.getRoles().add("USER");

        Result<User> result = service.add(user);
        if (result.isSuccess()) {
            // happy path
            HashMap<String, String> map = new HashMap<>();
            map.put("appUserId", String.valueOf(user.getUserId()));
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> edit(@PathVariable String userId, @RequestBody User user) {
        if (!userId.equals(user.getUserId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<User> result = service.edit(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteById(@PathVariable String userId) {
        if (service.deleteById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
