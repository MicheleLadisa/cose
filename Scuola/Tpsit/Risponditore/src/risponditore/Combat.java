package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ladis
 */
public class Combat implements Runnable
{

    private Node currentNode;
    private Node[] arrayNode = new Node[7];
    private Pg pg;
    private Monster monster;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean end = false;

    public Combat(Socket socket)
    {
        this.socket = socket;
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex)
        {
            Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
        }
        //creazione nodi del grafo
        arrayNode[0] = new Node("mainMenu", 7);
        arrayNode[1] = new Node("menuAttack", 3);
        arrayNode[2] = new Node("menuObjects", 2);
        arrayNode[3] = new Node("magicMenu", 2);
        arrayNode[4] = new Node("pgStatus", 1);
        arrayNode[5] = new Node("victory", 1);
        arrayNode[6] = new Node("defeat", 1);

        //creazione archi dei Nodi
        arrayNode[0].addEdge("attacks", arrayNode[1]);
        arrayNode[0].addEdge("objects", arrayNode[2]);
        arrayNode[0].addEdge("magics", arrayNode[3]);
        arrayNode[0].addEdge("status", arrayNode[4]);
        arrayNode[0].addEdge("escape", arrayNode[6]);
        arrayNode[0].addEdge("victory", arrayNode[5]);
        arrayNode[0].addEdge("defeat", arrayNode[6]);

        arrayNode[1].addEdge("back", arrayNode[0]);
        arrayNode[1].addEdge("light attack", arrayNode[0]);
        arrayNode[1].addEdge("heavy attack", arrayNode[0]);

        arrayNode[2].addEdge("back", arrayNode[0]);
        arrayNode[2].addEdge("used object", arrayNode[0]);

        arrayNode[3].addEdge("back", arrayNode[0]);
        arrayNode[3].addEdge("used magic", arrayNode[0]);

        arrayNode[4].addEdge("return Pg Status", arrayNode[0]);

        currentNode = arrayNode[0];
    }

    public Node getCurrentNode()
    {
        return currentNode;
    }

    public String commandList() //ritorno una stringa contenente i comandi disponibili dal nodo corrente
    {
        String s = new String();
        Edge[] edges = currentNode.getEdges();
        for (int i = 0; i < edges.length; i++)
        {
            s = s + edges[i].getCommand() + "\n";
        }
        return s;
    }

    public void useEdge(String command) //si sposta nel grafo utilizzando l'arco con il comando inserito
    {
        boolean commandExist = false;
        Edge[] edges = currentNode.getEdges();
        for (int i = 0; i < edges.length; i++)
        {
            if (command.equals(edges[i].getCommand()))
            {
                commandExist = true;
            }
        }
        if (commandExist)
        {
            int pgDamage;
            int monsterDamage;
            switch (command)
            {
                case "attack":
                    currentNode = arrayNode[1];
                    break;

                case "objects":
                    currentNode = arrayNode[2];
                    break;

                case "status":
                    currentNode = arrayNode[4];
                    break;

                case "escape":
                    currentNode = arrayNode[6];
                    break;

                case "back":
                    currentNode = arrayNode[0];
                    break;

                case "light attack":
                    pgDamage = pg.attack();
                    monster.damage(pgDamage);
                    monsterDamage = monster.attack();
                    pg.damage(monsterDamage);
                    out.println("you attack the monster inflict " + pgDamage + " damage and the monster attack you inflict " + monsterDamage + " damage");
                    currentNode = arrayNode[0];
                    break;

                case "heavy attack":
                    pgDamage = pg.attack() + pg.attack();
                    monster.damage(pgDamage);
                    monsterDamage = monster.attack() + monster.attack();
                    pg.damage(monsterDamage);
                    out.println("you attack the monster inflict " + pgDamage + " damage and the monster attack you inflict " + monsterDamage + " damage");
                    currentNode = arrayNode[0];
                    break;

                case "used object":
                    currentNode = arrayNode[0];
                    break;

                case "used magic":
                    currentNode = arrayNode[0];
                    break;

                case "return Pg Status":
                    out.println("hp: " + pg.getHp() + "\nmp: " + pg.getMp());
                    break;
                case "victory":
                    if (pg.getHp() <= 0)
                    {
                        currentNode = arrayNode[5];
                    } else
                    {
                        out.println("nice try but you can't use this command");
                    }
                    break;
                case "defeat":
                    currentNode = arrayNode[6];
                    break;
                default:
                    out.println("Impossible use the command \"" + command + "\"");
            }
        }
    }

    public void runStatus()
    {
        currentNode.getStatus();
        switch (currentNode.getStatus())
        {
            case "mainMenu":
            {
                out.println("What do you want to do?");
                out.println("attacks\nobjects\nmagics\nstatus\nescape");
                out.println("enter a command");
                try
                {
                    useEdge(in.readLine());
                } catch (IOException ex)
                {
                    Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "menuAttack":
            {
                out.println("What type of attck do you want to do?");
                out.println("light attack\nheavy attack");
                out.print("enter a command");
                try
                {
                    useEdge(in.readLine());
                } catch (IOException ex)
                {
                    Logger.getLogger(Combat.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "menuObjects":
            {
                break;
            }
            case "megicMenu":
            {
                break;
            }
            case "pgStatus":
            {
                useEdge("return Pg Status");
                break;
            }
            case "vinctory":
            {
                end = true;
                out.println("you win");
                break;
            }
            case "defeat":
            {
                end = true;
                out.println("you lost");
                break;
            }
        }
    }

    @Override
    public void run()
    {
        System.out.println("combattimento lanciato");
        //out.println("This is a fight simulator, after a question you have to type un command between those reported");
        //out.println("Evry menu have a command \"back\" go back to the previous menu");
        //out.println("Start fighting\n" + "A monster appears in front of you");
        while (!end)
        {
            runStatus();
        }
        out.println("end");
    }
}
