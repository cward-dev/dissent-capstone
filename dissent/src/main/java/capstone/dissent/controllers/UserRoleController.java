package capstone.dissent.controllers;

import capstone.dissent.domain.Result;
import capstone.dissent.domain.UserRoleService;
import capstone.dissent.models.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user-role")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserRole> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{userRoleId}")
    public ResponseEntity<UserRole> findById(@PathVariable int userRoleId){
        UserRole userRole = service.findById(userRoleId);
        if(userRole == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userRole);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody UserRole userRole) {
        Result<UserRole> result = service.add(userRole);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userRoleId}")
    public ResponseEntity<Object> edit(@PathVariable int userRoleId, @RequestBody UserRole userRole) {
        if (userRoleId != userRole.getUserRoleId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<UserRole> result = service.edit(userRole);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }
}
