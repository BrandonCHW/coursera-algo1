/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize = 0;
    private boolean[][] open;
    private WeightedQuickUnionUF site;

    private void OutOfBoundsGuard(int row, int col) {
        if ((row < 1 || row > gridSize) && (col < 1 || col > gridSize)) {
            throw new IllegalArgumentException();
        }
    }

    // creates n-by-n grid, with all sites initially blocked; 1 = open, 0 = blocked
    public Percolation(int n) {
        gridSize = n;
        open = new boolean[n][n];
        site = new WeightedQuickUnionUF(n * n);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        OutOfBoundsGuard(row, col);
        int r = row - 1;
        int c = col - 1;
        int id = r * gridSize + c;
        if (isOpen(row, col)) return;

        open[r][c] = true;
        if (id % gridSize - 1 >= 0 && isOpen(row, col - 1)) site.union(id, id - 1); // left
        if (id % gridSize + 1 < gridSize && isOpen(row, col + 1)) site.union(id, id + 1); // right
        if (id - gridSize >= 0 && isOpen(row - 1, col)) site.union(id, id - gridSize); // up
        if (id + gridSize < Math.pow(gridSize, 2) && isOpen(row + 1, col))
            site.union(id, id + gridSize); // down
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
        int bottomId = r * gridSize + c;
        if (open[r][c]) {
            for (int i = 0; i < gridSize; i++) {
                if (site.find(i) == site.find(bottomId)) {
                    return true;
                }
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
        for (int j = 1; j <= gridSize; j++) {
            if (isFull(gridSize, j)) {
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
