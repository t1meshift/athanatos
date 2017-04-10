package ru.infiniteam;

import com.esotericsoftware.kryonet.*;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.ArrayList;

import static ru.infiniteam.BlockchainAPI.sync;
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
        srv.getKryo().register(Block.class); //serialize Block
        srv.getKryo().register(NetPacket.class); //serialize NetPacket
        srv.getKryo().register(byte[].class);
        srv.getKryo().register(Timestamp.class);
        srv.getKryo().register(java.util.Date.class);
        clt1 = new Client();
        clt1.start();
        clt1.getKryo().register(Block.class); //serialize Block
        clt1.getKryo().register(NetPacket.class); //serialize NetPacket
        clt1.getKryo().register(byte[].class);
        clt1.getKryo().register(Timestamp.class);
        clt1.getKryo().register(java.util.Date.class);

        try {
            clt1.connect(TIMEOUT_MS, SERVER_ADDR, port);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            srv.bind(port);
            srv.addListener(new Listener(){
                public void connected(Connection c)
                {
                    InetSocketAddress addr = c.getRemoteAddressTCP();
                    if (clt2 == null && clt1.getRemoteAddressTCP() != addr) {
                        clt2 = new Client();
                        clt2.start();
                        clt2.getKryo().register(Block.class); //serialize Block
                        clt2.getKryo().register(NetPacket.class); //serialize NetPacket
                        clt2.getKryo().register(byte[].class);
                        clt2.getKryo().register(Timestamp.class);
                        clt2.getKryo().register(java.util.Date.class);
                        try {
                            System.out.println(addr.getHostString());
                            clt2.connect(TIMEOUT_MS, addr.getHostString(), port);
                            //clts.get(clts.size() - 1).addListener(listener);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(-1);
                        }
                    }
                }
                public void received(Connection c, Object o)
                {
                    if (o.getClass() != NetPacket.class) return;
                    NetPacket req = (NetPacket) o;
                    BlockchainDB db = DBManager.get();
                    Block block;
                    switch(req.packet)
                    {
                        case "getBlock":
                            block = db.readValue((String) req.argument);
                            if (block != null)
                                c.sendTCP(block);
                            else {
                                sync();
                                //NOT SYNCED
                                //c.sendTCP(null);
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
