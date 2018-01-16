/**
 * Created by Ryan on 5/30/2017.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSites;

    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0){
            throw new java.lang.IllegalArgumentException();
        }

        openSites = new double[trials];
        int index = 0;

        for(int x = 0; x < trials; x++){
            Percolation test = new Percolation(n);

            while(test.percolates() == false) {
                int randomOne = StdRandom.uniform(1,n+1);
                int randomTwo = StdRandom.uniform(1,n+1);

                test.open(randomOne, randomTwo);
            }

            openSites[index] = (double)test.numberOfOpenSites()/(double)(n*n);
            index++;
        }
    }

    public double mean(){
        return StdStats.mean(openSites);
    }

    public double stddev(){
        return StdStats.stddev(openSites);
    }

    public double confidenceLo(){
        return StdStats.mean(openSites) - ((1.96*StdStats.stddev(openSites))/openSites.length);
    }

    public double confidenceHi(){
        return StdStats.mean(openSites) + ((1.96*StdStats.stddev(openSites))/openSites.length);
    }

    public static void main(String[] args) {
        int inputN = Integer.parseInt(args[0]);
        int inputT = Integer.parseInt(args[1]);

        PercolationStats test = new PercolationStats(inputN, inputT);

        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
