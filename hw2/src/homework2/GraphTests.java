package homework2;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the graph
 * and shortest path finding algorithm implementations of homework assignment
 * #2.
 */
public class GraphTests extends ScriptFileTests {

    // black-box test are inherited from super
    public GraphTests(java.nio.file.Path testFile) {
        super(testFile);
    }

    // white-box tests for Graph
    /**
     * Adds a node to a graph, expects node to not exist in graph and
     * graph.addNode(n) to return 0
     */
    @Test
    public void AddNewNode() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        assertEquals("addNode(n)", 0, graph.addNode(n1));
    }

    /**
     * Tries to add a node to a graph that already exists in the graph. Expects
     * node to exist in graph and graph.addNode(n) to return 1
     */
    @Test
    public void AddExistingNode() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        graph.addNode(n1);
        assertEquals("addNode(n)", 1, graph.addNode(n1));
    }

    /**
     * Adds an edge between n1 and n2 to a graph, expects the edge to not exist
     * in graph, and graph.addEdge(n1, n2) to return 0
     */
    @Test
    public void AddNewEdge() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        assertEquals("addEdge(n1, n2)", 0, graph.addEdge(n1, n2));
    }

    /**
     * Tries to add an edge between n1 and n2 to a graph. Expects the edge to
     * exist in the graph and graph.addEdge(n1, n2) to return 1
     */
    @Test
    public void AddExistingEdge() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        assertEquals("addEdge(n1, n2)", 1, graph.addEdge(n1, n2));
    }

    // white-box tests for DFS
    /**
     * Invokes DfsAlgorithm.invokeAlgorithm with two nodes and one edge between
     * them, expects to return 2-node path
     */
    @Test
    public void TwoNodeDfs() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        NodeCountingPath nPath = new NodeCountingPath(n1);
        nPath = nPath.extend(n2);
        assertEquals("DfsAlgorithm.invokeAlgorithm(n1, n2)", nPath, dfs.invokeAlgorithm(n1, n2));
    }

    /**
     * Invokes DfsAlgorithm.run with start=end expects to return true
     */
    @Test
    public void StartEqualsEnd() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        graph.addNode(n1);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        assertEquals("DfsAlgorithm.invokeAlgorithm(n1, n1)", true, dfs.run(n1, n1));
    }

    /**
     * Invokes DfsAlgorithm.invokeAlgorithm, after algorithm is done expects all
     * nodes in graph to be white
     */
    @Test
    public void AllNodesWhite() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 1);
        WeightedNode n2 = new WeightedNode("n2", 2);
        WeightedNode n3 = new WeightedNode("n2", 3);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addEdge(n1, n2);
        graph.addEdge(n2, n3);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        dfs.invokeAlgorithm(n1, n3);
        assertEquals("node.getColor()", "White", n1.getColor());
        assertEquals("node.getColor()", "White", n2.getColor());
        assertEquals("node.getColor()", "White", n3.getColor());
    }

    /**
     * Invokes DfsAlgorithm.run with end=null, expects run method to return
     * false
     */
    @Test
    public void RunNullEnd() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        assertEquals("dfs.run(n1, null)", false, dfs.run(n1, null));
    }

    /**
     * Invokes DfsAlgorithm.invokeAlgorithm with no end node, expects method to
     * return NodeCountingPath
     */
    @Test
    public void NoEndSpecified() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 5);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        NodeCountingPath nPath = new NodeCountingPath(n1);
        nPath = nPath.extend(n2);
        assertEquals("dfs.invokeAlgorithm(n1)", nPath, dfs.invokeAlgorithm(n1));
    }

    /**
     * Invokes DfsAlgorithm.invokeAlgorithm for a graph with a back-edge.
     * Expects node.backEdges=1
     */
    @Test
    public void OneBackEdge() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 10);
        WeightedNode n2 = new WeightedNode("n2", 5);
        WeightedNode n3 = new WeightedNode("n3", 1);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addEdge(n1, n2);
        graph.addEdge(n2, n3);
        graph.addEdge(n2, n1);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        dfs.run(n1, n3);
        assertEquals("WeightedNode.getBackEdges()", 1, n2.getBackEdges());
    }

    /**
     * Invokes DfsAlgorithm.invokeAlgorithm for a graph with no edges. Expects
     * invokeAlgorithm to return null (no path between start and end)
     */
    @Test
    public void EndNodeUnreachable() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 10);
        WeightedNode n2 = new WeightedNode("n2", 5);
        graph.addNode(n1);
        graph.addNode(n2);
        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        assertEquals("dfs.invokeAlgorithm(n1, n2)", null, dfs.invokeAlgorithm(n1, n2));
    }

    // white-box tests for PathFinder
    /**
     * Invokes PathFinder.findMinPath() with no start nodes. Expects to return
     * null
     */
    @Test
    public void NoStartNodes() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n = new WeightedNode("n", 1);
        HashSet<WeightedNode> startSet = new HashSet<WeightedNode>();
        HashSet<WeightedNode> endSet = new HashSet<WeightedNode>();
        endSet.add(n);
        PathFinder pf = new PathFinder(graph, startSet, endSet);
        assertEquals("pf.findMinPath()", null, pf.findMinPath());
    }

    /**
     * Invokes PathFinder.findMinPath() with no end nodes. Expects to return
     * null
     */
    @Test
    public void NoEndNodes() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n = new WeightedNode("n", 1);
        HashSet<WeightedNode> startSet = new HashSet<WeightedNode>();
        HashSet<WeightedNode> endSet = new HashSet<WeightedNode>();
        startSet.add(n);
        PathFinder pf = new PathFinder(graph, startSet, endSet);
        assertEquals("pf.findMinPath()", null, pf.findMinPath());
    }

    /**
     * Invokes PathFinder.findMinPath() with 1 start node and 1 end node.
     * Expects to return a NodecountingPath
     */
    @Test
    public void OneNodes() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 1);
        WeightedNode n2 = new WeightedNode("n2", 2);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        HashSet<WeightedNode> startSet = new HashSet<WeightedNode>();
        HashSet<WeightedNode> endSet = new HashSet<WeightedNode>();
        startSet.add(n1);
        endSet.add(n2);
        PathFinder pf = new PathFinder(graph, startSet, endSet);
        NodeCountingPath ncp = new NodeCountingPath(n1);
        ncp = ncp.extend(n2);
        assertEquals("pf.findMinPath()", ncp, pf.findMinPath());
    }

    /**
     * Invokes PathFinder.findMinPath() with 2 start nodes and 2 end nodes.
     * Expects to return a NodecountingPath
     */
    @Test
    public void TwoNodes() {
        Graph<WeightedNode> graph = new Graph<WeightedNode>();
        WeightedNode n1 = new WeightedNode("n1", 1);
        WeightedNode n2 = new WeightedNode("n2", 2);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(n1, n2);
        HashSet<WeightedNode> startSet = new HashSet<WeightedNode>();
        HashSet<WeightedNode> endSet = new HashSet<WeightedNode>();
        startSet.add(n1);
        startSet.add(n2);
        endSet.add(n1);
        endSet.add(n2);
        PathFinder pf = new PathFinder(graph, startSet, endSet);
        NodeCountingPath ncp = new NodeCountingPath(n1);
        assertEquals("pf.findMinPath()", ncp, pf.findMinPath());
    }

}
