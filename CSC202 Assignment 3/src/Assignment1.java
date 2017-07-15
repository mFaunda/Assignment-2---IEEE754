//Mike Faunda III

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Cell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.lang.Math;

import javafx.geometry.Insets;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


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
    
    //Data Storage
    static BST treeStorage = new BST<Restaurant>();
    
    //Declare all buttons needed
    Button resSearchButton, resNewB, resCancelB, resAddB, resReset;
    
    //Declare all labels needed
    Label resNameL, resAddressL, resLocationL, resPhoneL, resPictureL;
    
    //Declare all text fields
    TextField resNameT, resAddressT, resLocationT, resPhoneT, resPictureT;
    
    //Declare Stages, Scenes, and Panes
    Stage resWindow;
    Scene resDisplay, addNewRestaurant, resView;
    BorderPane resBPane1 = new BorderPane(); BorderPane resBPane2 = new BorderPane();
    VBox resVPane1 = new VBox();
    GridPane resGPane = new GridPane();
    HBox resHPane = new HBox();
    
    //Pop-up for errors
    AlertBox resAlert = new AlertBox();
    
    //TableView
    TableView<Restaurant> resTable;
    TableColumn<Restaurant, String> nameColumn = new TableColumn<>("Name");
    TableColumn<Restaurant, String> addressColumn = new TableColumn<>("Address");
    TableColumn<Restaurant, String> phoneColumn = new TableColumn<>("Phone Number");
    TableColumn<Restaurant, String> latColumn = new TableColumn<>("Latitude");
    TableColumn<Restaurant, String> longColumn = new TableColumn<>("Longitude");
    TableColumn<Button, Button> pictureColumn = new TableColumn<>("Picture");
    ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();
    
    public ObservableList<Restaurant> getRestaurants()
    {
        treeStorage.reset();
        Queue<Restaurant> tempQ = treeStorage.inqueue;
        int counter = tempQ.size();
        restaurants = null;
        restaurants = FXCollections.observableArrayList();
        for(int x=0; x<counter; x++)
        {
            restaurants.add(tempQ.front());
            tempQ.dequeue();
        }
        treeStorage.reset();
        return restaurants;
    }
    
    //Excel Data
    static List sheetData = new ArrayList();
    static String filename = "restaurant.xls";
    static FileInputStream fis = null;
    static HSSFWorkbook workbook = null;
    static HSSFSheet sheet = null;
    
    public void rowFactoryMaker()
    {
        resTable.setRowFactory(tv ->
        {
            TableRow<Restaurant> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY)
                {
                    
                    Restaurant clickedRow = row.getItem();
                    
                    Label resN = new Label("Name: " + clickedRow.getName());
                    Label resA = new Label("Address: " + clickedRow.getAddress());
                    Label resLa = new Label("Latitude: " + clickedRow.getLat());
                    Label resLo = new Label("Longitude: " + clickedRow.getLon());
                    Label resPh = new Label("Phone Number: " + clickedRow.getPhone());
                    String resPi = clickedRow.getPicture();
                    ImageView imageView = new ImageView();
                    try
                    {
                        Image image = new Image(resPi);
                        imageView = new ImageView(image);
                        imageView.setFitHeight(150);
                        imageView.setFitWidth(150);
                    }
                    catch(Exception e)
                    {
                        resAlert.display("WARNING", "Invalid URL");
                        imageView.setFitHeight(150);
                        imageView.setFitWidth(150);
                    }
                    
                    resVPane1 = new VBox();
                    resVPane1.getChildren().addAll(resN, resA, resLa, resLo, resPh, imageView);
                    resBPane1.setLeft(resVPane1);
                    resWindow.setScene(resDisplay);
                }
            });
            return row;
        });
    }
    
    private static void readIntoBST(List sheetData)
    {
        //
        // Iterates the data, puts it into restaurant objects, and adds those to the BST before launch
        //
        for (int i = 1; i < sheetData.size(); i++)
        {
            List list = (List) sheetData.get(i);
            org.apache.poi.ss.usermodel.Cell cellN = (org.apache.poi.ss.usermodel.Cell)list.get(0); cellN.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            org.apache.poi.ss.usermodel.Cell cellA = (org.apache.poi.ss.usermodel.Cell)list.get(1); cellA.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            org.apache.poi.ss.usermodel.Cell cellLa = (org.apache.poi.ss.usermodel.Cell)list.get(2); cellLa.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            org.apache.poi.ss.usermodel.Cell cellLo = (org.apache.poi.ss.usermodel.Cell)list.get(3); cellLo.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            org.apache.poi.ss.usermodel.Cell cellPh = (org.apache.poi.ss.usermodel.Cell)list.get(4); cellPh.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            org.apache.poi.ss.usermodel.Cell cellPi = (org.apache.poi.ss.usermodel.Cell)list.get(5); cellPi.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
            if(cellN.getStringCellValue().equals(""))
                break;
            Restaurant resT = new Restaurant(cellN.getStringCellValue(),//name
                    cellA.getStringCellValue(),//address
                    cellLa.getStringCellValue(),//lat
                    cellLo.getStringCellValue(),//long
                    cellPh.getStringCellValue(),//phone
                    cellPi.getStringCellValue());//pic
            
            
            
            treeStorage.add(resT);
        }
        System.out.println(treeStorage);
    }
    
    private static void showExcelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                org.apache.poi.ss.usermodel.Cell cell = (org.apache.poi.ss.usermodel.Cell) list.get(j);
                if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
                cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
                if(cell.getStringCellValue().equals(""))
                    break;
            }
            System.out.println("");
        }
    }
    
    
    
    
    
    
    
    
 

   public static void main(String[]args) throws IOException
   {
       // An excel file name. You can create a file name with full
       // path information.
       try {
           // Create a FileInputStream that will be use to read the
           // excel 2003 file.
           fis = new FileInputStream(filename);
           // Create an excel workbook from the file system.
           workbook = new HSSFWorkbook(fis);
           // Get the first sheet on the workbook.
           sheet = workbook.getSheetAt(0);
           // When we have a sheet object in hand we can iterator on
           // each sheet's rows and on each row's cells. We store the
           // data read on an ArrayList so that we can print the
           // content of the excel to the console.
           Iterator rows = sheet.rowIterator();
           while (rows.hasNext()) {
               HSSFRow row = (HSSFRow) rows.next();
               Iterator cells = row.cellIterator();
            
               List data = new ArrayList();
               while (cells.hasNext()) {
                   HSSFCell cell = (HSSFCell) cells.next();
                   data.add(cell);
               }
            
               sheetData.add(data);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (fis != null) {
               fis.close();
           }
       }
       readIntoBST(sheetData);
       
      launch(args);//launch "Application" to start using the JavaFX resDisplay
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
      
      //RESGUI
    
       resWindow = primaryStage;
       resWindow.setTitle("Restaurant GUI");
       resWindow.setHeight(800);
       resWindow.setWidth(710);
    
       nameColumn.setMinWidth(150);
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    
       addressColumn.setMinWidth(225);
       addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    
       phoneColumn.setMinWidth(100);
       phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    
       latColumn.setMinWidth(100);
       latColumn.setCellValueFactory(new PropertyValueFactory<>("lat"));
    
       longColumn.setMinWidth(100);
       longColumn.setCellValueFactory(new PropertyValueFactory<>("lon"));
       ///////////////////////////////////////////////////////////////////////////
       pictureColumn.setMinWidth(100);
       pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
       ///////////////////////////////////////////////////////////////////////////
    
       resTable = new TableView<>();
       resTable.setItems(getRestaurants());
       resTable.getColumns().addAll(nameColumn, addressColumn, phoneColumn, latColumn, longColumn);
    
       //Create labels: resNameL, resAddressL, resLocationL, resPhoneL, resPictureL
       resNameL = new Label("Name: ");
       resAddressL = new Label("Address: ");
       resLocationL = new Label("Location: ");
       resPhoneL = new Label("Phone: ");
       resPictureL = new Label("Picture: ");
    
       //Create textfields: resNameT, resAddressT, resLocationT, resPhoneT
       resNameT = new TextField();
       resNameT.setPromptText("Name of Restaurant");
       resAddressT = new TextField();
       resAddressT.setPromptText("ex: 6409 Bumblebee Ln.");
       resLocationT = new TextField();
       resLocationT.setPromptText("Latitude:Longitude");
       resPhoneT = new TextField();
       resPhoneT.setPromptText("ex: 5551234567");
       resPictureT = new TextField();
       resPictureT.setPromptText("http://LinkToPicture.com");
       //Also textfields for searching
       //TextField searchN = new TextField(); searchN.setPromptText("Name of Restaurant");
       //TextField searchA = new TextField(); searchA.setPromptText("and/or Address");
       //TextField searchP = new TextField(); searchP.setPromptText("and/or Phone Number");
       TextField searchLat = new TextField();
       searchLat.setPromptText("Latitude");
       TextField searchLong = new TextField();
       searchLong.setPromptText("Longitude");
    
       //Create buttons: resSearchButton, resNewB, resCancelB, pictureB
       resReset = new Button("Reset");
       resReset.setOnAction(e ->
       {
           resTable = new TableView<>();
           resTable.setItems(getRestaurants());
           resTable.getColumns().addAll(nameColumn, addressColumn, phoneColumn, latColumn, longColumn);
           resBPane1.setCenter(resTable);
           resWindow.setScene(resDisplay);
           rowFactoryMaker();
       });
    
       resSearchButton = new Button("Search");
       resSearchButton.setOnAction(e ->
       {
        
           String locLat = searchLat.getText();
           String locLong = searchLong.getText();
           if(locLat.equals("")&&locLong.equals(""))
           {
               resAlert.display("WARNING!", "Enter either latitude, longitude, or both.");
               return;
           }
           else if((!locLat.equals("")) && locLong.equals(""))
           {
               try{
                   double locLatN = Double.parseDouble(locLat);
                   if(locLatN > 90 || locLatN < -90)
                   {
                       resAlert.display("WARNING", "Latitude Out of Bounds: (-90 -> 90)");
                       return;
                   }
               }
               catch(Exception exz)
               {
                   resAlert.display("WARNING", "Not a valid location. Recheck for typos.");
                   return;
               }
           }
           else if((!locLong.equals("")) && locLat.equals(""))
           {
               try{
                   double locLongN = Double.parseDouble(locLong);
                   if(locLongN > 180 || locLongN < -180)
                   {
                       resAlert.display("WARNING", "Longitude Out of Bounds: (-180 -> 180)");
                       return;
                   }
               }
               catch(Exception exz)
               {
                   resAlert.display("WARNING", "Not a valid location. Recheck for typos.");
                   return;
               }
           }
           else
           {
               try
               {
                   double locLatN = Double.parseDouble(locLat);
                   double locLongN = Double.parseDouble(locLong);
                   if(locLatN > 90 || locLatN < -90)
                   {
                       resAlert.display("WARNING", "Latitude Out of Bounds: (-90 -> 90)");
                       return;
                   }
                   if(locLongN > 180 || locLongN < -180)
                   {
                       resAlert.display("WARNING", "Longitude Out of Bounds: (-180 -> 180)");
                       return;
                   }
               }
               catch(Exception exz)
               {
                   resAlert.display("WARNING", "Not a valid location. Recheck for typos.");
                   return;
               }
           }
        
        
           //Search using parameters given
           Restaurant tempR = new Restaurant();
           tempR.setLat(searchLat.getText());
           tempR.setLon(searchLong.getText());
        
           treeStorage.reset();
           Queue<Restaurant> tempQ = treeStorage.inqueue;
           ObservableList<Restaurant> tempObList = FXCollections.observableArrayList();
           int tempInt = tempQ.size();
           if(!locLat.equals("")&&locLong.equals(""))
               for(int x=0; x<tempInt; x++)
               {
                   if(tempQ.front().getLat().equals(locLat))
                   {
                       tempObList.add(tempQ.front());
                   }
                   tempQ.dequeue();
               }
           else if(!locLong.equals("")&&locLat.equals(""))
               for(int x=0; x<tempInt; x++)
               {
                   if(tempQ.front().getLon().equals(locLong))
                   {
                       tempObList.add(tempQ.front());
                   }
                   tempQ.dequeue();
               }
           else
           {
               for(int x=0; x<tempInt; x++)
               {
                   if(tempQ.front().getLat().equals(locLat) && tempQ.front().getLon().equals(locLong))
                   {
                       tempObList.add(tempQ.front());
                   }
                   tempQ.dequeue();
               }
           }
           //Create new table with all matching restaurants
           resTable = new TableView<>();
           resTable.setItems(tempObList);
           resTable.getColumns().addAll(nameColumn, addressColumn, phoneColumn, latColumn, longColumn);
           //Display new table
           resBPane1.setCenter(resTable);
           resWindow.setScene(resDisplay);
           rowFactoryMaker();
       });
    
       resNewB = new Button("Add New Restaurant");
       resNewB.setOnAction(e ->
       {
           resWindow.setScene(addNewRestaurant);
       });
    
       resCancelB = new Button("Cancel");
       resCancelB.setMaxHeight(30);
       resCancelB.setOnAction(e ->
       {
           resNameT.setText("");
           resPictureT.setText("");
           resAddressT.setText("");
           resPhoneT.setText("");
           resLocationT.setText("");
           resWindow.setScene(resDisplay);
           rowFactoryMaker();
       });
    
       resAddB = new Button("Add!");
       resAddB.setMaxHeight(30);
       resAddB.setOnAction(e ->
       {
           String locLat = "";
           String locLong = "";
           //Add restaurant, refresh list, switch to resDisplay scene
           if(resNameT.getText().equals(""))
           {
               resAlert.display("WARNING", "Don't forget to add the name.");
               return;
           }
           if(resAddressT.getText().equals(""))
           {
               resAlert.display("WARNING", "Don't forget to add the address.");
               return;
           }
           if(resLocationT.getText().equals(""))
           {
               resAlert.display("WARNING", "Don't forget to add the location.");
               return;
           }
           if(resPhoneT.getText().equals(""))
           {
               resAlert.display("WARNING", "Don't forget to add the phone number.");
               return;
           }
           if(resPictureT.getText().equals(""))
           {
               resAlert.display("WARNING", "Don't forget to add a picture URL.");
               return;
           }
        
        
           String locT = resLocationT.getText();
           boolean locBool = false;
        
           if(locT.charAt(0) == ':')
           {
               resAlert.display("WARNING", "Colon goes in the middle silly");
               return;
           }
           if(locT.charAt(locT.length() - 1) == ':')
           {
               resAlert.display("WARNING", "Colon goes in the middle silly");
               return;
           }
        
        
        
           for(int x = 0; x < resLocationT.getText().length(); x++)
           {
               if(resLocationT.getText().charAt(x) == ':')
               {
                   if(locBool)
                   {
                       resAlert.display("WARNING", "Separate latitude and longitude with a single colon (:)");
                       return;
                   }
                   locBool = true;
               }
           }
           try{
               if(!locBool)
               {
                   resAlert.display("WARNING", "Add valid location and separate latitude and longitude with a single colon (:)");
                   return;
               }
            
               int xmg;
               for(xmg = 0; xmg < locT.length(); xmg++)
               {
                   if(locT.charAt(xmg) == ':')
                       break;
                   locLat = locLat + locT.charAt(xmg);
               }
               for(xmg = xmg + 1; xmg < locT.length(); xmg++)
               {
                   locLong = locLong + locT.charAt(xmg);
               }
               double locLatN = Double.parseDouble(locLat);
               double locLongN = Double.parseDouble(locLong);
               if(locLatN > 90 || locLatN < -90)
               {
                   resAlert.display("WARNING", "Latitude Out of Bounds: (-90 -> 90)");
                   return;
               }
               if(locLongN > 180 || locLongN < -180)
               {
                   resAlert.display("WARNING", "Longitude Out of Bounds: (-180 -> 180)");
                   return;
               }}
           catch(Exception exz)
           {
               resAlert.display("WARNING", "Not a valid location. Recheck for typos.");
               return;
           }
        
           //MARK END LOCATION CHECK
        
        
           for(int x = 0; x < resPhoneT.getText().length(); x++)
           {
               if(resPhoneT.getText().length() != 10)
               {
                   resAlert.display("Warning!", "Phone Number Must be 10 Numbers Long, no dashes, no commas, just numbers");
                   return;
               }
               if(!Character.isDigit(resPhoneT.getText().charAt(x)))
               {
                   resAlert.display("Warning!", "Phone Number Must be 10 Numbers Long, no dashes, no commas, just numbers");
                   return;
               }
           }//MARK END PHONE NUMBER CHECK
        
           Restaurant tempR = new Restaurant(resNameT.getText(), resAddressT.getText(), locLat, locLong, resPhoneT.getText(), resPictureT.getText());
        
           if(treeStorage.contains(tempR))
           {
               resAlert.display("Error!", "Restaurant already in list!");
           }
           else
               treeStorage.add(tempR);
        
           resTable.setItems(getRestaurants());
           resWindow.setScene(resDisplay);
           resNameT.setText("");
           resPictureT.setText("");
           resAddressT.setText("");
           resPhoneT.setText("");
           resLocationT.setText("");
           rowFactoryMaker();
       });
    
    
       //Format layout of GUI
       resGPane.setPadding(new Insets(20, 5, 5, 15));
       resGPane.setVgap(10);
       resGPane.setHgap(6);
    
       resHPane.setPadding(new Insets(5, 5, 5, 5));
       VBox resVPanePane = new VBox();
       resVPanePane.getChildren().addAll(resHPane, resNewB);
       resHPane.setAlignment(Pos.CENTER);
       resVPanePane.setAlignment(Pos.CENTER);
    
    
       resGPane.setConstraints(resNameL, 1, 1);
       resGPane.setConstraints(resNameT, 2, 1);
       resGPane.setConstraints(resAddressL, 1, 2);
       resGPane.setConstraints(resAddressT, 2, 2);
       resGPane.setConstraints(resLocationL, 1, 3);
       resGPane.setConstraints(resLocationT, 2, 3);
       resGPane.setConstraints(resPhoneL, 1, 4);
       resGPane.setConstraints(resPhoneT, 2, 4);
       resGPane.setConstraints(resPictureL, 1, 5);
       resGPane.setConstraints(resPictureT, 2, 5);
       resGPane.setConstraints(resCancelB, 1, 6);
       resGPane.setConstraints(resAddB, 2, 6);
    
       resGPane.getChildren().addAll(resNameL, resNameT, resAddressL, resAddressT, resLocationL, resLocationT, resPhoneL, resPhoneT, resPictureL, resPictureT, resCancelB, resAddB);
       resHPane.getChildren().addAll(searchLat, searchLong, resSearchButton, resReset);
    
       resBPane1.setBottom(resVPanePane);
       resBPane1.setCenter(resTable);
    
       resDisplay = new Scene(resBPane1, 500, 425);
       addNewRestaurant = new Scene(resGPane, 250, 200);
    
       resWindow.setScene(resDisplay);
       
       //RESGUI
      
      
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
            
               if(userArray.length>0)
               {
                  if(usrST.getText().equals(""))
                  {
                     alert.display("Warning!", "ENTER A USERNAME");
                     return;
                  }
                  for(int x=0; x<userArray.length; x++)
                  {
                     if(usrST.getText().equals(userArray[x].getUsername()))
                     {
                        alert.display("Warning!", "Username Already Taken");
                        return;
                     }
                  }//MARK END USERNAME CHECK
               }
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
               System.out.println(monthInt);
               int dateInt = Integer.parseInt(dateofbirthstring.substring(2,4));
               System.out.println(dateInt);   
               int yearInt = Integer.parseInt(dateofbirthstring.substring(4));
               System.out.println(yearInt);
                  
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
               
               for(int x=0; x<userArray.length; x++)
               {
                  if(usernameCheck.equals(userArray[x].getUsername()))
                     if(passwordCheck.equals(userArray[x].getPassword())){
                        File file = userArray[x].getPicture();
                        alert.displayB("Congratulations!", "You succesfully logged in!", "Logout", file);
                         window.setScene(resDisplay);
                        return;}
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