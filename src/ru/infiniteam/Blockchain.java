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
    //TODO GET LAST BLOCK!!!!!!!!!!
}
