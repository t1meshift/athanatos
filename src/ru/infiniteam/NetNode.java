package ru.infiniteam;

import com.esotericsoftware.kryonet.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import static ru.infiniteam.Constants.*;

/**
 * Created by Boris (rewrited by t1meshift) on 08.04.2017.
 */
public class NetNode {

    final private Listener listener = new Listener(){
        public void received(Connection c, Object o)
        {
            //TODO SAVE RESULT TO VARIABLE
        }
    };
    public Server srv;
    public ArrayList<Client> clts;


    NetNode(int port){
        srv = new Server();
        srv.start();
        clts.add(new Client());
        clts.get(0).start();
        //TODO connect to host in settings
        try
        {
            srv.bind(port);
            srv.getKryo().register(Block.class); //serialize Block
            srv.getKryo().register(NetPacket.class); //serialize NetPacket
            srv.addListener(new Listener(){
                public void connected(Connection c)
                {
                    InetSocketAddress addr = c.getRemoteAddressTCP();
                    clts.add(new Client());
                    clts.get(clts.size()-1).start();
                    try {
                        clts.get(clts.size() - 1).connect(TIMEOUT_MS, addr.getHostString(), port);
                        clts.get(clts.size() - 1).addListener(listener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
                public void received(Connection c, Object o)
                {
                    NetPacket req = (NetPacket) o;
                    BlockchainDB db = DBManager.get();
                    Block block;
                    switch(req.packet)
                    {
                        case "getBlock":
                            block = db.readValue((String) req.packet);
                            if (block != null)
                                c.sendTCP(block);
                            else
                                BlockchainAPI.sync();
                                //NOT SYNCED
                            break;
                        case "getLastBlock":
                            block = db.lastValue();
                            c.sendTCP(block);
                    }
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
