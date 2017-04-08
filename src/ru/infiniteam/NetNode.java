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
                Block t = srv.receiveBlock();
                //записать блок в базу
            }
        }
    }

    public class ClientThread extends Thread {
        @Override
        public void run() {
            NetClient clt = new NetClient("PLACEHOLDER", PORT);
            //TODO client thread

        }
    }

    NetNode(){
        ServerThread srv = new ServerThread();
        srv.start();
    }
}
