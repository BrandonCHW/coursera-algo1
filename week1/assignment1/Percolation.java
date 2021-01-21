/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int _n = 0;
    private boolean[][] open;
    private WeightedQuickUnionUF site;

    private void OutOfBoundsGuard(int row, int col) {
        if ((row < 1 || row > _n) && (col < 1 || col > _n)) {
            throw new IllegalArgumentException();
        }
    }

    // creates n-by-n grid, with all sites initially blocked; 1 = open, 0 = blocked
    public Percolation(int n) {
        _n = n;
        open = new boolean[n][n];
        site = new WeightedQuickUnionUF(n * n);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        OutOfBoundsGuard(row, col);
        int r = row - 1;
        int c = col - 1;
        int id = r * _n + c;
        if (isOpen(row, col)) return;

        open[r][c] = true;
        if (id % _n - 1 >= 0 && isOpen(row, col - 1)) site.union(id, id - 1); // left
        if (id % _n + 1 < _n && isOpen(row, col + 1)) site.union(id, id + 1); // right
        if (id - _n >= 0 && isOpen(row - 1, col)) site.union(id, id - _n); // up
        if (id + _n < Math.pow(_n, 2) && isOpen(row + 1, col)) site.union(id, id + _n); // down
    }

    // is the site (row, col) open
    public boolean isOpen(int row, int col) {
        OutOfBoundsGuard(row, col);
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        OutOfBoundsGuard(row, col);
        int r = row - 1;
        int c = col - 1;
        int bottomId = r * _n + c;
        if (open[r][c]) {
            try {
                for (int i = 0; i < _n; i++) {
                    if (site.find(i) == site.find(bottomId)) {
                        return true;
                    }
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("error at isFull");
                return false;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        int n = open.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (open[i][j]) count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int j = 1; j <= _n; j++) {
            if (isFull(_n, j)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = 5;
        Percolation perc = new Percolation(size);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(4, 3);
        perc.open(4, 2);
        perc.open(5, 2);
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.print(perc.isOpen(i, j) ? "1" : "0");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println(perc.percolates() ? "Percolates" : "Doesn't Percolate");
    }
}
