public class Percolation {

    private int[][] grid;
    private int open_count;
    private final int dim;
    private UnionFind UF;

	// Create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
        dim = n;
        open_count = 0;
        UF = new UnionFind(n);
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
        
        if (isOpen(row - 1, col))
            UF.union(row - 1, col, row, col);

        if (isOpen(row, col + 1))
            UF.union(row, col + 1, row, col);
        
        if (isOpen(row, col - 1))
            UF.union(row, col - 1, row, col);
        
        if (isOpen(row + 1, col))
            UF.union(row, col, row + 1, col);

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
        return isOpen(row, col) && UF.connected(0, -1, row, col);
    } 
	
    // number of open sites
    public int numberOfOpenSites() {
        return open_count;
    } 
	
    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites() < dim) return false;
        return UF.connected(0, -1, dim-1, dim);
    }
    
    // unit testing (required)
    public static void main(String[] args) {} 
}
