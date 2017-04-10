import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int[][] grid;
    private int open_count;
    private final int dim;
    private final int size;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UF_color;

	// Create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
        if (n <= 0) 
            throw new java.lang.IllegalArgumentException();
        dim = n;
        size = n*n;
        open_count = 0;
        boolean color = false;
        UF = new WeightedQuickUnionUF(size + 2);
        UF_color = new WeightedQuickUnionUF(size + 1);
        grid = new int[n][n];
        
        for (int i = 1; i <= dim; i++) {
            UF.union(0, i);
            UF.union(size + 1, size + 1 - i);
            UF_color.union(0, i);
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = -1;
    }
    
    // open the site (row, col) if it is not open already
	public void open(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim)
            throw new java.lang.IndexOutOfBoundsException();
                                                             
        int comp = dim*row + col + 1;
        if (grid[row][col] != -1) return;
        grid[row][col] = 0;
        open_count++;
       
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            UF.union(dim*(row - 1) + col + 1, comp);
            UF_color.union(dim*(row - 1) + col + 1, comp);
        }

        if (col + 1 < dim && isOpen(row, col + 1)) {
            UF.union(dim*row + col + 2, comp);
            UF_color.union(dim*row + col + 2, comp);
        }

        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            UF.union(dim*row + col, comp);
            UF_color.union(dim*row + col, comp);
        }

        if (row + 1 < dim && isOpen(row + 1, col)) {
            UF.union(comp, dim*(row + 1) + col + 1);
            UF_color.union(comp, dim*(row + 1) + col + 1);
        }

        return; 
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim) 
            throw new java.lang.IllegalArgumentException();
        return grid[row][col] >= 0;
    } 
	
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim)
            throw new java.lang.IllegalArgumentException();
        return isOpen(row, col) && UF_color.connected(0, dim*row + col + 1);
    } 
	
    // number of open sites
    public int numberOfOpenSites() {
        return open_count;
    } 
	
    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites() < dim) return false;
        return UF.connected(0, size + 1);
    }
    
    // unit testing (required)
    public static void main(String[] args) {} 
}
