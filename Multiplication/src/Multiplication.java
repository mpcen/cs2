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
      System.out.printf("aString = %s\n", aString);
      char[] aCharArray = aString.toCharArray();
      String aCharArrayString = new String(aCharArray);
      System.out.printf("aCharArrayString = %s\n", aCharArrayString);

      // FOR B:
      int bSize = Integer.parseInt(scanner.next());
      String bString = scanner.next();
      System.out.println("bString =  " + bString);
      char[] bCharArray = bString.toCharArray();
      String bCharArrayString = new String(bCharArray);
      System.out.printf("bCharArrayString = %s\n", bCharArrayString);

      // Checks to see if a bit extention is needed by comparing the
        // bit-sizes of each operand
      // Adds leading 0'(s) to the smaller operands' bit-size to match
        // the larger operands' bit-size.

      if(extensionCheck(aCharArray, bCharArray)){
        System.out.printf("extensionCheck is TRUE\n");
        if(aCharArray.length < bCharArray.length){
          System.out.printf("Resizing aCharArray\n");
          aCharArray = extendBit(aCharArray, bCharArray.length);
        }
        else{
          System.out.printf("Resizing bCharArray\n");
          bCharArray = extendBit(bCharArray, aCharArray.length);
        }
      }

      System.out.println("Calling Multiply");
      //multiply(aString, bString);
      //System.out.println("aReversed = " + aReversed);
   //   multiply(aReversed, bReversed);

    }

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
    System.out.printf("Inside extendBit\n");
    char[] extendedArray = new char[bitSize];
    for(int i = charArray.length - 1; i >= 0; i--){
      System.out.printf("extendArray[%d] = %c\n", i, charArray[i]);
      extendedArray[i] = charArray[i];
    }
    // Fills the array (array_name, beginning_index, ending index, value)
    // Fills with trailing zero's that go from the lower significant bit
      // towards the higher significant bit
    Arrays.fill(extendedArray, charArray.length, 0, '0');
    String extendedArrayString = new String(extendedArray);
    System.out.printf("extendedArrayString = %s\n", extendedArrayString);
    return extendedArray;
  }

  public void multiply(String aString, String bString){
    System.out.println();

  }

  public String multTable(char b, char a){
      if(b == '1' && a == '1')
        return "1";
      return "0";
    }


/*
  public void multiply(char[] aReversed, char[] bReversed){

    int n = aReversed.length;
    String[] result = new String[2 * n - 1];
    String[] tempArray = new String[n];
    Arrays.fill(result, 0, 2 * n - 1, "0");
    Arrays.fill(tempArray, 0, n, "0");
    String aString = "";
    String bString = "";
    bString = Arrays.toString(result);
    String finalResult = "";

    for(int i = 0; i < n; i++){

      if(i == 0){
        for(int j = 0; j < n; j++)
          result[j] = multTable(bReversed[i], aReversed[j]);
           }

      else if(i == n - 1){
        Arrays.fill(result, 0, i, "0");
        for(int j = 0; j < n; j++)
          tempArray[j] = multTable(bReversed[i], aReversed[j]);
        for(int k = i; k < result.length; k++)
          result[k] = tempArray[k - i];
           }

      else{
        Arrays.fill(result, i - 1, i, "0");
        for(int j = 0; j < n; j++)
          tempArray[j] = multTable(bReversed[i], aReversed[j]);
        for(int k = i; k < n + i; k++)
          result[k] = tempArray[k - i];
        Arrays.fill(result, n + i, result.length, "0");
      }

    aString = Arrays.toString(result);

    System.out.println();
    //System.out.println("aString = " + aString);
    StringBuffer a = new StringBuffer(aString);
    StringBuffer b = new StringBuffer(bString);
    System.out.println("a: " + a.reverse());
    System.out.println("b: " + b.reverse());
    String finalA = a.toString();
    String finalB = b.toString();
    finalResult = binaryAddition(finalA, finalB);
    System.out.println("result: " + finalResult);
    //System.out.println("aString Orig = " + a.reverse());
    //System.out.println("bString = " + bString);

    System.out.println("----------------\n");
    //addition(aString, bString, n);
    bString = aString;

    }
  }
  */

public static String binaryAddition(String s1, String s2) {
      if (s1 == null || s2 == null) return "";
          int first = s1.length() - 1;
              int second = s2.length() - 1;
                  StringBuilder sb = new StringBuilder();
                      int carry = 0;
                          while (first >= 0 || second >= 0) {
                                    int sum = carry;
                                            if (first >= 0) {
                                                          sum += s1.charAt(first) - '0';
                                                                      first--;
                                                                              }
                                                    if (second >= 0) {
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
                                      return String.valueOf(sb);
}







  /*
  public String addition(String bitZero, bitOne){
    String result = "";
    int bitSize = extendBit
  }
  */

/*
  // Grade-school multiplication algorithm
  public void multiply(char[] a, char[] b){

    int x = 0;
    int n = a.length;
    int k;
    int[] result = new int[2 * n - 1];

    for(int i = 0; i < 2 * n - 1; i++){
      for(int j = Math.max(0, i + 1 - n); j < Math.min(i, n - 1); j++){
        k = i - j;
        x = x + (a[j] * b[k]);
      }
      result[i] = x % 2;
      x = (int)Math.floor(x/2);
    }

    for(int m = 0; m < result.length; m++)
      System.out.printf("%d", result[m]);
    System.out.println();

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

  // Beginning of main
  public static void main(String[] args){

    Multiplication mult = new Multiplication();
    mult.openFile();
    mult.readFile();
    mult.closeFile();

  }

}// End of class
