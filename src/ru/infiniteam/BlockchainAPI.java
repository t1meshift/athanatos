package ru.infiniteam;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
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
        FileIO save = new FileIO(filename, "rw");
        //BlockchainDB db = DBManager.get();
        for (KeyFile.BlockPair currKey : keys)
        {
            final Block[] block = {db.readValue(currKey.block_hash)};
            final boolean[] done = {false};
            if (block[0] == null)
            {
                Listener l = new Listener(){
                   public void received(Connection c, Object o)
                   {
                        if (o != null)
                        {
                            block[0] = (Block) o;
                            done[0] = true;
                            c.removeListener(this);
                        }
                   }
                };
                Listener l1 = new Listener(){
                    public void received(Connection c, Object o)
                    {
                        if (o != null)
                        {
                            block[0] = (Block) o;
                            done[0] = true;
                            c.removeListener(this);
                        }
                    }
                };
                net.clt1.addListener(l);
                if (net.clt2 != null)
                    net.clt2.addListener(l1);

                net.clt1.sendTCP(NetPacket.getBlock(currKey.block_hash));
                    try{
                        Thread.sleep(5000);}
                    catch(Exception e){}
                if (!done[0])
                    if (net.clt2 != null)
                        net.clt2.sendTCP(NetPacket.getBlock(currKey.block_hash));
                try{
                    Thread.sleep(5000);}
                catch(Exception e){}

                //net.clt1.removeListener(l);
                //net.clt2.removeListener(l);
            }
            byte[] decrypted = {};
            try {
                int length = new Random().nextInt(49)+16; //[16; 64] bounds
                CipherParameters params = new ParametersWithIV(
                        new KeyParameter( currKey.password.getBytes() ),
                        "FEDCBA9876543210".getBytes() //FIXME static IV
                );
                decrypted = Crypto.AES.decrypt(params, block[0].data);
            } catch (Exception e){e.printStackTrace();}

            save.writeChunk(decrypted);
        }
        save.close();
    }

    static void uploadFile(String filePath)
    {
        /*
        This function uploads file to network.
         */
        //BlockchainDB db = DBManager.get();
        System.out.println("UPLOAD START");
        FileIO file = new FileIO(filePath, "r");
        KeyFile key = new KeyFile();
        System.out.println("KEY FILE CREATED");
        List<String> patht = Lists.newArrayList(Splitter.on('\\').trimResults()
            .omitEmptyStrings()
            .split((CharSequence) filePath));
        key.fileName = patht.get(patht.size()-1);
        //System.out.println(key.fileName.getBytes()[0]);
        while (!file.isEOF())
        {
            byte[] data = file.readChunk();
            sync();
            Block last = db.lastValue();
            System.out.println("BLOCK HASH "+last.block_hash);
            byte[] encryptedData = {};
            String key1 = "";

            //************************************************
            System.out.println("CIPHERING...");
            try {
                int length = 16;//new Random().nextInt(49)+16; //[16; 64] bounds
                key1 = Crypto.generatePassword(length);
                CipherParameters params = new ParametersWithIV(
                        new KeyParameter( key1.getBytes() ),
                        "FEDCBA9876543210".getBytes() //FIXME static IV
                );
                encryptedData = Crypto.AES.encrypt(params, data);
                System.out.println(encryptedData.length);
            } catch (Exception e){e.printStackTrace();}

            //**************************************************
            Block newBlock = new Block(encryptedData, last.block_hash);
            net.clt1.sendTCP(NetPacket.uploadBlock(newBlock));
            if (net.clt2 != null)
                net.clt2.sendTCP(NetPacket.uploadBlock(newBlock));

            db.writeValue(newBlock);

            key.addKeyPair(newBlock, key1);
            System.out.println("SUCCESS");
        }
        key.printToFile("key_"+key.fileName+".bckey");
        System.out.println("READY!");
    }

    static void sync()
    {
        Stack<Block> blocks = new Stack<>();
        final boolean[] done = {false};
        Stack<Block> finalBlocks1 = blocks;
        Listener l1 = new Listener() {
            public void received(Connection c, Object o) {
                Block got = (Block) o;
                Block expected = db.lastValue();
                if (got.block_hash != expected.block_hash) {
                    finalBlocks1.push(got);
                    c.sendTCP(NetPacket.getBlock(got.prev_block_hash));
                } else {
                    done[0] = true;
                    c.removeListener(this);
                }
            }
        };
        Listener l2 = new Listener() {
            public void received(Connection c, Object o) {
                Block got = (Block) o;
                Block expected = db.lastValue();
                if (got.block_hash != expected.block_hash) {
                    finalBlocks1.push(got);
                    c.sendTCP(NetPacket.getBlock(got.prev_block_hash));
                } else {
                    done[0] = true;
                    c.removeListener(this);
                }
            }
        };
        net.clt1.addListener(l1);
        net.clt1.sendTCP(NetPacket.getLastBlock());
        try{
            Thread.sleep(1500);}
        catch(Exception e){}
        while (finalBlocks1.size()>0)
        {
            db.writeValue(finalBlocks1.pop());
        }
        //net.clt1.removeListener(l1);

        blocks = new Stack<>();
        done[0] = false;
        Stack<Block> finalBlocks = blocks;
        try {
            net.clt2.addListener(l2);
            net.clt2.sendTCP(NetPacket.getLastBlock());
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
            while (finalBlocks.size() > 0) {
                db.writeValue(finalBlocks.pop());
            }
        }catch (NullPointerException n)
        {
            System.out.println("client only?");
        }
        //net.clt2.removeListener(l1);
    }
}
