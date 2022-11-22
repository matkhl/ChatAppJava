package clientside;

import java.util.List;

public class MessageHandler extends Thread {

    private final ConnectionHandler connectionHandler;
    private final List<Message> msgSendQueue;

    public MessageHandler(ConnectionHandler connectionHandler, List<Message> msgSendQueue) {
        this.connectionHandler = connectionHandler;
        this.msgSendQueue = msgSendQueue;
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            synchronized (msgSendQueue) {
                for (int i = 0; i < msgSendQueue.size(); i++) {
                    Message msg = msgSendQueue.get(0);
                    connectionHandler.sendBytes(msg.getBytes());
                    msgSendQueue.remove(0);
                }
            }
        }
    }
}
