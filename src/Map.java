public class Map {
    private LinkedList[] words;
    private int size = 0;
    private int capacity;

    Map(int capacity) {
        this.capacity = capacity;
        words = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            words[i] = new LinkedList();
        }
    }

    public void add(String key) {
        int hash = hash(key);
        LinkedList.Node maybeNode = words[hash].get(key);
        if (maybeNode == null) {
            size++;
        }
        words[hash].insert(key);
    }

    public int get(String key) {
        int hash = hash(key);
        return words[hash].get(key).value;
    }

    private int hash(String key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    public MyQueue<String> keys() {
        MyQueue<String> queue = new MyQueue<String>();
        for (int i = 0; i < capacity; i++) {
            for (String word : words[i].keys()) {
                queue.add(word);
            }
        }
        return queue;
    }

    public int size() {
        return size;
    }

    private class LinkedList {
        private int size;
        private Node first;

        private class Node {
            private String key;
            private Integer value;
            private Node next;

            private Node(String key, Integer value, Node next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        public Node get(String key) {
            for (Node i = first; i != null; i = i.next) {
                if (key.equals(i.key)) {
                    return i;
                }
            }
            return null;
        }

        public void insert(String key) {
            for (Node i = first; i != null; i = i.next) {
                if (key.equals(i.key)) {
                    i.value++;
                    return;
                }
            }
            first = new Node(key, 1, first);
            size++;
        }

        public Iterable<String> keys() {
            MyQueue<String> queue = new MyQueue<String>();
            for (Node i = first; i != null; i = i.next) {
                queue.add(i.key);
            }
            return queue;
        }


    }
}