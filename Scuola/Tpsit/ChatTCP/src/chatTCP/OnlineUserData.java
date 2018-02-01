package chatTCP;

//@author ladis

import java.net.Socket;


public class OnlineUserData
{
    private String username;
    private Socket connection;
    public OnlineUserData(String username, Socket socket)
    {
        this.username=username;
        this.connection=socket;
    }

    public synchronized String getUsername()
    {
        return username;
    }

    public synchronized Socket getConnection()
    {
        return connection;
    }
}