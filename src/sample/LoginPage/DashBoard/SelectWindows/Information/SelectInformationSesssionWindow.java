package sample.LoginPage.DashBoard.SelectWindows.Information;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

//This class load the window to select csession
public class SelectInformationSesssionWindow {

    public static Stage StudentWindow;
   public SelectInformationSesssionWindow() throws IOException {

       StudentWindow = new Stage();
       Parent root= FXMLLoader.load(getClass().getResource("SelectClass.fxml"));
       Scene scene = new Scene(root);
       StudentWindow.setMaximized(true);
       StudentWindow.initModality(Modality.APPLICATION_MODAL);
       StudentWindow.setResizable(true);
       StudentWindow.setTitle("Select session");
       StudentWindow.getIcons().add(new Image("image/window_icon.png"));
       StudentWindow.centerOnScreen();
       StudentWindow.setMinHeight(700);
       StudentWindow.setMinWidth(1200);
       StudentWindow.setScene(scene);
       StudentWindow.show();


   }




}
