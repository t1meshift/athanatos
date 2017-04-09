package ru.infiniteam;

import static ru.infiniteam.Constants.*;

/**
 * Created by Boris on 08.04.2017.
 */
public class NetNode {

    public class ServerThread extends Thread {
        @Override
        public void run() {
            ClientServer srv = new ClientServer(Constants.PORT);
            srv.serverEstablish();
            while (!isInterrupted()){
                srv.receiveCmds(); //TODO REWRITE THIS MODULE
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
            ClientServer clt = new ClientServer(ip, PORT);
            clt.clientConnect();

        }
    }

    NetNode(){
        ServerThread srv = new ServerThread();
        srv.start();
    }
}
