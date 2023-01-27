package azka.noreen.translateall;

public class ConversationModel {
    public String textEntered;
    public  int type;
    public static final int LeftConversation=1;
    public static final int RightConversation=0;

    public ConversationModel(String textEntered, int type) {
        this.textEntered = textEntered;
        this.type = type;
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
