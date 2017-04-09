package ru.infiniteam;

import static ru.infiniteam.Constants.*;

/**
 * Created by Boris on 08.04.2017.
 */
public class NetNode {

    public class ServerThread extends Thread {
        @Override
        public void run() {
            NetServer srv = new NetServer(Constants.PORT);
            srv.establish();
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
            NetClient clt = new NetClient(ip, PORT);
            clt.connect();

        }
    }

    NetNode(){
        ServerThread srv = new ServerThread();
        srv.start();
    }
}
