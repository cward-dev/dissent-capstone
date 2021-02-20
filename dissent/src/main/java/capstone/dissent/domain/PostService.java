package capstone.dissent.domain;

import capstone.dissent.data.PostFeedbackTagRepository;
import capstone.dissent.data.PostRepository;
import capstone.dissent.models.Post;
import capstone.dissent.models.PostFeedbackTag;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostFeedbackTagRepository postFeedbackTagRepository;

    Validator validator;

    public PostService(PostRepository postRepository, PostFeedbackTagRepository postFeedbackTagRepository) {
        this.postRepository = postRepository;
        this.postFeedbackTagRepository = postFeedbackTagRepository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByArticleId(String articleId) {
        return postRepository.findByArticleId(articleId);
    }

    public List<Post> findByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public List<Post> findByTimestampRange(LocalDateTime start, LocalDateTime end) {
        return postRepository.findByTimestampRange(start, end);
    }

    public Post findById(String postId) {
        return postRepository.findById(postId);
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

        post = postRepository.add(post);
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

        if (!postRepository.edit(post)) {
            result.addMessage(String.format("Post ID: %s, not found", post.getPostId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(String postId) {
        return postRepository.deleteById(postId);
    }

    public Result<Void> addFeedbackTag(PostFeedbackTag postFeedbackTag) {
        Result<Void> result = validate(postFeedbackTag);
        if (!result.isSuccess()) {
            return result;
        }

        if (!postFeedbackTagRepository.add(postFeedbackTag)) {
            result.addMessage("Post feedback tag not added", ResultType.INVALID);
        }

        return result;
    }

    public boolean deleteFeedbackTagByKey(String postId, String userId, int feedbackTagId) {
        return postFeedbackTagRepository.deleteByKey(postId, userId, feedbackTagId);
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

    private Result<Void> validate(PostFeedbackTag postFeedbackTag) {
        Result<Void> result = new Result<>();
        if (postFeedbackTag == null) {
            result.addMessage("Post Feedback Tag cannot be null", ResultType.INVALID);
            return result;
        }

        if (postFeedbackTag.getFeedbackTag() == null) {
            result.addMessage("Feedback Tag cannot be null", ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForSpam(Post post) { // checks for another post by user in last 30 seconds

        List<Post> posts = findByUserId(post.getUser().getUserId());

        return posts.stream().anyMatch(p -> p.getTimestamp().isAfter(LocalDateTime.now().minusSeconds(10)));
    }
}
