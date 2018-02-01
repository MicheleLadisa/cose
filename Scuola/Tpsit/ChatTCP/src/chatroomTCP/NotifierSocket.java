package chatroomTCP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

 // @author ladis

class NotifierSocket
{

    LinkedList<Socket> listSocket = new LinkedList();
    PrintWriter out;

    public synchronized void notify(String s, Socket socket)
    {
        try
        {
            System.out.println("chat \""+s+"\"");
            for (int i = 0; i < listSocket.size(); i++)
            {
                if (listSocket.get(i) != socket)
                {
                    out = new PrintWriter(listSocket.get(i).getOutputStream(), true);
                    out.println(s);
                }
            }
        } catch (IOException ex)
        {
            Logger.getLogger(NotifierSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void remove(Socket s)
    {
        for (int i = 0; i < listSocket.size(); i++)
        {
            if (s == listSocket.get(i))
            {
                listSocket.remove(i);
            }
        }
    }

    public synchronized void add(Socket s)
    {
        listSocket.add(s);
    }
}