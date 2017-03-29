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
        return (head != null);
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        
        Node tmp;
        for (tmp = head; tmp.next != null && tmp.key.compareTo(key) < 0; tmp = tmp.next);
        
        if (tmp.key.compareTo(key) == 0) return tmp.value;
        else return null;
    } 
    
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        
        Node tmp;
        int i = 0;
        
        for (tmp = head; tmp.next != null && tmp.key.compareTo(key) < 0; tmp = tmp.next, i++);
        return i;
    } 

    public void put(Key key, Value val)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        if (val == null) { 
            delete(key); 
            return; 
        }
        // escreva seu método a seguir
    } 

    public void delete(Key key)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        // escreva seu método a seguir
    } 

    public void deleteMin() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
        // escreva seu método a seguir
    }

    public void deleteMax() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
        // escreva seu método a seguir
    }


   /***************************************************************************
    *  Ordered symbol table methods
    **************************************************************************/

    /** Returns the smallest key in this table.
     * Returns null if the table is empty.
     */
    public Key min() {
        // escreva seu método a seguir
    }

   
    /** Returns the greatest key in this table.
     * Returns null if the table is empty.
     */
    public Key max() {
        // escreva seu método a seguir
    }

    /** Returns a key that is strictly greater than 
     * (exactly) k other keys in the table. 
     * Returns null if k < 0.
     * Returns null if k is greater that or equal to 
     * the total number of keys in the table.
     */
    public Key select(int k) {
        // escreva seu método a seguir
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
        // escreva seu método a seguir
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
        // escreva seu método a seguir
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
         * da menor até a maior chave.<br>
         */
        public Iterator<Key> iterator() {
            return new KeysIterator();
        }
        
        private class KeysIterator implements Iterator<Key> {
            // variáveis do iterador
            
            public boolean hasNext() {
                // escreva seu método a seguir
            }

            public Key next() {
                // escreva seu método a seguir
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
        // escreva seu método a seguir
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
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}

