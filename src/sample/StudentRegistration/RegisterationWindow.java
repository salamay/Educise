package sample.StudentRegistration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterationWindow {
   public static Stage window;
    public  RegisterationWindow() throws IOException {

        //Create window
        System.out.println("[CreateWindow()]: creating window");
        window=new Stage();
        window.setTitle("Student Registeration");
        Parent root= FXMLLoader.load(getClass().getResource("RegisterStudent.fxml"));

        Scene scene=new Scene(root,1200,700);
        window.setResizable(true);
        window.setScene(scene);
        window.show();
        System.out.println("[CreateWindow()]: window Created");


    }
}
