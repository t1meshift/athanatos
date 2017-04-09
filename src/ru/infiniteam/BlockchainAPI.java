package ru.infiniteam;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import com.esotericsoftware.kryonet.Client;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest512;

import static ru.infiniteam.Constants.*;

/**
 * Created by t1meshft on 07.04.2017.
 */

public class BlockchainAPI
{
    private static final BlockchainDB db = DBManager.get();
    private static final NetNode net = new NetNode(PORT);
    static void downloadFile(KeyFile file)
    {
        /*
        This function downloads file from network.
         */
        String filename = file.getFileName();
        ArrayList<KeyFile.BlockPair> keys = file.getKeys();

        //BlockchainDB db = DBManager.get();
        for (KeyFile.BlockPair currKey : keys)
        {
            Block cachedLocal = db.readValue(currKey.block_hash);
            if (cachedLocal == null)
            {
                //TODO load from network!
            }
        }
    }

    static void uploadFile(String filePath)
    {
        /*
        This function uploads file to network.
         */
        //BlockchainDB db = DBManager.get();
        FileIO file = new FileIO(filePath, "r");
        while (file.isEOF())
        {
            byte[] data = file.readChunk();
            //TODO getLastBlock(); (sync with net)

            //************************************************

            //TODO encrypt (AES)

            try {
                int length = new Random().nextInt(49)+16; //[16; 64] bounds
                String key = Crypto.generatePassword(length);
                CipherParameters params = new ParametersWithIV(
                        new KeyParameter( key.getBytes() ),
                        "FEDCBA9876543210".getBytes() //FIXME static IV
                );
                byte[] encryptedData = Crypto.AES.encrypt(params, data);
            } catch (Exception e){}

            //**************************************************

            //TODO call Block constructor (encrypted_data, last_block_hash)
            //TODO upload wih client request
            //TODO add new Block's hash and password to KeyFile
        }
    }

    static void sync()
    {
        for (Client i : net.clts) {
            i.sendTCP(NetPacket.getLastBlock());
            //TODO fetch prev blocks till we reached our last block
            //then we saving blocks from stack
        }
    }
}
