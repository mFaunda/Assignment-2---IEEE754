//Mike Faunda III

/*

I implemented stack into my User interface because I only add one at a time, and push is useful for that.
When I search for users during login popping gives me an easy way to check each user against the credentials.

*/

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.lang.Math;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;


public class Assignment1 extends Application
{
//Declare all buttons needed
   Button loginB, signUpB, createB, cancelB;
   
//Declare all labels needed
   Label usrL, pwdL, fnmL, lnmL, dobL, gdrL, ssnL, phnL, cfmL, picL, emaL, usrSL, pwdSL;
   
//Declare Stages and Scenes
   Stage window;
   Scene login, signup;
   
   AlertBox alert = new AlertBox();
//Arrays used for data storage, first the username database, then what each user will have.
   public static User[] userArray = new User[0];
   public static Stack<User> userStack = new Stack();
   public static Stack<User> tempStack = new Stack();
 

   public static void main(String[]args)
   {
      launch(args);//launch "Application" to start using the JavaFX display
   }
   
   private void closeProgram()
   {
   //INSERT FILE SAVING HERE
      saveBeforeShutdown();
      window.close();
   }
   
   
//Method for saving array of users
   public void saveBeforeShutdown()
   {
   //ARRAY VERSION
   /*
      try
      {
         FileOutputStream fileOut = new FileOutputStream("userArray.ser");//creates a userArray serial file in output stream
         ObjectOutputStream out = new ObjectOutputStream(fileOut);//routs an object into the output stream.
         out.writeObject(userArray);// we designate our array of cards to be routed
         out.close();// closes the data paths
         fileOut.close();// closes the data paths
      }
      catch(IOException i)//exception stuff
      {
         i.printStackTrace();
      }
      */
      //STACK VERSION
      try
      {
         FileOutputStream fileOut = new FileOutputStream("userArray.ser");//creates a userArray serial file in output stream
         ObjectOutputStream out = new ObjectOutputStream(fileOut);//routs an object into the output stream.
         out.writeObject(userStack);// we designate our array of cards to be routed
         out.close();// closes the data paths
         fileOut.close();// closes the data paths
      }
      catch(IOException i)//exception stuff
      {
         i.printStackTrace();
      }
   }
   

   public void start(Stage primaryStage) throws Exception
   {
      window = primaryStage;
      
      window.setTitle("Login");
      BorderPane signupPane = new BorderPane();
      BorderPane loginPane = new BorderPane();
      login = new Scene(loginPane, 275, 200);
      signup = new Scene(signupPane, 540, 450);
      
      //Load user array using throw/catch if file is missing
      
      //ARRAY VERSION
      /*
      try// If this doesnt work throw an exception
      {
         FileInputStream fileIn = new FileInputStream("userArray.ser");// Read serial file.
         ObjectInputStream in = new ObjectInputStream(fileIn);// input the read file.
         userArray = (User[]) in.readObject();// allocate it to the object file already instanciated.
         in.close();//closes the input stream.
         fileIn.close();//closes the file data stream.
      }
      catch(IOException i)//exception stuff
      {
         alert.display("Save Not Found", "Save Not Found");
      }
      catch(ClassNotFoundException c)//more exception stuff
      {
         System.out.println("Error");
         c.printStackTrace();
         return;
      }
      */
      
      //STACK VERSION
      try// If this doesnt work throw an exception
      {
         FileInputStream fileIn = new FileInputStream("userArray.ser");// Read serial file.
         ObjectInputStream in = new ObjectInputStream(fileIn);// input the read file.
         userStack = (Stack<User>) in.readObject();// allocate it to the object file already instanciated.
         in.close();//closes the input stream.
         fileIn.close();//closes the file data stream.
      }
      catch(IOException i)//exception stuff
      {
         alert.display("Save Not Found", "Save Not Found");
      }
      catch(ClassNotFoundException c)//more exception stuff
      {
         System.out.println("Error");
         c.printStackTrace();
         return;
      }
      
      
      //Sign-Up Screen ***************************************************************************************************************************
      
      //Initialize layout layouts, buttons, and fields and such
      
      VBox vPane = new VBox(20);
      vPane.setPadding(new Insets(40, 2, 2, 2));
      
      GridPane gPane = new GridPane();
      gPane.setPadding(new Insets(20, 5, 5, 15));
      gPane.setVgap(10);
      gPane.setHgap(6);
      
      //Field Labels usrL, pwdL, fnmL, lnmLl, dobL, gdrL, ssnL, phnL, cfmL, picL, emaL;
      
      usrSL = new Label("*Username");
      pwdSL = new Label("*Password");
      fnmL = new Label("*First Name");
      lnmL = new Label("*Last Name");
      dobL = new Label("*Date of Birth");
      gdrL = new Label("*Gender");
      ssnL = new Label("SSN");
      phnL = new Label("Phone Number");
      cfmL = new Label("*Confirm Password");
      picL = new Label("Profile Picture");
      emaL = new Label("Email");
      
      
      //Field inputs                       //Set width of fields      //Prompt Text
      //For Sign Up   
      TextField usrST = new TextField(); usrST.setMinWidth(300);     usrST.setPromptText("6-16 Characters");
      PasswordField pwdST = new PasswordField(); pwdST.setMinWidth(300);     pwdST.setPromptText("Must have one cap, one number and one symbol.");
      TextField fnmT = new TextField(); fnmT.setMinWidth(300);     fnmT.setPromptText("First Name");
      TextField lnmT = new TextField(); lnmT.setMinWidth(300);     lnmT.setPromptText("Last Name");
      TextField dobT = new TextField(); dobT.setMinWidth(300);     dobT.setPromptText("MMDDYYYY");
      TextField gdrT = new TextField(); gdrT.setMinWidth(300);     gdrT.setPromptText("Any text can go here, you're welcome -_-");
      TextField ssnT = new TextField(); ssnT.setMinWidth(300);     ssnT.setPromptText("NO HYPHENS, NO SLASHES, ONLY NUMBERS");
      TextField phnT = new TextField(); phnT.setMinWidth(300);     phnT.setPromptText("NO HYPHENS, NO SLASHES, ONLY NUMBERS");
      PasswordField cfmT = new PasswordField(); cfmT.setMinWidth(300);     cfmT.setPromptText("Confirm Password");
      TextField emaT = new TextField(); emaT.setMinWidth(300);     emaT.setPromptText("Format: xxxx@mail.xxx");
      
      //Add Field Labels                        //Add Field Inputs
      gPane.setConstraints(usrSL, 1, 1);   gPane.setConstraints(usrST, 2, 1);
      gPane.setConstraints(pwdSL, 1, 2);   gPane.setConstraints(pwdST, 2, 2);
      gPane.setConstraints(cfmL, 1, 3);   gPane.setConstraints(cfmT, 2, 3);
      gPane.setConstraints(fnmL, 1, 4);   gPane.setConstraints(fnmT, 2, 4);
      gPane.setConstraints(lnmL, 1, 5);   gPane.setConstraints(lnmT, 2, 5);
      gPane.setConstraints(dobL, 1, 6);   gPane.setConstraints(dobT, 2, 6);
      gPane.setConstraints(gdrL, 1, 7);   gPane.setConstraints(gdrT, 2, 7);
      gPane.setConstraints(ssnL, 1, 8);   gPane.setConstraints(ssnT, 2, 8);
      gPane.setConstraints(phnL, 1, 9);   gPane.setConstraints(phnT, 2, 9);
      gPane.setConstraints(emaL, 1, 10);   gPane.setConstraints(emaT, 2, 10);
      gPane.setConstraints(picL, 1, 11);
      
      createB = new Button();
      createB.setText("Create Account!");
      
      createB.setOnAction(
            e -> {
            //INPUT VALIDATION ;-;
               if(fnmT.getText().length()<1){
                  alert.display("Warning!", "ENTER A FIRST NAME");
                  return;
               }
            
               if(lnmT.getText().length()<1){
                  alert.display("Warning!", "ENTER A LAST NAME");
                  return;
               }
            
               if(gdrT.getText().length()<1){
                  alert.display("Warning!", "ENTER A GENDER");
                  return;
               }
            
               if(usrST.getText().equals(""))
               {
                  alert.display("Warning!", "ENTER A USERNAME");
                  return;
               }
               
               //STACK VERSION
               tempStack.setAll(userStack.getAll());
               if(userStack.size()>0)
               {   
                  for(int x=0; x<tempStack.size()+1; x++)
                  {
                     if(usrST.getText().equals(tempStack.top().getUsername()))
                     {
                        alert.display("Warning!", "Username Already Taken");
                        return;
                     }
                     tempStack.pop();
                  }//MARK END USERNAME CHECK
               }
               
            
            
            //ARRAY VERSION
            /*
               if(userArray.length>0)
               {
                  for(int x=0; x<userArray.length; x++)
                  {
                     if(usrST.getText().equals(userArray[x].getUsername()))
                     {
                        alert.display("Warning!", "Username Already Taken");
                        return;
                     }
                  }//MARK END USERNAME CHECK
                 
               }   
               */
               
               
               
               boolean capital = false;
               boolean number = false;
               boolean special = false;
               boolean lower = false;
               for(int x=0; x<pwdST.getText().length(); x++)
               {
                  if(Character.isUpperCase(pwdST.getText().charAt(x)))
                     capital = true;
                  if(Character.isDigit(pwdST.getText().charAt(x)))
                     number = true;
                  if(Character.isLowerCase(pwdST.getText().charAt(x)))
                     lower = true;
                  if(!(Character.isLowerCase(pwdST.getText().charAt(x)) || Character.isDigit(pwdST.getText().charAt(x)) || Character.isUpperCase(pwdST.getText().charAt(x))))
                     special = true;
               }
               if(!(capital && number && special && lower))
               {
                  System.out.println(capital);
                  System.out.println(number);
                  System.out.println(special);
                  System.out.println(lower);
                  alert.display("Warning!", "Password must have at least a lower case letter, uppercase letter, number, and symbol.");
                  return;
               }//MARK END PASSWORD CHECK
                  
               if(!cfmT.getText().equals(pwdST.getText()))
               {
                  alert.display("Warning!", "Passwords do no match");
                  return;
               }//MARK END CONFIRM PASSWORD
                  
                  
               for(int x=0; x<ssnT.getText().length(); x++)
               {
                  if(ssnT.getText().length()!=9)
                  {
                     alert.display("Warning!", "SSN Must be 9 Numbers Long");
                     return;
                  }
                  if(!Character.isDigit(ssnT.getText().charAt(x)))
                  {
                     alert.display("Warning!", "SSN Must be 9 Numbers Long");
                     return;
                  }
               }//MARK END SSN CHECK
                  
               
               for(int x=0; x<dobT.getText().length(); x++)
               {
                  if(dobT.getText().length()!=8)
                  {
                     alert.display("Warning!", "Date Must be 8 Numbers Long in the format MMDDYYYY");
                     return;
                  }
                  if(!Character.isDigit(dobT.getText().charAt(x)))
                  {
                     alert.display("Warning!", "Date Must be 8 Numbers Long in the format MMDDYYYY");
                     return;
                  }
               }
               String dateofbirthstring = dobT.getText();
               int monthInt = Integer.parseInt(dateofbirthstring.substring(0,2));
               int dateInt = Integer.parseInt(dateofbirthstring.substring(2,4));  
               int yearInt = Integer.parseInt(dateofbirthstring.substring(4));
                  
               if(monthInt>12 || monthInt<1){
                  alert.display("Warning!", "There are 12 months, 1-12. Pick from those integers.");
                  return;}
                  
               if(dateInt>31 || dateInt<1){
                  alert.display("Warning!", "Please pick from 1-31 days. You're the reason programmers are stressed.");
                  return;}
                  
               if((monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) && dateInt>30){
                  alert.display("Warning!", "There are only 30 days in that month genius.");
                  return;}
                  
               if(monthInt == 2 && !(yearInt%100==0 && yearInt%400!=0) && yearInt%4==0 && dateInt>29){
                  alert.display("Warning!", "That year is a leap year, February can only have 29 days.");
                  return;}
                  
               if(monthInt == 2 && dateInt>28 && !(!(yearInt%100==0 && yearInt%400!=0) && yearInt%4==0)){
                  alert.display("Warning!", "That year is not a leap year, February can only have 28 days.");
                  return;}//END DATE OF BIRTH CHECK
                  
               for(int x=0; x<phnT.getText().length(); x++)
               {
                  if(phnT.getText().length()!=10)
                  {
                     alert.display("Warning!", "Phone Number Must be 10 Numbers Long, no dashes, no commas, just numbers");
                     return;
                  }
                  if(!Character.isDigit(phnT.getText().charAt(x)))
                  {
                     alert.display("Warning!", "Phone Number Must be 10 Numbers Long, no dashes, no commas, just numbers");
                     return;
                  }
               }//MARK END PHONE NUMBER CHECK
               
               if(emaT.getText().length()>0)
               {
                  String emailString = emaT.getText();
                  int substringInt = 0;
                  if(emailString.length()<10){
                     alert.display("Warning!", "Email must be at least 10 characters long in the format x@mail.xxx where the first x can be any number of any characters and the last three x's can be any 3 characters.");
                     return;
                  }
               
                  for(int x=0; x<emaT.getText().length(); x++)     
                  {
                     if(emailString.charAt(x)=='@')
                     {
                        substringInt = x;
                        break;
                     }
                  }
                  String last3 = emailString.substring(substringInt+6);
                  emailString = emailString.substring(substringInt,substringInt+6);
                  if(!emailString.equals("@mail.")){
                     alert.display("Warning!", "Email must be at least 10 characters long in the format x@mail.xxx where the first x can be any number of any characters and the last three x's can be any 3 characters.");
                     return;
                  }
               
                  if(last3.length()!=3){
                     alert.display("Warning!", "Email must be at least 10 characters long in the format x@mail.xxx where the first x can be any number of any characters and the last three x's can be any 3 characters.");
                     return;
                  }
               
               }//MARK END EMAIL CHECK
               
               File file = null;
               while(true)
               {
                  alert.display("Dialogue Box dont mind this title", "Time to find a profile picture!");
                  FileChooser fileChooser = new FileChooser();
                  fileChooser.setTitle("Search for Profile Picture");
                  file = fileChooser.showOpenDialog(window);
                  if(file!=null)
                     break;
               }
            
               userStack.push(new User(fnmT.getText(), lnmT.getText(), ssnT.getText(), dobT.getText(), gdrT.getText(), usrST.getText(), emaT.getText(), phnT.getText(), pwdST.getText(), file));
               User[] tempArray = new User[userArray.length+1];
               int xy = 0;
               for(xy=0; xy<userArray.length; xy++)
               {
                  tempArray[xy] = userArray[xy];
               }
               tempArray[xy] = new User(fnmT.getText(), lnmT.getText(), ssnT.getText(), dobT.getText(), gdrT.getText(), usrST.getText(), emaT.getText(), phnT.getText(), pwdST.getText(), file);
               userArray = tempArray;
               
               saveBeforeShutdown();
               alert.display("YAY!", "User Succesfully Created and Saved");
               window.setScene(login); 
            });
         
      cancelB = new Button();
      cancelB.setText("Cancel");
      cancelB.setOnAction(
            e -> {
               usrST.setText("");
               pwdST.setText("");
               fnmT.setText("");
               lnmT.setText("");
               dobT.setText("");
               gdrT.setText("");
               ssnT.setText("");
               phnT.setText("");
               cfmT.setText("");
               emaT.setText("");
               window.setScene(login);   
            });
      
      GridPane.setConstraints(createB, 1, 13);
      GridPane.setConstraints(cancelB, 2, 13);
      
      gPane.getChildren().addAll(usrSL, usrST, pwdSL, pwdST, fnmL, fnmT, lnmL, lnmT, dobL, dobT, gdrL, gdrT, ssnL, ssnT, phnL, phnT, cfmL, cfmT, emaL, emaT, createB, cancelB);
      
      signupPane.setPadding(new Insets(5, 5, 5, 5));
      signupPane.setLeft(vPane);
      signupPane.setCenter(gPane);
      
      
      
      
      
      //Login Screen ***************************************************************************************************************************
      
      
      VBox vPanel = new VBox(20);
      vPanel.setPadding(new Insets(40, 2, 2, 2));
      
      GridPane gPanel = new GridPane();
      gPanel.setPadding(new Insets(20, 5, 5, 15));
      gPanel.setVgap(20);
      gPanel.setHgap(6);
      
      usrL = new Label("Username");
      pwdL = new Label("Password");
      TextField usrT = new TextField(); usrT.setMinWidth(50);     usrT.setPromptText("Username");
      PasswordField pwdT = new PasswordField(); pwdT.setMinWidth(50);     pwdT.setPromptText("Password");
      
      loginB = new Button();
      loginB.setText("Login");
      loginB.setOnAction(
            e -> {
               
               String usernameCheck = usrT.getText();
               String passwordCheck = pwdT.getText();
               
               //Array Version
               /*
               for(int x=0; x<userArray.length; x++)
               {
                  if(usernameCheck.equals(userArray[x].getUsername()))
                     if(passwordCheck.equals(userArray[x].getPassword())){
                        File file = userArray[x].getPicture();
                        alert.displayB("Congratulations!", "You succesfully logged in!", "Logout", file);
                        return;}
               }
               */
               //Stack Version
               tempStack.setAll(userStack.getAll());;
               for(int x=0; x<userStack.size(); x++)
               {
                  if(usernameCheck.equals(tempStack.top().getUsername()))
                     if(passwordCheck.equals(tempStack.top().getPassword())){
                        File file = tempStack.top().getPicture();
                        alert.displayB("Congratulations!", "You succesfully logged in!", "Logout", file);
                        return;}
                  tempStack.pop();
               }  
               
               alert.display("Warning!", "User does not exist or password is incorrect.");
               
            });
         
      signUpB = new Button();
      signUpB.setText("Sign Up");
      signUpB.setOnAction(
            e -> {
               window.setScene(signup);
               usrT.setText("");
               pwdT.setText("");
            });
            
            
      gPanel.setConstraints(usrL, 1, 1);   gPanel.setConstraints(usrT, 2, 1);
      gPanel.setConstraints(pwdL, 1, 2);   gPanel.setConstraints(pwdT, 2, 2);
      
      gPanel.setConstraints(loginB, 1, 3);
      gPanel.setConstraints(signUpB, 2, 3);
      
      gPanel.getChildren().addAll(usrL, usrT, pwdL, pwdT, loginB, signUpB);
      
      loginPane.setPadding(new Insets(5, 5, 5, 5));
      loginPane.setLeft(vPanel);
      loginPane.setCenter(gPanel);
      
      
      window.setOnCloseRequest(
         e -> {
            e.consume();
            closeProgram();
         });
         
   
      window.setScene(login);
      window.show();
   }



   









}