package ru.infiniteam;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by boriswinner on 07.04.2017.
 */
/*
        TODO:
        So, I will have to go at 7:00 am and I must leave TODO list in case someone doesn't read Trello board
        -------

        Commands to be received by server:
        - get last block
        - get block by id

        by client:
        - download block by id
        - upload block to server (if command received)
        - get commands for this client
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

    public void connect(){
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

    public int sendBlock(Block ablock){
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

    public Block receiveBlock() {
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

    public void closeConnection(){
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
    public Block getBlock(String block_hash)
    {
        return null; //FIXME
    }

}
