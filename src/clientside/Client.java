package clientside;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static String address = "localhost";
    public static int port = 5535;

    public static List<Message> msgSendQueue = new ArrayList<Message>();
    public static List<Message> msgReceiveQueue = new ArrayList<Message>();

    public static final ChatTextContainer chatTextContainer = new ChatTextContainer();

    public static void main(String[] args) throws IOException {

        System.out.println("Starting client");

        Socket s = new Socket(address, port);
        if (!s.isConnected()) return;

        GuiHandler gui = new GuiHandler(new InputHandler(port, msgSendQueue), msgSendQueue, chatTextContainer);
        ConnectionHandler connectionHandler = new ConnectionHandler(s, msgReceiveQueue);
        MessageHandler messageHandler = new MessageHandler(connectionHandler, msgSendQueue);
        OutputHandler outputHandler = new OutputHandler(msgReceiveQueue, gui);

        messageHandler.start();
        outputHandler.start();
    }
}