package ru.infiniteam;

public class Main {

    public static void main(String[] args) {
        NetServer srv = new NetServer(8841);
        NetClient clt = new NetClient("127.0.0.1",8841);
    }
}
