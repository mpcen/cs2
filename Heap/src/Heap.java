/*
 * Emmanuel Martinez
 * COP3503C
 * Assignment 5
*/

import java.util.*;
import java.io.*;

public class Heap{

  private Scanner scanner;
  private Integer[] H;

  public void openFile(){
    try{
      scanner = new Scanner(new File("heapops.txt"));
    }
    catch(Exception e){
      System.out.println("File not found!");
    }
  }

  public void readFile(){

    //Operation is assigned from the input file
    String op = new String(scanner.next());

    // Call the load method and store it in array H.
    System.out.println("loading");
    System.out.println();
    this.H = load();

    // Loop through the entire input file. Stop at the eof
    while(scanner.hasNext()){
      // The operator
      op = scanner.next();

      // Switch statements that call the specific method for
        // each operation called from input file.
      switch(op.toLowerCase()){

        case("print"):
          System.out.printf("printing\n");
          if(this.H.length == 1)
            System.out.println("(empty)");
          else{
            printArray(this.H);
            System.out.println();
          }
          break;

        case("build-heap"):
          System.out.printf("building-heap\n");
          heapBottomUp(this.H);
          System.out.println();
          break;

        case("delete-max"):
          System.out.printf("deleting-max\n");
          this.H = deleteMax(this.H);
          System.out.println();
          break;

        case("load"):
          System.out.printf("loading\n");
          this.H = load();
          System.out.println();
          break;

        case("insert"):
          System.out.printf("inserting (NOT FINISHED)\n");
          System.out.println();
          break;

        case("heapsort"):
          System.out.printf("heapsort (NOT FINISHED)\n");
          System.out.println();
          break;

        default:
          System.out.printf("Something else\n");
          System.out.println();
          break;
      }
    }
  }

  // Delete-Max Method
  public Integer[] deleteMax(Integer[] H){
    int n = H.length - 1;
    int root = H[1];
    int lastNode = H[n];

    H[1] = lastNode;
    H[n] = root;

    Integer[] newH = new Integer[n - 1];
    newH = Arrays.copyOf(H, n);

    n = newH.length - 1;
    int k = 0;
    int v = 0;
    int j = 0;
    boolean heap;

    for(int i = 1; i >= 1; i--){
      k = i;
      v = newH[k];
      heap = false;

      // Heapify this joint
      while(!heap && 2 * k <= n){
        j = 2 * k;
        if(j < n)//there are 2 children
          if(newH[j] < newH[j + 1])
            j++;
        if(v >= newH[j])
          heap = true;
        else{
          newH[k] = newH[j];
          k = j;
        }
        newH[k] = v;
      }
    }
    return newH;
  }


  // The build-heap algorithm that uses bottom-up implementation.
  // input = an array H[1...n]
  // output = a heap H[1...n]
  /*
     Starting with the last parental node k, check if
     the parental dominance holds for its children.
     If not, swap k with its largest child and check
     parental dominance again. Continue until you finish
     with the root node. Return the heap.
  */
  public Integer[] heapBottomUp(Integer[] H){
    int n = H.length - 1;
    boolean heap;
    int j = 0;
    int k = 0;
    int v = 0;

    for(int i = n/2; i >= 1; i--){
      k = i;
      v = H[k];
      heap = false;

      // Heapify
      while(!heap && 2 * k <= n){
        j = 2 * k;
        if(j < n) //there are two children
          if(H[j] < H[j + 1])
            j++;
        if(v >= H[j])
          heap = true;
        else{
          H[k] = H[j];
          k = j;
        }
        H[k] = v;
      }
    }
    return H;
  }

  // Called when operator scans a print string
  public void printArray(Integer[] H){
    for(int i = 1; i < H.length; i++)
      System.out.printf("%s ", H[i]);
    System.out.println();
  }

  // Gets called when a load operation is scanned from input
  // Stores values into an arrayList
  // This arrayList is stored in array H
  public Integer[] load(){
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(-1);

    while(scanner.hasNextInt())
      arrayList.add(scanner.nextInt());

    this.H = new Integer[arrayList.size()];

    for(int i = 0; i < arrayList.size(); i++)
      this.H[i] = arrayList.get(i);

    return H;
  }

  public void closeFile(){
    scanner.close();
  }

  public static void main(String[] args){
    Heap execute = new Heap();
    execute.openFile();
    execute.readFile();
    execute.closeFile();
  }

}
