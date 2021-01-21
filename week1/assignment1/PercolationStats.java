/* *****************************************************************************
 *  Name:              Brandon Chan
 *  Coursera User ID:  10101010100
 *  Last modified:     1/1/2077
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int gridSize;
    private int nTrials;
    private double[] xt;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 1 || trials <= 1) throw new IllegalArgumentException();
        gridSize = n;
        nTrials = trials;
        xt = new double[trials];
    }

    private void generateTrials() {
        for (int i = 0; i < nTrials; i++) {
            Percolation perc = new Percolation(gridSize);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            xt[i] = (double) perc.numberOfOpenSites() / Math.pow(gridSize, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(nTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide n (size) and T (# experiments)");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        stats.generateTrials();
        System.out.printf("mean:%f%n", stats.mean());
        System.out.printf("stddev:%f%n", stats.stddev());
        System.out.printf("95 confidence interval: [%f, %f]%n", stats.confidenceLo(),
                          stats.confidenceHi());
    }
}
