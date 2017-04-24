public class Board {
    private final int size;
    private int[][] board;
    private int hammingCache;
    private int manhattanCache;

    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        board = tiles;
        size = 0;
        //hammingCache = new int[tiles.length()];
    }

    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        return 0;
    }       
    
    // board size N
    public int size() {
        return size;
    }
    
    // number of tiles out of place
    public int hamming() {
        return 0;
    }                   
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }                 
    
    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }                
    
    // is this board solvable?
    public boolean isSolvable() {
        return false;
    }            
    
    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }        
    
    // all neighboring boards
    //public Iterable<Board> neighbors() {}     
    
    // string representation of this board (in the output format specified below)
    //public String toString() {}               
    
    // unit testing
    public static void main(String[] args) {
        int[][] tiles = {{1,2,3},
                         {4,5,6},
                         {7,8,9}};
        System.out.println(tiles.length);
    }
}