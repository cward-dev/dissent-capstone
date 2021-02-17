package capstone.dissent.domain;

import capstone.dissent.data.TopicRepository;
import capstone.dissent.models.Topic;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class TopicService {

    private final TopicRepository repository;

    Validator validator;

    public TopicService(TopicRepository repository) {
        this.repository = repository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Topic> findAll() {
        return repository.findAll();
    }

    public Topic findById(int topicId) {
        return repository.findById(topicId);
    }

    public Result<Topic> add(Topic topic) {
        Result<Topic> result = validate(topic);
        if (!result.isSuccess()) {
            return result;
        }

        if (topic.getTopicId() != 0) {
            result.addMessage("Topic ID cannot be set for `add` operation", ResultType.INVALID);
        }

        topic = repository.add(topic);
        result.setPayload(topic);
        return result;
    }

    public Result<Topic> edit(Topic topic) {
        Result<Topic> result = validate(topic);
        if (!result.isSuccess()) {
            return result;
        }

        if (topic.getTopicId() <= 0) {
            result.addMessage("Topic ID must be set for `add` operation", ResultType.INVALID);
        }

        if (!repository.edit(topic)) {
            result.addMessage(String.format("Topic ID: %s, not found", topic.getTopicId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int topicId) {
        return repository.deleteById(topicId);
    }

    private Result<Topic> validate(Topic topic) {
        Result<Topic> result = new Result<>();

        if (topic == null) {
            result.addMessage("Topic cannot be null", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<Topic>> violations = validator.validate(topic);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Topic> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (checkForDuplicate(topic)) {
            result.addMessage(String.format("Topic Name: %s, already exists", topic.getTopicName()), ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForDuplicate(Topic topic) {
        return findAll().stream().anyMatch(t -> t.getTopicId() != topic.getTopicId()
                && t.getTopicName().equalsIgnoreCase(topic.getTopicName()));
    }
}
