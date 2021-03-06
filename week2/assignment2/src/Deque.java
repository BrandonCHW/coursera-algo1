import java.util.Iterator;
import java.util.NoSuchElementException;

/*
    front -> B A C D <- end
             next -> (default iteration direction)
             <- prev
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node front;
    private Node end;

    // construct an empty deque
    public Deque() {
        size = 0;
        front = null;
        end = null;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add a null item");
        }
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            front = node;
            end = node;
        } else {
            Node oldFront = front;
            oldFront.prev = node;
            front = node;
            front.next = oldFront;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add a null item");
        }
        Node node = new Node();
        node.item = item;
        if (isEmpty()) {
            front = node;
            end = node;
        } else {
            Node oldEnd = end;
            oldEnd.next = node;
            end = node;
            end.prev = oldEnd;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove first, queue is empty.");
        }
        Item oldItem = front.item;
        if (size == 1) {
            front = null;
            end = null;
        } else {
            Node newFront = front.next;
            newFront.prev = null;
            front = newFront;
        }
        size--;
        return oldItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove last, queue is empty.");
        }
        Item oldItem = end.item;
        if (size == 1) {
            front=null;
            end = null;
        } else {
            Node newEnd = end.prev;
            newEnd.next = null;
            end = newEnd;
        }
        size--;
        return oldItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<>() {

            private Node currentNode = front;

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

    private static void test() {
        Deque<Integer> deq = new Deque<>();
        deq.addFirst(1);
        deq.addFirst(2);
        deq.addFirst(3);
        deq.addFirst(4);
        deq.addFirst(5);
        Iterator it = deq.iterator();
        for (Integer i : deq) {
            System.out.print(i);
        }
        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        test();
        Deque<String> deq = new Deque<>();
        // Test 1: isEmpty returns 0 when empty
        System.out.println( "Deque.isEmpty(): " + (deq.isEmpty() ? "OK" : "FAIL"));

        // Test 2: Adding items at the front works   result: front-> B A <- end
        deq.addFirst("A");
        deq.addFirst("B");
        System.out.println( "Deque.addFirst(): " + (deq.size == 2 ? "OK" : "FAIL"));

        // Test 4: Adding items at the end works   result: front-> B A C D <- end
        deq.addLast("C");
        deq.addLast("D");
        System.out.println( "Deque.addLast(): " + (deq.size == 4 ?  "OK" : "FAIL"));

        // Test 5: Size works
        System.out.println( "Deque.size: " + (deq.size() == 4 ?  "OK" : "FAIL"));

        // Test 6: Iterator hasNext
        Iterator<String> it = deq.iterator();
        System.out.println( "Deque.iterator().hasNext(): " + (it.hasNext() ?  "OK" : "FAIL"));

        // Test 6.5: Iterator remove throws
        try {
            it.remove();
            System.out.println("Deque.iterator().remove() doesn't throw (FAIL!)");
        } catch(UnsupportedOperationException e) {
            System.out.println("Deque.iterator().remove() OK");
        }

        // Test 7: iterator next
        String next = it.next();
        System.out.println( "Deque.iterator().next(): " + (next == "B" ?  "OK" : "FAIL"));

        // Test 8: Removing items from the front works  result: front-> A C D <- end
        String removedFront = deq.removeFirst();
        System.out.println( "Deque.removeFirst(): " + (deq.size == 3 && removedFront.equals("B") ? "OK" : "FAIL"));

        // Test 9: Removing items from the end works   result: front-> A C <- end
        String removedEnd = deq.removeLast();
        System.out.println("Deque.removeLast(): " + (deq.size == 2 && removedEnd.equals("D") ? "OK" : "FAIL"));
    }
}
