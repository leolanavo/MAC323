import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import java.util.Iterator; 

public class LinkedListST<Key extends Comparable<Key>, Value> {
    
    private Node head;
    private int size;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node() {
           key = null;
           value = null;
           next = null;
        }

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            next = null;
        }

    }
    
    public LinkedListST() {
        head = new Node();
        size = 0;
    }   

    public boolean contains(Key key) {
        Node tmp;
        
        for (tmp = head; tmp.next != null && tmp.key.compareTo(key) < 0; tmp = tmp.next);
        
        return (tmp.key.compareTo(key) == 0);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (!(head != null));
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        
        Node tmp = head;
        while (tmp.next != null && tmp.key.compareTo(key) < 0) 
            tmp = tmp.next;

        if (tmp.key.compareTo(key) == 0) return tmp.value;
        else return null;
    } 
    
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        
        Node tmp = head;
        int i = 0;
        
        while (tmp.next != null && tmp.key.compareTo(key) < 0) {
            tmp = tmp.next;
            i++;
        }
        
        return i;
    } 

    public void put(Key key, Value val)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        
        if (val == null) { 
            delete(key); 
            return; 
        }
        
        Node new_entry = new Node(key, val);
        
		Node tmp = head;
        Node prev = null;

		while (tmp != null && tmp.key != null&& tmp.key.compareTo(key) < 0) {
            prev = tmp;
            tmp = tmp.next;
        }
        
		if (prev == null) {
            new_entry.next = head;
            head = new_entry;
        }
        else if (tmp == null)
            prev.next = new_entry;

        else if (tmp.key != null && tmp.key.compareTo(key) == 0)
            tmp.value = val;
        
        else {
            prev.next = new_entry;
            new_entry.next = tmp;
        }
        
        return;
    } 

    public void delete(Key key)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        
        Node tmp = head;
        Node prev = null;
        while (tmp.next != null && tmp.key.compareTo(key) < 0) {
            prev = tmp;
            tmp = tmp.next;
        }

        if (tmp.key.compareTo(key) == 0 && prev != null) {
            prev.next = tmp.next;
            tmp.key = null;
            tmp.value = null;
        }

        else if (prev == null) {
            tmp.key = null;
            tmp.value = null;
            head = head.next;
        }

        return;

    } 

    public void deleteMin() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
        
        delete(head.key);
        return;
    }

    public void deleteMax() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
        
        Node tmp = head;
        Node prev = null;

        while (tmp.next != null) {
            prev = tmp;
            tmp = tmp.next;
        }
        
        if (prev != null) 
            prev.next = null;
        tmp.key = null;
        tmp.value = null;
    }


    /** Returns the smallest key in this table.
     *  Returns null if the table is empty. */
    public Key min() {
        if(isEmpty()) return null;
        return head.key;
    }

   
    /** Returns the greatest key in this table.
     *  Returns null if the table is empty. */
    public Key max() {
        Node tmp = head;
        while(tmp.next != null)
            tmp = tmp.next;

        return tmp.key;
    }

    /** Returns a key that is strictly greater than 
     * (exactly) k other keys in the table. 
     * Returns null if k < 0.
     * Returns null if k is greater that or equal to 
     * the total number of keys in the table. */
    public Key select(int k) {
        if (k < 0 || k >= size()) return null;
        
        Node tmp = head;
        for (int i = 0; tmp != null && i <= k; i++)
            tmp = tmp.next;
        return tmp.key;
    }

    /** Returns the greatest key that is 
     * smaller than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is smaller than any key in the table), 
     * returns null.
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (head == null || head.key.compareTo(key) > 0) return null;

        Node tmp = head;
        Node prev = null;

        while (tmp != null && tmp.key.compareTo(key) < 0) {
            prev = tmp;
            tmp = tmp.next;
        }
        
        if (tmp == null) return null;
        if (tmp.key.compareTo(key) == 0) return tmp.key; 
        return prev.key;
    }

    /** Returns the smallest key that is 
     * greater than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is greater than any key in the table), 
     * returns null.
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (head == null) return null;
        Node tmp = head;

        while (tmp != null && tmp.key.compareTo(key) < 0)
            tmp = tmp.next;

        if (tmp == null) return null;
        return tmp.key;
    }

    /** Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st, use the
     * foreach notation: for (Key key : st.keys()).
     */
    public Iterable<Key> keys() {
        return new ListKeys();
    }

    /**
     * implements Iterable<Key> significa que essa classe deve 
     * ter um método iterator(), acho...
     */
    private class ListKeys implements Iterable<Key> {
        /** 
         * Devolve um iterador que itera sobre os itens da ST 
         * da menor até a maior chave.
         */
        public Iterator<Key> iterator() {
            return new KeysIterator();
        }
        
        private class KeysIterator implements Iterator<Key> {
            Node current = head;
            Key key;
            
            public boolean hasNext() {
                return (current.next != null);
            }

            public Key next() {
                key = current.key;
                current = current.next;
                return key;
            }
                    
            public void remove() { 
                throw new UnsupportedOperationException(); 
            }
        }
    }


   /***************************************************************************
    *   Check internal invariants: pode ser útil durante o desenvolvimento 
    **************************************************************************/
    
    // are the items in the linked list in ascending order?
    private boolean isSorted() {
        if (head == null || head == null) return true;
        Node tmp = head;
        while (tmp != null) {
            if (tmp.key.compareTo(tmp.next.key) >= 0) return false;
        }
        return true;
    }

   /** Test client.
    * Reads a sequence of strings from the standard input.
    * Builds a symbol table whose keys are the strings read.
    * The value of each string is its position in the input stream
    * (0 for the first string, 1 for the second, and so on).
    * Then prints all the (key,value) pairs.
    */
    public static void main(String[] args) { 
        LinkedListST<String, Integer> st;
        st = new LinkedListST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        StdOut.println("hello");
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}

