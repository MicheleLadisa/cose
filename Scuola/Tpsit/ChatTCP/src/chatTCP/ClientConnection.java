package chatTCP;

//@author ladis
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientConnection implements Runnable
{

    Socket connection;
    BufferedReader in;
    PrintWriter out;
    String username;
    String password;
    OnlineUsersList list;
    String destinationUsername;
    PrintWriter destinationMessage;

    public ClientConnection(Socket connection, OnlineUsersList list)
    {
        this.connection = connection;
        this.list = list;
        try
        {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new PrintWriter(connection.getOutputStream(), true);

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
            out.println("you are connected to the server");
            boolean access = false;

            while (!access)
            {
                input = in.readLine();
                username = in.readLine();
                password = in.readLine();
                if (input.compareToIgnoreCase("sing in") == 0)
                {
                    if (SQLiteHelper.checkCredentials(username, password))
                    {
                        if (list.checkOnlineUser(username))
                        {
                            out.println("access denied");
                        } else
                        {
                            out.println("access garanted");
                            access = true;
                            System.out.println("Un untente ha efettuato l'accesso");
                        }
                    } else
                    {
                        if (SQLiteHelper.checkUsername(username))
                        {
                            out.println("access denied");
                            System.out.println("Ad un utednte è stato negato l'accesso");
                        } else
                        {
                            out.println("the account doesn't exist");
                            System.out.println("Un utente ha cercato di accedere con un account inesistente");
                        }
                    }
                } else if (input.compareToIgnoreCase("sing up") == 0)
                {
                    if (SQLiteHelper.addNewUser(username, password))
                    {
                        out.println("account created");
                        access = true;
                        System.out.println("Un untente ha creato un account");
                    } else
                    {
                        out.println("account already exist");
                        System.out.println("Un untente ha porvato a creare un account");
                    }
                }
            }
            list.addUsers(username, connection);

            out.println("Type /help to see the list of command");
            //help
            //cout "users name" change output
            //uonl users online list
            //smes "user name" "message" write message to
            //reto ritorna il tuo output
            while (true)
            {
                input = in.readLine();
                if (input.substring(0, 1).compareTo("/") == 0)
                {
                    String command = input.substring(0, 5);
                    switch (command)
                    {
                        case "/help":
                            out.println("/cout \"users name\"->change output. Change the users that is the destination of your message\n"
                                    + "/uonl->user online list. Return the list of online users\n"
                                    + "/smes \"username\" \"message\"->send message. Send a single message at the user typed whitout change the defoult destination of your message\n"
                                    + "/reto-> return output. Return the name of the user that is selec as the destination of your message\n"
                                    + "Don't put the quotation marks when you use a command");
                            break;
                        case "/cout":
                            destinationUsername = input.substring(6, input.length());
                            if (SQLiteHelper.checkUsername(destinationUsername))
                            {
                                if (list.checkOnlineUser(destinationUsername))
                                {
                                    destinationMessage = new PrintWriter(list.getSocketforUsername(destinationUsername).getOutputStream(), true);
                                    out.println("The user is online and he is selected as destination of your message");
                                } else
                                {
                                    out.println("this user isn't online");
                                    destinationUsername = null;
                                    destinationMessage = null;
                                }
                            } else
                            {
                                out.println("this user dosen't exist");
                                destinationUsername = null;
                                destinationMessage = null;
                            }
                            break;
                        case "/uonl":
                            out.println(list.onlineUsersList());
                            break;
                        case "/smes":
                            for (int i = 6; i < input.length(); i++)
                            {
                                if (input.charAt(i) == ' ')
                                {
                                    String tdun = input.substring(6, i);
                                    System.out.println(tdun);
                                    String message = input.substring(i + 1, input.length());
                                    System.out.println(message);
                                    if (SQLiteHelper.checkUsername(tdun))
                                    {
                                        if (list.checkOnlineUser(tdun))
                                        {
                                            PrintWriter tdm = new PrintWriter(list.getSocketforUsername(tdun).getOutputStream(), true);
                                            tdm.println(username+": "+message);
                                        } else
                                        {
                                            out.println("this user isn't online");
                                        }
                                    } else
                                    {
                                        out.println("this user dosen't exist");
                                    }
                                    break;
                                }
                            }
                            break;
                        case "/reto":
                            out.println(destinationUsername);
                            break;
                        default:
                            out.println("Command not found, type /help for see the list of command ");
                    }
                } else
                {
                    if (destinationMessage != null)
                    {
                        destinationMessage.println(username + ": " + input);
                    } else
                    {
                        out.println("You don't have selected anyone as destination of your message");
                    }
                }
            }

        } catch (IOException ex)
        {
            System.out.println("a user disconect");
        } finally
        {
            list.removeUser(username);
        }
    }
}
