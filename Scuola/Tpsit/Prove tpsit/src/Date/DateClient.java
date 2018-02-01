package Date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateClient
{

    public static void main(String[] args)
    {
        String indirizzoServer = "localhost";
        Socket s;
        try
        {
            s = new Socket(indirizzoServer, 9090);
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String output = "Data ricevuta: ";
            while (output != null)
            {
                System.out.print(output);
                output = input.readLine();
            }
        } catch (IOException ex)
        {
            Logger.getLogger(DateClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
