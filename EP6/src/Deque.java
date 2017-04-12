import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import java.util.Iterator; 
import java.util.NoSuchElementException; 

public class Deque<Item> implements Iterable<Item> {
  
    private int size;
    private Node head;

    private class Node {
        public Item item;
        public Node next;

        public Node() {
            item = null;
            next = null;
        }
        
        public Node(Item item) {
            this.item = item;
            next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        head = null;
        size = 0;
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
        if (item == null)
            throw new java.lang.NullPointerException();
        
        Node entry = new Node(item);
        if (head != null) entry.next = head;
        head = entry;
        
        size++;
        
    }          
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        
        Node entry = new Node(item);
        
        Node tmp = head;
        for (int i = 0; i < size; i++) {
            if (tmp.next == null) break;
            tmp = tmp.next;
        }
        
        if (tmp == null) head = entry;
        else tmp.next = entry;
        
        size++;
    }           
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        
        Item entry = head.item;
        Node oldhead = head;
        head = head.next;
        oldhead = null;
        size--;

        return entry;
    }                
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        
        Node tmp = head;
        Node prev = null;

        for (int i = 0; i < size; i++) {
            if (tmp.next == null) break;
            prev = tmp;
            tmp = tmp.next;
        }
        
        Item entry = tmp.item;
        tmp = null;
        if (prev != null) prev.next = null;
        
        size--;

        return entry;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListItens();
    }

    private class ListItens implements Iterator<Item> {
        Node current;
        Item item;

        public ListItens() {
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
        Deque<String> dq = new Deque<String>();
        String comando, palavra;
        
        dq.addLast(" estudar");
        dq.addLast(" MAC0323");
        dq.addFirst(" adoro");
        dq.addFirst("eu");

        String result = "";
        for(String s : dq) {
            result = result.concat(s);
        }
        StdOut.println(result);


        //String PROMPT = ">>> ";
        //StdOut.print(PROMPT);
        
        //while (!StdIn.isEmpty()) {
            //comando = StdIn.readString();
     
			//switch (comando) {
				//case "empty":
					//StdOut.println(dq.isEmpty());
					//break;
				//case "size":
					//StdOut.println(dq.size());
					//break;
				//case "first":
					//palavra = StdIn.readString();
					//dq.addFirst(palavra);
					//break;
				//case "last":
					//palavra = StdIn.readString();
					//dq.addLast(palavra);
					//break;
				//case "rf":
					//StdOut.println(dq.removeFirst());
					//break;
				//case "rl":
					//StdOut.println(dq.removeLast());
					//break;
				//case "show":
					//for(String s: dq)
                        //StdOut.println(s);
					//break;
            //}
            //StdOut.print(PROMPT);
        //}
    }
}   


