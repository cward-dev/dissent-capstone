package capstone.dissent.domain;

import capstone.dissent.data.UserRepository;
import capstone.dissent.models.User;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    Validator validator;

    public UserService(UserRepository repository) {
        this.repository = repository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String userId) {
        return repository.findById(userId);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != null) {
            result.addMessage("User ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user = repository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> edit(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() == null || user.getUserId().isBlank()) {
            result.addMessage("User ID must be set for `edit` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.edit(user)) {
            result.addMessage(String.format("User ID: %s, not found", user.getUserId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(String userId) {
        return repository.deleteById(userId);
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (isDuplicateUsername(repository.findAll(), user)) {
            result.addMessage("Username already exists.", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        return result;
    }

    private boolean isDuplicateUsername(List<User> users, User user) {

        return users.stream()
                .filter(u -> !u.getUserId().equals(user.getUserId())) // removes updating user
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));
    }
}
