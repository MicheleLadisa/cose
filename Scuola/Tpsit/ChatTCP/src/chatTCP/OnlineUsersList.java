package chatTCP;

// @author ladis
import java.net.Socket;
import java.util.LinkedList;

public class OnlineUsersList
{

    LinkedList<OnlineUserData> users = new LinkedList();

    public synchronized Socket getSocketforUsername(String username)// ritorna il socket legato all'utente inserito
    {
        Socket s = null;
        for (int i = 0; i < users.size(); i++)
        {
            if (username.compareTo(users.get(i).getUsername()) == 0)
            {
                s = users.get(i).getConnection();
            }
        }
        return s;
    }

    public synchronized boolean checkOnlineUser(String username) //ritorna true se trova un utente online con l'username inserito
    {
        boolean check=false;
        for(int i=0;i<users.size();i++)
        {
            if(username.compareTo(users.get(i).getUsername()) == 0)
            {
                System.out.println("priva");
                check=true;
                break;
            }
        }
        return check;
    }

    public synchronized String onlineUsersList()
    {
        String onlineList="";
        for(int i=0;i<users.size();i++)
        {
            onlineList=onlineList+users.get(i).getUsername()+"\n";
        }
        return onlineList;
    }
    
    public synchronized void addUsers(String username, Socket connection)
    {
        OnlineUserData u = new OnlineUserData(username, connection);
        users.add(u);
    }

    public synchronized void removeUser(String username)
    {
        for (int i = 0; i < users.size(); i++)
        {
            if (username.compareTo(users.get(i).getUsername()) == 0)
            {
                users.remove();
                break;
            }
        }
    }
}
