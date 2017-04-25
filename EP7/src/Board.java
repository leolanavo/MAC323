public class Board {
    public int moves;
    private final int size;
    private int[] hammingCache;
    private int[] manhattanCache;
    private int[][] board;
    private int[][] previous;


    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        board = tiles;
        size = tiles.length;
        int length = tiles.length*tiles.length + 1;
        hammingCache = new int[length];
        manhattanCache = new int[length];
    }

    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        return board[i][j];
    }       
    
    // board size N
    public int size() {
        return size;
    }
    
    // number of tiles out of place
    public int hamming() {
        int index, diff;
        
        if (moves == 0) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    index = size*row + col + 1;
                    hammingCache[index] = Math.abs(index - tileAt(row, col));;
                }
            }
        }
        else {
            index = gotMoved();
            int row = (index - 1)/size;
            int col = (index - 1)%size;
            hammingCache[index] = Math.abs(index - tileAt(row, col));
        }
        
        int sum = 0;
        for (int x: hammingCache)
            sum += x;
        return sum;
    }                   
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int index, diffRow, difCol;
        
        if (moves == 0) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    index = size*row + col + 1;
                    value = tileAt(row, col);
                    diffRow = Math.abs((value - 1)/size - row);
                    diffCol = Math.abs((value - 1)%size - col);
                    manhattanCache[index] = diffRow + diffCol;
                }
            }
        }
        else {
            index = gotMoved();
            int row = (index - 1)/size;
            int col = (index - 1)%size;
            value = tileAt(row, col);
            diffRow = Math.abs((value - 1)/size - row);
            diffCol = Math.abs((value - 1)%size - col);
            manhattanCache[index] = diffRow + diffCol;
        }
        
        int sum = 0;
        for (int x: hammingCache)
            sum += x;
        return sum;
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
        Board x = (Board)y;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (this.tileAt(row, col) != x.tileAt(row, col))
                    return false;
            }
        }
        return true;
    }

    private int gotMoved () {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int index = tileAt(row, col);
                if (index != 0 && index != previous[row][col])
                    return (size*row + col + 1);
            }
        }
        return 0;
    }
    
    // all neighboring boards
    //public Iterable<Board> neighbors() {}     
    
    // string representation of this board (in the output format specified below)
    //public String toString() {}               
    
    // unit testing
    public static void main(String[] args) {
        int[][] tiles = {{1,2,3},
                         {4,5,6},
                         {7,8,0}};
        System.out.println(tiles.length);
    }
}
