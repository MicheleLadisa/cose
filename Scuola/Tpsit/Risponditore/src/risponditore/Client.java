package risponditore;

// @author ladis
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client
{

    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 9090);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s;
            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            boolean end=false;
            while(!end)
            {
                do
                {
                    s=serverInput.readLine();
                    System.out.println(s);
                }while(!s.equalsIgnoreCase("enter a command") || !s.equalsIgnoreCase("you win") || !s.equalsIgnoreCase("you lose"));
                if(s.equalsIgnoreCase("enter a command"))
                {
                    serverOutput.println(userInput.readLine());
                }
                else if(s.equalsIgnoreCase("you win") || !s.equalsIgnoreCase("you lose"))
                {
                    end=true;
                }
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
