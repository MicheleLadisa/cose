package chatTCP;

//@author ladis
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatTCP
{

    public static void main(String[] args)
    {
        SQLiteHelper.newDatabase();
        SQLiteHelper.newDatabase();
        ServerSocket server;
        Socket s;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        OnlineUsersList list = new OnlineUsersList();
        try
        {
            server = new ServerSocket(9090);
            System.out.println("Il server è stato lanciato con successo");
            while (true)
            {
                s = server.accept();
                executor.execute(new ClientConnection(s, list));
                System.out.println("Un client si è connesso");
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ChatTCP.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
            executor.shutdown();
        }
    }
}