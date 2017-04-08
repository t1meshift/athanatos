package ru.infiniteam;

import java.io.*;
import java.util.ArrayList;

import static ru.infiniteam.Constants.*;


/**
 * Created by Boris on 08.04.2017.
 */
public class FileIO {
    RandomAccessFile in;
    //String tprev_block_hash;

    FileIO(String name, String mode){
        try {
            this.in = new RandomAccessFile(new File("name"),"mode");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] readChunk(){
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 0; i < BLOCK_SIZE ; ++i){
            try {
                data.add(in.readByte());
            } catch (EOFException a) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bdata = new byte[data.size()];
        for(int i = 0; i < data.size(); i++) {
            bdata[i] = data.get(i).byteValue();
        }
        return (bdata);
    }
}
