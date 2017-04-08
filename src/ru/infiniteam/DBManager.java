package ru.infiniteam;

import static ru.infiniteam.Constants.*;
/**
 * Created by t1meshft on 08.04.2017.
 */
public class DBManager {
    public static BlockchainDB get() {
        return database;
    }
    private static BlockchainDB database = new BlockchainDB(DB_NAME);
}
