public class DoubleLinkedList<Item> {
    private int n;
    private Node before;
    private Node after;

    public static void main(String [] args) {
        if (args.length >= 1 && args[0].equals("test")) {
            runTests();
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    public DoubleLinkedList() {
        before = new Node();
        after = new Node();
        before.next = after;
        after.previous = before;
    }

    public void insert(Item item) {
        Node newFirst = new Node();
        Node oldFirst = before.next;
        newFirst.item = item;
        newFirst.previous = before;
        newFirst.next = oldFirst;
        before.next = newFirst;
        oldFirst.previous = newFirst;
    }

    public Item removeLIFO() {
        if (before.next != after) {
            Item item = before.next.item;
            Node newFirst = before.next.next;
            newFirst.previous = before;
            before.next = newFirst;
            return item;
        } else {
            return null;
        }
    }

    public Item removeFIFO() {
        if (before.next != after) {
            Item item = after.previous.item;
            Node newLast = after.previous.previous;
            newLast.next = after;
            after.previous = newLast;
            return item;
        } else {
            return null;
        }
    }

    private static void runTests() {
        testInsert(new Integer(9));
        testInsert("test");
        testLengthAfterInsertRemoveFIFO(new Integer(9));
        testLengthAfterInsertRemoveFIFO("test");
        testRemoveFIFORemovesCorrectItem();
        testRemoveLIFORemovesCorrectItem();
    }

    private static <U> void testInsert(U item) {
        DoubleLinkedList<U> list = new DoubleLinkedList();
        list.insert(item);
        assertTrue(list.before.next.item == item, "Expected first item to equal '" + item + "' instead of '" + list.before.next.item); ;
    }

    private static void testRemoveLIFORemovesCorrectItem() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList();
        Integer int9 = new Integer(9),
        int10 = new Integer(10);
        list.insert(int9);
        list.insert(int10);
        Integer removedInt = list.removeLIFO();
        assertTrue(removedInt.equals(int10), "Failed to remove correct item in LIFO remove - expected '" + int10 + "' but got '" + removedInt + "'.");
    }

    private static void testRemoveFIFORemovesCorrectItem() {
        DoubleLinkedList<String> list = new DoubleLinkedList();
        String string9 = "nine",
                string10 = "ten";
        list.insert(string9);
        list.insert(string10);
        String removed = list.removeFIFO();
        assertTrue(removed.equals(string9), "Failed to remove correct item in FIFO remove - expected '" + string9 + "' but got '" + removed + "'.");

    }

    private static <U> void testLengthAfterInsertRemoveFIFO(U item) {
        DoubleLinkedList list = new DoubleLinkedList();
        list.insert(item);
        list.removeFIFO();
        assertTrue(list.before.next == list.after, "");
    }

    private static void assertTrue(boolean isTrue, String message) {
        System.out.println(isTrue ? "Pass" : "Fail: " + message);
    }

}
