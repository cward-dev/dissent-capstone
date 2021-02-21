package capstone.dissent.domain;

import capstone.dissent.data.UserRoleRepository;
import capstone.dissent.models.UserRole;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;

    Validator validator;

    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<UserRole> findAll() {
        return repository.findAll();
    }

    public UserRole findById(int userRoleId) {
        return repository.findById(userRoleId);
    }

    public Result<UserRole> add(UserRole userRole) {
        Result<UserRole> result = validate(userRole);
        if (!result.isSuccess()) {
            return result;
        }

        if (userRole.getUserRoleId() != 0) {
            result.addMessage("UserRole ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        userRole = repository.add(userRole);
        result.setPayload(userRole);
        return result;
    }

    public Result<UserRole> edit(UserRole userRole) {
        Result<UserRole> result = validate(userRole);
        if (!result.isSuccess()) {
            return result;
        }

        if (userRole.getUserRoleId() == 0) {
            result.addMessage("UserRole ID must be set for `edit` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.edit(userRole)) {
            result.addMessage(String.format("UserRole ID: %s, not found", userRole.getUserRoleId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<UserRole> validate(UserRole userRole) {
        Result<UserRole> result = new Result<>();

        if (userRole == null) {
            result.addMessage("UserRole cannot be null", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<UserRole>> violations = validator.validate(userRole);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserRole> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        return result;
    }
}
