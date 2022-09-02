package a.b.utils.packet.server;

import a.b.utils.packet.Packet;

public class PacketChat extends Packet {
    public String message;
    public PacketChat() {  }
    public PacketChat(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}