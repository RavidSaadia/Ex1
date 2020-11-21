package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private HashMap<Integer, node_info> hmGraphNode;// The graph Nodes List
    private HashMap<Integer, HashMap<Integer, Double>> hmNiEdge;//The node neighbors and the connecting edges



    private int nodeSize;
    private int MC;
    private int edgeSize;
    private static int nodeId = 0;


    public WGraph_DS() {
        hmGraphNode = new HashMap<Integer, node_info>();
        hmNiEdge = new HashMap<Integer, HashMap<Integer, Double>>();
//        hmNi = new HashMap<Integer, HashMap<Integer, node_info>>();
        nodeSize = 0;
        MC = 0;
        edgeSize = 0;

    }

    public WGraph_DS(weighted_graph g) {  //copy constructor

        //hmGraphNode copy
        this.hmGraphNode = new HashMap<Integer, node_info>();  //copy the graph nodes
        LinkedList<node_info> g_nodes = new LinkedList<>(g.getV());
        for (node_info i : g_nodes) {
            node_info j = new Node_Info(i.getKey());
            this.hmGraphNode.put(j.getKey(), j);
        }

        //hmNiEdges copy
        this.hmNiEdge = new HashMap<Integer, HashMap<Integer, Double>>();
        LinkedList<node_info> g_nodesKey =new LinkedList<>(g.getV());

        for (node_info i : g_nodesKey) {
            Integer iKey = i.getKey();
            hmNiEdge.put(iKey, new HashMap<Integer, Double>());
            LinkedList<node_info> g_nodes2 = new LinkedList<>(g.getV(iKey));

            for (node_info j : g_nodes2) {
                Integer jKey = j.getKey();
                hmNiEdge.get(iKey).put(jKey,g.getEdge(iKey,jKey));

            }
        }





        this.MC = g.getMC();
        this.nodeSize = g.nodeSize();
        this.edgeSize = g.edgeSize();
    }


    @Override
    public node_info getNode(int key) {

        return hmGraphNode.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return (hmNiEdge.get(node1).containsKey(node2) && hmNiEdge.get(node2).containsKey(node1));

    }

    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return (hmNiEdge.get(node1).get(node2));

        }
        return -1;
    }


    @Override
    public void addNode(int key) {
        if (!hmGraphNode.containsKey(key)) {        // do only if the key is unique
            hmGraphNode.put(key, new Node_Info(key));
            hmNiEdge.put(key, new HashMap<Integer, Double>());

            nodeSize++;
            MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2 || !hmGraphNode.containsKey(node1) || !hmGraphNode.containsKey(node2) || w < 0) {
            return;
        } // cant connect node to himself or connect to not exist node
        if (hasEdge(node1, node2)) {
            hmNiEdge.get(node1).replace((node2), w);
            hmNiEdge.get(node2).replace((node1), w);
            MC++;
            return;
        }
        hmNiEdge.get(node1).put(node2, w);// add node2 and new edge to node1 Map
        hmNiEdge.get(node2).put(node1, w);// add node1 and new edge to node2 Map

        edgeSize++;
        MC++;
    }

    @Override
    public Collection<node_info> getV() {
        return hmGraphNode.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        LinkedList list = new LinkedList(hmNiEdge.get(node_id).keySet());
        HashMap<Integer, node_info> newHm = new HashMap<>();
        Iterator i = list.iterator();
int counter = 0;
        while (i.hasNext()) {
            newHm.put(counter++, hmGraphNode.get(i.next()));
        }
        return newHm.values();

    }

    @Override
    public node_info removeNode(int key) {


        if (!this.hmGraphNode.containsKey(key)) {// check if exist
            return null;
        }
        node_info removed_Node = this.getNode(key);

        LinkedList<node_info> removedNi = new LinkedList<>(getV(removed_Node.getKey()));// removed_Node neighbor List
        for (node_info i : removedNi) {
            hmNiEdge.get(i.getKey()).remove(removed_Node.getKey());//remove the node from his neighbors

            hmNiEdge.get(removed_Node.getKey()).remove(i.getKey());//remove the neighbors from the node


            edgeSize--;

        }

        hmGraphNode.remove(removed_Node.getKey());
        nodeSize--;
        MC++;
        return removed_Node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (this.hasEdge(node1, node2)) {
            hmNiEdge.get(node1).remove(node2);
            hmNiEdge.get(node2).remove(node1);
            edgeSize--;
            MC++;
        }
    }

    @Override
    public int nodeSize() {
        return nodeSize;
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return MC;
    }


    private class Node_Info implements node_info, Serializable {

        private int key;
        private String info;
        private double tag;

        private Node_Info(int k) {

            key = k;
            info = "";
            tag = 0;
        }

        private Node_Info(Node_Info node) {
            key = node.key;
            info = node.info;
            tag = node.tag;
        }


        public void removeNode(node_info node) {
            hmNiEdge.remove(node.getKey());
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;

        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {

            this.tag = t;
        }
    }
}
