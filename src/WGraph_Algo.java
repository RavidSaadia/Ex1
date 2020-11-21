package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g;


    public WGraph_Algo() {
        this.g = new WGraph_DS();
    }

    public WGraph_Algo(weighted_graph g) {
        this.g = g;
    }

    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    @Override
    public weighted_graph getGraph() {
        return g;
    }

    @Override
    public weighted_graph copy() {

        return (new WGraph_DS(g));
    }



    /**
     * Chek if the graph is connected by "coloring" all the nodes that
     * connected to the 'startPoint' and check in the end if all the nodes
     * in the graph are painted.
     * Use the tag for paint.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 0) {
            return true;
        }
        if (this.g.nodeSize() > this.g.edgeSize() + 1) { //the minimum number of edges for a connected graph, shallow chek.
            return false;
        }
//        boolean flag = false; // if there is a single node in the graph, don't get in the next -if-.
        for (node_info i : this.g.getV()) { //reset all the Tags in the graph to 0.
//           if (g.getV(i.getKey()).size() == 0 && flag ){ //check if there is a lonely node --> false
//               return false;}
            i.setTag(0);
//            flag = true;
        }
        node_info startPoint = (g.getV().iterator().next()); // get a random Node in the graph.
        Queue<node_info> q = new LinkedList<>(); // Queue to store all the check Node.
        q.add(startPoint);

        while (!q.isEmpty()) {
            node_info v = q.poll();
            for (node_info i : g.getV(v.getKey())) { //check if V neighbor has been checked.
                if (i.getTag() == 0) {
                    q.add(i);
                    i.setTag(1);    //mark 1- in the queue but not checked yet.
                }
            }
            v.setTag(2);  // mark 2- done to check this node.
        }
        LinkedList<node_info> checklist = new LinkedList<>(g.getV()); //List of all the graph nodes.

        for (node_info i : checklist) { // check if there is a node who dosen't marked with 2.
            if (i.getTag() != 2) return false;
        }
        return true;
    }

    /**
     *  Chek the shortest path distance by update the distance of every node that connected
     *  to the src, from the src.
     *  every node gets also the key of his father that we could reverse the path back at
     *  the 'shortestPath' method.
     *  when we finnish to scan the nodes we return the tag value of dets.
     *  Use the tag for distance and the Info for saving the father key.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;// trivial case.
        for (node_info i : this.g.getV()) { //reset all the Tags to 0.
            i.setTag(Integer.MAX_VALUE);// tag = distance from src.
            i.setInfo(""); //  info = the nodes father.
        }

        Queue<node_info> q = new LinkedList<>();
        q.add(this.g.getNode(src));
        this.g.getNode(src).setTag(0.0); // put the src in the queue


        while (!q.isEmpty()) {   // if empty you can continue
            node_info now = q.poll();
            for (node_info i : this.g.getV(now.getKey())) { // look at the neighbor.
                if (now.getTag() + g.getEdge(now.getKey(), i.getKey()) < i.getTag()) { // if the current path is shorter-->update.
                    if (i.getKey() != dest) { // if its not the dest put it in the queue.
                        q.add(i);
                    }
                    i.setInfo("" + now.getKey()); // set info = the nodes father
                    i.setTag(now.getTag() + g.getEdge(now.getKey(), i.getKey())); //set the new tag.

                }
            }

        }
        if (g.getNode(dest).getTag() < Integer.MAX_VALUE) {// if you get to the dest node return his tag.
            return g.getNode(dest).getTag();
        }
        return -1;// if you didn't get to the dest node return -1.
    }

    /**
     * Using the 'shortestPathDist' method to reverse the way back from the dest to the src.
     * in the end we reverse the list back.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (shortestPathDist(src, dest) == -1) { //if there is no path return null.
            return null;
        }
        LinkedList<node_info> ans = new LinkedList<>();
        ans.add(this.g.getNode(dest)); //start the list from the end (dest).

        node_info i = this.g.getNode(dest); // i = the list iterator.

        while (i.getTag() != 0) { //loop until you find the src (when the Tag = 0).
            ans.add(g.getNode(Integer.parseInt(i.getInfo())));
            i = (g.getNode(Integer.parseInt(i.getInfo())));
        }
        Collections.reverse(ans); // reverse because we start at the end.
        return ans;
    }

    @Override
    public boolean save(String file) {

        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(this.g);
            f.close();
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {

        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);
            weighted_graph lodeGraph = (weighted_graph)o.readObject();
            this.g = new WGraph_DS(lodeGraph);
            o.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
