package ru.infiniteam;

import java.util.ArrayList;

/**
 * Created by t1meshft on 08.04.2017.
 */
public class KeyFile {
    private String fileName;
    private int size;
    private ArrayList<String> blocks;
    private ArrayList<String> passwords;

    KeyFile(String fileName)
    {
        this.fileName = fileName;
        this.size = 0;
    }

    public void addKeyPair(Block block, String password)
    {
        blocks.add(block.block_hash);
        passwords.add(password);
        size++;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String toString()
    {
        String result = fileName + "\n" +
                String.valueOf(size) + "\n";
        for (int i = 0; i < size; i++)
        {
            result += blocks.get(i) + " " + passwords.get(i) + "\n";
        }
        return result;
    }

}
