import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    public Stack() {
        first = null;
        size = 0;
    }

    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public Iterator<Item> iterator() {
        return new StackIterator<Item>(first);
    }

    private class StackIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public StackIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

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
