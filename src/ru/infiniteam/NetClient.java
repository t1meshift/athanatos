package ru.infiniteam;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by boriswinner on 07.04.2017.
 */
public class NetClient {
    protected String address;
    protected int port;
    protected Socket socket;
    protected InetAddress ipAddress;
    protected InputStream sin;
    protected OutputStream sout;
    DataInputStream in;
    DataOutputStream out;
    ObjectOutputStream serializer;
    ObjectInputStream deserializer;

    NetClient(String address, final int port){
        this.address = address;
        this.port = port;
    }

    public void Connect(){
        try{
            this.ipAddress = InetAddress.getByName(this.address);
            this.socket = new Socket(this.ipAddress, this.port);

            //для передачи по сети строк, примитивов и т.п.
            this.sin = this.socket.getInputStream();
            this.sout = this.socket.getOutputStream();
            this.in = new DataInputStream(this.sin);
            this.out = new DataOutputStream(this.sout);

            //для передачи объектов по сети
            this.serializer = new ObjectOutputStream(this.socket.getOutputStream());
            this.deserializer = new ObjectInputStream(this.socket.getInputStream());

            System.out.println("Connected");
        }catch (Exception e) {e.printStackTrace();}
    }

    public int SendBlock(Block ablock){
        try {
            serializer.writeObject(ablock);
            serializer.flush();
        } catch (IOException e) {
            System.out.println("IOExc");
            e.printStackTrace();
            return (-1);
        }
        return (0);
    }

}
