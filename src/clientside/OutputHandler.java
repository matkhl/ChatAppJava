package clientside;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OutputHandler extends Thread {

    private final List<Message> msgReceiveQueue;

    public OutputHandler(List<Message> msgReceiveQueue) throws IOException {
        this.msgReceiveQueue = msgReceiveQueue;
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            synchronized (msgReceiveQueue) {
                for (int i = 0; i < msgReceiveQueue.size(); i++) {
                    Message msg = msgReceiveQueue.get(0);
                    System.out.println(new String(msg.getBytes(), StandardCharsets.UTF_8));
                    msgReceiveQueue.remove(0);
                }
            }
        }
    }
}
