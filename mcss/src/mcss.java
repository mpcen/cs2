// Emmanuel Martinez
// PID: e2904661
// Feb 9, 2014
// COP 3503C, Spring 2014
// Assignment #2 - mcss.java

import java.util.*;
import java.io.*;

public class mcss {

    //Method for reading,storing, and computing the values from input file
    public static void readFile(String fileName){
        try{
            //Create new Scanner object named scanner
            Scanner scanner = new Scanner(new FileReader(fileName));

            //testcases will hold the value of the first line of input file
            int testcases = Integer.parseInt(scanner.nextLine().split("\\s+")[0]);

            //Array that will get allocated later which will hold the values
            //needing to be computed
            int[] mainArray;

            int mcssValue = 0;  // The variable that will hold the MCSS

            for(int i = 0; i < testcases; i++){
                //Creates a temp array and store the first value of the 2nd line
                //which corresponds to the number of inputs for each case.
                //This number will be used to allocate the "mainArray" previously made
                String[] tempArray = scanner.nextLine().split("\\s+");

                //Size the array with the new value
                mainArray = new int[tempArray.length-1];

                for(int j = 1; j < tempArray.length; j++){
                    mainArray[j-1] = Integer.parseInt(tempArray[j]);
                }

                //*************************
                //Beginning of calculations
                //*************************

                //CUBE FUNCTION

                //Array for storing the computed average values
                double[] averageArray = new double[3];
                int numRuns = 100;

                // start timer
                final long start = System.nanoTime();

                // run function: mcss_cubed(array), mcss_squared(array), or mcss_linear(array)
                for (int k = 0; k < numRuns; k++)
                    //Call cube function
                    mcss_cubed(mainArray);

                // end timer
                final long end = System.nanoTime();

                //Store values for n^3 in averageArray
                averageArray[0] = (((end-start) / 1000000.0) / numRuns) * 1000000.0;

                //SQUARE FUNCTION

                // start timer
                final long start2 = System.nanoTime();

                // run function: mcss_cubed(array), mcss_squared(array), or mcss_linear(array)
                for (int k = 0; k < numRuns; k++){
                    //Call cube function
                    mcss_squared(mainArray);
                }

                // end timer
                final long end2 = System.nanoTime();

                //Store values for n^2 in averageArray
                averageArray[1] = (((end2-start2) / 1000000.0) / numRuns) * 1000000.0;

                //LINEAR FUNCTION

                // start timer
                final long start3 = System.nanoTime();

                for (int k = 0; k < numRuns; k++){

                    //Call linear function
                    mcssValue = mcss_linear(mainArray);
                }

                // end timer
                final long end3 = System.nanoTime();

                //Store values for n in averageArray
                averageArray[2] = (((end3-start3) / 1000000.0) / numRuns) * 1000000.0;

                System.out.printf("%d ", mcssValue);
                for(int m = 0; m < 3; m++){
                    System.out.printf("%.0f ", averageArray[m]);
                }
                System.out.println();

            }
        }catch(IOException ex){
            System.out.println("IO Exception Error!");
        }
    }

    public static int mcss_cubed(int [] array) {
        int max = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                int sum = 0;

                for (int k = i; k <= j; k++)
                    sum += array[k];

                if (sum > max)
                    max = sum;
            }
        }
        return max;
    }

    public static int mcss_squared(int [] array) {
        int max = 0;

        for (int i = 0; i < array.length; i++) {
            int sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];

                if (sum > max)
                    max = sum;
            }
        }
        return max;
    }

    public static int mcss_linear(int [] array) {
        int max = 0, sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];

            if (sum > max)
                max = sum;
            else if (sum < 0)
                sum = 0;
        }
        return max;
    }

    public static void main(String [] args) {
        readFile("mcss.txt");
    }
}