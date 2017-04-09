package ru.infiniteam;

import static ru.infiniteam.Constants.*;

/**
 * Created by Boris on 08.04.2017.
 */
public class NetNode {

    public ClientServer srv;
    public ClientServer clt;

    public class ServerThread extends Thread {
        @Override
        public void run() {
            srv = new ClientServer(Constants.PORT);
            srv.serverEstablish();
            while (!isInterrupted()){
                Block b = srv.receiveBlock();
                byte t = b.data[0];
                System.out.println(b); //TODO REWRITE THIS MODULE
            }
        }
    }

    public class ClientThread extends Thread {
        private String ip;
        ClientThread(String ip)
        {
            this.ip = ip;
        }
        @Override
        public void run() {
            clt = new ClientServer(ip, PORT);
            clt.clientConnect();
        }
    }

    NetNode(String address){
        ServerThread srvtrd = new ServerThread();
        srvtrd.start();
        ClientThread clttrd = new ClientThread(address);
        clttrd.start();
    }
}
