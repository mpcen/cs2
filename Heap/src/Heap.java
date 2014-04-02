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

    // Arraylist that will hold the loaded values
    //ArrayList<Integer> arrayList = new ArrayList<Integer>();
    //arrayList.add(-1);

    //Operation is assigned from the input file
    String op = new String(scanner.next());

    this.H = load();

    // Since the first line of the input file is always a load <array>
      // store the array into an arraylist.
    /*while(scanner.hasNextInt())
      arrayList.add(scanner.nextInt());

    this.H = new Integer[arrayList.size()];
*/

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
            //System.out.printf("first string = %s\n", arrayList[1]);
            printArray(H);
            System.out.println();
          }
          break;

        case("build-heap"):
          System.out.printf("build-heap\n");
          //heapBottomUp(arrayList);
          System.out.println();
          break;

        case("delete-max"):
          System.out.printf("delete-max\n");
          System.out.println();
          break;

        case("load"):
          System.out.printf("load\n");
          //arrayList = load();
          System.out.println("\nNew ArrayList:");
          //printArray(arrayList);
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
  public ArrayList<Integer> heapBottomUp(ArrayList<Integer> H){
    int n = H.size() - 1;
    boolean heap;
    System.out.println("n = " + n);
    int j = 0;
    int k = 0;
    int v = 0;
    for(int i = n/2; i >= 1; i--){
      System.out.println("i = " + i);
      k = i;
      v = H.get(k);
      heap = false;
/*
      while(!heap && 2 * k <= n){
        j = 2 * k;
        if(j < n) //there are two children
          if(H[j] < H[j] + 1])
            j++;
        if(v >= H[j])
          heap = true;
        else{
          H[k] = H[j];
          k = j;
        }
        H[k] = v;
      }
      */System.out.println("k = " + i + "\n" + "v = " + v);

    }
    //double d = (double)H.get(0);
    //System.out.println("double d = " + d);
    return H;
  }

  public void printArray(Integer[] H){
    for(int i = 1; i < H.length; i++)
      System.out.printf("%s ", H[i]);
    System.out.println();
  }

  public Integer[] load(){
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(-1);

    while(scanner.hasNextInt())
      arrayList.add(scanner.nextInt());

    this.H = new Integer[arrayList.size()];
    this.H[0] = -1;

    for(int i = 1; i < arrayList.size(); i++)
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
