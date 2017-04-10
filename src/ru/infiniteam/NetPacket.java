package ru.infiniteam;

/**
 * Created by t1meshft on 09.04.2017.
 */
public class NetPacket {
    String packet;
    Object argument;
    NetPacket(String packet, Object argument)
    {
        this.packet = packet;
        this.argument = argument;
    }
    public static NetPacket getBlock(String hash)
    {
        return new NetPacket("getBlock", hash);
    }
    public static NetPacket getLastBlock()
    {
        return new NetPacket("getLastBlock", "0");
    }
    public static NetPacket uploadBlock(Block block)
    {
        return new NetPacket("uploadBlock", block);
    }
}
