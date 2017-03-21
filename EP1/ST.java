import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stopwatch;

public class ST {
    int length;
    int[] freq;
    String[] words;
    
    // Create an empty symbol table
    public ST() {
        length = 256;
        freq = new int[length];
        words = new String[length];
    }

    // Returns the value associated with the given key if the key is in the symbol table
    // and -1 if the key is not in the symbol table
    // Throws java.lang.NullPointerException - if key is null
    public int get(String key) {
        
        if (key == null) 
            throw new java.lang.NullPointerException("ST.get(): key is null");
        
        int i = findPos(key);

        if (key.equals(words[i])) return (freq[i]);
        else return -1;
    }

    // Insert the key into the symbol table and increase its value
    // Throws java.lang.NullPointerException - if key is null
    public void put(String key) {

        if (key == null) 
            throw new java.lang.NullPointerException("ST.put(): key is null"); 
        
        int i = findPos(key);
        
        if (i == length) resize(2*length);
        if (words[i] == null) words[i] = key;
        freq[i]++;
    }

    
    // Removes the key and associated value from the symbol table (if the key
    // is in the symbol table).
    // Throws java.lang.NullPointerException - if key is null
    public void delete(String key) {

        if (key == null)
            throw new java.lang.NullPointerException("ST.delete(): key is null");

        int i;

        for (i = findPos(key); i < length - 1 && words[i] != null; i++) {
            words[i] = words[i + 1];
            freq[i] = freq[i + 1];
        }
        
        i++;
        words[i] = null;
        freq[i] = 0;

        if ((i - 1) == length/4) resize(length/4);

    }

    // Does this symbol table contain the given key?
    // Throws  java.lang.NullPointerException - if key is null
    public boolean contains(String key) {
        if (key == null)
            throw new java.lang.NullPointerException("ST.contains(): key is null");
        
        int i = findPos(key);
    
        if (i == length) return false;
        return true;
    }

    // Returns the number of key-value pairs in this symbol table
    public int size() {
        int i;
        for (i = 0; i < length && words[i] != null; i++);
        return i;
    }

    // Is this symbol table empty?
    // Returns: true if this symbol table is empty and false otherwise
    public boolean isEmpty() {
        if (words[0] != null) return false;
        return true;
    }

    // Returns the largest (= maior frequência) key in the symbol table
    // Throws java.util.NoSuchElementException - if the symbol table is empty
    public String max() {
        int i, max, index;

        for (i = index = 0, max = freq[0]; i < length; i++)
            if (max < freq[i]) {
                max = freq[i];
                index = i;
            }   

        return words[index];
    }

    // Returns a string representing the symbol table
    // This string is used when we use StdOut.print*() to show the table
    public String toString() {
        int i;
        String str = "{";

        for (i = 0; i < length - 1 && words[i + 1] != null; i++)
            str += "'" + words[i] + "': " + freq[i] + " , ";

        str += "'" + words[i] + "': " + freq[i] + "}";

        return str;
    }


    // Move the symbol table to one of size k
    private void resize(int k) {
        String[] tmpstr = new String[k];
        int[] tmpint = new int[k];
        
        for (int i = 0; i < length && words[i] != null; i++) {
            tmpstr[i] = words[i];
            tmpint[i] = freq[i];
        }
        
        words = tmpstr;
        freq = tmpint;
    }

    // Recieves a string and search for it in the symbol table.
    // Returns the position that the key occupies in the table or
    // the first free position.
    private int findPos(String key) {
        int i;
        for (i = 0; i < length && !(key.equals(words[i])) && words[i] != null; i++);
        return i;
    }

    public static void main(String[] args) {
        String PROMPT  = ">>> ";
        String SHOW    = "show"; // show the ST
        String KEYS    = "keys"; // show the keys in the ST
        String SIZE    = "size"; // show the number of key-value pairs in the ST
        String MAX     = "max";  // show the key with the biggest 
        String s;
        
        In in = new In(args[0]);
        ST st1 = new ST();
        ST st2 = new ST();

        Stopwatch sw = new Stopwatch();
        
        while (!in.isEmpty()) {
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                st2.put(chaves[i]);
            }
        }
        
        StdOut.print(PROMPT);
        
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (s.equals(SHOW))
                StdOut.println(st2);

            else if (s.equals(SIZE))
                StdOut.println(st2.size());

            else if (s.equals(MAX))
                StdOut.println("'" + st2.max() + "'");

            else if (s.equals(KEYS))
                 for (String key: st2.words)
                    StdOut.println(key);

            else {
                StdOut.println(st2.get(s));
            }
            StdOut.print(PROMPT);
         }
    }
}


