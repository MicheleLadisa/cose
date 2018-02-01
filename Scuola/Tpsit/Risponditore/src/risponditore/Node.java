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
public class Node
{

    private String status;
    private Edge[] edges;
    private int MaxEdges; //il numero archi che avrà il nodo.Non è soggetto a cambaimenti essendo una struttura statica
    private int currentEdges = 0; //il numero di archi che ha attualmente il nodo

    public Node(String status, int nMaxOfEdges)
    {
        this.status = status;
        this.MaxEdges = nMaxOfEdges;
        edges = new Edge[nMaxOfEdges];
    }

    public void addEdge(String command, Node endNode)
    {
        if (currentEdges < MaxEdges)
        {
            edges[currentEdges] = new Edge(command, endNode);
            currentEdges++;
        }
        else{
            System.out.println("impossibile creare l'arco con comando"+command+"verso il nodo "+endNode.getStatus());
        }
    }

    public String getStatus()
    {
        return status;
    }

    public Edge[] getEdges()
    {
        return edges;
    }
}
