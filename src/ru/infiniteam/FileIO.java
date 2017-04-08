package ru.infiniteam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

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

    /*public Block readBlock(){

    }*/
}
