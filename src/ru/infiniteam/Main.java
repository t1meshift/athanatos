package ru.infiniteam;

import java.util.Calendar;

public class Main {

    public static final int PORT = 8841;

    public static void main(String[] args) {
        //test server
        NetServer srv = new NetServer(PORT);
        srv.establish();
        Block received = srv.receiveBlock();
        System.out.println(received.data[1]);
        byte[] test1 = {9,8};
        Block t1 = new Block(test1);
        srv.sendBlock(t1);

        //test client
        NetClient clt = new NetClient("127.0.0.1",PORT);
        byte[] test = {9,8};
        Block t = new Block(test);
        clt.connect();
        clt.sendBlock(t);
        Block received1 = clt.receiveBlock();
        System.out.println(received1.data[1]);
    }
}
