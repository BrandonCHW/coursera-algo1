/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int _n;
    private int n_trials;
    private double xt[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 1 || trials <= 1) throw new IllegalArgumentException();
        _n = n;
        n_trials = trials;
        xt = new double[trials];
    }

    private void generateTrials() {
        for (int i = 0; i < n_trials; i++) {
            Percolation sample = new Percolation(_n);
            while (!sample.percolates()) {
                int row = StdRandom.uniform(_n) + 1;
                int col = StdRandom.uniform(_n) + 1;
                if (!sample.isOpen(row, col)) {
                    sample.open(row, col);
                }
            }
            xt[i] = (double) sample.numberOfOpenSites() / (_n * _n);
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
        double mean = mean();
        double variance = Math.sqrt(stddev());
        return mean - (1.96 * variance / Math.sqrt(n_trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double variance = Math.sqrt(stddev());
        return mean + (1.96 * variance / Math.sqrt(n_trials));
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
