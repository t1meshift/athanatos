package ru.infiniteam;

import java.util.Calendar;

import static ru.infiniteam.Constants.*;

public class Main extends GUIofPrj{

    public static void main(String[] args) {
        BlockchainDB db = DBManager.get();
        FileIO pasta = new FileIO("pasta.txt", "rw");
        byte[] dataGenesis = pasta.readChunk();
        db.writeValue(new Block(dataGenesis));

        new GUIofPrj();
    }
}
