package ru.infiniteam;

public class Main {

    public static void main(String[] args) {
        NetServer srv = new NetServer(8841);
        NetClient clt = new NetClient("127.0.0.1",8841);
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
