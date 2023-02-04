package azka.noreen.translateall;

public class ConversationModel {
    public String textEntered;
    public String translated;

    public  int type;
    public static final int LeftConversation=1;
    public static final int RightConversation=0;

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public ConversationModel(String textEntered, String translated, int type) {
        this.textEntered = textEntered;
        this.type = type;
        this.translated = translated;
    }

    public String getTextEntered() {
        return textEntered;
    }

    public void setTextEntered(String textEntered) {
        this.textEntered = textEntered;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
