package capstone.dissent.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Source {

    // properties
    private final int MAX_CHARACTERS = 255;

    // these fields should auto-populate from the API call
    private String sourceId;
    @NotNull(message = "Source name cannot be blank")
    @Size(min = 2, max = MAX_CHARACTERS, message = "Source name must between 2 and 255 characters")
    //Min size of 2 to accommodate Abbreviations of News Source Names
    private String sourceName;
    @NotNull(message = "Website Url cannot be blank")
    @Size(min = 4, max = MAX_CHARACTERS, message = "URL must between 4 and 255 characters")
    //Min size of 4 to accommodate the smallest url {u.co}
    private String websiteUrl;
    @Size(max=255,message = "Description should be less than 255 characters")
    private String description;

    // constructor(s)
    public Source() {

    }

    public Source(String sourceId) {
        this.sourceId = sourceId;
    }

    public Source(String sourceId, String sourceName, String websiteUrl, String description) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.websiteUrl = websiteUrl;
        this.description = description;
    }


    // getters/setters
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
