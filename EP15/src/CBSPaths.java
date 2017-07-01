import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class CBSPaths {

    public String MP;
    public int cities;
    public int flights;
    public int executives;
    public String[] places;
    public EdgeWeightedDigraph paths;
    public LinearProbingHashST<String, Integer> dictionary; 

    public CBSPaths(int cities, int flights, int executives) {
        this.cities = cities;
        this.flights = flights;
        this.executives = executives;
        this.places = new String[executives];
        this.dictionary = new LinearProbingHashST<>(cities); 
        this.paths = new EdgeWeightedDigraph(cities);
        
        for (int i = 0; i < flights; i++) {
            String from = StdIn.readString();
            String to = StdIn.readString();
            int time = StdIn.readInt();
            addFlight(from, to, time);
        }

        for (int i = 0; i < executives; i++)
            moveExecutive(i, StdIn.readString());

        MP = StdIn.readString();
    }

    public Iterable<String> safePlaces () {
        int count;
        MinPQ<String> queue = new MinPQ<>();
        
        for (String to: dictionary.keys()) {
            count = 0;
            for (String from: places) {
                if (travelTime(MP, to) > travelTime(from, to))
                    count++;
            }
            if (count == places.length)
                queue.insert(to);
        }
        
        if (queue.isEmpty()) return null;
        else return queue;
    }

    public int travelTime (String source, String destiny) {
        int from = dictionary.get(source);
        int to = dictionary.get(destiny);
        int[] distTo = new int[cities];
        boolean[] marked = new boolean[cities];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < cities; i++) {
            distTo[i] = Integer.MAX_VALUE;
            marked[i] = false;
        }

        distTo[from] = 0;
        stack.push(from);
        
        while (from != to) {
            from = stack.pop();
            for (DirectedEdge x: paths.adj(from)) {
                if (!marked[x.to()]) stack.push(x.to());
                if (distTo[x.to()] > distTo[x.from()] + x.weight())
                    distTo[x.to()] = distTo[x.from()] + (int) x.weight();
            }
            marked[from] = true;
        }

        return distTo[to];
    }

    public void addFlight (String source, String destiny, int time) {
        if (!dictionary.contains(source))
            dictionary.put(source, dictionary.size());
        
        if (!dictionary.contains(destiny))
            dictionary.put(destiny, dictionary.size());
       
        int sourceCode = dictionary.get(source);
        int destinyCode = dictionary.get(destiny);
        
        paths.addEdge(new DirectedEdge(sourceCode, destinyCode, (double) time)); 
    }

    public void moveExecutive (int i, String city) {
        places[i] = city;
    }

    public static void main (String[] args) {
        CBSPaths grid = new CBSPaths(StdIn.readInt(), StdIn.readInt(), StdIn.readInt());
        Iterable<String> safeZones = grid.safePlaces();
       
        if (safeZones == null) {
            StdOut.println("VENHA COMIGO PARA CURITIBA");
            return;
        }
         
        for (String str: safeZones)
            StdOut.println(str);
    }
}
