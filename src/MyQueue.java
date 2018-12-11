import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

    public MyQueue() {
        first = null;
        last = null;
        size = 0;
    }

    public void add(Item item) {
        Node oldLast = last;
        last = new Node();
        last.next = null;
        last.item = item;
        if (size == 0) {
            first = last;
        } else {
            oldLast.next = last;
        }
        ++size;
    }

    public int length() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {

        }
        public Item next() {
            if (current == null) {
                throw  new NoSuchElementException();
            } else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
