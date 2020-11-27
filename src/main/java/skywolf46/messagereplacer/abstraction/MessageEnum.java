package skywolf46.messagereplacer.abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MessageEnum {

    private List<String> messages;

    public MessageEnum(String... msg) {
        this.messages = new ArrayList<>(Arrays.asList(msg));
    }

    public List<String> get() {
        return new ArrayList<>(messages);
    }
}
