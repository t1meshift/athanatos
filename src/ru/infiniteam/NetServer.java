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

    public Block ReceiveBlock() {
        Block t = null;
        try {
            t = (Block) deserializer.readObject();
        } catch (IOException e) {
            System.out.println("IO");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClNotFnd");
            e.printStackTrace();
        }
        return t;
    }

    public void CloseConnection(){
        try {
            sin.close();
            sout.close();
            in.close();
            out.close();
            serializer.close();
            deserializer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
