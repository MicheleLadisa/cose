package chatTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

// @author ladis
public class Client
{

    public static void main(String[] args)
    {
        try
        {
            Socket s = new Socket("localhost", 9090);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            String command = "";
            String username = "";
            String password;
            boolean access = false;

            //fine dichiarazione variabili 
            System.out.println(in.readLine());
            while (!access)
            {
                while (command.compareToIgnoreCase("sing in") != 0 && command.compareToIgnoreCase("sing up") != 0)
                {
                    System.out.println("Type \"sing in\" if you already have an account \nType \"sing up\" if you don't have an account");
                    command = userInput.readLine();
                    command = command.trim();
                }
                System.out.print("Type name: ");
                username = userInput.readLine().trim();
                System.out.print("Type password: ");
                password = userInput.readLine().trim();
                out.println(command);
                out.println(username);
                out.println(password);
                String serverAnswer = in.readLine();
                if (serverAnswer.compareTo("access garanted") == 0)
                {
                    access = true;
                    System.out.println("access executed");
                } else if (serverAnswer.compareTo("access denied") == 0)
                {
                    System.out.println("Wrong credentials");
                    command = "";
                } else if (serverAnswer.compareTo("the account doesn't exist") == 0)
                {
                    System.out.println("the account doesn't exist");
                    command = "";
                    
                } else if (serverAnswer.compareTo("account created") == 0)
                {
                    access = true;
                    System.out.println("the account was created successfully");
                } else if (serverAnswer.compareTo("account already exist") == 0)
                {
                    System.out.println("the account already exists");
                    command = "";
                }
            }
            executor.execute(new SocketInput(s, in, username));
            executor.execute(new SocketOutput(s, out, userInput, username));
            executor.shutdown();
        } catch (IOException ex)
        {
            System.out.println("Il server non è online");
        }
    }
}

class SocketInput implements Runnable
{

    Socket s;
    BufferedReader in;
    String name;

    public SocketInput(Socket s, BufferedReader in, String name)
    {
        this.s = s;
        this.in = in;
        this.name = name;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                System.out.println(in.readLine());

            }
        } catch (IOException ex)
        {
            System.out.println("Sei stato disconnesso dal server");
        }
    }
}

class SocketOutput implements Runnable
{

    Socket socket;
    String s;
    String name;
    BufferedReader userInput;
    PrintWriter out;

    public SocketOutput(Socket s, PrintWriter out, BufferedReader userInput, String name)
    {
        this.socket = s;
        this.name = name;
        this.out = out;
        this.userInput = userInput;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                out.println(userInput.readLine());
            }
        } catch (IOException ex)
        {
            System.out.println("Sei stato disconnesso dal server");
        }
    }
}
