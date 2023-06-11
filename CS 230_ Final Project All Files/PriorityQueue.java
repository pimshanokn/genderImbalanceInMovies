package javafoundations;
import javafoundations.exceptions.*;

/**
 * PriorityQueue is an implementation of a Queue structure -- when
 * elements are enqueued, the elements are compared to one another
 * to determine the order they appear in the queue. The order from
 * highest priority to lowest can be displayed when the queue is 
 * dequeued.
 *
 * @author Moji, Janice, and Hana
 * @version 12/16/2022
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T>
{
    private LinkedMaxHeap<T> heap;

    /**
     * Constructor for objects of class PriorityQueue
     */
    public PriorityQueue() {
        heap = new LinkedMaxHeap<T>();
    }

    /**
     * Adds element to the PriorityQueue, making sure that the
     * element is sorted (prioritized) as needed
     * 
     * @param  element  the element to be added
     */
    public void enqueue (T element) {
        heap.add(element);
    }

    /**
     * Removes and returns the element at the front of the queue
     * 
     * @throws  EmptyCollectionException
     * @return  the element that was removed
     */
    public T dequeue() throws EmptyCollectionException {
        if (heap.root == null) {
            throw new EmptyCollectionException ("Dequeue operation failed. Queue is empty");
        }  

        T result = heap.removeMax();
        return result;
    }

    /**
     * Returns a reference to the element at the front of the queue 
     * without removing it
     * 
     * @return  the first (largest) element of the PriorityQueue
     */
    public T first() {
        return heap.getMax();
    }

    /**
     * Returns true if the queue contains no elements and false 
     * otherwise
     * 
     * @return  true if the PriorityQueue has no elements, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements in the queue
     * 
     * @return  the number of elements in the PriorityQueue
     */
    public int size() {
        return heap.size();
    }

    /**
     * Returns a string representation of the queue in an inorder order
     * 
     * @return  a String representation of the PriorityQueue, listing elements in inorder order
     */
    public String toString() {
        return heap.toString();
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> pQInt = new PriorityQueue<Integer>();

        // Testing enqueue
        System.out.println("Testing enqueue on PriorityQueue (enqueue Integers 110, 121, 230, 307, 240, 313)");
        pQInt.enqueue(111);
        pQInt.enqueue(121);
        pQInt.enqueue(230);
        pQInt.enqueue(307);
        pQInt.enqueue(240);
        pQInt.enqueue(313);

        // Testing first
        System.out.println("\nTesting first on PriorityQueue (expect 313): " + pQInt.first()); 

        // Testing size
        System.out.println("\nTesting size on PriorityQueue (expect 6): " + pQInt.size());

        // Testing isEmpty
        System.out.println("\nTesting isEmpty on PriorityQueue (expect false): " + pQInt.isEmpty());

        // Testing toString
        System.out.println("\nTesting toString on PriorityQueue (expect inorder order):");
        System.out.println(pQInt);

        // Testing dequeue
        System.out.println("Testing dequeue on PriorityQueue until all elements are dequeued (expect highest to lowest):");
        while (!pQInt.isEmpty()) {
            System.out.println("Dequeuing " + pQInt.dequeue());
        }

        // Testing isEmpty
        System.out.println("\nTesting isEmpty on empty PriorityQueue (expect true): " + pQInt.isEmpty());
    }
}
