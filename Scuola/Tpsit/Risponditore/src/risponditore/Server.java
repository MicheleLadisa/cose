package risponditore;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

//@author ladis
public class Server
{
    public static void main (String[] args)
    {
        try
        {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            ServerSocket server=new ServerSocket(9090);
             System.out.println("server lanciato");
            try
            {
                while(true)
                {
                    executor.execute(new Combat(server.accept()));
                    System.out.println("new combat");
                }
            }
            finally
            {
                server.close();
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
