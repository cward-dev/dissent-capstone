package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class FeedbackTag {

    int feedbackTagId;

    private final int MAX_CHARACTERS = 255;
    @NotBlank(message = "Feedback Tag name cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Feedback Tag name must be between 1 and 255 characters")
    String name;

    boolean isActive;

    public FeedbackTag(){
    }

    public FeedbackTag(String name) {
        this.name = name;
    }

    public FeedbackTag(int feedbackTagId, String name, boolean isActive) {
        this.feedbackTagId = feedbackTagId;
        this.name = name;
    }

    public int getFeedbackTagId() {
        return feedbackTagId;
    }

    public void setFeedbackTagId(int feedbackTagId) {
        this.feedbackTagId = feedbackTagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFeedbackTagId());
    }
}
