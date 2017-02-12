//Mike Faunda III
import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.io.*;

public class ConversionAlgorithm
{

   
   static String sdec;
   static double ddec;
   static String hex;
   static String I3s;
   static String I3d;
   static String bin;
   static boolean neg = false;
   static String[] solution = {sdec, bin, hex, I3s, I3d};
   
   public static void toBin(double num)
   {
      bin = "";
      if(num<0)
         neg=true;
      else
         neg=false;
      double absd = Math.abs(num);   //absolute value of number
      double big = Math.floor(absd); //whole part of number
      double small = absd-big;       //decimal part of number
      
      //turn the whole part into binary
      while(big != 0)
      {
         bin = (int)(big%2) + bin;
         big = Math.floor(big/2);
      }
      
      //add decimal point
      bin = bin+".";
      
      //turn the decimal part into binary
      while(small != 0)
      {
         bin = bin + (int)(small*2);
         small = (small*2)-(Math.floor(small*2));
      }
      
      //add negative sign if necessary
      if(neg)
         bin = "-"+bin;
   }
   
   public static void toHex(double num)
   {
      hex = "";
      /* redundant negative check
      if(num<0)
         neg=true;
      else
         neg=false;
         */
      double absd = Math.abs(num);   //absolute value of number
      double big = Math.floor(absd); //whole part of number
      double small = absd-big;       //decimal part of number
      
      //turn the whole part into hex, making 10-15 special characters
      while(big != 0)
      {
         int remainder = (int)(big%16);
      
         if(remainder==10)
            hex = "A" + hex;
         else if(remainder==11)
            hex = "B" + hex;
         else if(remainder==12)
            hex = "C" + hex;
         else if(remainder==13)
            hex = "D" + hex;
         else if(remainder==14)
            hex = "E" + hex;
         else if(remainder==15)
            hex = "F" + hex;
         else
            hex = remainder + hex;
         big = Math.floor(big/16);
      }
      
      //add decimal point
      hex = hex+".";
      
      //turn the decimal part into hex, making 10-15 special characters
      while(small != 0)
      {
         int remainderkindasorta = (int)(small*16);
         if(remainderkindasorta==10)
            hex = hex + "A";
         else if(remainderkindasorta==11)
            hex = hex + "B";
         else if(remainderkindasorta==12)
            hex = hex + "C";
         else if(remainderkindasorta==13)
            hex = hex + "D";
         else if(remainderkindasorta==14)
            hex = hex + "E";
         else if(remainderkindasorta==15)
            hex = hex + "F";
         else
            hex = hex + remainderkindasorta;
         small = (small*16)-(Math.floor(small*16));
      }
      
      //add negative sign if necessary
      if(neg)
         hex = "-"+hex;
   
   }

   public static void toI3s(String num)
   {
      String tempBin = "";
      int counter = 0;
      //Truncate extra zeroes in the front
      for(int x=0; x<num.length(); x++)
      {
         if(num.charAt(x)=='0')
         {}
         else if(num.charAt(x)=='-')
            tempBin = tempBin+num.charAt(x);
         else
         {
            counter = x;
            break;
         }
      }
      
      for(int x=counter; x<num.length(); x++)
      {
         tempBin = tempBin + num.charAt(x);
      }
      
      //find the decimal point
      String signedBit;     //duh
      int exponentI;        //holds the decimal point's value in decimal
      String exponentS = "";     //holds the decimal point's value in bias-127 binary
      boolean thereIsOne = false;
      
      for(int x=0; x<tempBin.length(); x++)
      {
      
         if(tempBin.charAt(x)=='.')
         {
            thereIsOne = true;
         }
      
      }   
      
   
      if(neg)     //save negative sign to a signed bit
      {
         signedBit = "1";
      }
      else
      {
         signedBit = "0";
      }
   
      if(thereIsOne) //locate exponent based off of decimal
      {
         if(neg)
            exponentI = tempBin.indexOf('.')-1;
         else
            exponentI = tempBin.indexOf('.');
      }
      else
         exponentI = tempBin.length()-1;
      
      
      exponentI--;//move to behind the first "1" instead of in front of it
      
      
      double whileTemp = exponentI+127; //maintain exponentI variable while converting to bias-127
      while(whileTemp != 0)            //convert exponent to binary and store in string variable
      {
         exponentS = (int)(whileTemp%2) + exponentS;
         whileTemp = Math.floor(whileTemp/2);
      }  
      
      String newBin = "";
      //redundancy in length, but it puts 0's at the end of the fraction(mantissa). Will be longer for I3d.
      tempBin = tempBin+"0000000000000000000000000000000000000000000";
      int loopx = 1;
      int loopend = 25;
      if(neg)
      {
         loopx++;
         loopend++;
      }
      for(int x=loopx; x<loopend; x++)
      {
         if(tempBin.charAt(x)=='.')
            x++;
         newBin = newBin+tempBin.charAt(x);
      }
      I3s=signedBit+exponentS+newBin;
   }
   
   
   
   public static void toI3d(String num)
   {
   
      String tempBin = "";
      int counter = 0;
      //Truncate extra zeroes in the front
      for(int x=0; x<num.length(); x++)
      {
         if(num.charAt(x)=='0')
         {
         }
         else if(num.charAt(x)=='-')
            tempBin = tempBin+num.charAt(x);
         else
         {
            counter = x;
            break;
         }
      }
      
      for(int x=counter; x<num.length(); x++)
      {
         tempBin = tempBin + num.charAt(x);
      }
      
      //find the decimal point
      String signedBit;     //duh
      int exponentI;        //holds the decimal point's value in decimal
      String exponentS = "";     //holds the decimal point's value in bias-1023 binary
      boolean thereIsOne = false;
      
      for(int x=0; x<tempBin.length(); x++)
      {
      
         if(tempBin.charAt(x)=='.')
         {
            thereIsOne = true;
         }
      
      }   
      
   
      if(neg)     //save negative sign to a signed bit
      {
         signedBit = "1";
      }
      else
      {
         signedBit = "0";
      }
   
      if(thereIsOne) //locate exponent based off of decimal
      {
         if(neg)
            exponentI = tempBin.indexOf('.')-1;
         else
            exponentI = tempBin.indexOf('.');
      }
      else
         exponentI = tempBin.length()-1;
      
      
      exponentI--;//move to back behind the first "1" instead of in front of it
      
      
      double whileTemp = exponentI+1023; //maintain exponentI variable while converting to bias-1023
      while(whileTemp != 0)            //convert exponent to binary and store in string variable
      {
         exponentS = (int)(whileTemp%2) + exponentS;
         whileTemp = Math.floor(whileTemp/2);
      }  
      
      String newBin = "";
      //redundancy in length, but it puts 0's at the end of the fraction(mantissa).
      tempBin = tempBin+"0000000000000000000000000000000000000000000000000000000000000000000000000000000000";
      int loopx = 1;
      int loopend = 54;
      if(neg)
      {
         loopx++;
         loopend++;
      }
      for(int x=loopx; x<loopend; x++)
      {
         if(tempBin.charAt(x)=='.')
            x++;
         newBin = newBin+tempBin.charAt(x);
      }
      I3d=signedBit+exponentS+newBin;
   }
   
   public static void hexToBin(String num)
   {
   
      String tempHex ="";
      String tempBin ="";
      String hexBig  ="";
      String hexSmall="";
      String negSign ="";
      if(num.charAt(0)=='-')
      {
         neg = true;
         for(int x=1; x<num.length(); x++)
            tempHex = tempHex + num.charAt(x);
         negSign = "-";
      }
      else
      {
         tempHex=num;
         neg = false;
      }
      int counter = 0;
   
      while(counter<tempHex.length() && tempHex.charAt(counter)!='.')
      {
         hexBig= hexBig+ tempHex.charAt(counter);
         counter++;
      }
      for(counter = counter+1; counter<tempHex.length(); counter++)
         hexSmall = hexSmall + tempHex.charAt(counter);
   
      for(int x=0; x<hexBig.length(); x++)
         if(hexBig.charAt(x)=='0')
            tempBin = tempBin+"0000";
         else if(hexBig.charAt(x)=='1')
            tempBin = tempBin+"0001";
         else if(hexBig.charAt(x)=='2')
            tempBin = tempBin+"0010";
         else if(hexBig.charAt(x)=='3')
            tempBin = tempBin+"0011";
         else if(hexBig.charAt(x)=='4')
            tempBin = tempBin+"0100";
         else if(hexBig.charAt(x)=='5')
            tempBin = tempBin+"0101";
         else if(hexBig.charAt(x)=='6')
            tempBin = tempBin+"0110";
         else if(hexBig.charAt(x)=='7')
            tempBin = tempBin+"0111";
         else if(hexBig.charAt(x)=='8')
            tempBin = tempBin+"1000";
         else if(hexBig.charAt(x)=='9')
            tempBin = tempBin+"1001";
         else if(hexBig.charAt(x)=='A')
            tempBin = tempBin+"1010";
         else if(hexBig.charAt(x)=='B')
            tempBin = tempBin+"1011";
         else if(hexBig.charAt(x)=='C')
            tempBin = tempBin+"1100";
         else if(hexBig.charAt(x)=='D')
            tempBin = tempBin+"1101";
         else if(hexBig.charAt(x)=='E')
            tempBin = tempBin+"1110";
         else if(hexBig.charAt(x)=='F')
            tempBin = tempBin+"1111";
            
      tempBin = tempBin+".";
      
      for(int x=0; x<hexSmall.length(); x++)
         if(hexSmall.charAt(x)=='0')
            tempBin = tempBin+"0000";
         else if(hexSmall.charAt(x)=='1')
            tempBin = tempBin+"0001";
         else if(hexSmall.charAt(x)=='2')
            tempBin = tempBin+"0010";
         else if(hexSmall.charAt(x)=='3')
            tempBin = tempBin+"0011";
         else if(hexSmall.charAt(x)=='4')
            tempBin = tempBin+"0100";
         else if(hexSmall.charAt(x)=='5')
            tempBin = tempBin+"0101";
         else if(hexSmall.charAt(x)=='6')
            tempBin = tempBin+"0110";
         else if(hexSmall.charAt(x)=='7')
            tempBin = tempBin+"0111";
         else if(hexSmall.charAt(x)=='8')
            tempBin = tempBin+"1000";
         else if(hexSmall.charAt(x)=='9')
            tempBin = tempBin+"1001";
         else if(hexSmall.charAt(x)=='A')
            tempBin = tempBin+"1010";
         else if(hexSmall.charAt(x)=='B')
            tempBin = tempBin+"1011";
         else if(hexSmall.charAt(x)=='C')
            tempBin = tempBin+"1100";
         else if(hexSmall.charAt(x)=='D')
            tempBin = tempBin+"1101";
         else if(hexSmall.charAt(x)=='E')
            tempBin = tempBin+"1110";
         else if(hexSmall.charAt(x)=='F')
            tempBin = tempBin+"1111";
   
      tempBin = negSign+tempBin;
      
      bin=tempBin;
   }
   
   
   public static void binToDec(String num)
   {
   
      String tempDec = "";
      String tempBin = "";
      String binBig  = "";
      String binSmall= "";
      double tempDecimal = 0;
   
      if(num.charAt(0)=='-')
      {
         neg = true;
         for(int x=1; x<num.length(); x++)
            tempBin = tempBin + num.charAt(x);
      }
      else
      {
         tempBin=num;
         neg = false;
      }
      
      int counter = 0;
      while(counter<tempBin.length() && tempBin.charAt(counter)!='.')
      {
         binBig= binBig+ tempBin.charAt(counter);
         counter++;
      }
      for(counter = counter+1; counter<tempBin.length(); counter++)
         binSmall = binSmall + tempBin.charAt(counter);
   
      for(int x=0; x<binBig.length(); x++)
      {
      
         if(binBig.charAt(x)=='1')
            tempDecimal = tempDecimal+ (Math.pow(2,binBig.length()-x-1));
            
      }
   
      for(int x=0; x<binSmall.length(); x++)
      {
      
         if(binSmall.charAt(x)=='1')
            tempDecimal = tempDecimal + (1/Math.pow(2, x+1));
      
      }
   
      if(neg && tempDecimal!=0)
         tempDecimal = tempDecimal*-1;
      sdec=""+tempDecimal;
      ddec=tempDecimal;
   
   
   }
   
   public static void i3sToBin(String num)
   {
   
      String tempi3s = num;
      String tempBin = "1";
      String exponent ="";
      String mantissa ="";
      double decExp = 0;
   
      if(num.charAt(0)=='1')
         neg = true;
      
      else if(num.charAt(0)=='0')
         neg = false;
   
      for(int x=1; x<9; x++)
         exponent = exponent + num.charAt(x);
      
      for(int x=9; x<32; x++)
         mantissa = mantissa + num.charAt(x);
   
      for(int x=0; x<exponent.length(); x++)
      {
      
         if(exponent.charAt(x)=='1')
            decExp = decExp+ (Math.pow(2,exponent.length()-x-1));
            
      }
      
      decExp = decExp - 127;
      
      for(int x=0; x<mantissa.length(); x++)
      {
         if(x==(decExp))
            tempBin = tempBin+"."+mantissa.charAt(x);
         else
            tempBin = tempBin+mantissa.charAt(x);
      }
   
      if(neg)
         tempBin="-"+tempBin;
      
      bin = tempBin;
   
   }



   public static void i3dToBin(String num)
   {
   
      String tempi3d = num;
      String tempBin = "1";
      String exponent ="";
      String mantissa ="";
      double decExp = 0;
   
      if(num.charAt(0)=='1')
         neg = true;
      
      else if(num.charAt(0)=='0')
         neg = false;
   
      for(int x=1; x<12; x++)
         exponent = exponent + num.charAt(x);
      
      for(int x=12; x<52; x++)
         mantissa = mantissa + num.charAt(x);
   
      for(int x=0; x<exponent.length(); x++)
      {
      
         if(exponent.charAt(x)=='1')
            decExp = decExp+ (Math.pow(2,exponent.length()-x-1));
            
      }
      
      decExp = decExp - 1023;
      
      for(int x=0; x<mantissa.length(); x++)
      {
         if(x==(decExp))
            tempBin = tempBin+"."+mantissa.charAt(x);
         else
            tempBin = tempBin+mantissa.charAt(x);
      }
   
      if(neg)
         tempBin="-"+tempBin;
      
      bin = tempBin;
   
   }



   public static String[] calculate(String num, int type)
   {
      String megaVar = num;
   /*
   type 0 == from decimal                    binToDec(bin)
   type 1 == from binary                     toBin(ddec) or hexToBin(hex)
   type 2 == from hexadecimal                toHex(ddec)
   type 3 == from IEEE754 Single Presicion   toI3s(bin)
   type 4 == from IEEE754 Double Presicion   toI3d(bin)
   
   String array will be interpreted by the following:
   0: Decimal String
   1: Binary String
   2: Hexadecimal String
   3: IEEE754 Single Presicion String
   4: IEEE754 Double Presicion String
   
   
   */
   
      if(type == 0)
      {
         sdec=megaVar;
         if(sdec.length()!=0)
            ddec=Double.parseDouble(megaVar);
         toBin(ddec);
         toHex(ddec);
         toI3s(bin);
         toI3d(bin);
      }
      
      else if(type == 1)
      {
         binToDec(megaVar);
         toBin(ddec);
         toHex(ddec);
         toI3s(bin);
         toI3d(bin);
      }
      
      else if(type == 2)
      {
         hex=megaVar;
         hexToBin(megaVar);
         binToDec(bin);
         toI3s(bin);
         toI3d(bin);
      }
      
      else if(type == 3)
      {
         I3s = megaVar;
         i3sToBin(megaVar);
         binToDec(bin);
         toHex(ddec);
         toI3d(bin);
      }
      
      else if(type == 4)
      {
         I3d = megaVar;
         i3dToBin(megaVar);
         binToDec(bin);
         toHex(ddec);
         toI3s(bin);
      }
      
      
      //remove unnecessary decimals before continuing
      String redo;
      if(sdec.length()!=0)
         if(sdec.charAt(sdec.length()-1)=='.')
         {
            redo="";
            for(int x=0; x<sdec.length()-1; x++)
               redo=redo+sdec.charAt(x);
            sdec=redo;
         }
      
      if(bin.length()!=0)
         if(bin.charAt(bin.length()-1)=='.')
         {
            redo="";
            for(int x=0; x<bin.length()-1; x++)
               redo=redo+bin.charAt(x);
            bin=redo;
         }
      
      if(hex.length()!=0)
         if(hex.charAt(hex.length()-1)=='.')
         {
            redo="";
            for(int x=0; x<hex.length()-1; x++)
               redo=redo+hex.charAt(x);
            hex=redo;
         }
         
         
      if(sdec.length()!=0)
      {
         solution[0]=sdec;
         solution[1]=bin;
         solution[2]=hex;
         solution[3]=I3s;
         solution[4]=I3d;
      }
      else
      {
         solution[0]="";
         solution[1]="";
         solution[2]="";
         solution[3]="";
         solution[4]="";
      }
      
      return solution;
   }

}