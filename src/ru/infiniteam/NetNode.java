package ru.infiniteam;

/**
 * Created by Boris on 08.04.2017.
 */
public class NetNode {

    public class ServerRunnable implements Runnable {
        @Override
        public void run() {
            NetServer srv = new NetServer(Constants.PORT);
            srv.establish();
            while (true){
                Block t = srv.receiveBlock();
                //записать блок в базу
            }
        }
    }

    /*public class ClientRunnable implements Runnable {
        @Override
        public void run() {
            NetClient clt = new NetClient()
        }
    }*/

    NetNode(){
        Thread sThread = new Thread(new ServerRunnable());
        sThread.start();
    }
}
