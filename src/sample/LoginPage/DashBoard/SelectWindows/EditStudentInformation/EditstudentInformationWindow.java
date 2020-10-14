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
        Scene scene = new Scene(root);
        StudentWindow.setMaximized(true);
        StudentWindow.initModality(Modality.APPLICATION_MODAL);
        StudentWindow.setResizable(true);
        StudentWindow.centerOnScreen();
        StudentWindow.setMinHeight(700);
        StudentWindow.setTitle("Edit student information");
        StudentWindow.getIcons().add(new Image("image/window_icon.png"));
        StudentWindow.setMinWidth(1200);
        StudentWindow.setScene(scene);
        StudentWindow.show();
    }
}
