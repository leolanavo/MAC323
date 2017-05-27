import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;

public class WordFinder {
    
    SeparateChainingHashST<String, LinkedList<Integer>> hashst;
    
    public WordFinder(String[] str) {
        hashst = new SeparateChainingHashST<>();
        LinkedList<Integer> index = null;

        for (int i = 0; i < str.length; i++)
            for (String word: str[i].split(" ")) {
                index = hashst.get(word);
                    
                if (index == null)
                    index = new LinkedList<>();
                
                if (!index.contains(i)) 
                    index.add(i);
                
                hashst.put(word, index);
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

    public int[] appearsIn(String str) {
        
        LinkedList<Integer> index = hashst.get(str);
        if (index == null)
            return new int[0];
        int[] v = new int[index.size()];
        int i = 0;

        for (int x: index) {
            v[i] = x;
            i++;
        }

        return v;
    }

    public static void main(String[] args) {
        String[] str = new String[2];
        str[0] = "Oi tudo bom com voce";
        str[1] = "Oi tudi sim obrigado";
        
        WordFinder wd = new WordFinder(str);
        StdOut.println("max: " + wd.getMax());
        StdOut.println("--indexes--");
        for (int x: wd.appearsIn("Oi"))
            StdOut.println(x);
        StdOut.println("-----------");
        StdOut.println("contido em 0 e 1: " + wd.containedIn(0, 1));
    }
}
