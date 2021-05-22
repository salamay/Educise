package sample.LoginPage.DashBoard.SelectWindows.Information;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

//This class load the window to select csession
public class SelectInformationSesssionAndClassWindow {

    public static Stage StudentWindow;
   public SelectInformationSesssionAndClassWindow() throws IOException {

       StudentWindow = new Stage();
       Parent root= FXMLLoader.load(getClass().getResource("SelectClass.fxml"));
       StudentWindow.setTitle("Select session");
       StudentWindow.initModality(Modality.APPLICATION_MODAL);
       Scene scene=new Scene(root,1360,750);
       StudentWindow.centerOnScreen();
       StudentWindow.setMaximized(true);
       StudentWindow.getIcons().add(new Image("image/window_icon.png"));
       StudentWindow.setScene(scene);
       StudentWindow.show();


   }




}
