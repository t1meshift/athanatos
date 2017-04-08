package ru.infiniteam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import static ru.infiniteam.Constants.*;


/**
 * Created by Boris on 08.04.2017.
 */
public class FileIO {
    RandomAccessFile in;

    FileIO(String name, String mode){
        try {
            this.in = new RandomAccessFile(new File("name"),"mode");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Block readBlock(){
        Block res = null;
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 0; i < BLOCK_SIZE; ++i){
            try {
                data.add(in.readByte());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data.toArray().
        return (res);
    }
}
