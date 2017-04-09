package ru.infiniteam;

import java.util.Calendar;

import static ru.infiniteam.Constants.*;

public class Main {

    public static void main(String[] args) {
        NetNode node1 = new NetNode("10.193.57.253");
        byte[] test = {9,8};
        Block t = new Block(test);
        node1.clt.uploadBlock(t);

        //NetNode node2 = new NetNode("");

        /*
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
        */
        FileIO file = new FileIO("test.txt", "r");
        file.readChunk();
        System.out.println(file.isEOF());
        file.readChunk();
        System.out.println(file.isEOF());
    }
}
