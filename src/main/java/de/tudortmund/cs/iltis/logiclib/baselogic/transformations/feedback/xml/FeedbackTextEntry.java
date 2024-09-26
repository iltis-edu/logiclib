package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

public class FeedbackTextEntry {

    private String type, text;

    public FeedbackTextEntry(String type, String text) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}
