package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;
import ex1.src.*;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void constructorCheck() {
        weighted_graph g = graphCapels();
        assertTrue(g.hasEdge(10, 11));
        weighted_graph g2 = new WGraph_DS(g);
        g2.removeEdge(10, 11);
        g2.connect(50, 13, 13);
        assertTrue(g2.hasEdge(13, 50));
        assertFalse(g.hasEdge(13, 50));
        assertFalse(g2.hasEdge(10, 11));
        assertTrue(g.hasEdge(10, 11));


        weighted_graph s = graphCapels();
        weighted_graph s1 = new WGraph_DS(s);
        assertNotEquals(s1.getNode(44), s.getNode(44));
        assertEquals(s.getEdge(58, 59), s1.getEdge(58, 59));
        assertEquals(s.edgeSize(), s1.edgeSize());
        s.removeEdge(4, 5);
        assertNotEquals(s.edgeSize(), s1.edgeSize());
    }

    @Test
    void getNode() {
        weighted_graph g = graphCapels();
        weighted_graph g2 = graphCapels();
        assertNotEquals(g2.getNode(4), g.getNode(4));
        assertEquals(g.getNode(4), g.getNode(4));
    }

    @Test
    void hasEdge() {
        WGraph_DS g = graphCapels(); //new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(1, 2, 5);
        g.connect(3, 3, 3);

        assertTrue(g.hasEdge(10, 11));
        assertFalse(g.hasEdge(0, 2));
        assertFalse(g.hasEdge(3, 3));
    }

    @Test
    void getEdge() {
        WGraph_DS g = graphCapels();

        assertEquals(4, g.getEdge(4, 5));
        g.removeEdge(4, 5);
        assertEquals(-1, g.getEdge(4, 5));
        assertEquals(-1, g.getEdge(50, 5));
        assertEquals(66, g.getEdge(66, 67));

    }

    @Test
    void addNode() {
        weighted_graph g = graphCapels();

        g.addNode(78);
        g.addNode(0);
        g.addNode(56);
        g.addNode(443);
        g.addNode(555);
        g.addNode(4);
        assertEquals(102, g.nodeSize());
    }

    @Test
    void connect() {
        weighted_graph g = graphBilter();
        g.connect(4, 5, 45);
        g.connect(46, 555, 9999);
        g.connect(466, 555, 4675);
        assertEquals(45, g.getEdge(5, 4));
        assertEquals(4675, g.getEdge(466, 555));
        assertEquals(9999, g.getEdge(46, 555));


    }


    @Test
    void removeNode() {
        WGraph_DS g = graphCapels();
g.removeNode(3);
assertNull(g.getNode(3));

    }

    @Test
    void removeEdge() {
        WGraph_DS g = graphCapels();
g.removeEdge(4,5);
assertNotEquals(4,g.getEdge(4,5));
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
}