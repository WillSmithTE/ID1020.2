import java.io.File;
import java.io.IOException;

public class ShortestPath implements Testable {

    private static final String TEST_FILE_NAME = "TestNYC.txt";
    private static final int NUM_VERTICES = 264346;
    private static final int NUM_EDGES = 733846;

    private double[] distTo;
    private Edge[] edgeTo;
    private Queue<Double> queue;

    public static void main(String[] args) {
        if (args.length == 3) {
            int source = Integer.parseInt(args[0]),
                    detour = Integer.parseInt(args[1]),
                    destination = Integer.parseInt(args[2]);
            File file = new File(TEST_FILE_NAME);
            Digraph graph = makeGraphFrom3ColumnTxtFormat(file, NUM_VERTICES);

            ShortestPath main = new ShortestPath(graph, source);
            ShortestPath detourRoute = new ShortestPath(graph, detour);
            StringBuilder builder = new StringBuilder();
            if (main.hasPathTo(detour) && detourRoute.hasPathTo(destination)) {
                builder.append("Path from " + source + " to " + detour + ": ");
                for (Edge edge : main.pathTo(detour)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }
                builder.append("\nPath from " + detour + " to " + destination + ": ");
                for (Edge edge : detourRoute.pathTo(destination)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }

            } else {
                builder.append("No route found");
            }
            System.out.println(builder.toString());
        } else if (args.length == 2) {
            int source = Integer.parseInt(args[0]),
                    destination = Integer.parseInt(args[1]);
            File file = new File(TEST_FILE_NAME);
            Digraph graph = makeGraphFrom3ColumnTxtFormat(file, NUM_VERTICES);

            ShortestPath main = new ShortestPath(graph, source);
            StringBuilder builder = new StringBuilder();
            if (main.hasPathTo(destination)) {
                builder.append("Path from " + source + " to " + destination + ": ");
                for (Edge edge : main.pathTo(destination)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }
            } else {
                builder.append("No route found");
            }
            System.out.println(builder.toString());


        } else if (args.length == 1 && args[0].equals("test")) {
            runTestSuite();
        }
    }

    private static Digraph makeGraphFrom3ColumnTxtFormat(File file, int numVertices) {
        Digraph graph = new Digraph(numVertices);
        try {
            Util.readLinesFromFile(file, (line) -> {
                String[] words = line.split(" ");
                if (words.length == 3) {
                    graph.addEdge(new Edge(Integer.parseInt(words[0]), Integer.parseInt(words[1]), Double.parseDouble(words[2])));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public ShortestPath(Digraph graph, int s) {
        distTo = new double[graph.vertices()];
        edgeTo = new Edge[graph.vertices()];

        for (int i = 0; i < graph.vertices(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        queue = new Queue<Double>(graph.vertices());
        queue.insert(s, distTo[s]);
        while (!queue.isEmpty()) {
            int min = queue.delMin();
            for (Edge e : graph.adjacents(min)) {
                relax(e);
            }
        }
    }

    private void relax(Edge e) {
        int tail = e.tail, head = e.head;
        if (distTo[head] > distTo[tail] + e.weight) {
            distTo[head] = distTo[tail] + e.weight;
            edgeTo[head] = e;
            if (queue.contains(head)) {
                queue.decreaseKey(head, distTo[head]);
            } else {
                queue.insert(head, distTo[head]);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Stack<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        } else {
            Stack<Edge> path = new Stack<>();
            for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.tail]) {
                path.push(e);
            }
            return path;
        }
    }

    private class Queue<Item extends Comparable<Item>> {
        private int size;
        private int[] pq;
        private int[] qp;
        private Item[] keys;

        public Queue(int max) {
            size = 0;
            keys = (Item[]) new Comparable[max + 1];
            pq = new int[max + 1];
            qp = new int[max + 1];
            for (int i = 0; i <= max; i++) {
                qp[i] = -1;
            }

        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean contains(int i) {
            return qp[i] != -1;
        }

        public void insert(int i, Item key) {
            size++;
            qp[i] = size;
            pq[size] = i;
            keys[i] = key;
        }

        public int delMin() {
            int min = pq[1];
            swap(1, size--);
            qp[min] = -1;
            return min;
        }

        public void decreaseKey(int i, Item key) {
            keys[i] = key;
        }

        private void swap(int i, int j) {
            int x = pq[i];
            pq[i] = pq[j];
            pq[j] = x;
        }

    }

    private static void runTestSuite() {
        testPathTo();
        testDistTo();
        testHasPathTo();
        testFindsShortestPath();
    }

    private static void testPathTo() {
        Digraph graph = makeGraphFrom3ColumnTxtFormat(new File(TEST_FILE_NAME), 13);
        ShortestPath path = new ShortestPath(graph, 1);

        Stack<Edge> pathTo6 = path.pathTo(6);
        boolean pathTo2IsRight = pathTo6.pop().head == 2,
                pathTo3IsRight = pathTo6.pop().head == 3,
                pathTo4IsRight = pathTo6.pop().head == 4,
                pathTo5IsRight = pathTo6.pop().head == 5,
                pathTo6IsRight = pathTo6.pop().head == 6;

        Util.assertTrue(pathTo2IsRight && pathTo3IsRight && pathTo4IsRight && pathTo5IsRight && pathTo6IsRight,
                "path from 1-6 was incorrect");
    }

    private static void testDistTo() {
        Digraph graph = makeGraphFrom3ColumnTxtFormat(new File(TEST_FILE_NAME), 13);
        ShortestPath path = new ShortestPath(graph, 1);

        boolean distTo2IsRight = path.distTo[2] == 2008,
                distTo3IsRight = path.distTo[3] == 2108;

        Util.assertTrue(distTo2IsRight && distTo3IsRight, "incorrect calculations of distance between vertices");
    }

    private static void testHasPathTo() {
        Digraph graph = makeGraphFrom3ColumnTxtFormat(new File(TEST_FILE_NAME), 13);
        ShortestPath path = new ShortestPath(graph, 1);

        Util.assertTrue(path.hasPathTo(2), "vertex 1 should have a path to vertex 2");

        Util.assertTrue(!path.hasPathTo(10), "vertex 1 should NOT have a path to vertex 10");
    }

    private static void testFindsShortestPath() {
        Digraph graph = makeGraphFrom3ColumnTxtFormat(new File(TEST_FILE_NAME), 13);
        ShortestPath path = new ShortestPath(graph, 7);

        boolean hasPathTo11 = path.hasPathTo(11);
        Stack<Edge> pathTo11 = path.pathTo(11);

        Util.assertTrue(hasPathTo11, "Path should exist between 7 and 11");
        Util.assertTrue(pathTo11.size() == 1, "Path between 7 and 11 should be of length 1");

    }
}
