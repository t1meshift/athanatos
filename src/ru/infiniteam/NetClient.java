package ru.infiniteam;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by t1meshft on 07.04.2017.
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

    NetClient(String address, final int PORT){
        this.address = address;
        this.port = PORT;
    }

    public int Connect(){
        try{
            this.ipAddress = InetAddress.getByName(this.address);
            this.socket = new Socket(this.ipAddress, this.port);
            this.sin = this.socket.getInputStream();
            this.sout = this.socket.getOutputStream();
            this.in = new DataInputStream(this.sin);
            this.out = new DataOutputStream(this.sout);
            System.out.println("Connected");
            return(0);
        } catch (Exception x) {return(-1);}
    }


}
