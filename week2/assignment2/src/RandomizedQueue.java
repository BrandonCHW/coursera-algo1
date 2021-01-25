import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node front;

    private class Node {
        Item item;
        Node next;

        public Node copy() {
            Node copy = new Node();
            copy.item = this.item;
            copy.next = next;
            return copy;
        }
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

    private RandomizedQueue<Item> copy() {
        RandomizedQueue<Item> queueCopy = new RandomizedQueue<>();
        if (!isEmpty()) {
            Node frontCopy = front.copy();
            Node current = frontCopy;
            while (current.next != null) {
                current.next = current.next.copy();
                current = current.next;
            }
            Node toEnqueue = frontCopy;
            while (toEnqueue != null) {
                queueCopy.enqueue(toEnqueue.item);
                toEnqueue = toEnqueue.next;
            }
        }
        return queueCopy;
    }

    // Note: How to improve: use array as data structure instead of linked list (timing grade)
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        RandomizedQueue<Item> copy = this.copy();
        RandomizedQueue<Item> shuffled = new RandomizedQueue<>();
        while (!copy.isEmpty()) {
            shuffled.enqueue(copy.dequeue());
        }

        return new Iterator<>() {
            private Node currentNode = shuffled.front;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("There is no next item to return");
                Item curr = currentNode.item;
                currentNode = currentNode.next;
                return curr;
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

        // Test 8: Sample
        String random = rq.sample();
        System.out.println( "RandomizedQueue.sample(): " + (rq.size == 4 ? "OK" : "FAIL"));

        for (int i = 0; i < 8; i++) {
            System.out.println(rq.sample());
        }

        // Test 9: Removing items from the front works  result: front-> A C D <- end
        String removedFront = rq.dequeue();
        StdOut.println( "RandomizedQueue.dequeue(): " + (rq.size == 3 ? "OK" : "FAIL"));

    }

}