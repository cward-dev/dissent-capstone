package capstone.dissent.domain;

import capstone.dissent.data.UserRepository;
import capstone.dissent.models.FeedbackTagHelper;
import capstone.dissent.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    Validator validator;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.encoder = encoder;

    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException(username + " not found.");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList());

        return new org.springframework.security.core
                .userdetails
                .User(user.getUsername(), user.getPassword(), authorities);
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

        result = validatePassword(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != null) {
            result.addMessage("User ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user = repository.add(user);
        result.setPayload(user);
        return result;
    }


    public Result<User> edit(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
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

    public List<FeedbackTagHelper> getAllUserFeedbackById(String userId) {
        return repository.getAllUserFeedbackById(userId);
    }

    private Result<User> validatePassword(User user) {
        Result<User> result = new Result();
        String password = user.getPassword();

        // Regex's to check valid password.
        String regexDigit = ".*\\d.*";
        String regexLowerCase = ".*[a-z].*";
        String regexUpperCase = ".*[A-Z].*";
        String regexLength = "(?=\\S+$).{8,20}";
        String regexSpecial = "[a-zA-Z0-9]*";


        if (!password.matches(regexDigit)) {
            result.addMessage("Password must contain a digit 0-9.", ResultType.INVALID);
        }

        if (!(password.matches(regexLowerCase) && password.matches(regexUpperCase))) {
            result.addMessage("Password must contain an upper and lowercase letter.", ResultType.INVALID);
        }

        if (!password.matches(regexLength)) {
            result.addMessage("Password must be between 8 and 20 characters with no white space.", ResultType.INVALID);
        }

        if (password.matches(regexSpecial)) {
            result.addMessage("Password must contain at least one special character [!@#$%^&-+=()]", ResultType.INVALID);
        }

        return result;
    }

    private boolean isDuplicateUsername(List<User> users, User user) {
        return users.stream()
                .filter(u -> !u.getUserId().equals(user.getUserId())) // removes updating user
                .anyMatch(u -> u.getUsername().equals(user.getUsername()));
    }

}
