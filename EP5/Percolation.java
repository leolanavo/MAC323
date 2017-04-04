public class Percolation {
	// create n-by-n grid, with all sites initially blocked
	public Percolation(int n) {}
    
    // open the site (row, col) if it is not open already
	public void open(int row, int col) {}
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {} 
	
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {} 
	
    // number of open sites
    public int numberOfOpenSites() {} 
	
    // does the system percolate?
    public boolean percolates() {}
	
    // unit testing (required)
    public static void main(String[] args) {} 
}
