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

      System.out.println("Calling Multiply");
      multiply(aCharArray, bCharArray);

      // Convert back to strings to pass into karatsuba
      String aToString = new String(aCharArray);
      String bToString = new String(bCharArray);
      karatsuba(aToString, bToString);
    }

  }

  public String multiply(char[] aCharArray, char[] bCharArray){
    int n = aCharArray.length;
    System.out.printf("Inside Multiply\n");

    char[] multArray = new char[n];
    Arrays.fill(multArray, 0, multArray.length, '0');
    char[] tempArray = new char[2 * n - 1];
    Arrays.fill(tempArray, 0, tempArray.length, '0');
    String result = "0";
    String tempResultString= "0";
    //Arrays.fill(resultArray, 0, resultArray.length, '0');

    for(int i = n - 1; i >= 0; i--){
      if(i == n - 1){
        for(int j = n - 1; j >= 0; j--){
          tempArray[i + j] = multTable(aCharArray[j], bCharArray[i]);
        }
        String tempString = new String(tempArray);
        System.out.printf("%s\n", tempString);
        result = binaryAddition(result, tempString);

        tempString = result;
      }

      else if(i == 0){
        Arrays.fill(tempArray, 0, 2 * n - 1, '0');
        for(int j = n - 1; j >= 0; j--)
          tempArray[j] = multTable(aCharArray[j], bCharArray[i]);
        String tempString2 = new String(tempArray);
        System.out.printf("%s\n", tempString2);
        result = binaryAddition(result, tempString2);
        //System.out.printf("\n\nANSWER  = %s\n", result);
        tempString2= result;

      }

      else{
        Arrays.fill(tempArray, 0, 2 * n - 1, '0');
        for(int j = n - 1; j >= 0; j--)
          multArray[j] = multTable(aCharArray[j], bCharArray[i]);
        for(int k = tempArray.length - n + i; k >= i; k--)
          tempArray[k] = multArray[k - i];
        String newString = new String(tempArray);
        System.out.printf("%s\n", newString);
        result = binaryAddition(result, newString);

      }

      //call add
    }
    System.out.printf("\n\nANSWER = %s\n", result);
    return result;
  }

  public void karatsuba(String aString, String bString){
    System.out.printf("---------------\n\nInside KARATSUBA\n");
    // Get the length of the strings
    int n = aString.length();
    // Split the arrays into n/2
    int firstSize = n / 2;
    int secondSize = n - firstSize;

    //System.out.printf("aString = %s\n", aString);
    //System.out.printf("bString = %s\n", bString);

    // Creating substrings of aStringArray and bStringArray)
      // named (a1, a2, b1, and b2)
    String a1 = aString.substring(0, firstSize);
    char[] a1CharAray = a1.toCharArray();
    String a2 = aString.substring(firstSize, aString.length());
    char[] a2CharArray = a2.toCharArray();
    //System.out.printf("a1 = %s\n", a1);
    //System.out.printf("a2 = %s\n", a2);

    String b1 = bString.substring(0, firstSize);
    char[] b1CharArray = b1.toCharArray();
    String b2 = bString.substring(firstSize, bString.length());
    char[] b2CharArray = b2. toCharArray();
    //System.out.printf("b1 = %s\n", b1);
    //System.out.printf("b2 = %s\n", b2);

    String c1 = multiply(a1CharAray, b1CharArray);
    //System.out.printf("c1 = %s\n", c1);
    char[] c1Array = c1.toCharArray();

    String c2 = multiply(a2CharArray, b2CharArray);
    //System.out.printf("c2 = %s\n", c2);
    char[] c2Array = c2.toCharArray();

    String c3_1 = binaryAddition(a1, a2);
    char[] c3_1Array = c3_1.toCharArray();
    String c3_2 = binaryAddition(b1, b2);
    char[] c3_2Arrary = c3_2.toCharArray();
    String c3 = multiply(c3_1Array, c3_2Arrary);
    //System.out.printf("c3 = %s\n", c3);
    char[] c3Array = c3.toCharArray();

    char[] tempArray = new char[2 * c1Array.length - 1];
    System.out.printf("tempArray.length = %d\n", tempArray.length);
    System.out.printf("c1Array.length = %d\n", c1Array.length);
    String twosComplement = "";
    for(int i = c1Array.length - 1; i >= 0; i--){
      System.out.printf("i = %d\n", i);
      tempArray[i] = invertBit(c1Array[i]);
    }
    String invertedArrayString = new String(tempArray);
    System.out.printf("invertedArrayString = %s\n", invertedArrayString);
    twosComplement = binaryAddition(invertedArrayString, "1");
    System.out.printf("twos complement = %s\n", twosComplement);

  }

  public char invertBit(char bit){
    System.out.printf("Inside invertBit\n");
    System.out.printf("Taking in %c\n", bit);
    if(bit == '0')
      bit = '1';
    else if(bit == '1')
      bit = '0';
    System.out.printf("Returning %c\n", bit);
    return bit;
  }

  // Method that returns true if bit-sizes are not equal
  public boolean extensionCheck(char[] aArray, char[] bArray){
    if(aArray.length == bArray.length){
      System.out.printf("Arrays are same size\n");
      return false;
    }
    System.out.printf("Arrays are different size. Extending\n");
    return true;
  }

// Method that extends the smaller bit size to match the larger bit size.
  // Adds zero's to fill the bits.
  public char[] extendBit(char[] charArray, int bitSize){

    System.out.println("Bit Size " + bitSize);
    System.out.printf("Inside extendBit\n");
    System.out.println("charArray.length = " + charArray.length);

    char[] extendedArray = new char[bitSize];

    Arrays.fill(extendedArray, 0, bitSize, '0');

    for(int i = charArray.length - 1; i >= 0; i--){
      System.out.printf("i = %d\n", i);
      System.out.printf("extendArray[%d] = %c\n", i + bitSize - charArray.length, charArray[i]);
      extendedArray[i + bitSize - charArray.length] = charArray[i];
      String extendedArrayString = new String(extendedArray);
      System.out.printf("extendedArrayString = %s\n", extendedArrayString);
    }

    // Fills the array (array_name, beginning_index, ending index, value)
    // Fills with trailing zero's that go from the lower significant bit
      // towards the higher significant bit

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
    System.out.printf("inside binaryAddition\n");
    System.out.printf("s1 = %s\ns2 = %s\n", s1, s2);

     if(s1 == null || s2 == null)
       return "";

     int carry = 0;
     int first = s1.length() - 1;
     int second = s2.length() - 1;

     StringBuilder sb = new StringBuilder();

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

     sb.reverse();

     System.out.printf("Returning %s from BINARYADDITION\n", String.valueOf(sb));
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
