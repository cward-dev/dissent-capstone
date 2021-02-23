package capstone.dissent.domain;

import capstone.dissent.data.FeedbackTagRepository;
import capstone.dissent.models.FeedbackTag;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class FeedbackTagService {

    private final FeedbackTagRepository repository;

    Validator validator;

    public FeedbackTagService(FeedbackTagRepository repository) {
        this.repository = repository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<FeedbackTag> findAll() {
        return repository.findAll();
    }

    public List<FeedbackTag> findAllWithInactive() {
        return repository.findAllWithInactive();
    }

    public FeedbackTag findById(int feedbackTagId) {
        return repository.findById(feedbackTagId);
    }

    public Result<FeedbackTag> add(FeedbackTag feedbackTag) {
        Result<FeedbackTag> result = validate(feedbackTag);
        if (!result.isSuccess()) {
            return result;
        }

        if (feedbackTag.getFeedbackTagId() != 0) {
            result.addMessage("Feedback Tag ID cannot be set for `add` operation", ResultType.INVALID);
        }

        feedbackTag = repository.add(feedbackTag);
        result.setPayload(feedbackTag);
        return result;
    }

    public Result<FeedbackTag> edit(FeedbackTag feedbackTag) {
        Result<FeedbackTag> result = validate(feedbackTag);
        if (!result.isSuccess()) {
            return result;
        }

        if (feedbackTag.getFeedbackTagId() <= 0) {
            result.addMessage("Feedback Tag ID must be set for `add` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.edit(feedbackTag)) {
            result.addMessage(String.format("Feedback Tag ID: %s, not found", feedbackTag.getFeedbackTagId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean activateById(int feedbackTagId) {
        return repository.activateById(feedbackTagId);
    }

    public boolean deleteById(int feedbackTagId) {
        return repository.deleteById(feedbackTagId);
    }

    private Result<FeedbackTag> validate(FeedbackTag feedbackTag) {
        Result<FeedbackTag> result = new Result<>();

        if (feedbackTag == null) {
            result.addMessage("Feedback Tag cannot be null", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<FeedbackTag>> violations = validator.validate(feedbackTag);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<FeedbackTag> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (checkForDuplicate(feedbackTag)) {
            result.addMessage(String.format("Feedback Tag Name: %s, already exists", feedbackTag.getName()), ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForDuplicate(FeedbackTag feedbackTag) {
        return findAllWithInactive().stream().anyMatch(ft -> ft.getFeedbackTagId() != feedbackTag.getFeedbackTagId()
                && ft.getName().equalsIgnoreCase(feedbackTag.getName()));
    }
}
