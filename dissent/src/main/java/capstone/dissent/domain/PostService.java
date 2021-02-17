package capstone.dissent.domain;

import capstone.dissent.data.PostRepository;
import capstone.dissent.models.Post;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository repository;

    Validator validator;

    public PostService(PostRepository repository) {
        this.repository = repository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public List<Post> findByArticleId(String articleId) {
        return repository.findByArticleId(articleId);
    }

    public List<Post> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByTimestampRange(start, end);
    }

    public Post findById(String postId) {
        return repository.findById(postId);
    }

    public Result<Post> add(Post post) {
        Result<Post> result = validate(post);
        if (!result.isSuccess()) {
            return result;
        }

        if (post.getPostId() != null) {
            result.addMessage("Post ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        if (checkForSpam(post)) {
            result.addMessage("Cannot submit another post so quickly, please try again soon", ResultType.INVALID);
            return result;
        }

        post = repository.add(post);
        result.setPayload(post);
        return result;
    }

    public Result<Post> edit(Post post) {
        Result<Post> result = validate(post);
        if (!result.isSuccess()) {
            return result;
        }

        if (post.getPostId() == null || post.getPostId().isBlank()) {
            result.addMessage("Post ID must be set for `edit` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.edit(post)) {
            result.addMessage(String.format("Post ID: %s, not found", post.getPostId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(String postId) {
        return repository.deleteById(postId);
    }

    private Result<Post> validate(Post post) {
        Result<Post> result = new Result<>();

        if (post == null) {
            result.addMessage("Post cannot be null", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Post> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        return result;
    }

    private boolean checkForSpam(Post post) { // checks for another post by user in last 30 seconds

        List<Post> posts = findByUserId(post.getUserId());

        return findByUserId(post.getUserId()).stream().anyMatch(p -> p.getTimestamp().isAfter(post.getTimestamp().minusSeconds(30)));
    }
}
