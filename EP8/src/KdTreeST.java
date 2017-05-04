import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;
import java.util.Comparator;

public class KdTreeST<Value> {
    
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private Node<Value> root;
    private int size;
    private boolean inserted;

    private class Node<Value> {
        public Point2D coord;
        public Node<Value> left;
        public Node<Value> right;
        public Value val;
        public boolean direction;
        
        public Node() {
            coord = null;
            val = null;
            right = null;
            left = null;
            direction = false;
        }
        
        public Node(Point2D coord, Value val) {
            this.coord = coord;
            this.val = val;
            right = null;
            left = null;
            direction = false;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        size = 0;
    }                                
   
    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }                       
   
    // number of points
    public int size() {
        return size;
    }                          
   
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null)
            throw new java.lang.NullPointerException("put: p is null");
        if (val == null)
            throw new java.lang.NullPointerException("put: val is null");
    
        inserted = true;
        
        if (this.isEmpty())
            root = new Node<Value>(p, val);
        else
            put(p, val, root);
        
        if (inserted) size++;
    }
   
    // value associated with point p
    public Value get(Point2D p) {                  
        if (p == null)
            throw new java.lang.NullPointerException("get: p is null");

        return get(p, root);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {          
        if (p == null)
            throw new java.lang.NullPointerException("contains: p is null");

        return contains(p, root);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        LinkedList<Point2D> list = new LinkedList<Point2D>();
        points(list, root);
        return list;
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {              
        if (rect == null)
            throw new java.lang.NullPointerException("range: p is null");

        LinkedList<Point2D> list = new LinkedList<Point2D>();
        
        for (Point2D x : points())
            if (rect.contains(x))
                list.add(x);

        return list;
    }
   
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {  
        if (p == null)
            throw new java.lang.NullPointerException("nearest: p is null");

        double dist = Double.MAX_VALUE;
        Point2D y = new Point2D(0, 0);

        for (Point2D x : points()) {
            double tmp = p.distanceSquaredTo(x);
            if (dist > tmp) {
                dist = tmp;
                y = x;
            }
        }
        return y;
    }

    public Iterable<Point2D> nearest(Point2D p, int k) {  
        if (p == null)
            throw new java.lang.NullPointerException("nearest: p is null");

        double dist = Double.MAX_VALUE;
        Point2D y = new Point2D(0, 0);
        MinPQ<Point2D> queue = new MinPQ<Point2D>();

        for (Point2D x : points()) {
            double tmp = p.distanceSquaredTo(x);
            if (dist > tmp) {
                dist = tmp;
                y = x;
            }
        }
    }

    private void put(Point2D p, Value val, Node<Value> root) {
        
        if (root.coord.equals(p)) {
            root.val = val;
            inserted = false;
            return;
        }

        else if (root.direction ? root.coord.x() <= p.x() : root.coord.y() <= p.y()) {
            if (root.right == null) {
                root.right = new Node<Value>(p, val);
                root.right.direction = !root.direction;
                return;
            }
            else {
                put(p, val, root.right);
                return;
            }
        }
        
        else {
            if (root.left == null) {
                root.left = new Node<Value>(p, val);
                root.left.direction = !root.direction;
                return;
            }
            else { 
                put(p, val, root.left);
                return;
            }
        }
    }

    private Value get(Point2D p, Node<Value> root) {
        if (root == null)
            return null;

        else if (root.coord.equals(p))
            return root.val;

        else if (root.direction ? root.coord.x() <= p.x() : root.coord.y() <= p.y())
            return get(p, root.right);

        else
            return get(p, root.left);
    }

    private boolean contains(Point2D p, Node<Value> root) {
        if (root == null)
            return false;

        else if (root.coord.equals(p))
            return true;

        else if (root.direction ? root.coord.x() <= p.x() : root.coord.y() <= p.y())
            return contains(p, root.right);

        else
            return contains(p, root.left);
    }

    private void points(LinkedList<Point2D> list, Node<Value> root) {
        if (root == null) 
            return;
        else    
            list.add(root.coord);
        
        points(list, root.left);
        points(list, root.right);
        
        return;
    }

    // unit testing (required)
    public static void main(String[] args) {}                   
}
