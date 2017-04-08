package ru.infiniteam;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {

        //test server
        NetServer srv = new NetServer(8841);
        srv.Establish();
        Block received = srv.ReceiveBlock();
        System.out.println(received.data[1]);
        byte[] test1 = {9,8};
        Calendar calendar1 = Calendar.getInstance();
        java.util.Date now1 = calendar1.getTime();
        java.sql.Timestamp currentTimestamp1 = new java.sql.Timestamp(now1.getTime());
        Block t1 = new Block(currentTimestamp1,test1,"1","2","3");
        srv.SendBlock(t1);

        //test client
        NetClient clt = new NetClient("127.0.0.1",8841);
        byte[] test = {9,8};
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        Block t = new Block(currentTimestamp,test,"1","2","3");
        clt.Connect();
        clt.SendBlock(t);
        Block received1 = clt.ReceiveBlock();
        System.out.println(received1.data[1]);
        //TODO TEST Blockchain classes!!!!!!!!!!
        // yours, t1meshift.
        /*
        TODO:
        So, I will have to go at 7:00 am and I must leave TODO list in case someone doesn't read Trello board
        -------
        
        Commands to be received by server:
        - get last block
        - get block by id

        by client:
        - download block by id
        - upload block to server (if command received)
        - get commands for this client
         */
    }
}
