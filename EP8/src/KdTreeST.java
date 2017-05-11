import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.MaxPQ;
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
        public Value val;
        public boolean direction;
        public RectHV rect;
        
        public Node<Value> left;
        public Node<Value> right;
        
        public Node() {
            coord = null;
            val = null;
            rect = null;
            right = null;
            left = null;
            direction = HORIZONTAL;
        }
        
        public Node(Point2D coord, Value val) {
            this.coord = coord;
            this.val = val;
            right = null;
            left = null;
            direction = VERTICAL;
            rect = null;
        }
    }
    
    private class priorityPoint implements Comparator<Point2D> {
        
        private Point2D para;

        public priorityPoint(Point2D para) {
            this.para = para;
        }

        public int compare (Point2D p, Point2D q) {
            if (p.distanceSquaredTo(para) < q.distanceSquaredTo(para)) return -1;
            if (p.distanceSquaredTo(para) == q.distanceSquaredTo(para)) return 0;
            return 1;
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
        
        if (this.isEmpty()) {
            root = new Node<Value>(p, val);
            root.rect = new RectHV(0, 0, 1, 1);
        }
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
        
        range(rect, root, list);
        return list;
    }
   
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {  
        if (p == null)
            throw new java.lang.NullPointerException("nearest: p is null");

        double dist = Double.MAX_VALUE;
        Point2D near = new Point2D(0, 0);
        
        near = nearest(p, root, dist, near);
        
        return near;
    }

    public Iterable<Point2D> nearest(Point2D p, int k) {  
        if (p == null)
            throw new java.lang.NullPointerException("nearest: p is null");

        Point2D near = new Point2D(0, 0);
        MaxPQ<Point2D> queue = new MaxPQ<Point2D>(new priorityPoint(p));
    
        nearest(p, root, k, queue);

        return queue;
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
                
                if (root.direction == VERTICAL)
                    root.right.rect = new RectHV(root.coord.x(), root.rect.ymin(), 
                                                 root.rect.xmax(), root.rect.ymax());
                else
                    root.right.rect = new RectHV(root.rect.xmin(), root.coord.y(), 
                                                 root.rect.xmax(), root.rect.ymax());
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
                
                if (root.direction == VERTICAL)
                    root.left.rect = new RectHV(root.rect.xmin(), root.rect.ymin(), 
                                                root.coord.x(), root.rect.ymax());
                else
                    root.left.rect = new RectHV(root.rect.xmin(), root.rect.ymin(),
                                                root.rect.xmax(), root.coord.x());
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

    private void points(LinkedList<Point2D> list, Node<Value> tmp) {
        if (tmp == null) return;
        
        if (tmp == root) list.add(tmp.coord);
        if (tmp.left != null) list.add(tmp.left.coord);
        if (tmp.right != null) list.add(tmp.right.coord);
        
        points(list, tmp.left);
        points(list, tmp.right);
        
        return;
    }

    private void range(RectHV rect, Node<Value> root, LinkedList<Point2D> list) {
        if (!root.rect.intersects(rect) || root == null)
            return;
        
        if (rect.contains(root.coord))
            list.add(root.coord);
        
        range(rect, root.right, list);
        range(rect, root.left, list);
    
        return;
    }
    
    private Point2D nearest(Point2D p, Node<Value> root, double dist, Point2D near) {
        if (root == null || root.rect.distanceTo(p) > dist) 
            return near;
        
        if (root.coord.distanceTo(p) < dist) {
            dist = root.coord.distanceTo(p);
            near = root.coord;
        }
        
        near = nearest(p, root.right, dist, near);
        near = nearest(p, root.left, dist, near);

        return near;
    }

    private void nearest(Point2D p, Node<Value> root, int k, MaxPQ<Point2D> queue) {

        if (root == null || root.rect.distanceTo(p) > queue.max().distanceTo(p)) 
            return;
       
        if (root.coord.distanceTo(p) < queue.max().distanceTo(p)) {
            queue.insert(root.coord);
            if (queue.size() > k)
                queue.delMax();
        }
        
        nearest(p, root.right, k, queue);
        nearest(p, root.left, k, queue);

        return;
    }

    // unit testing (required)
    public static void main(String[] args) {}                   
}
