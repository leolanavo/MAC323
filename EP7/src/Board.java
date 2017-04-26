import java.util.Iterator;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    public int moves;
    private final int size;
    private int hamming;
    private int manhattan;
    private int[][] board;
    public Board previous;


    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        board = tiles;
        size = tiles.length;
        int length = tiles.length*tiles.length + 1;
        hamming = 0;
        manhattan = 0;
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
        
        if (previous == null) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (tileAt(row, col) != idealValue(row, col))
                        hamming++;
                }
            }
        }
        
        else {
            hamming--;
            index = gotMoved();
            int row = (index - 1)/size;
            int col = (index - 1)%size;
            if (index != tileAt(row, col))
                hamming++;
        }
        
        return hamming;
    }                   
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int index, diffRow, diffCol, value;
        
        if (moves == 0) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    value = tileAt(row, col);
                    
                    diffRow = Math.abs((value - 1)/size - row);
                    diffCol = Math.abs((value - 1)%size - col);
                    
                    manhattan += (diffRow + diffCol);
                }
            }
        }
        else {
            index = gotMoved();

            //adicionar a subtração do anterior
            
            int row = (index - 1)/size;
            int col = (index - 1)%size;
            
            value = tileAt(row, col);
            
            diffRow = Math.abs((value - 1)/size - row);
            diffCol = Math.abs((value - 1)%size - col);
            
            manhattan += (diffRow + diffCol);
        }
        
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (idealValue(row, col) != tileAt(row, col))
                    return false;
        return true;
    }                
    
    // Return true if the board is solvable, false, otherwise.
    public boolean isSolvable() {
        int inversions = 0;
        int blankRow = 0;
        int idealPos, currPos;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                
                currPos = tileAt(row, col);
                idealPos = idealValue(row, col);
                
                if (tileAt(row, col) == 0)
                    blankRow = row;
                else if (row < col && currPos != idealPos)
                    inversions += Math.abs((currPos - idealPos));
            }
        }

        if (size%2 == 0 && (inversions + blankRow)%2 != 0)
            return true;
        if (size%2 != 0 && inversions%2 == 0)
            return true;

        return false;
    }            
    
    // Return true if this.board and y.board are equals,
    // false, otherwise.
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

    // What position was moved in the previou round.
    private int gotMoved () {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int indexCurr = this.tileAt(row, col);
                int indexPrev = previous.tileAt(row, col);
                if (indexCurr != 0 && indexCurr != indexPrev)
                    return idealValue(row, col);
            }
        }
        return 0;
    }

    // Return the ideal value that should be in that position
    private int idealValue(int row, int col) {
        return size*row + col + 1;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        return neighborsListing();
    }

    private LinkedList<Board> neighborsListing() {
        
        int row = 0, col = 0;
        LinkedList<Board> neighbors = new LinkedList<Board>();
        
        for (row = 0; row < size; row++)
            for (col = 0; col < size; col++)
                if (tileAt(row, col) == 0)
                    break;


        if (row - 1 > 0) {
            Board tmp = new Board(move(row - 1, col, row, col));
            tmp.previous = this;
            tmp.moves = this.moves + 1;
            tmp.hamming();
            tmp.manhattan();
            neighbors.add(tmp);
        }
        
        if (row + 1 < size) {
            Board tmp = new Board(move(row + 1, col, row, col));
            tmp.previous = this;
            tmp.moves = this.moves + 1;
            tmp.hamming();
            tmp.manhattan();
            neighbors.add(tmp);
        }

        if (col - 1 > 0) {
            Board tmp = new Board(move(row, col - 1, row, col));
            tmp.previous = this;
            tmp.moves = this.moves + 1;
            tmp.hamming();
            tmp.manhattan();
            neighbors.add(tmp);
        }
        
        if (col + 1 < size) {
            Board tmp = new Board(move(row, col + 1, row, col));
            tmp.previous = this;
            tmp.moves = this.moves + 1;
            tmp.hamming();
            tmp.manhattan();
            neighbors.add(tmp);
        }

        return neighbors;
    }

    private int[][] move(int row1, int col1, int row2, int col2) {
        int[][] moved = new int[size][size];
        
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                moved[row][col] = board[row][col];

        int tmp = moved[row2][col2];
        
        moved[row2][col2] = moved[row1][col1];
        moved[row1][col1] = tmp;

        return moved;
    }
    
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
