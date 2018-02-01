/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

/**
 *
 * @author ladis
 */
public class Edge
{
    private String command;
    private Node endNode;
    
    public Edge(String command, Node endNode)
    {
        this.command=command;
        this.endNode=endNode;
    }

    public String getCommand()
    {
        return command;
    }

    public Node getEndNode()
    {
        return endNode;
    }
}
