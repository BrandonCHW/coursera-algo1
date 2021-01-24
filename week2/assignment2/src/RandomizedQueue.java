import java.util.Iterator;
import

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node front;

    private class Node {
        Item item;
        Node next;
    }
    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        front = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        Node node = new Node();
        node.item = item;
        if (!isEmpty()) {
            node.next = front;
        }
        front = node;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        int randomIndex =
    }

    // return a random item (but do not remove it)
    public Item sample()

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()

    // unit testing (required)
    public static void main(String[] args)

}