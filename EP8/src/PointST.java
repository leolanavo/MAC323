import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;

public class PointST<Value> {

    private RedBlackBST<Point2D, Value> flamengo;
   
    // construct an empty symbol table of points 
    public PointST() {
        flamengo = new RedBlackBST<>();
    }                             
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return flamengo.isEmpty();
    }
   
    // number of points
    public int size() {
        return flamengo.size();
    }
   
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null)
            throw new java.lang.NullPointerException("put: p is null");
        if (val == null)
            throw new java.lang.NullPointerException("put: val is null");
        flamengo.put(p, val);
    }
   
    // value associated with point p
    public Value get(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("get: p is null");
        return flamengo.get(p);
    }
   
    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("contains: p is null");
        return flamengo.contains(p);
    }            
   
    // all points in the symbol table
    public Iterable<Point2D> points() {
        return flamengo.keys();
    }  
   
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException("range: rect is null");
        
        LinkedList<Point2D> list = new LinkedList<>();
        
        for (Point2D x: points())
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

        for (Point2D x: points()) {
            double tmp = p.distanceSquaredTo(x);
            if (dist > tmp) {
                dist = tmp;
                y = x;
            }
        }

        return y;
    }             
   
    // unit testing (required)
    public static void main(String[] args) {
    }                  
}
