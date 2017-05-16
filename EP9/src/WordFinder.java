import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.lang.StringBuilder;
import java.util.LinkedList;

public class WordFinder {
    
    SeparateChainingHashST<String, LinkedList<Integer>> hashst;
    
    public WordFinder(String[] str) {
        hashst = new SeparateChainingHashST<>();
        StringBuilder tmp = new StringBuilder();
        LinkedList<Integer> index = null;
        char c = 0;


        for (int i = 0; i < str.length; i++) 
            for (int j = 0; j < str[i].length(); j++) {
                c = str[i].charAt(j);
                
                if (c >= 64 && c <= 90 || c >= 97 && c <= 122)
                    tmp.append(c);
                
                else if (c == 32) {
                    index = hashst.get(tmp.toString());
                    
                    if (index == null)
                        index = new LinkedList<>();
                    
                    if (!index.contains(i)) 
                        index.add(i);
                    
                    hashst.put(tmp.toString(), index);
                }
            }   
    }       

    public String getMax() {
        String str = null;
        int size = Integer.MIN_VALUE;
        int tmp;
        
        for (String x: hashst.keys()) {
            tmp = hashst.get(x).size();
            if (tmp > size) {
                size = tmp;
                str = x;
            }
        }

        return str;
    }

    public String containedIn(int a, int b) {
        LinkedList<Integer> index = null;

        for (String x: hashst.keys()) {
            index = hashst.get(x);
            if (index.contains(a) && index.contains(b))
                return x;
        }
        
        return null;
    }

    public int[] appearIn(String str) {
        
        LinkedList<Integer> index = hashst.get(str);
        int[] v = new int[index.size()];
        int i = 0;

        for (int x: index) {
            v[i] = x;
            i++;
        }

        return v;
    }

    public static void main(String[] args) {
    
    }
}
