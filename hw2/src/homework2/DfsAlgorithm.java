package homework2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

/***
 * DfsAlgorithm is a class intended to run the DFS algorithm on a given graph
 * and a source node. A destination node is optionally provided. More
 * information on DFS (Depth First Search) can be found here:
 * https://en.wikipedia.org/wiki/Depth-first_search
 */

public class DfsAlgorithm {
    private Graph<WeightedNode>      _graph;
    private LinkedList<WeightedNode> _visited;
    private NodeCountingPath         _currentPath;

    /**
     * Creates a DfsAlgorithm object, for a given graph.
     * 
     * @requires A valid graph.
     * @effects Constructs a new empty DfsAlgorithm object.
     */
    public DfsAlgorithm(Graph<WeightedNode> graph) {
        this._graph = graph;
        this._visited = new LinkedList<WeightedNode>();
        this._currentPath = null;
    }

    /**
     * Colors all nodes in the graph with a given color.
     * 
     * @requires A valid color, i.e. 'White', 'Grey', or 'Black'.
     * @effects Sets the given color for all nodes in DfsAlgorithm's graph.
     */
    public void colorAll(String color) {
        Set<WeightedNode> graphNodes = this._graph.getNodes();
        for (WeightedNode node : graphNodes) {
            node.setColor(color);
        }
    }

    /**
     * Zeroes back edges property for all nodes in the graph.
     * 
     * @effects Sets the back edges property zero for all nodes in
     *          DfsAlgorithm's graph.
     */
    public void zeroBackEdges() {
        Set<WeightedNode> graphNodes = this._graph.getNodes();
        for (WeightedNode node : graphNodes) {
            node.setBackEdges(0);
        }
    }

    /**
     * Recursive function run for given start node and end node.
     * 
     * @requires A valid node start, and a valid node or null as the end
     *           argument.
     * @return if end != null -> true if end has been visited during this run,
     *         false if it hasn't. if end == null -> always false.
     */
    public boolean run(WeightedNode start, WeightedNode end) {

        _visited.add(start);

        if (this._currentPath == null) {
            this._currentPath = new NodeCountingPath(start);
        } else {
            this._currentPath = _currentPath.extend(start);
        }

        PriorityQueue<WeightedNode> pq = new PriorityQueue<WeightedNode>(11, Collections.reverseOrder());
        HashSet<WeightedNode> startChildren = this._graph.getChildren(start);
        if (startChildren != null) {
            for (WeightedNode child : startChildren) {
                pq.add(child);
            }
        }

        start.setColor("Grey");

        if (start.equals(end)) {
            return true;
        }

        WeightedNode child = pq.poll();
        while (child != null) {
            if (!(this._visited.contains(child))) {
                if (run(child, end) == true) {
                    return true;
                }
            } else {
                start.setBackEdges(start.getBackEdges() + 1);
            }
            child = pq.poll();
        }

        start.setColor("Black");

        return false;

    }

    private NodeCountingPath recalcPath(NodeCountingPath ncp) {
        NodeCountingPath retPath = null;
        Iterator<WeightedNode> iter = ncp.iterator();
        while (iter.hasNext()) {
            if (retPath == null) {
                retPath = new NodeCountingPath(iter.next());
            } else {
                retPath = retPath.extend(iter.next());
            }
        }
        return retPath;
    }

    /**
     * Invokes the DfsAlgorithm from the given start node to the given end node.
     * 
     * @requires Two valid nodes in the graph, start and end.
     * @return A node counting path from start to end using the DFS algorithm,
     *         or null if no such path was found.
     */
    public NodeCountingPath invokeAlgorithm(WeightedNode start, WeightedNode end) {
        // Initially, all nodes should be colored white
        this.colorAll("White");
        this.zeroBackEdges();

        if (this.run(start, end) == true) {
            // Finally, all nodes should be colored white
            NodeCountingPath retPath = this.recalcPath(this._currentPath);
            this.colorAll("White");
            this.zeroBackEdges();
            return retPath;
        } else {
            // Finally, all nodes should be colored white
            this.colorAll("White");
            this.zeroBackEdges();
            return null;
        }

    }

    /**
     * Invokes the DfsAlgorithm from the given start node.
     * 
     * @requires A valid node in the graph, start.
     * @return A node counting path from start until all nodes were visited in
     *         the DFS algorithm.
     */
    public NodeCountingPath invokeAlgorithm(WeightedNode start) {
        // Initially, all nodes should be colored white
        this.colorAll("White");
        this.zeroBackEdges();

        /*
         * In the case where no end node is provided, null is sent as end node,
         * which will cause the comparison start.equals(end) to always return
         * false, and the algorithm will continue until all nodes in graph are
         * visited
         */
        this.run(start, null);

        NodeCountingPath retPath = this.recalcPath(this._currentPath);

        // Finally, all nodes should be colored white
        this.colorAll("White");
        this.zeroBackEdges();

        return retPath;

    }
}
