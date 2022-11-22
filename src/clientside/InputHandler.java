package clientside;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class InputHandler {

    private final int port;
    private final List<Message> msgSendQueue;

    public InputHandler(int port, List<Message> msgSendQueue) {
        this.port = port;
        this.msgSendQueue = msgSendQueue;
    }

    public void handleInput(String input) {
        if (!input.equals("")) {
            synchronized (msgSendQueue) {
                msgSendQueue.add(new Message(port, input.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
}
