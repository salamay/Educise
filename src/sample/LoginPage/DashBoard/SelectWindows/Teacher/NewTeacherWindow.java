package sample.LoginPage.DashBoard.SelectWindows.Teacher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class NewTeacherWindow {
    static Stage window;
    public NewTeacherWindow() throws IOException {
        //Create window
        System.out.println("[CreateWindow()]: creating window");
        window=new Stage();
        window.setTitle("Student Registeration");
        Parent root= FXMLLoader.load(getClass().getResource("teacher.fxml"));

        Scene scene=new Scene(root,1200,700);
        window.setResizable(true);
        window.centerOnScreen();
        window.setMaximized(true);
        window.setScene(scene);
        window.setTitle("Register tearcher");
        window.getIcons().add(new Image("image/window_icon.png"));
        window.show();
        System.out.println("[NewTeacherWIndow]: window Created");

    }
}
