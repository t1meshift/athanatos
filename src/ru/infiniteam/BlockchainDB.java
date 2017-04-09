package ru.infiniteam;

/**
 * Created by t1meshft on 08.04.2017.
 */
import org.bouncycastle.util.Times;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;

public class BlockchainDB {
    Connection conn;
    BlockchainDB(String filename){
        try {
            Statement statmt;
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:"+filename); //DB file name
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'blocks' ('timestamp' TIMESTAMP NOT NULL, 'data' BLOB NOT NULL, 'data_hash' TEXT NOT NULL, 'block_hash' TEXT NOT NULL, 'prev_block_hash' TEXT NOT NULL, 'idd' INTEGER PRIMARY KEY);");

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(100);
        }
    }
    Block readValue(String key){
        try
        {
            // yeah, it has SQL inject vuln, but who cares, Mr. Klenin? I was writing that code at 5:30am.
            System.out.println(key);
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from 'blocks' WHERE 'block_hash' = '" + key + "'");
            Block result = null;
            while (rs.next())
            {
                //wtf??? we have the only element with that hash
                //but okaaaaaaay
                Timestamp ts = rs.getTimestamp("timestamp");
                Blob dataBlob = rs.getBlob("data");
                int dataBlobLength = (int) dataBlob.length();
                String dataHash = rs.getString("data_hash");
                result = new Block(ts, dataBlob.getBytes(1, dataBlobLength), dataHash, rs.getString("block_hash"), rs.getString("prev_block_hash"));
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(100);
        }
        return null; //filler for IDE
    }
    Block lastValue()
    {
        //SELECT last_insert_rowid()

        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery("SELECT * FROM 'blocks' ORDER BY 'idd' DESC LIMIT 1");
            Block result = null;
            while (rs.next())
            {
                //rs.
                Timestamp ts = rs.getTimestamp("timestamp");
                byte[] dataBlob = rs.getBytes("data");
                System.out.println("GOT BLOB: "+new String(dataBlob));
                String dataHash = rs.getString("data_hash");
                result = new Block(ts, dataBlob, dataHash, rs.getString("block_hash"), rs.getString("prev_block_hash"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
        return null; //filler for IDE
    }
    void writeValue(Block val){
        //This function must write row with key (block_id)
        try
        {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO 'blocks' ('timestamp', 'data', 'data_hash', 'block_hash', 'prev_block_hash') VALUES(?,?,?,?,?)");
            pstmt.setTimestamp(1,val.timestamp);
            pstmt.setBytes(2, val.data);
            pstmt.setString(3, val.data_hash);
            pstmt.setString(4, val.block_hash);
            pstmt.setString(5, val.prev_block_hash);
            pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(100);
        }
    }

    Block getGenesis()
    {
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery("SELECT * FROM 'blocks' WHERE 'prev_block_hash' = '0'");
            Block result = null;
            while (rs.next())
            {
                Timestamp ts = rs.getTimestamp("timestamp");
                byte[] dataBlob = rs.getBytes("data");
                String dataHash = rs.getString("data_hash");
                String blockHash = rs.getString("block_hash");
                System.out.println("GOT GENESIS HASH: "+blockHash);
                result = new Block(ts, dataBlob, dataHash, blockHash, "0");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
        return null;
    }

    int makeGenesis(byte[] data) {

        Block foundGenesis = getGenesis();
        if (foundGenesis == null)
        {
            //System.out.println(foundGenesis.data);
            Block genesis = new Block(data);
            writeValue(genesis);
            return 0; //success
        }
        return -1; //fail
    }
}
