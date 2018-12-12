import java.util.Iterator;

public class Digraph {

    private final int vertices;
    private int edges;
    private Bag<Edge>[] adjacents;
    private int[] indegree;

    public Digraph(int vertices) {
        this.vertices = vertices;
        this.edges = edges;
        this.indegree = new int[vertices];
        adjacents = new Bag[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacents[i] = new Bag<Edge>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public Bag<Edge> adjacents(int v) {
        return adjacents[v];
    }

    public Bag<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int i = 0 ; i < vertices ; i++) {
            for (Edge e : adjacents[i]) {
                list.add(e);
            }
        }
        return list;
    }

    public void addEdge(Edge edge){
        adjacents[edge.tail].add(edge);
        indegree[edge.head]++;
        edges++;
    }

    public int outDegree(int vertex) {
        return adjacents[vertex].size();
    }

    public class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;
        private int size;

        public Bag() {
            first = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public void add(Item item) {
            Node<Item> oldFirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldFirst;
            size++;
        }

        @Override
        public Iterator<Item> iterator() {
            return new BagIterator<Item>(first);
        }

        private class BagIterator<Item> implements Iterator<Item> {

            private Node<Item> current;

            public BagIterator(Node<Item> first) {
                current = first;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        private class Node<Item> {
            private Item item;
            private Node<Item> next;
        }
    }
}