package ru.infiniteam;

import java.util.ArrayList;

/**
 * Created by t1meshft on 08.04.2017.
 */
public class KeyFile {
    private String fileName; //имя зашифрованного файла
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

    KeyFile(String inputFileName)
    {
        FileIO in = new FileIO(inputFileName,"r");
        this.fileName = in.readString();
        int i = Integer.parseInt(in.readString());
        for (int j = 0; i < i; ++j)
        {
            String t = in.readString();
            String s1 = t.split(" ")[0];
            String s2 = t.split(" ")[1];
            BlockPair bt = new BlockPair(s1,s2);
            this.blocks.add(bt);
        }
    }

    KeyFile(){
        this.fileName = "";
        blocks = new ArrayList<BlockPair>();
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

}
