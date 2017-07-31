/*
 * Index object (Within Items)
 */
package chatbot;

/**
 *
 * @author David
 */
public class Index {

    private String timestamp;

    private String value;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"timestamp\":" + "\"" + timestamp + "\"" + ", \"value\":" + value + "}";
    }
}
