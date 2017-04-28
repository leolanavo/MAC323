import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.SET;
import java.util.LinkedList;

public class Solver {
    private Board puzzle;
    private int moves;
    private MinPQ<Board> queue;
    private SET<Board> visited;
    private Stack<Board> printList;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        if (initial == null)
            throw new java.lang.NullPointerException(); 
        
        if (!initial.isSolvable())
            throw new java.lang.IllegalArgumentException();
        
        puzzle = initial;
        queue = new MinPQ<Board>();
        visited = new SET<Board>();

        visited.add(puzzle);

        puzzle.manhattan();
        puzzle.moves = 0;
        puzzle.previous = null;
        
        while (!puzzle.isGoal()) {
            Iterable<Board> list = puzzle.neighbors();
            
            for (Board tmp: list)
                if (!visited.contains(tmp))
                    queue.insert(tmp);

            puzzle = queue.delMin();
            visited.add(puzzle);
        }

        moves = puzzle.moves;
        
    }
    
    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }                     
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        printList = new Stack<Board>();

        while (puzzle != null) {
            printList.push(puzzle);
            puzzle = puzzle.previous;
        }
    
        return printList;
    }  
    
    // unit testing
    public static void main(String[] args) {
         In file = new In(args[0]);
         int size = file.readInt();
         int[][] tiles = new int[size][size]; 
        
         for (int row = 0; row < size; row++)
             for (int col = 0; col < size; col++)
                 tiles[row][col] = file.readInt();

         Board init = new Board(tiles);
         init.hamming();
         init.manhattan();
         Solver out = new Solver(init);
         StdOut.println(out.moves());

         Iterable<Board> prin = out.solution();

         for (Board x: prin)
             StdOut.println(x);
    }  
}
