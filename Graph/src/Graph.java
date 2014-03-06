/**
 * Recursive DFS (needs to change parameters from TopSort to Graph
 * Manny Martinez
 */

import java.util.*;
import java.io.*;

public class TopSort {

    private Scanner scanner;


    public void openFile(){

        try{
            scanner = new Scanner(new File("graphs.txt"));
        }
        catch(Exception e){
            System.out.println("Could not find file!");
        }

    }


    public void readFile(){

        int k = Integer.parseInt(scanner.next());

        // For each graph
        for(int g = 0; g < k; g++){

            // Get the starting vertex
            int startingVertex = Integer.parseInt(scanner.next()); // n = starting vertex

            // Get the size of the matrix
            int n = Integer.parseInt(scanner.next());

            Integer[][] matrix = new Integer[n][n];

            for(int y = 0; y < n; y++){

                for(int x = 0; x < n; x++)

                    matrix[y][x] = Integer.parseInt(scanner.next());

            }

            System.out.printf("Graph(%d, %d)\n", g, startingVertex);

            for(int y = 0; y < n; y++){

                for(int x = 0; x < n; x++)

                    System.out.printf("%d ", matrix[y][x]);

                System.out.println();

            }

            System.out.printf("preDFS(%d, %d)\n", g, startingVertex);

            preDFS(matrix, startingVertex);

        }

    }


    public void preDFS(Integer[][] matrix, int startingVertex){

        int count = 0;

        System.out.printf("count = %d\n", count);

        Integer[] visited = new Integer[matrix.length];

        Arrays.fill(visited,0);

        System.out.printf("visited array: ");

        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%d ", visited[i]);
        }

        System.out.println();

        for(int v = startingVertex; v < matrix.length; v++){
            if(visited[v] == 0)
                DFS(matrix, visited, v, count);
        }

    }

    public void DFS(Integer[][] matrix, Integer[] visited, int v, int count){

        System.out.printf("*** PUSH %d ***\n", v);
        int topOfStack = v;
        count += 1;
        System.out.printf("count = %d\n", count);

        visited[v] = count;

        System.out.printf("visited array: ");

        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%d ", visited[i]);
        }

        System.out.println();

        for(int w : this.adjacent(matrix, v)){

            System.out.printf("for %d:\n", w);

            if(visited[w] == 0)
                DFS(matrix, visited, w, count);

            System.out.printf("%d is already visited\n", w);

        }

        System.out.printf("*** POP %d ***\n", topOfStack);
    }

    public Integer[] adjacent(Integer[][] matrix, int v){

        System.out.printf("adj %d: ", v);

        LinkedList<Integer> list = new LinkedList<Integer>();

        for(int i = 0; i < matrix.length; i++){

            if(matrix[v][i] == 1){

                list.add(i);
                System.out.printf("%d ", i);

            }

        }

        System.out.println();
        return list.toArray(new Integer[list.size()]);

    }


    public void closeFile(){
        scanner.close();
    }


    public static void main(String[] args){

        TopSort sorter = new TopSort();
        sorter.openFile();
        sorter.readFile();
        sorter.closeFile();

    }

}
