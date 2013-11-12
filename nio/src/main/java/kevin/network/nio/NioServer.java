package kevin.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioServer {
    Selector selector;
    

    public void listen() throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        selector = Selector.open();
        ss.configureBlocking(false);
        ss.socket().bind(new InetSocketAddress(9999));
        ss.register(selector, SelectionKey.OP_ACCEPT);
    }
    
    public void select() {
        selector.select();
        Iterator it = selector.selectedKeys().iterator();
        while(it.hasNext()) {
            
        }
    }
}
