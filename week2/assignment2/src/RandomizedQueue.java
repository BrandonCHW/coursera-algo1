import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

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
        if (item == null) {
            throw new IllegalArgumentException("Can't enqueue a null item");
        }
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
        notEmptyGuard();
        if (size == 1) {
            Item item = front.item;
            front = null;
            size--;
            return item;
        }
        Node beforeNode = getRandomNode(true);
        Node toRemove = beforeNode.next;
        Item item = toRemove.item;
        beforeNode.next = toRemove.next;
        size--;

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        notEmptyGuard();
        Node randomNode = getRandomNode(false);
        return randomNode.item;
    }

    /**
     * Randomly generates an index number of returns the corresponding node counting from the front
     * @param toRemove if true, the previous node is returned (in cases which size > 1). node.next can then be called
     * @return the node at index i of the queue
     */
    private Node getRandomNode(boolean toRemove) {
        int randomIndex = StdRandom.uniform(1, size + 1);
        if (size == 1 || randomIndex == 1) {
            return front;
        }
        Node current = front;
        if (toRemove) {
            randomIndex -= 1;
        }
        int i = 1;
        while (i != randomIndex) {
            current = current.next;
            ++i;
        }
        return current;
    }

    private void notEmptyGuard() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't dequeue: Queue is empty");
        }
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private Node currentNode = front;

            @Override
            public boolean hasNext() {
                return currentNode.next != null;
            }

            @Override
            public Item next() {
                if (currentNode.next == null) {
                    throw new NoSuchElementException("There is no next item to return");
                }
                currentNode = currentNode.next;
                return currentNode.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        // Test 1: isEmpty returns 0 when empty
        System.out.println( "RandomizedQueue.isEmpty(): " + (rq.isEmpty() ? "OK" : "FAIL"));

        // Test 2: Adding items at the front works   result: front-> B A <- end
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        System.out.println( "RandomizedQueue.enqueue(): " + (rq.size == 4 ? "OK" : "FAIL"));

        System.out.println( "RandomizedQueue.size(): " + (rq.size() == 4 ?  "OK" : "FAIL"));

        Iterator<String> it = rq.iterator();
        System.out.println( "RandomizedQueue.hasNext(): " + (it.hasNext() ?  "OK" : "FAIL"));

        try {
            it.remove();
            System.out.println("RandomizedQueue.iterator().remove() doesn't throw (FAIL!)");
        } catch(UnsupportedOperationException e) {
            System.out.println("RandomizedQueue.iterator().remove() OK");
        }

        // Test 7: iterator next
        String next = it.next();
        System.out.println( "RandomizedQueue.iterator().next(): " + (next == "C" ?  "OK" : "FAIL"));

        // Test 8: Sample
        String random = rq.sample();
        System.out.println( "RandomizedQueue.sample(): " + (rq.size == 4 ? "OK" : "FAIL"));

        for (int i = 0; i < 100; i++) {
            System.out.println(rq.sample());
        }

        // Test 9: Removing items from the front works  result: front-> A C D <- end
        String removedFront = rq.dequeue();
        System.out.println( "RandomizedQueue.dequeue(): " + (rq.size == 3 ? "OK" : "FAIL"));

    }

}