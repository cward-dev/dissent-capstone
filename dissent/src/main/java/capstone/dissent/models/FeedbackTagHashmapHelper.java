package capstone.dissent.models;

public class FeedbackTagHashmapHelper {

    int occurrences;
    String colorHex;

    public FeedbackTagHashmapHelper(Integer occurrences, String colorHex) {
        this.occurrences = occurrences;
        this.colorHex = colorHex;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
