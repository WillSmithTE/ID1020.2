public class ShortestPath {


    private class Digraph {

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

        public int edges() {
            return edges;
        }

        public void addEdge(Edge edge){

        }

        private class Edge {
            final int tail;
            final int head;
            final double weight;

            public Edge(int tail, int head, double weight) {
                this.tail = tail;
                this.head = head;
                this.weight = weight;
            }
        }

        private class Bag<Item> {
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

            private class Node<Item> {
                private Item item;
                private Node<Item> next;
            }
        }
    }
}
