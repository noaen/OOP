package homework2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * This class implements a testing driver which reads test scripts from files
 * for testing Graph and PathFinder.
 */
public class TestDriver {

    // String -> Graph: maps the names of graphs to the actual graph
    private final Map<String, Graph> graphs = new HashMap<>();
    // String -> WeightedNode: maps the names of nodes to the actual node
    private final Map<String, WeightedNode> nodes = new HashMap<>();
    private final BufferedReader            input;
    private final PrintWriter               output;

    /**
     * Creates a new TestDriver.
     * 
     * @requires r != null && w != null
     * @effects Creates a new TestDriver which reads command from <tt>r</tt> and
     *          writes results to <tt>w</tt>.
     */
    public TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * Executes the commands read from the input and writes results to the
     * output.
     * 
     * @effects Executes the commands read from the input and writes results to
     *          the output.
     * @throws IOException
     *             - if the input or output sources encounter an IOException.
     */
    public void runTests() throws IOException {

        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            // echo blank and comment lines
            if (inputLine.trim().length() == 0 || inputLine.charAt(0) == '#') {
                output.println(inputLine);
                continue;
            }

            // separate the input line on white space
            StringTokenizer st = new StringTokenizer(inputLine);
            if (st.hasMoreTokens()) {
                String command = st.nextToken();

                List<String> arguments = new ArrayList<>();
                while (st.hasMoreTokens())
                    arguments.add(st.nextToken());

                executeCommand(command, arguments);
            }
        }

        output.flush();
    }

    private void executeCommand(String command, List<String> arguments) {

        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("CreateNode")) {
                createNode(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("FindPath")) {
                findPath(arguments);
            } else if (command.equals("DfsAlgorithm")) {
                dfsAlgorithm(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {

        if (arguments.size() != 1)
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        Graph<WeightedNode> newGraph = new Graph<WeightedNode>();
        Graph<WeightedNode> existingGraph = graphs.putIfAbsent(graphName, newGraph);
        if (existingGraph == null) {
            output.println("created graph " + graphName);
        } else {
            output.println("graph with name " + graphName + " already exists");
        }
    }

    private void createNode(List<String> arguments) {

        if (arguments.size() != 2)
            throw new CommandException("Bad arguments to createNode: " + arguments);

        String nodeName = arguments.get(0);
        String cost = arguments.get(1);
        createNode(nodeName, cost);
    }

    private void createNode(String nodeName, String cost) {
        WeightedNode newNode = new WeightedNode(nodeName, Integer.parseInt(cost));
        WeightedNode existingNode = nodes.putIfAbsent(nodeName, newNode);
        if (existingNode == null) {
            output.println("created node " + nodeName + " with cost " + cost);
        } else {
            output.println("node with name " + nodeName + " already exists");
        }
    }

    private void addNode(List<String> arguments) {

        if (arguments.size() != 2)
            throw new CommandException("Bad arguments to addNode: " + arguments);

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);
        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        if (!nodes.containsKey(nodeName)) {
            output.println("no node with name " + nodeName + " exists");
            return;
        }
        Graph<WeightedNode> graphToAdd = graphs.get(graphName);
        WeightedNode nodeToAdd = nodes.get(nodeName);
        if (graphToAdd.nodeInGraph(nodeToAdd)) {
            output.println("node " + nodeName + " already exists in graph " + graphName);
            return;
        }
        graphToAdd.addNode(nodeToAdd);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {

        if (arguments.size() != 3)
            throw new CommandException("Bad arguments to addEdge: " + arguments);

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        addEdge(graphName, parentName, childName);
    }

    private void addEdge(String graphName, String parentName, String childName) {
        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        if (!nodes.containsKey(parentName)) {
            output.println("no node with name " + parentName + " exists");
            return;
        }
        if (!nodes.containsKey(childName)) {
            output.println("no node with name " + childName + " exists");
            return;
        }
        Graph<WeightedNode> graphToAdd = graphs.get(graphName);
        WeightedNode parentNode = nodes.get(parentName);
        WeightedNode childNode = nodes.get(childName);
        if (!graphToAdd.nodeInGraph(parentNode)) {
            output.println("node " + parentName + " does not exist in graph " + graphName);
            return;
        }
        if (!graphToAdd.nodeInGraph(childNode)) {
            output.println("node " + childName + " does not exist in graph " + graphName);
            return;
        }
        if (graphToAdd.addEdge(parentNode, childNode) == 1) {
            output.println("edge from " + parentName + " to " + childName + " already exists in graph " + graphName);
            return;
        } else {
            output.println("added edge from " + parentName + " to " + childName + " in " + graphName);
        }
    }

    private void listNodes(List<String> arguments) {

        if (arguments.size() != 1)
            throw new CommandException("Bad arguments to listNodes: " + arguments);

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        Graph<WeightedNode> graphToAdd = graphs.get(graphName);
        Set<WeightedNode> graphNodes = graphToAdd.getNodes();
        ArrayList<String> nodeNames = new ArrayList<String>();
        for (WeightedNode n : graphNodes) {
            nodeNames.add(n.getName());
        }

        nodeNames.sort(String.CASE_INSENSITIVE_ORDER);

        output.print(graphName + " contains:");
        for (String name : nodeNames) {
            output.print(" " + name);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {

        if (arguments.size() != 2)
            throw new CommandException("Bad arguments to listChildren: " + arguments);

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        if (!nodes.containsKey(parentName)) {
            output.println("no node with name " + parentName + " exists");
            return;
        }
        Graph<WeightedNode> graphToAdd = graphs.get(graphName);
        WeightedNode parentNode = nodes.get(parentName);
        HashSet<WeightedNode> children = graphToAdd.getChildren(parentNode);
        ArrayList<String> childrenNames = new ArrayList<String>();
        for (WeightedNode n : children) {
            childrenNames.add(n.getName());
        }

        childrenNames.sort(String.CASE_INSENSITIVE_ORDER);

        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (String name : childrenNames) {
            output.print(" " + name);
        }
        output.println();

    }

    private void findPath(List<String> arguments) {

        String graphName;
        List<String> sourceArgs = new ArrayList<>();
        List<String> destArgs = new ArrayList<>();

        if (arguments.size() < 1)
            throw new CommandException("Bad arguments to FindPath: " + arguments);

        Iterator<String> iter = arguments.iterator();
        graphName = iter.next();

        // extract source arguments
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.equals("->"))
                break;
            sourceArgs.add(s);
        }

        // extract destination arguments
        while (iter.hasNext())
            destArgs.add(iter.next());

        if (sourceArgs.size() < 1)
            throw new CommandException("Too few source args for FindPath");

        if (destArgs.size() < 1)
            throw new CommandException("Too few dest args for FindPath");

        findPath(graphName, sourceArgs, destArgs);
    }

    private void findPath(String graphName, List<String> sourceArgs, List<String> destArgs) {
        HashSet<WeightedNode> sourceNodes = new HashSet<WeightedNode>();
        HashSet<WeightedNode> destNodes = new HashSet<WeightedNode>();

        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        Graph<WeightedNode> graph = graphs.get(graphName);

        for (String source : sourceArgs) {
            if (!nodes.containsKey(source)) {
                output.println("no node with name " + source + " exists");
                return;
            }
            WeightedNode sourceNode = nodes.get(source);
            if (!graph.nodeInGraph(sourceNode)) {
                output.println("node " + source + " does not exist in graph " + graphName);
                return;
            }
            sourceNodes.add(sourceNode);
        }

        for (String dest : destArgs) {
            if (!nodes.containsKey(dest)) {
                output.println("no node with name " + dest + " exists");
                return;
            }
            WeightedNode destNode = nodes.get(dest);
            if (!graph.nodeInGraph(destNode)) {
                output.println("node " + dest + " does not exist in graph " + graphName);
                return;
            }
            destNodes.add(destNode);
        }

        PathFinder pf = new PathFinder(graph, sourceNodes, destNodes);
        NodeCountingPath retPath = pf.findMinPath();
        if (retPath != null) {
            output.print("found path in " + graphName + ": ");
            Iterator<WeightedNode> it = retPath.iterator();
            while (it.hasNext()) {
                output.print(it.next().getName() + " ");
            }
            output.println("with cost " + (int)retPath.getCost());
        } else {
            output.println("could not find a path in " + graphName);
        }

    }

    private void dfsAlgorithm(List<String> arguments) {

        if ((arguments.size() < 2) || (arguments.size() > 3)) {
            throw new CommandException("Bad arguments to dfsAlgorithm: " + arguments);
        }

        String graphName = arguments.get(0);
        String sourceArg = arguments.get(1);
        if (arguments.size() == 2) {
            dfsAlgorithm(graphName, sourceArg);
            return;
        } else {
            String destArg = arguments.get(2);
            dfsAlgorithm(graphName, sourceArg, destArg);
        }
    }

    private void dfsAlgorithm(String graphName, String sourceArg, String destArg) {
        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        if (!nodes.containsKey(sourceArg)) {
            output.println("no node with name " + sourceArg + " exists");
            return;
        }
        if (!nodes.containsKey(destArg)) {
            output.println("no node with name " + destArg + " exists");
            return;
        }

        Graph<WeightedNode> graph = graphs.get(graphName);

        WeightedNode sourceNode = nodes.get(sourceArg);
        if (!graph.nodeInGraph(sourceNode)) {
            output.println("node " + sourceArg + " does not exist in graph " + graphName);
            return;
        }

        WeightedNode destNode = nodes.get(destArg);
        if (!graph.nodeInGraph(destNode)) {
            output.println("node " + destArg + " does not exist in graph " + graphName);
            return;
        }

        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        NodeCountingPath retDfs = dfs.invokeAlgorithm(sourceNode, destNode);
        output.print("dfs algorithm output " + graphName + " " + sourceArg + " -> " + destArg + ":");
        if (retDfs != null) {
            Iterator<WeightedNode> it = retDfs.iterator();
            while (it.hasNext()) {
                output.print(" " + it.next().getName());
            }
            output.println();
        } else {
            output.println(" no path was found");
        }
    }

    private void dfsAlgorithm(String graphName, String sourceArg) {
        if (!graphs.containsKey(graphName)) {
            output.println("no graph with name " + graphName + " exists");
            return;
        }
        if (!nodes.containsKey(sourceArg)) {
            output.println("no node with name " + sourceArg + " exists");
            return;
        }

        Graph<WeightedNode> graph = graphs.get(graphName);

        WeightedNode sourceNode = nodes.get(sourceArg);
        if (!graph.nodeInGraph(sourceNode)) {
            output.println("node " + sourceArg + " does not exist in graph " + graphName);
            return;
        }

        DfsAlgorithm dfs = new DfsAlgorithm(graph);
        NodeCountingPath retDfs = dfs.invokeAlgorithm(sourceNode);
        output.print("dfs algorithm output " + graphName + " " + sourceArg + ":");
        Iterator<WeightedNode> it = retDfs.iterator();
        while (it.hasNext()) {
            output.print(" " + it.next().getName());
        }
        output.println();
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java TestDriver <name of input script>");
        System.err.println("to read from standard input: java TestDriver");
    }

    public static void main(String args[]) {

        try {
            // check for correct number of arguments
            if (args.length > 1) {
                printUsage();
                return;
            }

            TestDriver td;
            if (args.length == 0)
                // no arguments - read from standard input
                td = new TestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
            else {
                // one argument - read from file
                java.nio.file.Path testsFile = Paths.get(args[0]);
                if (Files.exists(testsFile) && Files.isReadable(testsFile)) {
                    td = new TestDriver(Files.newBufferedReader(testsFile, Charset.forName("US-ASCII")),
                            new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + testsFile.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }
}

/**
 * This exception results when the input file cannot be parsed properly.
 */
class CommandException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommandException() {
        super();
    }

    public CommandException(String s) {
        super(s);
    }
}