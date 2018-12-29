package homework2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/***
 * Graph represents a directed graph, i.e. a set of nodes with edges connecting
 * them. The edges are directed. There can't be more than one edge between two nodes
 * in the same direction. However, there can be two edges with opposite directions
 * between two nodes. 
 * Children of node 'A' are the nodes that have an edge connected from node 'A'
 * to them.
 * Parents of node 'A' are the nodes that have an edges connected from them to node 'A'.
 */

/*
 * Representation invariant:
 * No two equal elements as two separate keys in _nodes - this constraint is enforced by the container (HashMap).
 * No two equal elements in same key's value (HashSet) - also enforced by the container (HashSet).
 * Keys are not null in HashMap nodes.
 * Elements are not null in HashSet.
 */


public class Graph<N> {
	
	private void checkRep() {
		for (Map.Entry<N, HashSet<N>> entry : _nodes.entrySet()) {			
			assert(entry.getKey() != null) : "Found null node in graph";
			for (N n : entry.getValue()) {
				assert(n != null) : "Found null node as node's child";
			}
		}
	}
	
	private HashMap<N, HashSet<N>> _nodes;
	
	/**
     * Creates an empty graph.
     * @effects Constructs a new empty graph.
     */
	public Graph() {
		_nodes = new HashMap<N, HashSet<N>>();
		checkRep();
	}
	
	/**
	 * Adds a node to the graph. The node is not connected to any other nodes yet.
	 * @requires A valid node of type N (the graph's declared node type).
	 * @effects Adds the given node to the graph, if it does not already exists in the graph.
	 * @return 0 if node was successfully added, 1 if the node already exists in the graph.
	 */
	
	public int addNode(N n) {
		checkRep();
		HashSet<N> retVal = _nodes.putIfAbsent(n, new HashSet<N>());
		if (retVal == null) {
			checkRep();
			return 0;
		}
		else {
			checkRep();
			return 1;
		}
	}
	
	/**
	 * Adds an edge to the graph, directed from n1 to n2.
	 * @requires Two valid nodes, and both n1 and n2 are in the graph.
	 * @effects Connects n1 to n2, if there is no edge between them, in this direction, already.
	 * @return 0 if the edge was successfully added, 1 if the edge already exists in the graph.
	 */
	public int addEdge(N n1, N n2) {
		checkRep();
		HashSet<N> retVal = _nodes.get(n1);
		if (retVal.contains(n2)) {
			return 1;
		}
		retVal.add(n2);
		checkRep();
		return 0;
	}
	
	/**
	 * Returns a set of all existing nodes in the graph.
	 * @return A set containing all nodes in the graph.
	 */
	public Set<N> getNodes() {
		checkRep();
		return _nodes.keySet();
	}
	
	/**
	 * Returns a HashSet of all the children of a given node.
	 * @requires A valid node of type N, which exists in the graph.
	 * @return An ArrayList containing all the children of node n.
	 */
	public HashSet<N> getChildren(N n) {
		checkRep();
		return _nodes.get(n);
	}
	
	/**
	 * Checks if a given node is already in the graph.
	 * @requires A valid node of type N.
	 * @return true if n exists in the graph, false if it doesn't.
	 */
	public boolean nodeInGraph(N n) {
		checkRep();
		return _nodes.containsKey(n);
	}
	
//	/**
//	 * Checks if a given edge is already in the graph.
//	 * @requires Two valid nodes, n1 does not equals n2, which exist in the graph.
//	 * @return true if there is an edge from n1 to n2, false if there isn't.
//	 */
//	public boolean edgeInGraph(N n1, N n2) {
//		checkRep();
//		HashSet<N> retVal = _nodes.get(n1);
//		checkRep();
//		return retVal.contains(n2);
//	}
}
