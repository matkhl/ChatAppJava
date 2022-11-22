package serverside;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageHandler extends Thread {

    private final List<ConnectionHandler> connectionHandlers;
    private final List<Message> messages;

    public MessageHandler(List<ConnectionHandler> connectionHandlers, List<Message> messages) throws IOException {
        this.connectionHandlers = connectionHandlers;
        this.messages = messages;
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            synchronized (messages) {
                synchronized (connectionHandlers) {
                    for (int i = 0; i < messages.size(); i++) {
                        Message msg = messages.get(0);
                        System.out.println("Recieved message: " + new String(msg.getBytes(), StandardCharsets.UTF_8));
                        int port = msg.getSenderPort();
                        for (ConnectionHandler connectionHandler : connectionHandlers) {
                            if (connectionHandler.getPort() != port)
                                connectionHandler.sendBytes(msg.getBytes());
                        }
                        messages.remove(0);
                    }
                }
            }
        }
    }
}
