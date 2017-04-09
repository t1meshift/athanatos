package ru.infiniteam;

import java.io.*;
import java.util.ArrayList;

import static ru.infiniteam.Constants.*;


/**
 * Created by Boris on 08.04.2017.
 */
public class FileIO {
    RandomAccessFile file;

    FileIO(String name, String mode){
        try {
            this.file = new RandomAccessFile(new File(name),mode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean isEOF()
    {
        boolean result = true;
        try
        {
            result = file.getFilePointer() >= file.length();
        }
        catch (Exception e){}
        return result;
    }

    public byte[] readChunk(){
        ArrayList<Byte> data = new ArrayList<>();
        for (int i = 0; i < BLOCK_SIZE ; ++i){
            try {
                data.add(file.readByte());
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

    public void writeChunk(byte[] data){
        try {
            file.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeString(String input){
        try {
            file.writeUTF(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
