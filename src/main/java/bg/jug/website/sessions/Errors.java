package bg.jug.website.sessions;

import javax.enterprise.inject.Model;
import java.util.List;

/**
 * @author Vladimir Tsanev
 */
@Model
public class Errors {

    private List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
