package ru.infiniteam;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest512;

/**
 * Created by t1meshft on 07.04.2017.
 */

public class BlockchainAPI
{
    void downloadFile(KeyFile file)
    {
        /*
        This function downloads file from network.
         */
        String filename = file.getFileName();
        ArrayList<KeyFile.BlockPair> keys = file.getKeys();

        BlockchainDB db = DBManager.get();
        for (KeyFile.BlockPair currKey : keys)
        {
            Block cachedLocal = db.readValue(currKey.block_hash);
            if (cachedLocal == null)
            {
                //TODO load from network!
            }
        }
    }

    void uploadFile(String filePath)
    {
        /*
        This function uploads file to network.
         */
        BlockchainDB db = DBManager.get();
        FileIO file = new FileIO(filePath, "r");
        while (file.isEOF())
        {
            byte[] data = file.readChunk();
            //TODO getLastBlock(); (sync with net)
            //TODO encrypt (AES? Threefish?)
            //TODO call Block constructor (encrypted_data, last_block_hash)
            //TODO upload wih client request
            //TODO add new Block's hash and password to KeyFile
        }
    }
}
