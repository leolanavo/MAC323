import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut; 
import java.util.LinkedList;

public class Tail {
    
    In file;
    LinkedList<String> queue;

    public Tail(String fileName) {
        file = new In(fileName);
        queue = new LinkedList<String>();
        String line;

        while(!file.isEmpty()) {
            line = file.readLine();
            queue.addLast(line);
            if (queue.size() > 10)
                queue.removeFirst();
        }

    }   
    
    public Tail(String fileName, int k) {
        file = new In(fileName);
        queue = new LinkedList<String>();
        String line;

        while(!file.isEmpty()) {
            line = file.readLine();
            queue.addLast(line);
            if (queue.size() > k)
                queue.removeFirst();
        }
    } 

    public Iterable<String> lines() {
        return queue;
    }
    
    private static void use() {
        String message = "Use: java Tail [-n NUM] [FILE]\n" +
            "   Print the last 10 lines of each FILE to standard output.\n" +
            "   With more than one FILE, precede each with a header giving the file name.\n" +
            "   -n NUM: output the last NUM lines, instead of the last 10";
        StdOut.println(message);
    }
    
   /** Unit test
    */
    public static void main(String[] args) {
        if (args.length == 0) {
            use();
            return;
        }
        
        if (args[0].equals("-n")) {
            int k = Integer.parseInt(args[1]);
            for (int i = 2;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i], k);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }
            
        }
        else {
            for (int i = 0;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i]);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }
        }
    }
}

