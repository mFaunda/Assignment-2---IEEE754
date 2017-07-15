//Mike Faunda III

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import javafx.geometry.Insets;
import static javafx.application.Application.launch;

public class ResGUI extends Application
{
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
    
    @SuppressWarnings({ "unchecked", "unchecked" })
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
                
                
        launch(args);
    }
    
    private static void showExcelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(cell.getStringCellValue().equals(""))
                    break;
            }
            System.out.println("");
        }
    }
    
    private static void readIntoBST(List sheetData)
    {
        //
        // Iterates the data, puts it into restaurant objects, and adds those to the BST before launch
        //
        for (int i = 1; i < sheetData.size(); i++)
        {
            List list = (List) sheetData.get(i);
            Cell cellN = (Cell)list.get(0); cellN.setCellType(Cell.CELL_TYPE_STRING);
            Cell cellA = (Cell)list.get(1); cellA.setCellType(Cell.CELL_TYPE_STRING);
            Cell cellLa = (Cell)list.get(2); cellLa.setCellType(Cell.CELL_TYPE_STRING);
            Cell cellLo = (Cell)list.get(3); cellLo.setCellType(Cell.CELL_TYPE_STRING);
            Cell cellPh = (Cell)list.get(4); cellPh.setCellType(Cell.CELL_TYPE_STRING);
            Cell cellPi = (Cell)list.get(5); cellPi.setCellType(Cell.CELL_TYPE_STRING);
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
    
    public void start(Stage primaryStage) throws Exception
    {
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
        VBox vPane = new VBox();
        vPane.getChildren().addAll(resHPane, resNewB);
        resHPane.setAlignment(Pos.CENTER);
        vPane.setAlignment(Pos.CENTER);
    
    
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
    
        resBPane1.setBottom(vPane);
        resBPane1.setCenter(resTable);
    
        resDisplay = new Scene(resBPane1, 500, 425);
        addNewRestaurant = new Scene(resGPane, 250, 200);
    
        resWindow.setScene(resDisplay);
        resWindow.show();


    }

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
}