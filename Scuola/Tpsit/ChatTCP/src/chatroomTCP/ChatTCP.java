package chatroomTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

//@author ladis
public class ChatTCP
{

    public static void main(String[] args) throws IOException
    {
        SQLiteHelper.newDatabase();
        SQLiteHelper.newTable();
        ServerSocket server = new ServerSocket(9090);
        System.out.println("Il server chatTCP è stato lanciato con successo");
        Socket s;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        NotifierSocket notifier = new NotifierSocket();
        try
        {
            while (true)
            {
                s = server.accept();
                executor.execute(new ClientConnection(s,notifier));
                System.out.println("Un client si è connesso");
            }
        } finally
        {
            executor.shutdown();
        }
    }
}

class ClientConnection implements Runnable
{

    Socket s;
    BufferedReader in;
    PrintWriter out;
    String username;
    String password;
    NotifierSocket notifier;

    public ClientConnection(Socket s, NotifierSocket notifier)
    {
        this.s = s;
        this.notifier = notifier;
        try
        {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException ex)
        {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run()
    {
        try
        {
            String input;
            out.println("You are connected to the server");
            boolean access = false;
            
            while(!access)
            {
                input=in.readLine();
                username=in.readLine();
                password=in.readLine();
                if(input.compareToIgnoreCase("sing in")==0)
                {
                   if(SQLiteHelper.checkCredentials(username, password))
                   {
                       out.println("access granted");
                       access=true;
                   }
                   else
                   {
                       out.println("access denied");
                   }
                }
                else if(input.compareToIgnoreCase("sing up")==0)
                {
                    if(SQLiteHelper.addNewUser(username, password))
                    {
                       out.println("account created"); 
                       access=true;
                    }
                    else
                    {
                       out.println("account already exist"); 
                    }
                }
            }
            
            System.out.println("A client signed in with the account \""+username+"\"");
            notifier.add(s);
            notifier.notify(username+" is connected",s);
            while (true)
            {
                input = in.readLine();
                notifier.notify(username + ": " + input,s);
            }
            
        } catch (IOException ex)
        {
            System.out.println("THe client \""+username+"\" disconected");
        } finally
        {
            notifier.notify(username+" is disconected",s);
            notifier.remove(s);
        }
    }
}