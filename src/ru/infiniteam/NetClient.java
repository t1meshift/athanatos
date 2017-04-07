package ru.infiniteam;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by t1meshft on 07.04.2017.
 */
public class NetClient {
   // public static final int PORT = 8080;

    NetClient(String address, final int PORT){
        try{
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, PORT);
            System.out.println("Connected");
        } catch(Exception x) { x.printStackTrace(); throw new Error();}
    }
}
