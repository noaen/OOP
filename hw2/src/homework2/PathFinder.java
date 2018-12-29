package homework2;

import java.util.HashSet;

/***
 * PathFinder is a class intended to find the minimum cost path between a set of
 * starting nodes and a set of ending nodes, in a given graph, using the DFS
 * algorithm.
 */
public class PathFinder {
    private Graph<WeightedNode>   _graph;
    private HashSet<WeightedNode> _startNodes;
    private HashSet<WeightedNode> _endNodes;

    /**
     * Create a new PathFinder object with a given graph, a set of starting
     * nodes and a set of ending nodes.
     * 
     * @requires A valid graph, a valid hash set of start nodes, and a valid
     *           hashset of ending nodes, all in the graph.
     * @effects Create a new PathFinder object.
     */
    public PathFinder(Graph<WeightedNode> graph, HashSet<WeightedNode> startNodes, HashSet<WeightedNode> endNodes) {
        this._graph = graph;
        this._startNodes = startNodes;
        this._endNodes = endNodes;
    }

    /**
     * Finds the minimum cost path using the DFS algorithm between one of the
     * starting nodes to one of the ending nodes.
     * 
     * @return A NodeCountingPath for the minimum cost path, using the DFS
     *         algorithm, or null if no path exists between none of the starting
     *         nodes to none of the ending nodes. Or null if no start node or no
     *         end node was specified.
     */
    public NodeCountingPath findMinPath() {
        NodeCountingPath minPath = null;
        DfsAlgorithm dfs = new DfsAlgorithm(this._graph);
        for (WeightedNode startNode : this._startNodes) {
            for (WeightedNode endNode : this._endNodes) {
                NodeCountingPath currentPath = dfs.invokeAlgorithm(startNode, endNode);
                if (currentPath != null) {
                    if ((minPath == null) || (currentPath.getCost() < minPath.getCost())) {
                        minPath = currentPath;
                    }
                }
            }
        }
        return minPath;
    }
}
