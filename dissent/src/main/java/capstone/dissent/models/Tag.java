package capstone.dissent.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class Tag {

    private final int MAX_CHARACTERS = 255;
    String tagId;
    @NotNull(message = "Tag name cannot be blank!")
    @Size(max = MAX_CHARACTERS, message = "Name exceeded maximum characters ${MAX_CHARACTERS}")
    String name;

    public Tag(String tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public Tag(){

    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getTagId(), tag.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId());
    }
}
