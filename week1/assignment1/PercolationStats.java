/* *****************************************************************************
 *  Name:              Brandon Chan
 *  Last modified:     21/01/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int nTrials;
    private final double[] xt;
    private final double confidence_95_PLEASE_STOP_CRYING = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 1 || trials <= 1) throw new IllegalArgumentException();
        int gridSize = n;
        nTrials = trials;
        xt = new double[trials];

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
        return mean() - ((confidence_95_PLEASE_STOP_CRYING * stddev()) / Math.sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidence_95_PLEASE_STOP_CRYING * stddev()) / Math.sqrt(nTrials));
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
        System.out.printf("mean:%f%n", stats.mean());
        System.out.printf("stddev:%f%n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]%n", stats.confidenceLo(),
                          stats.confidenceHi());
    }
}
