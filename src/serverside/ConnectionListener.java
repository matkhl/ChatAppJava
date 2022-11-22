package serverside;

import java.io.*;
import java.net.*;
import java.util.List;

public class ConnectionListener extends Thread {

    private final int port;
    private final List<ConnectionHandler> connectionHandlers;
    private final List<Message> messages;

    public ConnectionListener(int port, List<ConnectionHandler> connectionHandlers, List<Message> messages) throws IOException {
        this.port = port;
        this.connectionHandlers = connectionHandlers;
        this.messages = messages;
    }

    @SuppressWarnings({"InfiniteLoopStatement", "resource"})
    @Override
    public void run() {
        while (true) {
            ServerSocket ss;
            try {
                ss = new ServerSocket(port);
                Socket s = ss.accept();
                if (s.isConnected()) {
                    synchronized (connectionHandlers) {
                        connectionHandlers.add(new ConnectionHandler(s, messages));
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }
}
