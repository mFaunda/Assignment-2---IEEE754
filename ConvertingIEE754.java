//Mike Faunda III

import java.lang.Math;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;


public class ConvertingIEE754 extends Application
{
//Strings for all inputs/outputs
   String decimalNum;
   String I3sNum;
   String I3dNum;
   String hexNum;
   String binNum;
   String[] solution = new String[5];

// Declare Stages and Scenes
   Stage window;
   Scene scene1;

   public static void main(String[]args)
   {
      launch(args);//launch "Application" to start using the JavaFX display
   }

   public void start(Stage primaryStage) throws Exception
   {
      window = primaryStage;
   
   
      window.setTitle("Assignment 2 - Decimal to IEEE754"); //Set window name
   
   
   //Initialize layout layouts, buttons, and fields and such
      BorderPane mainPane = new BorderPane();
   
      VBox vPane = new VBox(20);
      vPane.setPadding(new Insets(40, 2, 2, 2));
   
      GridPane gPane = new GridPane();
      gPane.setPadding(new Insets(20, 5, 5, 15));
      gPane.setVgap(20);
      gPane.setHgap(6);
   
   //Number Labels; Decimal, Binary, Hexadecimal, IEEE754 Floating-Point Single Presicion, IEEE754 Floating-Point Double Presicion
      Label decL = new Label("Decimal");
      Label binL = new Label("Binary");
      Label hexL = new Label("Hexadecimal");
      Label i3sL = new Label("IEEE754 Floating-Point Single Presicion");
      Label i3dL = new Label("IEEE754 Floating-Point Double Presicion");
   
   //Number inputs                   //Set width of fields      //Prompt Text
      TextField decT = new TextField(); decT.setMinWidth(600);     decT.setPromptText("Enter decimal here");
      TextField binT = new TextField(); binT.setMinWidth(600);     binT.setPromptText("Enter binary here");
      TextField hexT = new TextField(); hexT.setMinWidth(600);     hexT.setPromptText("Enter hexadeicmal here");
      TextField i3sT = new TextField(); i3sT.setMinWidth(600);     i3sT.setPromptText("Enter IEEE754 Floating-Point Single Presicion here");
      TextField i3dT = new TextField(); i3dT.setMinWidth(600);     i3dT.setPromptText("Enter IEEE754 Floating-Point Double Presicion here");
   
   //Add Number Labels                    //Add Number Inputs
      GridPane.setConstraints(decL, 1, 1);   GridPane.setConstraints(decT, 2, 1);
      GridPane.setConstraints(binL, 1, 2);   GridPane.setConstraints(binT, 2, 2);
      GridPane.setConstraints(hexL, 1, 3);   GridPane.setConstraints(hexT, 2, 3);
      GridPane.setConstraints(i3sL, 1, 4);   GridPane.setConstraints(i3sT, 2, 4);
      GridPane.setConstraints(i3dL, 1, 5);   GridPane.setConstraints(i3dT, 2, 5);
   
   //Clear All Button
   
      Button clear = new Button("Clear All");
      clear.setOnAction(
            e -> {
            
               decT.setText("");
               binT.setText("");
               hexT.setText("");
               i3sT.setText("");
               i3dT.setText("");
            
            });
   
   //Convert From IEEE754 Single Presicion 
   
      Button convertFromIES = new Button("Convert");
      convertFromIES.setOnAction(
            e -> {
            
               String tempString = "";
               tempString = i3sT.getText();
               boolean isNum = false;
               String extraString = "";
            
               if(tempString.length()==32)
               {
                  for(int x = 0; x<tempString.length(); x++)
                  {
                     if(tempString.charAt(x)=='0')
                        isNum = true;
                     else if(tempString.charAt(x)=='1')
                        isNum = true;
                     else
                     {
                        isNum = false;
                        break;
                     }
                  
                  }
               
                  if(!isNum)
                  {
                     AlertBox.display("ERROR!", "ERROR: Input is 32-bits long but conatains a character besides either \"0\" or \"1\".");
                     decT.setText("");
                     binT.setText("");
                     hexT.setText("");
                     i3sT.setText("");
                     i3dT.setText("");
                  }
               }
               else if(tempString.length()==0)
                  AlertBox.display("ERROR!", "ERROR: Please enter a number.");
               else
                  AlertBox.display("ERROR!", "ERROR: Please enter a 32-bit number");
            
            
               if(isNum)
               {
                  String[] solution = ConversionAlgorithm.calculate(tempString, 3);
               
                  decT.setText(solution[0]);
                  binT.setText(solution[1]);
                  hexT.setText(solution[2]);
                  i3sT.setText(solution[3]);
                  i3dT.setText(solution[4]);
               
               }
            
            
            
            
            });
   
   //Convert From IEEE754 Double Presicion
   
      Button convertFromIED = new Button("Convert");
      convertFromIED.setOnAction(
            e -> {
            
               String tempString = "";
               tempString = i3dT.getText();
               boolean isNum = false;
               String extraString = "";
            
               if(tempString.length()==64)
               {
                  for(int x = 0; x<tempString.length(); x++)
                  {
                     if(tempString.charAt(x)=='0')
                        isNum = true;
                     else if(tempString.charAt(x)=='1')
                        isNum = true;
                     else
                     {
                        isNum = false;
                        break;
                     }
                  
                  }
               
                  if(!isNum)
                  {
                     AlertBox.display("ERROR!", "ERROR: Input is 64-bits long but is not a vaild IEEE754 Double Presicion number.");
                     decT.setText("");
                     binT.setText("");
                     hexT.setText("");
                     i3sT.setText("");
                     i3dT.setText("");
                  }
               }
               else if(tempString.length()==0)
                  AlertBox.display("ERROR!", "ERROR: Please enter a number.");
               else
                  AlertBox.display("ERROR!", "ERROR: Please enter a 64-bit number");
            
            
               if(isNum)
               {
                  String[] solution = ConversionAlgorithm.calculate(tempString, 4);
               
                  decT.setText(solution[0]);
                  binT.setText(solution[1]);
                  hexT.setText(solution[2]);
                  i3sT.setText(solution[3]);
                  i3dT.setText(solution[4]);
               
               }
            
            
            
            });
   
   
   //Convert From Decimal
   
      Button convertFromDec = new Button("Convert");
      convertFromDec.setOnAction(
            e -> {
            
               String tempString = "";
               tempString = decT.getText();
               boolean decimal = false;
               boolean multDec = false;
               boolean multNeg = false;
               boolean isNum = false;
               String extraString = "";
               if(tempString.length()!=0)
               {
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                     {
                        if(decimal)
                        {
                           multDec = true;
                           AlertBox.display("ERROR", "ERROR: You have multiple decimals.");
                           decT.setText("");
                           binT.setText("");
                           hexT.setText("");
                           i3sT.setText("");
                           i3dT.setText("");
                           break;
                        }
                        else
                           decimal = true;
                     }
                  
                  }
               
                  for(int x = 1; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='-')
                     {
                        multNeg = true;
                        AlertBox.display("ERROR", "ERROR: You have too many negative signs or it is in the wrong place.");
                        decT.setText("");
                        binT.setText("");
                        hexT.setText("");
                        i3sT.setText("");
                        i3dT.setText("");
                        break;
                     }
                  
                  }
               
               
               
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                        isNum = true;
                     else if(tempString.charAt(x)=='0')
                        isNum = true;
                     else if(tempString.charAt(x)=='1')
                        isNum = true;
                     else if(tempString.charAt(x)=='2')
                        isNum = true;
                     else if(tempString.charAt(x)=='3')
                        isNum = true;
                     else if(tempString.charAt(x)=='4')
                        isNum = true;
                     else if(tempString.charAt(x)=='5')
                        isNum = true;
                     else if(tempString.charAt(x)=='6')
                        isNum = true;
                     else if(tempString.charAt(x)=='7')
                        isNum = true;
                     else if(tempString.charAt(x)=='8')
                        isNum = true;
                     else if(tempString.charAt(x)=='9')
                        isNum = true;
                     else if(tempString.charAt(x)=='-')
                        isNum = true;
                     else
                     {
                        isNum = false;
                        break;
                     }
                  
                  }
               
                  if(tempString.length()==1 && tempString.charAt(0)=='.')
                     isNum = false;
               
                  if(tempString.length()==1 && tempString.charAt(0)=='-')
                     isNum = false;
               
                  if(!isNum)
                  {
                     AlertBox.display("ERROR!", "ERROR: '"+tempString+"' is not a decmial number.");
                     decT.setText("");
                     binT.setText("");
                     hexT.setText("");
                     i3sT.setText("");
                     i3dT.setText("");
                  }
               
               }
               else
                  AlertBox.display("ERROR!", "ERROR: Please enter a number.");
            
               if(!multDec && !multNeg && isNum)
               {
                  String[] solution = ConversionAlgorithm.calculate(tempString, 0);
               
                  decT.setText(solution[0]);
                  binT.setText(solution[1]);
                  hexT.setText(solution[2]);
                  i3sT.setText(solution[3]);
                  i3dT.setText(solution[4]);
               
               }
            
            });
   
   
   //Convert From HexaDecimal
   
      Button convertFromHex = new Button("Convert");
      convertFromHex.setOnAction(
            e -> {
            
               String tempString = "";
               tempString = hexT.getText();
               boolean decimal = false;
               boolean multDec = false;
               boolean multNeg = false;
               boolean isNum = false;
               String extraString = "";
               if(tempString.length()!=0)
               {
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                     {
                        if(decimal)
                        {
                           multDec = true;
                           AlertBox.display("ERROR", "ERROR: You have multiple decimals.");
                           decT.setText("");
                           binT.setText("");
                           hexT.setText("");
                           i3sT.setText("");
                           i3dT.setText("");
                           break;
                        }
                        else
                           decimal = true;
                     }
                  
                  }
               
                  for(int x = 1; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='-')
                     {
                        multNeg = true;
                        AlertBox.display("ERROR", "ERROR: You have too many negative signs or it is in the wrong place.");
                        decT.setText("");
                        binT.setText("");
                        hexT.setText("");
                        i3sT.setText("");
                        i3dT.setText("");
                        break;
                     }
                  
                  }
               
               
               
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                        isNum = true;
                     else if(tempString.charAt(x)=='0')
                        isNum = true;
                     else if(tempString.charAt(x)=='1')
                        isNum = true;
                     else if(tempString.charAt(x)=='2')
                        isNum = true;
                     else if(tempString.charAt(x)=='3')
                        isNum = true;
                     else if(tempString.charAt(x)=='4')
                        isNum = true;
                     else if(tempString.charAt(x)=='5')
                        isNum = true;
                     else if(tempString.charAt(x)=='6')
                        isNum = true;
                     else if(tempString.charAt(x)=='7')
                        isNum = true;
                     else if(tempString.charAt(x)=='8')
                        isNum = true;
                     else if(tempString.charAt(x)=='9')
                        isNum = true;
                     else if(tempString.charAt(x)=='-')
                        isNum = true;
                     else if(tempString.charAt(x)=='A')
                        isNum = true;
                     else if(tempString.charAt(x)=='B')
                        isNum = true;
                     else if(tempString.charAt(x)=='C')
                        isNum = true;
                     else if(tempString.charAt(x)=='D')
                        isNum = true;
                     else if(tempString.charAt(x)=='E')
                        isNum = true;
                     else if(tempString.charAt(x)=='F')
                        isNum = true;
                     else
                     {
                        isNum = false;
                        break;
                     }
                  }
               
                  if(tempString.length()==1 && tempString.charAt(0)=='.')
                     isNum = false;
               
                  if(tempString.length()==1 && tempString.charAt(0)=='-')
                     isNum = false;
               
                  if(!isNum)
                  {
                     AlertBox.display("ERROR!", "ERROR: '"+tempString+"' is not a hexidecimal number.");
                     decT.setText("");
                     binT.setText("");
                     hexT.setText("");
                     i3sT.setText("");
                     i3dT.setText("");
                  }
               
               }
               else
                  AlertBox.display("ERROR!", "ERROR: Please enter a number.");
            
               if(!multDec && !multNeg && isNum)
               {
                  String[] solution = ConversionAlgorithm.calculate(tempString, 2);
               
                  decT.setText(solution[0]);
                  binT.setText(solution[1]);
                  hexT.setText(solution[2]);
                  i3sT.setText(solution[3]);
                  i3dT.setText(solution[4]);
               
               }
            
            });  
   
   
   
   
   //Convert From Binary 
   
      Button convertFromBin = new Button("Convert");      
      convertFromBin.setOnAction(
            e -> {
            
               String tempString = "";
               tempString = binT.getText();
               boolean decimal = false;
               boolean multDec = false;
               boolean multNeg = false;
               boolean isNum = false;
               String extraString = "";
               if(tempString.length()!=0)
               {
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                     {
                        if(decimal)
                        {
                           multDec = true;
                           AlertBox.display("ERROR", "ERROR: You have multiple decimals.");
                           decT.setText("");
                           binT.setText("");
                           hexT.setText("");
                           i3sT.setText("");
                           i3dT.setText("");
                           break;
                        }
                        else
                           decimal = true;
                     }
                  
                  }
               
                  for(int x = 1; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='-')
                     {
                        multNeg = true;
                        AlertBox.display("ERROR", "ERROR: You have too many negative signs or it is in the wrong place.");
                        decT.setText("");
                        binT.setText("");
                        hexT.setText("");
                        i3sT.setText("");
                        i3dT.setText("");
                        break;
                     }
                  
                  }
               
               
               
                  for(int x = 0; x<tempString.length(); x++)
                  {
                  
                     if(tempString.charAt(x)=='.')
                        isNum = true;
                     else if(tempString.charAt(x)=='0')
                        isNum = true;
                     else if(tempString.charAt(x)=='1')
                        isNum = true;
                     else if(tempString.charAt(x)=='-')
                        isNum = true;
                     else
                     {
                        isNum = false;
                        break;
                     }
                  }
               
                  if(tempString.length()==1 && tempString.charAt(0)=='.')
                     isNum = false;
               
                  if(tempString.length()==1 && tempString.charAt(0)=='-')
                     isNum = false;
               
                  if(!isNum)
                  {
                     AlertBox.display("ERROR!", "ERROR: '"+tempString+"' is not a binary number.");
                     decT.setText("");
                     binT.setText("");
                     hexT.setText("");
                     i3sT.setText("");
                     i3dT.setText("");
                  }
               
               }
               else
                  AlertBox.display("ERROR!", "ERROR: Please enter a number.");
            
               if(!multDec && !multNeg && isNum)
               {
                  String[] solution = ConversionAlgorithm.calculate(tempString, 1);
               
                  decT.setText(solution[0]);
                  binT.setText(solution[1]);
                  hexT.setText(solution[2]);
                  i3sT.setText(solution[3]);
                  i3dT.setText(solution[4]);
               
               }
            
            }); 
   
   
      GridPane.setConstraints(clear, 3, 6);
   
      vPane.getChildren().addAll(convertFromDec, convertFromBin, convertFromHex, convertFromIES, convertFromIED);
      gPane.getChildren().addAll(decL, decT, binL, binT, hexL, hexT, i3sL, i3sT, i3dL, i3dT, clear);
   
      mainPane.setPadding(new Insets(5, 5, 5, 5));
      mainPane.setLeft(vPane);
      mainPane.setCenter(gPane);
   
   
   //Create a scene and add "layout" to it, then show the scene on primaryStage
      scene1 = new Scene(mainPane, 1075, 350);
      window.setScene(scene1);
      window.show();
   }

}