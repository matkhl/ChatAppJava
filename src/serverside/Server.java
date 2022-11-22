package serverside;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static int port = 5535;

    public static List<ConnectionHandler> connectionHandlers = new ArrayList<ConnectionHandler>();
    public static List<Message> messages = new ArrayList<Message>();

    public static void main(String[] args) throws IOException {

        System.out.println("Starting server");

        ConnectionListener connectionListener = new ConnectionListener(port, connectionHandlers, messages);
        MessageHandler msgHandler = new MessageHandler(connectionHandlers, messages);
        connectionListener.start();
        msgHandler.start();
    }
}