package sample.SelectWindows.Information;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

//This class load the window to select csession
public class StudentSelectClassInfo {

    static Stage StudentWindow;
   public StudentSelectClassInfo() throws IOException {

       StudentWindow = new Stage();
       Parent root= FXMLLoader.load(getClass().getResource("SelectClass.fxml"));
       Scene scene = new Scene(root);
       StudentWindow.setScene(scene);
       StudentWindow.setMaximized(true);
       StudentWindow.initModality(Modality.APPLICATION_MODAL);
       StudentWindow.setResizable(true);
       StudentWindow.centerOnScreen();
       StudentWindow.show();

   }




}
