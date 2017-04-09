package ru.infiniteam;

import com.esotericsoftware.kryonet.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import static ru.infiniteam.Constants.*;

/**
 * Created by Boris (rewrited by t1meshift) on 08.04.2017.
 */
public class NetNode {
    public Server srv;
    public Client clt1, clt2;


    NetNode(int port){
        srv = new Server();
        srv.start();
        clt1 = new Client();
        clt1.start();
        try {
            clt1.connect(TIMEOUT_MS, SERVER_ADDR, port);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            srv.bind(port);
            srv.getKryo().register(Block.class); //serialize Block
            srv.getKryo().register(NetPacket.class); //serialize NetPacket
            srv.addListener(new Listener(){
                public void connected(Connection c)
                {
                    InetSocketAddress addr = c.getRemoteAddressTCP();
                    clt2 = new Client();
                    clt2.start();
                    try {
                        clt2.connect(TIMEOUT_MS, addr.getHostString(), port);
                        //clts.get(clts.size() - 1).addListener(listener);
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
                            else {
                                BlockchainAPI.sync();
                                //NOT SYNCED
                                c.sendTCP(null);
                            }
                            break;
                        case "getLastBlock":
                            block = db.lastValue();
                            c.sendTCP(block);
                            break;
                        case "uploadBlock":
                            block = (Block) req.argument;
                            db.writeValue(block);
                            break;
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
