package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class FeedbackTag {

    int feedbackTagId;

    private final int MAX_CHARACTERS = 255;
    @NotBlank(message = "Feedback Tag name cannot be blank")
    @Size(max = MAX_CHARACTERS, message = "Feedback Tag name must be between 1 and 255 characters")
    String name;
    @Size(min = 7, max=7, message = "Feedback Tag color hex must be 7 characters")
    String colorHex;

    boolean isActive = true;

    public FeedbackTag(){
    }

    public FeedbackTag(String name, String colorHex) {
        this.name = name;
        this.colorHex = colorHex;
    }

    public FeedbackTag(int feedbackTagId, String name, String colorHex, boolean isActive) {
        this.feedbackTagId = feedbackTagId;
        this.name = name;
        this.colorHex = colorHex;
        this.isActive = isActive;
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

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
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
