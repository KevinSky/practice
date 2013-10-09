package kevin.socket.chat.server;

import java.io.IOException;

/**
 * 
 * @author kevin
 *
 */
public class Server 
{
    public static void main( String[] args ) throws IOException
    {
        (new ChatServer()).start();
    }
}
