package capstone.dissent.models;

public class FeedbackTagHelper {

    String title;
    int value;
    String color;

    public FeedbackTagHelper(String title, int value, String color) {
        this.title = title;
        this.value = value;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
