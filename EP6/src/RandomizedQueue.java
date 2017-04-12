import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        
        public Node() {
            item = null;
            next = null;
        }

        public Node(Item item) {
            this.item = item;
            next = null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = null;
        tail = null;
        size = 0;
    }                 
   
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }                 
   
    // return the number of items on the queue
    public int size() {
        return size;
    }                        
   
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        
        Node entry = new Node(item);
        size++;

        if (head == null) head = entry;
        if (tail != null) tail.next = entry;
        tail = entry;
    }           
   
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(0, size);
        
        Item item;
        Node tmp = head;
        Node prev = null;

        for (int i = 0; i < index; i++) {
            prev = tmp;
            tmp = tmp.next;
        }

        if (prev == null) {
            item = head.item;
            head = head.next;
        }

        else if (tmp == tail) {
            item = tail.item;
            tail = prev;
            tail.next = null;
        }

        else {
            item = tmp.item;
            prev.next = tmp.next;
        }

        tmp = null;
        size--;
        return item;
    }                    
   
    // return a random item (but do not remove it)
    public Item sample() {                     
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(0, size);
        
        Node tmp = head;

        for (int i = 0; i < index; i++)
            tmp = tmp.next;

        return tmp.item;
    }
   
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListQueue();
    }
   
    private class ListQueue implements Iterator<Item> {
        Node current;
        Item item;

        public ListQueue() {
            current = new Node();
            current.next = head;
            Item item;
        }

        public boolean hasNext() {
            return (current != null && current.next != null);
        }

        public Item next() {
            if (!hasNext()) 
                throw new java.util.NoSuchElementException();
            current = current.next;
            item = current.item;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();    
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String statement, word;
        
        String PROMPT = ">>> ";
        StdOut.print(PROMPT);

        while (!StdIn.isEmpty()) {
            statement = StdIn.readString();

            switch(statement) {
                case "empty":
                    StdOut.println(rq.isEmpty());
                    break;
                
                case "size":
                    StdOut.println(rq.size());
                    break;
                
                case "add":
                    word = StdIn.readString();
                    rq.enqueue(word);
                    break;
                
                case "remove":
                    StdOut.println(rq.dequeue());
                    break;
                
                case "sample":
                    StdOut.println(rq.sample());
                    break;
                
                case "show":
                    Iterator<String> ite = rq.iterator();
                    while(ite.hasNext())
                        StdOut.println(ite.next());
                    break;
            }
            StdOut.print(PROMPT);
        }
    }   
}
