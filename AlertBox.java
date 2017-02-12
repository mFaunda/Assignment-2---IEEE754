//Mike Faunda III

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox
{

   public static void display(String title, String message)
   {
   
      Stage window = new Stage();
   
      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle(title);
      window.setMinWidth(350);
      window.setMinHeight(250);
   
      Label label = new Label();
      label.setText(message);
      Button closeButton = new Button("Okay");
      closeButton.setOnAction(e -> window.close());
   
      VBox layout = new VBox(10);
      layout.setPadding(new Insets(15, 15, 15, 15));
      layout.getChildren().addAll(label, closeButton);
      layout.setAlignment(Pos.CENTER);
   
      Scene scene = new Scene(layout);
      window.setScene(scene);
      window.showAndWait();
   }

}