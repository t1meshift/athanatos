package ru.infiniteam;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by t1meshft on 07.04.2017.
 */
public class NetServer {
    protected int port;
    protected ServerSocket ss;
    protected Socket socket;
    protected InputStream sin;
    protected OutputStream sout;
    DataInputStream in;
    DataOutputStream out;


    NetServer(final int PORT){
        this.port = PORT;
    }

    public int Establish(){
        try {
            this.ss = new ServerSocket(this.port);
            System.out.println("Waiting for a client...");
            this.socket = this.ss.accept();
            System.out.println("Got a client");
            this.sin = this.socket.getInputStream();
            this.sout = this.socket.getOutputStream();
            this.in = new DataInputStream(this.sin);
            this.out = new DataOutputStream(this.sout);
            return (0);
        } catch(Exception x) {return (-1);}
    }
}
