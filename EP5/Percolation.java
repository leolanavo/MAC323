public class Percolation {

    int[][] grid;
    int open_count;
    int full_count;
    UnionFind UF;

	// Create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
        open_count = 0;
        full_count = 0;
        UF = new UnionFind(n);
        grid = new int[n][n];
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = -1;
    }
    
    // open the site (row, col) if it is not open already
	public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= 0)
            throw new java.lang.NullPointerException("open() position out of range");
        if (grid[row][col] != -1) return;
        grid[row][col] = 0;
        open_count++;

        if (isOpen(row - 1, col)
            UF.union(row - 1, col, row, col);

        if (isOpen(row, col + 1) {}
        if (isOpen(row, col - 1) {}
        if (isOpen(row + 1, col) {}
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= 0)
            throw new java.lang.NullPointerException("isOpen() position out of range");
        return grid[row][col] >= 0;
    } 
	
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= 0)
            throw new java.lang.NullPointerException("isFull() position out of range");
        return grid[row][col] == 1;
    } 
	
    // number of open sites
    public int numberOfOpenSites() {
        return open_count;
    } 
	
    // does the system percolate?
    public boolean percolates() {}
	
    // unit testing (required)
    public static void main(String[] args) {} 
}
