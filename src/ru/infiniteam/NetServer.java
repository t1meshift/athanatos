package ru.infiniteam;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by boriswinner on 07.04.2017.
 */
public class NetServer {
    protected int port;
    protected ServerSocket ss;
    protected Socket socket;
    protected InputStream sin;
    protected OutputStream sout;
    DataInputStream in;
    DataOutputStream out;
    ObjectOutputStream serializer;
    ObjectInputStream deserializer;


    NetServer(final int PORT){
        this.port = PORT;
    }

    public int Establish(){
        try {
            this.ss = new ServerSocket(this.port);
            System.out.println("Waiting for a client...");
            this.socket = this.ss.accept();
            System.out.println("Got a client");

            //для передачи примитивов по сети
            this.sin = this.socket.getInputStream();
            this.sout = this.socket.getOutputStream();
            this.in = new DataInputStream(this.sin);
            this.out = new DataOutputStream(this.sout);

            //для передачи объектов по сети
            this.serializer = new ObjectOutputStream(this.socket.getOutputStream());
            this.deserializer = new ObjectInputStream(this.socket.getInputStream());
            return (0);
        } catch(Exception x) {return (-1);}
    }

    public Block ReceiveBlock(){
        try {
            return ((Block)deserializer.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
