/**
 * Emmanuel Martinez pid: e2904661
 * February 25, 2014
 * COP3503C
 * Assignment 3
 *
 * Objective:
 * Implement topological sorting using depth-first search and decrease-by-one and conquer techniques.
 */

import java.util.*;
import java.io.*;

public class TopoSort {
    // Stack will be used for printing purposes only
    Stack<Integer> stack = new Stack<Integer>();

    // Printing method for output
    private static void printStack(Stack<Integer> s){
        System.out.printf("%s ", s.peek());
    }
    private Scanner scanner;

    // Open the input file
    public void openFile(){
        try{
            scanner = new Scanner(new File("graphs.txt"));
        }
        catch(Exception e){
            System.out.println("Could not find file!");
        }
    }


    // Reads input file:
    public void readFile(){

        // k = number of graphs
        int k = Integer.parseInt(scanner.next());

        // For each graph
        for(int g = 0; g < k; g++){

            // Get the size of the matrix
            int n = Integer.parseInt(scanner.next());

            // Get the starting vertex
            int startingVertex = 0;

            // Create the matrix
            Integer[][] matrix = new Integer[n][n];

            // Populate the matrix with scanned data
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++)
                    matrix[y][x] = Integer.parseInt(scanner.next());
            }

            // Calls the testGraph method to check and see if the graph is
            // acyclic or not. If it is acyclic, call the searching
            // algorithms, else don't try sorting.
            if(testGraph(matrix)){
                System.out.printf("TS(%d, DFS): ", g);
                preDFS(matrix, startingVertex);

                System.out.printf("TS(%d, DBO): ", g);
                DBO(matrix, startingVertex);
                System.out.println();
            }
            else{
                System.out.printf("TS(%d, DFS): NO TOPOLOGICAL SORT\n", g);
                System.out.printf("TS(%d, DBO): NO TOPOLOGICAL SORT\n", g);
            }
        }
    }

    /*-------------------------------------
            Beginning of the DBO Algorithm
     ------------------------------------*/

    public void DBO(Integer[][] matrix, int startingVertex){
        // Empty list that will contain all of the sorted elements
        ArrayList<Integer> L = new ArrayList<Integer>();
        // Empty set of nodes that have no incoming edges
        ArrayList<Integer> S = getInEdges(matrix);

        while(!(S.isEmpty())){
            int n = S.remove(0);
            L.add(n);

            // returns all of the outgoing edges from vertex n
            // and removes those edges from the graph
            for(Integer m : getEdge(matrix, n)){
                //remove edge going from n to m
                //in other words, change from 1 to 0
                matrix[n][m] = 0;

                // If vertex n has no outgoing edges
                // return true
                if(noEdges(matrix, m))
                    S.add(m);
            }
        }
        for(Integer s : L) //use for-each loop
            System.out.printf("%d ", s);
    }

    //returns an arrayList of the index that have all zeros vertically
    public ArrayList<Integer> getInEdges(Integer[][] matrix){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int y = 0 ; y < matrix.length ;y++){
            for(int x = 0; x < matrix[y].length; x++){
                if(matrix[x][y] == 1)
                    break;

                if(x + 1 >= matrix[y].length && matrix[x][y] == 0)
                    list.add(y);
            }
        }
        return list;
    }

    // returns true if there are no outgoing edges, false regardless
    public Boolean noEdges(Integer[][] matrix, int n){
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i][n] == 1)
                return false;
        }
        return true;
    }

    public ArrayList<Integer> getEdge(Integer[][] matrix, int n){
        // List that will be returned if there is an outgoing edge
        // coming from vertex n
        ArrayList<Integer> list = new ArrayList<Integer>();
        int i = Arrays.asList(matrix[n]).indexOf(1);

        if(i == -1)
            return list;
        for(int x = i; x < matrix.length; x++){
            if(matrix[n][x] == 1){
                list.add(x);
            }
        }
        return list;
    }

    /*
    Checks the length of the matrix, if it is == 0, the graph is a cyclic.
    If it is == -1 then it is not acyclic.
    else, there is a leaf present, find the leaf
     */
    public boolean testGraph(Integer[][] matrix){
        // Acyclic
        if(matrix.length == 0){
            //System.out.println("Acyclic");
            return true;
        }
        // Cyclic
        else if(findLeaf(matrix) == -1){
            return false;
        }
        // Continue traversing the matrix
        else return testGraph(removeLeaf(matrix, findLeaf(matrix)));
    }

    // Removes the leaf from the array by coping the original passed-in
    // matrix, but skip the leaf index
    public Integer[][] removeLeaf(Integer[][] matrix, int leaf){
        // New matrix with the size = to minus 1 of the original since
        // we are removing 1 index at a time
        Integer[][] adjustedMatrix = new Integer[matrix.length-1][matrix.length-1];

        int p = 0; // temp var that holds the place for the new adjusted matrix
        for(int y = 0; y < matrix.length; y++){
            if(y == leaf)
                continue; // skip this index

            int q = 0; // temp var that holds the place for the new adjusted matrix
            for(int x = 0; x < matrix.length; x++){
                if(x == leaf)
                    continue; // skip this index

                adjustedMatrix[p][q] = matrix[y][x];
                ++q;
            }
            ++p;
        }
        return adjustedMatrix;
    }

    // Finds the leaf in the matrix by summing the index values from each row.
    // if the sum == 0, then the row is a leaf. Else, that vertex has an edge
    public int findLeaf(Integer[][] matrix){
        int edgeCounter = 0;
        for(int y = 0; y < matrix.length; y++){
            for(int x = 0; x < matrix.length; x++){
                if(matrix[y][x] == 1)
                    edgeCounter++;
            }
            if(edgeCounter == 0)
                return y;
            else edgeCounter = 0;
        }
        // Else, no leaf present. Graph concludes that it has a back-edge
        return -1;
    }

    //-----------------------------------------
    // Beginning of the recursive DFS algorithm
    //-----------------------------------------

    // preDFS initializes the visited and popped arrays with 0's
    // then loops for each unvisited vertex.
    public void preDFS(Integer[][] matrix, int startingVertex){

        // Time counter
        int time = 0;

        Integer[] visited = new Integer[matrix.length];
        Integer[] popped = new Integer[matrix.length];

        // Fills the newly created arrays with 0's meaning univisited and unpopped
        Arrays.fill(visited, 0);
        Arrays.fill(popped, 0);

        // for each vertex v inside visited array
        for(int v = startingVertex; v < matrix.length; v++){
            if(visited[v] == 0){
                time = DFS(matrix, visited, v, time);
            }
        }

        // Output printing purposes only
        for(int m = 0; m < matrix.length; m++){
            printStack(stack);
            stack.pop();
        }
        System.out.println();
    }

    // DFS algorithm that takes in all data getting carried through program run-time
    //
    public int DFS(Integer[][] matrix, Integer[] visited, int v, int time){
        time += 1;
        visited[v] = time;

        // For-each loop that runs for each vertex that has adjacent vertices
        for(int w : this.adjacent(matrix, v)){
            if(visited[w] == 0)
                DFS(matrix, visited, w, time);
        }
        stack.push(v);

        return time;
    }

    // Called each time there is a new vertex being tested for adjacent vertices
    // Returns a list to the DFS method with the adjacent vertices (v) of (u)
    public Integer[] adjacent(Integer[][] matrix, int v){
        LinkedList<Integer> list = new LinkedList<Integer>();

        for(int i = 0; i < matrix.length; i++){
            if(matrix[v][i] == 1){
                list.add(i);
            }
        }
        // returns the list
        return list.toArray(new Integer[list.size()]);
    }

    public void closeFile(){
        scanner.close();
    }

    //Beginning of main
    public static void main(String[] args){
        TopoSort sorter = new TopoSort();
        sorter.openFile();
        sorter.readFile();
        sorter.closeFile();
    }
}
