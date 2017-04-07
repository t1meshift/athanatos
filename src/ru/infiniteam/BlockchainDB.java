package ru.infiniteam;

/**
 * Created by t1meshft on 08.04.2017.
 */
import java.sql.*;

public class BlockchainDB {
    BlockchainDB(String filename){
        //TODO open DB from file
        //TODO if file doesn't exist, we must create new DB file and create table there
        /*
        TODO:
        table called 'blocks'
        columns called as they are represented in Trello/Blockchain class/etc.
         */
    }
    Blockchain.Block readValue(String key){
        //TODO This function must read row with key (block_id)
        return null; //FIXME FILLER
    }
    void writeValue(Blockchain.Block val, String key){
        //TODO This function must write row with key (block_id)
    }

}
