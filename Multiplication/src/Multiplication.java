/*
	Emmanuel Martinez
	COP3503C
	Assignment #4
*/

import java.util.*;
import java.io.*;

public class Multiplication{

  private Scanner scanner;

  public void openFile(){
    try{
        scanner = new Scanner(new File("mult.txt"));
    }
    catch(Exception e){
      System.out.println("Could not find file!");
    }
  }

  // Reads input file
  public void readFile(){

    // k = number of multiplications
    int k = Integer.parseInt(scanner.next());

    // For each multiplication
    for(int i = 0; i < k; k++){

      // Scan the bit size of integer a
      int aSize = Integer.parseInt(scanner.next());

      // Create an array to hold the bit values for a
      Integer[] a = new Integer[aSize];

      //Store the bit values into a's indexes
      for(int j = 0; j < aSize; j++){
        a[j] = Integer.parseInt(scanner.next());
      }

      // Scan the bit size for integer b
      int bSize = Integer.parseInt(scanner.next());

      // Create an array to hold the bit values for b
      Integer[] b = new Integer[bSize];

      // Store the bit values into b's indexes
      for(int j = 0; j < bSize; j++){
        b[j] = Integer.parseInt(scanner.next());
      }

      // DEBUGGING
      System.out.printf("%d ", aSize);
      for(int j = 0; j < aSize; j++)
        System.out.printf("%d", a[j]);

      System.out.printf("%d ", bSize);
      for(int j = 0; j < bSize; j++)
        System.out.printf("%d", b[j]);

    }


  }

  public void closeFile(){
    scanner.close();
  }

  public static void main(String[] args){

    Multiplication mult = new Multiplication();
    mult.openFile();
    mult.readFile();
    mult.closeFile();

  }

}
