package netty.demo.common;

/**
 * @author FS
 * @date 2018/8/14 17:09
 */
public class ProtocolHeader {

    private byte type;

    private byte server;

    private int bodyLength;

    public ProtocolHeader() {

    }

    public ProtocolHeader(byte type, byte server, int bodyLength) {
        this.type = type;
        this.server = server;
        this.bodyLength = bodyLength;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getServer() {
        return server;
    }

    public void setServer(byte server) {
        this.server = server;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }
}
