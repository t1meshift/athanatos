package ru.infiniteam;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest512;

/**
 * Created by t1meshft on 07.04.2017.
 */

public class Blockchain
{
    public class Block
    {
        protected Timestamp timestamp;
        protected byte[] data;
        protected String data_hash;
        protected String block_hash;
        protected String prev_block_hash;

        Block(byte[] data,String prev_block_hash) {
            DigestSHA3 data_hash = new Digest512();
            data_hash.update(data);

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            DigestSHA3 block_hash = new Digest512();

            byte[] a = ts.toString().getBytes(); // TIMESTAMP
            byte[] b = data_hash.digest(); // DATA_HASH
            byte[] c = prev_block_hash.getBytes(); //PREV_BLOCK_HASH

            byte[] block_bytes = new byte[data.length + a.length + b.length + c.length];
            System.arraycopy(data, 0, block_bytes, 0, data.length);
            System.arraycopy(a, 0, block_bytes, data.length, a.length);
            System.arraycopy(b, 0, block_bytes,data.length + a.length, b.length);
            System.arraycopy(c, 0, block_bytes,data.length + a.length + b.length, c.length);

            block_hash.update(block_bytes);

            this.timestamp = ts;
            this.data = data;
            this.data_hash = data_hash.digest().toString();
            this.block_hash = block_hash.digest().toString();
            this.prev_block_hash = prev_block_hash;
        }

        Block(byte[] data)
        {
            this(data, "0");
        }

        Block(Timestamp timestamp, byte[] data, String data_hash, String block_hash, String prev_block_hash)
        {
            this.timestamp = timestamp;
            this.data = data;
            this.data_hash = data_hash;
            this.block_hash = block_hash;
            this.prev_block_hash = prev_block_hash;
        }
    }
    private  ArrayList<Block> blockchain;
    Blockchain(Block genesisBlock)
    {
        this.blockchain = new ArrayList<>();
        this.blockchain.add(genesisBlock);
    }
    Blockchain(ArrayList<Block> blockchain)
    {
        this.blockchain = blockchain;
    }
}
