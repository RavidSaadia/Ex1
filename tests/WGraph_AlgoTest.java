package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {


    @Test
    void copy() {
        weighted_graph_algorithms g = new WGraph_Algo();
        weighted_graph w = graph_path();
        g.init(w);
        assertFalse(g.getGraph().hasEdge(2, 3));
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g.copy());
        assertFalse(g2.getGraph().hasEdge(2, 3));
        g2.getGraph().connect(2, 3, 4);
        assertTrue(g2.getGraph().hasEdge(2,3));
        assertFalse(g.getGraph().hasEdge(2,3));
    }

    @Test
    void isConnected() {
        weighted_graph G = graphBilter();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(G);
        assertTrue(g1.isConnected());
        g1.getGraph().removeEdge(234, 235);
        assertFalse(g1.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g1 = graph_path();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(g1);

        assertEquals(5, g.shortestPathDist(0, 9));

    }

    @Test
    void shortestPath() {
        weighted_graph g1 = graph_path();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(g1);

        List<node_info> list = g.shortestPath(0, 9);

        int[] expected = {0, 1, 3, 8, 5, 9};

        boolean flag = true;

        int i = 0;

        for (node_info n : list) {
            flag &= n.getKey() == expected[i++];
        }
        assertEquals(true, flag);
        list = g.shortestPath(6, 2);
        assertEquals(null, list);
        list = g.shortestPath(2, 7);
        assertEquals(null, list);
        list = g.shortestPath(2, 2);
        assertEquals(2, list.get(0).getKey());
    }

    @Test
    void saveAndLode() {

        weighted_graph g1 = graph_path();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(g1);
        String s = "graph.txt";
        g.save(s);
        weighted_graph g2 = graph_path();
        weighted_graph_algorithms gg = new WGraph_Algo();
        g.load(s);
        gg.init(g2);
        assertFalse(g.isConnected());
        g2.connect(2, 3, 3);
        g2.connect(2, 7, 3);
        gg.save(s);
        g.load(s);
        assertTrue(g.isConnected());

    }


    public static WGraph_DS graphCapels() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < 100; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 100; i = i + 2) {
            g.connect(i, i + 1, i);

        }
        return g;
    }

    public static WGraph_DS graphBilter() {
        WGraph_DS g = new WGraph_DS();

        for (int i = 0; i < 1000000; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 1000000; i++) {
            g.connect(i, i + 1, i);
        }
//        for (int i = 0; i < 1000000; i ++) {
//            g.connect(i, i + 2, i);
//        }
        return g;
    }

    public static WGraph_DS graph_path() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
            g.addNode(i);
        }
        g.connect(0, 1, 1);
        g.connect(3, 1, 1);
        g.connect(8, 3, 1);
        g.connect(5, 8, 1);
        g.connect(5, 9, 1);
        g.connect(0, 9, 10);
        g.connect(0, 4, 5);
        g.connect(4, 9, 1);
        g.connect(0, 6, 7);
        g.connect(5, 6, 1);
        g.connect(3, 4, 6);
        return g;
    }
}