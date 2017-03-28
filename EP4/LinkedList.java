import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut; 


public class LinkedList {
    
    private int limit;
    public int size;
    private Node head;
    private Node tail;

    public class Node {
        public String entry;
        public Node next;
    
        public Node () {
            entry = null;
            next = null;
        }
        
        public Node (String str) {
            entry = str;
            next = null;
        }
    }

    public LinkedList() {
        limit = 6;
        size = 0;
        head = new Node();
        tail = head;
    }

    public LinkedList(int cap) {
        limit = cap;
        size = 0;
        head = new Node();
        tail = new Node();
    }

    public void add(String str) {
        
        Node new_entry = new Node(str);

        if (size == 0) {
            head = new_entry;
            tail = head;
        }
        else {
            tail.next = new_entry;
            tail = new_entry;
        }
        
        if (size == limit) head = head.next;
        else size++;
    }

    public String toString () {
        StringBuilder str_final = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            str_final.append(head.entry + "\n");
            head = head.next;
        }
        str_final.append(head.entry);
        return str_final.toString();
    }

    public static void main(String[] args) {
        
        In in = new In(args[1]);
        
        int border = 0;
        for (int i = 0; i < args[0].length(); i++) {
            border *= 10;
            border += (args[0].charAt(i) - '0');
        }
       
        LinkedList list = new LinkedList(border);
        while (!in.isEmpty()) {
            String line = in.readLine();
            list.add(line);
        }
        StdOut.println(list.size);
        StdOut.println(list.toString());
    }

}
