package capstone.dissent.domain;

import capstone.dissent.data.PostRepository;
import capstone.dissent.models.Post;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    public List<Post> findByParentPostId(String parentPostId) {
        return repository.findByParentPostId(parentPostId);
    }

    public List<Post> findByArticleId(String articleId) {
        return repository.findByArticleId(articleId);
    }

    public List<Post> findByUserId(String userId) {
        return repository.findByArticleId(userId);
    }

    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByTimestampRange(start, end);
    }

    public Post findById(String postId) {
        return repository.findById(postId);
    }

    public Result<Post> add(Post post) {
        Result<Post> result = validate(post);

        if (post.getPostId() != null) {
            result.addMessage("Post ID cannot be set for `add` operation.", ResultType.INVALID);
            return result;
        }

        post = repository.add(post);
        result.setPayload(post);
        return result;
    }

    public Post edit(String post) {
//        return repository.findById(post.g);
        return null;
    }

    public Post deleteById(String postId) {
        return repository.findById(postId);
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

        if (checkForSpam(post)) {
            result.addMessage("Cannot submit another post so quickly, please try again soon.", ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForSpam(Post post) {
        return findByUserId(post.getUserId()).stream().anyMatch(p -> p.getTimestamp().isAfter(LocalDateTime.now().minusSeconds(30)));
    }
}
