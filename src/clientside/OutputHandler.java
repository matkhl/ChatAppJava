package clientside;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OutputHandler extends Thread {

    private final List<Message> msgReceiveQueue;
    private final GuiHandler gui;

    public OutputHandler(List<Message> msgReceiveQueue, GuiHandler gui) throws IOException {
        this.msgReceiveQueue = msgReceiveQueue;
        this.gui = gui;
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    @Override
    public void run() {
        while (true) {
            synchronized (msgReceiveQueue) {
                for (int i = 0; i < msgReceiveQueue.size(); i++) {
                    Message msg = msgReceiveQueue.get(0);
                    String newText = new String(msg.getBytes(), StandardCharsets.UTF_8);
                    msgReceiveQueue.remove(0);
                    synchronized (gui) {
                        gui.updateChatText(newText, false);
                    }
                }
            }
        }
    }
}
