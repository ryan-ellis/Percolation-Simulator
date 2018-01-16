import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF perc;
    private int openCount = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if(n <= 0)
            throw new java.lang.IllegalArgumentException();

        grid = new boolean[n][n];
        perc = new WeightedQuickUnionUF(n*n + 2);

        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                grid[row][col] = false;
            }
        }
    }

    // Helper method for opening new sites
    private int openNew(int row, int col){
        row-=1;
        return row*grid.length + col + 1;
    }

    // Opens new site based on input parameters
    public void open(int row, int col){
        int input = openNew(row, col);
        if(row > 0 && col > 0 && row <= grid.length && col <= grid.length){
            row-=1;
            col-=1;

            if(grid[row][col] == false){
                grid[row][col] = true;
                openCount++;
            }

            if(row == 0){
                perc.union(0,input);
            }
            if(row == grid.length - 1){
                perc.union(1,input);
            }

            //Check left
            if(input - 1 >= 2 && input - 1 <= grid.length*grid.length + 1 && col - 1 >= 0 && grid[row][col - 1]){
                perc.union(input - 1, input);
            }

            //Check right
            if(input + 1 >= 2 && input + 1 <= grid.length*grid.length + 1 && col + 1 < grid.length && grid[row][col + 1]){
                perc.union(input + 1, input);
            }

            //Check up
            if(input - grid.length >= 2 && input - grid.length <= grid.length*grid.length + 1 && row - 1 >= 0 && grid[row - 1][col]){
                perc.union(input - grid.length, input);
            }

            //Check down
            if(input + grid.length >= 2 && input + grid.length <= grid.length*grid.length + 1 && row + 1 < grid.length && grid[row + 1][col]){
                perc.union(input + grid.length, input);
            }
        } else{
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    // Checks if a praticular location is open
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1];
    }

    // Checks if a particular location is full
    public boolean isFull(int row, int col)  {
        int input = oneD(row, col);

        return grid[row - 1][col - 1] && perc.connected(0, input);
    }

    // Returns the number of open sites
    public int numberOfOpenSites(){
        return openCount;
    }

    // Method checks whether or not the system percolates
    public boolean percolates(){
        return perc.connected(0,1);
    }
}
