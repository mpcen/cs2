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
    for(int i = 0; i < k; i++){

      int aSize = Integer.parseInt(scanner.next());
      String aString = scanner.next();
      char[] aArray = aString.toCharArray();
      char[] aReversed = reverse(aArray);

      int bSize = Integer.parseInt(scanner.next());
      String bString = scanner.next();
      char[] bArray = bString.toCharArray();
      char[] bReversed = reverse(bArray);

      System.out.println("Original reversed array's:");

      for(int j = 0; j < aReversed.length; j++)
        System.out.printf("%c", aReversed[j]);
      System.out.println();

      for(int j = 0; j < bReversed.length; j++)
        System.out.printf("%c", bReversed[j]);
      System.out.println();

      System.out.println();


      // Checks to see if a bit extention is needed by comparing the
        // bit-sizes of each operand
      // Adds leading 0'(s) to the smaller operands' bit-size to match
        // the larger operands' bit-size.
      if(extensionCheck(aArray, bArray)){
        if(aArray.length < bArray.length)
          aReversed = extendBit(aReversed, bArray.length);
        else bReversed = extendBit(bReversed, aArray.length);
      }

      System.out.println("Extended reversed array's:");

      for(int j = 0; j < aReversed.length; j++)
        System.out.printf("%c", aReversed[j]);
      System.out.println();

      for(int j = 0; j < bReversed.length; j++)
        System.out.printf("%c", bReversed[j]);
      System.out.println();

      System.out.println();

    }

  }


  // Method that returns true if bit-sizes are not equal
  public boolean extensionCheck(char[] aArray, char[] bArray){
    if(aArray.length == bArray.length)
      return false;
    return true;
  }


  // Method that extends the smaller bit size to match the larger bit size.
  // Adds zero's to fill the bits.
  public char[] extendBit(char[] reversedArray, int maxBitSize){
    char[] extendedArray = new char[maxBitSize];

    for(int i = 0; i < reversedArray.length; i++)
      extendedArray[i] = reversedArray[i];

    // Fills the array (array_name, beginning_index, ending index, value)
    // Fills with trailing zero's that go from the lower significant bit
      // towards the higher significant bit
    Arrays.fill(extendedArray, reversedArray.length, maxBitSize, '0');

    return extendedArray;
  }


  /*
  public void multiply(char[] aArray, char[] bArray){
    int x = 0;

    for(int i = 0; i < 2 *
  }
  */

  // Method to reverse the passed-in array to compute the multiplication
    // from "right-to-left"
  public char[] reverse(char[] array){

    int index = 0;

    for(int i = array.length - 1; i >= array.length / 2; i--){
      char temp = array[i];
      array[i] = array[index];
      array[index] = temp;
      index++;
    }
    return array;
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
