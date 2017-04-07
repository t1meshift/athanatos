package ru.infiniteam;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by t1meshft on 07.04.2017.
 */
public class NetServer {
    //public static final int PORT = 8080;

    NetServer(final int PORT){
        try {
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("Waiting for a client...");
            Socket socket = ss.accept();
            System.out.println("Got a client");
        } catch(Exception x) { x.printStackTrace(); throw new Error();}
    }
}
