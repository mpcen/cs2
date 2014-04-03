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
    this.H = load();

    // Loop through the entire input file. Stop at the eof
    while(scanner.hasNext()){
      // The operator
      op = scanner.next();

      // Switch statements that call the specific method for
        // each operation called from input file.
      switch(op.toLowerCase()){

        case("print"):
          System.out.printf("print\n");
          if(H.length == 1)
            System.out.println("empty");
          else{
            printArray(H);
            System.out.println();
          }
          break;

        case("build-heap"):
          System.out.printf("build-heap\n");
          heapBottomUp(H);
          System.out.println();
          break;

        case("delete-max"):
          System.out.printf("delete-max\n");
          System.out.println();
          break;

        case("load"):
          System.out.printf("load\n");
          this.H = load();
          System.out.println("\nNew ArrayList:");
          printArray(H);
          System.out.println();
          break;

        case("insert"):
          System.out.printf("insert\n");
          System.out.println();
          break;

        case("heapsort"):
          System.out.printf("heapsort\n");
          System.out.println();
          break;

        default:
          System.out.printf("Something else\n");
          System.out.println();
          break;
      }
    }
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
