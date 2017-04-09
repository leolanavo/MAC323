import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int[][] grid;
    private int open_count;
    private final int dim;
    private UnionFind UF;
    private UnionFind UF_color;

	// Create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
        if (n <= 0) 
            throw new java.lang.IllegalArgumentException();
        dim = n;
        open_count = 0;
        boolean color = false;
        UF = new UnionFind(n, false);
        UF_color = new UnionFind(n, true);
        grid = new int[n][n];
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = -1;
    }
    
    // open the site (row, col) if it is not open already
	public void open(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim) return;
                                                             
        if (grid[row][col] != -1) return;
        grid[row][col] = 0;
        open_count++;
        
        if (isOpen(row - 1, col)) {
            UF.union(row - 1, col, row, col);
            UF_color.union(row - 1, col, row, col);
        }

        if (isOpen(row, col + 1)) {
            UF.union(row, col + 1, row, col);
            UF_color.union(row, col + 1, row, col);
        }

        if (isOpen(row, col - 1)) {
            UF.union(row, col - 1, row, col);
            UF_color.union(row, col - 1, row, col);
        }

        if (isOpen(row + 1, col)) {
            UF.union(row, col, row + 1, col);
            UF_color.union(row, col, row + 1, col);
        }

        return; 
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim) return false;
        return grid[row][col] >= 0;
    } 
	
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= dim || col < 0 || col >= dim) return false;
        return isOpen(row, col) && UF_color.connected(0, -1, row, col);
    } 
	
    // number of open sites
    public int numberOfOpenSites() {
        return open_count;
    } 
	
    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites() < dim) return false;
        for (int i = 0; i < dim; i++) {
            if(UF.connected(0, -1, dim-1, i))
                return true;
        }
        return false;
    }
    
    // unit testing (required)
    public static void main(String[] args) {} 
}
