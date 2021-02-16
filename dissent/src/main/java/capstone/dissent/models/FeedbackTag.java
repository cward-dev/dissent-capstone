package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class FeedbackTag {

    private final int MAX_CHARACTERS = 255;
    int tagId;
    @NotNull(message = "Tag name cannot be blank!")
    @Size(max = MAX_CHARACTERS, min = 1, message = "Name has to be between 1 and 255 characters")
    String name;

    public FeedbackTag(String name) {
        this.name = name;
    }

    public FeedbackTag(){

    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getTagId());
    }
}
