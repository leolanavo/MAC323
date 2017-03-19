import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stopwatch;

public class Arr {
    int length;
    String[] words;
    int[] freq;

    public Arr(int cap) {
        length = cap;
        freq = new int[length];
        words = new String[length];
    }

    private void resize(int new_cap, Arr a) {
        Arr tmp = new Arr(new_cap);
        a.length = new_cap;
        

    }

    //Recieves a string and insert it on the symbol table's fisrt available
    //position, if it isn't a new world, it will sums 1 in the corresponding
    //frequency in the freq array. Resize the symbol table if needed.
    private void put(String str) {
        int i;

        for (i = 0; i < length && words[i] != null && words[i] != str; i++);
        
        //if (i == length) resize(length*2);
        
        if (words[i] != null)
            freq[i]++;

        else {
            words[i] = str;
            freq[i] = 1;
        }
    }

    public static void main (String[] args) {
        int cap = 10;
        String str;
        Arr a = new Arr(cap);
        In entry = new In(args[0]);
        
        for (int i = 0; !entry.isEmpty(); i++) {
            str = entry.readString();
            a.put(str);
        }

        for (int i = 0; a.words[i] != null; i++) StdOut.println(a.words[i]);
        
    }
}
