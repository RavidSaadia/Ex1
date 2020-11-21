This packedg implement an undirected weighted graph by Node data vertices:

Using 2 Hashmaps:
1. contains < node ID (key) , node_info> --> list of all the nodes in the graph.
2. contains < node ID (key) , < node ID (key) , Double > --> every node have his own Hashmap
 that represent his neighbors and the value of the edge that connect them.

every node contain:
-ID uniq number (key) 
-information as String object (info)
-Temporal data nember (tag)
doing sum manipulation on graphs:

- fine the shortest path between tow nodes in the graph with Dijkstra's algorithm.
 by updating the distance of every node that connected
 to the src, from the src.
 every node gets also the key of his father that we could reverse the path back at
 the 'shortestPath' method.
 when we finnish to scan the nodes we return the tag value of dets.
 Use the tag for distance and the Info for saving the father key.

-return the shortest path as a collection of  nodes.
 Using the 'shortestPathDist' method to reverse the way back from the dest to the src.
 in the end we reverse the list back.

- answer the question if the graph is connected (if there is an edges
that connect between *all* of the vertices).
 Chek if the graph is connected by "coloring" all the nodes that
 connected to the 'startPoint' and check in the end if all the nodes
 in the graph are painted.
 Use the tag for paint.
