/* *****************************************************************************
 *  Name:              Brandon Chan
 *  Last modified:     21/01/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize = 0;
    private boolean[][] open;
    private int topIndex = 0; //virtual top index
    private int botIndex = 0; //virtual bot index
    private int opened = 0;
    // includes virtual top & bottom
    private WeightedQuickUnionUF perc;

    // WQUUF used only for checking cell fullness (prevents backwash)
    private WeightedQuickUnionUF fullness;

    private void OutOfBoundsGuard(int row, int col) {
        if ((row < 1 || row > gridSize) || (col < 1 || col > gridSize)) {
            throw new IllegalArgumentException();
        }
    }

    // creates n-by-n grid, with all sites initially blocked; 1 = open, 0 = blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        open = new boolean[n][n];
        perc = new WeightedQuickUnionUF(n * n + 2);
        fullness = new WeightedQuickUnionUF(n * n + 1);
        topIndex = n * n;
        botIndex = n * n + 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        int r = row - 1;
        int c = col - 1;

        int id = r * gridSize + c;
        open[r][c] = true;
        opened++;
        if (row == 1) {
            perc.union(id, topIndex);
            fullness.union(id, topIndex);
        }
        else if (row == gridSize) {
            perc.union(id, botIndex);
        }
        if (id % gridSize - 1 >= 0 && isOpen(row, col - 1)) {
            perc.union(id, id - 1); // left
            fullness.union(id, id - 1);
        }
        if (id % gridSize + 1 < gridSize && isOpen(row, col + 1)) {
            perc.union(id, id + 1); // right
            fullness.union(id, id + 1);
        }
        if (id - gridSize >= 0 && isOpen(row - 1, col)) {
            perc.union(id, id - gridSize); // up
            fullness.union(id, id - gridSize); // up
        }
        if (id + gridSize < Math.pow(gridSize, 2) && isOpen(row + 1, col)) {
            perc.union(id, id + gridSize); // down
            fullness.union(id, id + gridSize); // down
        }
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
        int id = r * gridSize + c;
        return fullness.find(id) == fullness.find(topIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return perc.find(botIndex) == perc.find(topIndex);
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
