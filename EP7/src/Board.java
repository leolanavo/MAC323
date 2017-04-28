import java.util.Iterator;
import java.lang.StringBuilder;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdOut;

public class Board implements Comparable<Board>{
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
        hamming = 0;
        manhattan = 0;
    }

    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        if (i >= 0 && j >= 0 && i < size && j < size)
            return board[i][j];
        return -1;
    }       
    
    // board size N
    public int size() {
        return size;
    }
    
    // Return the hamming priority of the current board.
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
            hamming = previous.hamming - 1;
            index = gotMoved();
            int row = (index - 1)/size;
            int col = (index - 1)%size;
            if (index != tileAt(row, col))
                hamming++;
        }
        
        return hamming;
    }                   
    
    // Return the manhattan priority of the current board.
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
            manhattan = previous.manhattan;
            index = gotMoved();

            int row = (index - 1)/size;
            int col = (index - 1)%size;
            
            int valueCurr = tileAt(row, col);
            int valuePrev = searchPrev(row, col, valueCurr);
            manhattan -= valuePrev;

            diffRow = Math.abs((valueCurr - 1)/size - row);
            diffCol = Math.abs((valueCurr - 1)%size - col);
            
            manhattan += (diffRow + diffCol);
        }
        
        return manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++)
                if (idealValue(row, col) != tileAt(row, col))
                    if (tileAt(row, col) != 0)
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
                
                if (currPos == 0)
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

    // Return the position that was moved in the previous round.
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
    
    // Return an iterable with all the posible movements
    // according to the current board.
    public Iterable<Board> neighbors() {
        return neighborsListing();
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder((size + 1)*size);
        for (int row = 0; row < size; row++) {
            if (row != 0) str.append("\n");
            for (int col = 0; col < size; col++)
                str.append(tileAt(row, col) + " ");
        }

        str.append("\n");

        return str.toString();
    }               

    public int compareTo(Board x) {
        return (this.manhattan + this.moves) - (x.manhattan + x.moves);
    }

    // Return the ideal value that should be in that coordenate
    private int idealValue(int row, int col) {
        return size*row + col + 1;
    }

    // Receives the coordinates to where the search will be
    // centered and the value of the tile that it needs to find.
    // Return the manhattan priority on the previous board 
    // of the piece corresponding to the given value.
    private int searchPrev(int row, int col, int value) {
        int diffRow, diffCol;

        if (row - 1 >= 0)
            if (previous.tileAt(row - 1, col) == value) {
                row--;
                diffRow = Math.abs((value - 1)/size - row);
                diffCol = Math.abs((value - 1)%size - col);
                return diffRow + diffCol;
            }
        
        else if (row + 1 < size)
            if (previous.tileAt(row + 1, col) == value) {
                row++;
                diffRow = Math.abs((value - 1)/size - row);
                diffCol = Math.abs((value - 1)%size - col);
                return diffRow + diffCol;
            }
        
        else if (col - 1 >= 0)
            if (previous.tileAt(row, col - 1) == value) {
                col--;
                diffRow = Math.abs((value - 1)/size - row);
                diffCol = Math.abs((value - 1)%size - col);
                return diffRow + diffCol;
            }
        
        else if (col + 1 < size)
            if (previous.tileAt(row, col + 1) == value) {
                col++;
                diffRow = Math.abs((value - 1)/size - row);
                diffCol = Math.abs((value - 1)%size - col);
                return diffRow + diffCol;
            }
        return 0;
    }
    

    // Return a linked list with all the movements possible
    // from the current board.
    private LinkedList<Board> neighborsListing() {
        
        int row = 0, col = 0;
        LinkedList<Board> neighbors = new LinkedList<Board>();
        
        for (row = 0; row < size; row++) {
            for (col = 0; col < size; col++)
                if (tileAt(row, col) == 0)
                    break;
            if (tileAt(row, col) == 0)
                break;
        }
            
        if (row - 1 >= 0) {
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

        if (col - 1 >= 0) {
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

    // Receives two coordinates.
    // Returns the matrix with the two coordinates exchanged.
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
    
    // unit testing
    public static void main(String[] args) {}
}
