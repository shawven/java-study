package netty.tcp;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 16:31
 */
public class Message {

    private int length;

    private String text;

    public Message() {
    }

    public Message(String text) {
        this.length = text.length();
        this.text = text;
    }

    public Message(int length, String text) {
        this.length = length;
        this.text = text;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("length=").append(length);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
