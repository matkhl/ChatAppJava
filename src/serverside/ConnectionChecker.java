package serverside;

import java.util.List;

public class ConnectionChecker extends Thread {
    private final List<ConnectionHandler> connectionHandlers;

    public ConnectionChecker(List<ConnectionHandler> connectionHandlers){
        this.connectionHandlers = connectionHandlers;
    }

    @SuppressWarnings({"InfiniteLoopStatement", "resource"})
    @Override
    public void run() {
        while (true) {
            // remove disconnected connectionHandlers
            synchronized (connectionHandlers) {
                for (int i = 0; i < connectionHandlers.size(); i++) {
                    if (!connectionHandlers.get(i).isConnected()) {
                        System.out.println("Client on port " + connectionHandlers.get(i).getPort() + " disconnected");
                        connectionHandlers.remove(i);
                        i--;
                    }
                }
            }
        }
    }
}
