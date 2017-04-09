package ru.infiniteam;

import java.util.ArrayList;

/**
 * Created by t1meshft on 08.04.2017.
 */
public class KeyFile {
    private String fileName;
    private ArrayList<BlockPair> blocks;

    class BlockPair {
        public String block_hash;
        public String password;
        BlockPair(String block_hash, String password)
        {
            this.block_hash = block_hash;
            this.password = password;
        }
    }

    KeyFile(String fileName)
    {
        this.fileName = fileName;
    }

    public void addKeyPair(Block block, String password)
    {
        blocks.add(new BlockPair(block.block_hash, password));
    }

    public String getFileName()
    {
        return fileName;
    }

    public BlockPair getBlockPair(int index)
    {
        return blocks.get(index);
    }

    public ArrayList<BlockPair> getKeys()
    {
        return blocks;
    }

    public String toString()
    {
        String result = fileName + "\n" +
                String.valueOf(blocks.size()) + "\n";
        for (BlockPair currBlock : blocks)
        {
            result += currBlock.block_hash + " " + currBlock.password + "\n";
        }
        return result;
    }

    public void printToFile(String name){
        String res = toString();
        FileIO out = new FileIO(name,"w");
        out.writeString(res);
    }

    public KeyFile readFromFile(String file){
        KeyFile res = null;
        FileIO in = new FileIO(file,"r");
        this.fileName = in.readString();
        for (BlockPair currBlock : blocks)
        {
            String t = in.readString();
            String s1 = t.split(" ")[0];
            String s2 = t.split(" ")[1];
            BlockPair bt = new BlockPair(s1,s2);
            blocks.add(bt);
        }
        return (res);
    }
}
