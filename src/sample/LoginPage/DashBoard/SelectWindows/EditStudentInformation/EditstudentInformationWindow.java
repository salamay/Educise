package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditstudentInformationWindow {
    public static Stage StudentWindow;
    public EditstudentInformationWindow(){
        StudentWindow = new Stage();
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("editstudentinformation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentWindow.initModality(Modality.APPLICATION_MODAL);
        Scene scene=new Scene(root,1360,750);
        StudentWindow.centerOnScreen();
        StudentWindow.setMaximized(true);
        StudentWindow.getIcons().add(new Image("image/window_icon.png"));
        StudentWindow.setScene(scene);
        StudentWindow.show();
    }
}
