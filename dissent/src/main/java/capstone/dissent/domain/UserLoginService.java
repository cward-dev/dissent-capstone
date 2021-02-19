package capstone.dissent.domain;

import capstone.dissent.data.UserLoginRepository;
import capstone.dissent.data.UserRepository;
import capstone.dissent.models.User;
import capstone.dissent.models.UserLogin;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class UserLoginService {

    private final UserLoginRepository repository;

    Validator validator;

    public UserLoginService(UserLoginRepository repository) {
        this.repository = repository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<UserLogin> findAll() {
        return repository.findAll();
    }

    public UserLogin findById(String userLoginId) {
        return repository.findById(userLoginId);
    }

    public Result<UserLogin> add(UserLogin userLogin) {
        Result<UserLogin> result = validate(userLogin);
        if (!result.isSuccess()) {
            return result;
        }

        if (userLogin.getUserLoginId() != null) {
            result.addMessage("UserLogin ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        userLogin = repository.add(userLogin);
        result.setPayload(userLogin);
        return result;
    }

    public Result<UserLogin> edit(UserLogin userLogin) {
        Result<UserLogin> result = validate(userLogin);
        if (!result.isSuccess()) {
            return result;
        }

        if (userLogin.getUserLoginId() == null || userLogin.getUserLoginId().isBlank()) {
            result.addMessage("UserLogin ID must be set for `edit` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.edit(userLogin)) {
            result.addMessage(String.format("UserLogin ID: %s, not found", userLogin.getUserLoginId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(String userLoginId) {
        return repository.deleteById(userLoginId);
    }

    private Result<UserLogin> validate(UserLogin userLogin) {
        Result<UserLogin> result = new Result<>();

        if (userLogin == null) {
            result.addMessage("UserLogin cannot be null", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<UserLogin>> violations = validator.validate(userLogin);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserLogin> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        return result;
    }
}
