package sample.LoginPage.DashBoard.SelectWindows.Teacher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

//This class load a window that contains listview that displays names of teachers
public class PreInformationWindow {
    static Stage window;
    public PreInformationWindow() throws IOException {
        //Create window
        window=new Stage();
        window.setTitle("Select teacher name");
        Parent root= FXMLLoader.load(getClass().getResource("preTeacherInfo.fxml"));
        Scene scene=new Scene(root,1200,720);
        window.setResizable(true);
        window.centerOnScreen();
        window.setMaximized(true);
        window.setScene(scene);
        window.getIcons().add(new Image("image/window_icon.png"));
        window.show();
    }
}
