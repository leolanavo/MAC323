import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    double[] xts;
    Percolation perc;
    int dim;
    int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        
        xts = new double[trials];
        dim = n*n;
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(n); 
            while(!perc.percolates()) {
                int row = StdRandom.uniform(0, n);
                int col = StdRandom.uniform(0, n);
                perc.open(row, col);
            }
            xts[i] = ((double)perc.numberOfOpenSites())/dim;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xts);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xts);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96*stddev()/Math.sqrt(trials)); 
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96*stddev()/Math.sqrt(trials));
    }
    
    public static void main(String[] args) {
        PercolationStats percstats = new PercolationStats(200, 100);
    } 
}
