import java.io.File;
import java.io.IOException;

public class ShortestPath implements Testable{

    private static final String FILE_NAME = "NYCtest.txt";
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
            File file = new File(FILE_NAME);
            Digraph graph = new Digraph(NUM_VERTICES);
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
        } else if (args.length == 1 && args[0].equals("test")) {
//            runTestSuite();
        }
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
            for (int i = 0 ; i <= max ; i++) {
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
}
