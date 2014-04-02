/*
	Emmanuel Martinez
	COP3503C
	Assignment #4
*/

import java.util.*;
import java.io.*;
import java.lang.*;

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

      // Store input strings as character arrays then reverse them for the
        // multiplication process.
      // FOR A:
      int aSize = Integer.parseInt(scanner.next());
      String aString = scanner.next();
      char[] aCharArray = aString.toCharArray();
      String aCharArrayString = new String(aCharArray);

      // FOR B:
      int bSize = Integer.parseInt(scanner.next());
      String bString = scanner.next();
      char[] bCharArray = bString.toCharArray();
      String bCharArrayString = new String(bCharArray);

      // Checks to see if a bit extention is needed by comparing the
        // bit-sizes of each operand
      // Adds leading 0'(s) to the smaller operands' bit-size to match
        // the larger operands' bit-size.
      if(extensionCheck(aCharArray, bCharArray)){
        if(aCharArray.length < bCharArray.length){
          aCharArray = extendBit(aCharArray, bCharArray.length);
        }
        else{
          bCharArray = extendBit(bCharArray, aCharArray.length);
        }
      }

      // Where we return results, i also account for leading zero's
      String finalOne = multiply(aCharArray, bCharArray);
      char[] finalOneArray = finalOne.toCharArray();
      int g = 0;
      int finalcounter = 0;
      while(finalOneArray[g] == '0'){
        finalcounter++;
        g++;
      }

      for(int h = finalcounter; h < finalOneArray.length; h++)
        System.out.printf("%c", finalOneArray[h]);
      System.out.println();
      //System.out.printf("ANSWER = %s\n", multiply(aCharArray, bCharArray));

      // Convert back to strings to pass into karatsuba
      String aToString = new String(aCharArray);
      String bToString = new String(bCharArray);
      System.out.printf("%s\n",karatsuba(aToString, bToString));

    }

  }

  // Multiplication method that will get called from both the grade-school
    // multiplication and karatsuba algorithms.
  public String multiply(char[] aCharArray, char[] bCharArray){

    // Setting n
    int n = aCharArray.length;

    // char arrays used to restructure the array sizes (to match)
    char[] multArray = new char[n];
    Arrays.fill(multArray, 0, multArray.length, '0');
    char[] tempArray = new char[2 * n - 1];
    Arrays.fill(tempArray, 0, tempArray.length, '0');
    String result = "0";
    String tempResultString = "0";

    // Creating the result of the product by calling a method 'multTable' which
     // acts as an inverter.
    for(int i = n - 1; i >= 0; i--){
      if(i == n - 1){
        for(int j = n - 1; j >= 0; j--){
          tempArray[i + j] = multTable(aCharArray[j], bCharArray[i]);
        }
        String tempString = new String(tempArray);
        result = binaryAddition(result, tempString);
        tempString = result;
      }

      else if(i == 0){
        Arrays.fill(tempArray, 0, 2 * n - 1, '0');
        for(int j = n - 1; j >= 0; j--)
          tempArray[j] = multTable(aCharArray[j], bCharArray[i]);
        String tempString2 = new String(tempArray);
        result = binaryAddition(result, tempString2);
        tempString2= result;

      }

      else{
        Arrays.fill(tempArray, 0, 2 * n - 1, '0');
        for(int j = n - 1; j >= 0; j--)
          multArray[j] = multTable(aCharArray[j], bCharArray[i]);
        for(int k = tempArray.length - n + i; k >= i; k--)
          tempArray[k] = multArray[k - i];
        String newString = new String(tempArray);

        result = binaryAddition(result, newString);

      }
    }
    //System.out.printf("\n\nANSWER = %s\n", result);
    return result;
  }

  // The ugliest karatsuba method you will ever see
  public String karatsuba(String aString, String bString){

    // Get the length of the strings
    int n = aString.length();
    // Split the arrays into n/2
    int firstSize = n / 2;
    int secondSize = n - firstSize;

    // Creating substrings of aStringArray and bStringArray)
      // named (a1, a2, b1, and b2)
    String a1 = aString.substring(0, secondSize);
    char[] a1CharArray = a1.toCharArray();
    String a0 = aString.substring(firstSize, aString.length());
    char[] a0CharArray = a0.toCharArray();
    String b1 = bString.substring(0, secondSize);
    char[] b1CharArray = b1.toCharArray();
    String b0 = bString.substring(firstSize, bString.length());
    char[] b0CharArray = b0. toCharArray();

    // c0 = c0 * b0
    String c0 = multiply(a0CharArray, b0CharArray);
    char[] c0CharArray = c0.toCharArray();

    // c2 = a1 * b1
    String c2 = multiply(a1CharArray, b1CharArray);
    char[] c2CharArray = c2.toCharArray();

    // getting the sums of the product of c1
    String c1_a = binaryAddition(a1, a0);
    String c1_b = binaryAddition(b1, b0);
    char[] c1_aCharArray = c1_a.toCharArray();
    char[] c1_bCharArray = c1_b.toCharArray();

    // Ensure bits are both n-size
    if(extensionCheck(c1_aCharArray, c1_bCharArray)){
      if(c1_aCharArray.length < c1_bCharArray.length){
         c1_aCharArray = extendBit(c1_aCharArray, c1_bCharArray.length);
       }
       else{
         c1_bCharArray = extendBit(c1_bCharArray, c1_aCharArray.length);
      }
    }

    // Deleting leading zeroes
    String c1Mult = multiply(c1_aCharArray, c1_bCharArray);
    char[] c1MultArray = c1Mult.toCharArray();
    int counter1 = 0;
    int y = 0;
    while(c1MultArray[y] == '0'){
      counter1++;
      y++;
    }

    char[] smallerc1Mult = new char[c1MultArray.length - counter1];
    for(int z = c1MultArray.length - counter1 - 1 ; z >= counter1; z--){
      smallerc1Mult[z] = c1MultArray[z+counter1];
    }

    // Deleting more leading zeroes
    String smallerc1String = new String(smallerc1Mult);
    String c2c0 = binaryAddition(c2, c0);
    char[] c2c0Array = c2c0.toCharArray();

    // Resizing after deleting the leading zeros
    if(extensionCheck(smallerc1Mult, c2c0Array)){
      if(smallerc1Mult.length < c2c0Array.length){
        smallerc1Mult = extendBit(smallerc1Mult, c2c0Array.length);
      }
      else{
        c2c0Array = extendBit(c2c0Array, smallerc1Mult.length);
      }
    }

    // New inverted array
    char[] c2c0Inverted = new char[c2c0Array.length];

    for(int i = c2c0Array.length - 1; i >= 0; i--)
      c2c0Inverted[i] = invertBit(c2c0Array[i]);

    // After finding the 2's complement of the sum of c2 and c0
    // I extend the bit to make it match the product of c1, then
    // i call the binaryaddition method to find the result of c1
    String c2c0InvertedString = new String(c2c0Inverted);
    String c2c02scomp = binaryAddition("1", c2c0InvertedString);
    String finalc1 = binaryAddition(c2c02scomp, smallerc1String);
    char[] finalc1Array = finalc1.toCharArray();
    char[] c2NewArray = new char[c2CharArray.length + n];
    Arrays.fill(c2NewArray, 0, c2NewArray.length, '0');
    for(int k = 0; k < c2CharArray.length; k++)
      c2NewArray[k] = c2CharArray[k];
    String c2NewString = new String(c2NewArray);

    // Add the first 2 terms of c
    char[] c1NewArray = new char[finalc1Array.length + (n/2)];
    Arrays.fill(c1NewArray, 0, c1NewArray.length, '0');
    for(int k = 0; k < finalc1Array.length; k++)
      c1NewArray[k] = finalc1Array[k];
    String c1NewString = new String(c1NewArray);

    // Return
    String cFinal1 = binaryAddition(c2NewString, c1NewString);
    return cFinal1;

  }

  // Method that inverts the bits to prepare for a 2's complement
  public char invertBit(char bit){
    if(bit == '0')
      bit = '1';
    else if(bit == '1')
      bit = '0';
    return bit;
  }

  // Method that returns true if bit-sizes are not equal
  public boolean extensionCheck(char[] aArray, char[] bArray){
    if(aArray.length == bArray.length)
      return false;
    return true;
  }

// Method that extends the smaller bit size to match the larger bit size.
  // Adds zero's to fill the bits.
  public char[] extendBit(char[] charArray, int bitSize){

    char[] extendedArray = new char[bitSize];
    Arrays.fill(extendedArray, 0, bitSize, '0');

    for(int i = charArray.length - 1; i >= 0; i--){
      extendedArray[i + bitSize - charArray.length] = charArray[i];
      String extendedArrayString = new String(extendedArray);
    }
    return extendedArray;
  }

  // The table that contains the 4 possible values of 2 bits getting multiplied.
  // Only return a 1
  public char multTable(char b, char a){
      if(b == '1' && a == '1')
        return '1';
      return '0';
    }

  // Addition function that gets called from multiply()
  // Takes in 2 strings
  public static String binaryAddition(String s1, String s2) {

    // For saftey, incase you pass in a null value
     if(s1 == null || s2 == null)
       return "";

     // Initializing bits
     int carry = 0;
     int first = s1.length() - 1;
     int second = s2.length() - 1;

     // Creating a string builder to utilize the built-in reverse() method
     StringBuilder sb = new StringBuilder();

     // While you're not at the end of the string
     while (first >= 0 || second >= 0) {
       int sum = carry;
       if (first >= 0){
         sum += s1.charAt(first) - '0';
         first--;
       }
     if (second >= 0){
       sum += s2.charAt(second) - '0';
       second--;
     }
     carry = sum >> 1;
     sum = sum & 1;
     sb.append(sum == 0 ? '0' : '1');
     }

     if (carry > 0)
       sb.append('1');
     // reverse the array from MSB to LSB
     sb.reverse();
     // Return it
     return String.valueOf(sb);
}

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

  // Beginning of main
  public static void main(String[] args){

    Multiplication mult = new Multiplication();
    mult.openFile();
    mult.readFile();
    mult.closeFile();

  }

}// End of class
