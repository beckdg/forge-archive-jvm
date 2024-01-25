package dev.forgearchive.virtualfs;


public final class VirtualInode {
    public enum Type { FILE, DIR }
    private final String name;
    private final Type type;
    private byte[] content;

    public VirtualInode(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public void setContent(byte[] c) { this.content = c.clone(); }
    public byte[] content() { return content == null ? new byte[0] : content.clone(); }
    public String name() { return name; }
    public Type type() { return type; }

}
