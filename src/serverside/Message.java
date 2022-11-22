package serverside;

public class Message {

    private static int count = 0;
    private final int id;
    private final int senderPort;
    private final byte[] bytes;

    public Message(int senderPort, byte[] bytes) {
        this.id = count;
        this.senderPort = senderPort;
        this.bytes = bytes;
        count++;
    }

    public int getId() {
        return id;
    }

    public int getSenderPort() {
        return senderPort;
    }

    public byte[] getBytes() {
        return bytes;
    }
}