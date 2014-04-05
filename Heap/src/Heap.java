/*
 * Emmanuel Martinez
 * COP3503C
 * Assignment 5: Execute operations for the heap data structure
 *               which are triggered by the following input strings
 *               scanned from the input file 'heapops.txt'
 *                        load
 *                        print
 *                        build-heap
 *                        delete-max
 *                        heapsort
 *                        insert
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
          if(this.H.length == 1)
            System.out.println("(empty)");
          else{
            printArray(this.H);
          }
          break;

        case("build-heap"):
          heapBottomUp(this.H);
          break;

        case("delete-max"):
          this.H = deleteMax(this.H);
          break;

        case("load"):
          this.H = load();
          break;

        case("insert"):
          this.H = insert(this.H, scanner.nextInt());
          break;

        case("heapsort"):
          this.H = heapsort(this.H);
          break;

        default:
          System.out.printf("Error in input file\n");
          break;
      }
    }
  }

  // Heapsort method
  public Integer[] heapsort(Integer[] H){

    // Ensure H is a heap
    H = heapBottomUp(H);

    // Setting size to n - 1 to ignore the zero'th index
    int n = H.length - 1;

    // New array that will hold the sorted elements
    Integer[] newH = new Integer[H.length];

    // i counts up while storing the element that gets
    // removed each time deleteMax is called.
    int i = 1;
    while(n >= 1){
      newH[i] = H[1];
      H = deleteMax(H);
      n--;
      i++;
    }
    printArray(newH);
    return H;
  }

  // Insert method
  public Integer[] insert(Integer[] H, int k){
    int n = H.length - 1;

    // Creates a new array newH which is 1 index larger than H
    // to store the new vlue coming in K
    // Copies H into newH and then adds K to the node after the
    // last leaf.
    Integer[] newH = new Integer[n + 2];
    for(int i = 0; i <= n; i++)
      newH[i] = H[i];

    // Add to the end of the array
    newH[n + 1] = k;

    //Re-build the beat using the bottom-up algorithm
    newH = heapBottomUp(newH);

    return newH;
  }

  // Delete-Max Method
  public Integer[] deleteMax(Integer[] H){
    int n = H.length - 1;
    int root = H[1];
    int lastNode = H[n];

    // Assigns the root and last node to their corresponding variables
    H[1] = lastNode;
    H[n] = root;

    // Creates a new array with size = n-1 to accommodate
    Integer[] newH = new Integer[n - 1];
    newH = Arrays.copyOf(H, n);

    // Rebuild the heap
    newH = heapBottomUp(newH);
    return newH;
  }

  /*Starting with the last parental node k, check if
     the parental dominance holds for its children.
     If not, swap k with its largest child and check
     parental dominance again. Continue until you finish
     with the root node. Return the heap.*/
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
