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

      int aSize = Integer.parseInt(scanner.next());

      // Create character arrays for a and b which will hold the strings
        // given in the input file then store the values in each array.
      char[] a = toCharArray();

      // Get the bit size of b
      int bSize = Integer.parseInt(scanner.next());

      char[] b = toCharArray();

      System.out.printf("%d ", aSize);
      for(int j = 0; j < aSize; j++){
        System.out.printf("%c", a[j]);
      }

      System.out.printf("%d ", bSize);
      for(int j = 0; j < bSize; j++){
        System.out.printf("%c", b[j]);
      }
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
