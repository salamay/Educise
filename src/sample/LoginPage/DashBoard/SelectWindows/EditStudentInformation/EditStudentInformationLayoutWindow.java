package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditStudentInformationLayoutWindow {
    //These variables are made static so as to be reference in EditingLayoutController class which serve as a
    // parameter to retrieve student information
    //From the datatbase
    public static Stage StudentWindow;
    public static String studentid;
    public void loadWindow(String id){
        this.studentid=id;
        System.out.println("[EditStudentInformationLayoutWindow]: studentname :"+studentid);
        StudentWindow = new Stage();
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("editinglayout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        StudentWindow.setTitle("Edit student information");
        StudentWindow.setMaximized(true);
        StudentWindow.initModality(Modality.APPLICATION_MODAL);
        StudentWindow.setResizable(true);
        StudentWindow.centerOnScreen();
        StudentWindow.setMinHeight(700);
        StudentWindow.setMinWidth(1200);
        StudentWindow.setScene(scene);
        StudentWindow.getIcons().add(new Image("image/window_icon.png"));
        StudentWindow.show();
    }
}
