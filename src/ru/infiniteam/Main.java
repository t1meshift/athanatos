package ru.infiniteam;

public class Main {

    public static void main(String[] args) {
        NetServer srv = new NetServer(8841);
        NetClient clt = new NetClient("127.0.0.1",8841);
        /*
        TODO:
        So, I will have to go at 7:00 am and I should leave TODO list in case someone doesn't read Trello board
        -------
        Commands list for client:
        - get block (id)
        - get commands for this client
        - upload block to server (if command received)
        -------
        For server:
        - Get last block in chain
         */
    }
}
