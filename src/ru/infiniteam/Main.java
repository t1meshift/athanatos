package ru.infiniteam;

import java.util.Calendar;

import static ru.infiniteam.Constants.*;

public class Main extends GUIofPrj{

    public static void main(String[] args) {
        BlockchainDB db = DBManager.get();
        FileIO pasta = new FileIO("pasta.txt", "rw");
        byte[] dataGenesis = pasta.readChunk();
        db.makeGenesis(dataGenesis);
        try {
            Block newBlock = new Block("здроствуйте как маритават огурци".getBytes("UTF-8"), db.lastValue().block_hash);
            db.writeValue(newBlock);
            db.DBG_getAllRows();
            String dataHash = newBlock.block_hash;
            Block bl = db.readValue(dataHash);
            System.out.println(new String(bl.data));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        new GUIofPrj();
    }
}
