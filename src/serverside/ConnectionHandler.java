package serverside;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionHandler {

    private final Socket s;
    private final List<Message> messages;
    private final int port;

    private boolean connected;
    private final LinkedBlockingDeque<byte[]> sendQueue = new LinkedBlockingDeque<byte[]>();

    public ConnectionHandler(Socket s, List<Message> messages) throws IOException {
        this.s = s;
        this.messages = messages;
        this.port = s.getPort();
        this.connected = s.isConnected();

        this.s.setTcpNoDelay(true);

        Thread receiveThread = new Thread(this::receive);
        Thread sendThread = new Thread(this::send);

        receiveThread.start();
        sendThread.start();

        System.out.println("Created new ConnectionHandler for client on port " + port);
    }

    private void receive() {
        DataInputStream in = null;
        try {
            in = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            connected = false;
        }
        while (connected) {
            try {
                int buffer = in.readInt();
                byte[] bytes = in.readNBytes(buffer);
                if (bytes != null && bytes.length > 0) {
                    synchronized (messages) {
                        messages.add(new Message(port, bytes));
                    }
                }
            } catch (IOException e) {
                connected = false;
            }
        }
    }

    private void send() {
        while (connected) {
            synchronized (sendQueue) {
                byte[] toSend = null;
                try {
                    toSend = sendQueue.removeFirst();
                } catch (NoSuchElementException e) {
                    continue;
                }
                if (toSend != null && toSend.length > 0) {
                    try {
                        DataOutputStream out = new DataOutputStream(s.getOutputStream());
                        out.writeInt(toSend.length);
                        out.write(toSend);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int getPort() {
        return port;
    }

    public boolean isConnected() {
        return connected;
    }

    public void sendBytes(byte[] bytes) {
        synchronized (sendQueue) {
            sendQueue.offer(bytes);
        }
    }

    public void close() {
        connected = false;
    }
}
