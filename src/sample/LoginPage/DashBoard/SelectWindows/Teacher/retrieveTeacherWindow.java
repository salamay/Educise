package sample.LoginPage.DashBoard.SelectWindows.Teacher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class retrieveTeacherWindow  {
    public static Stage window;
    public static String teacherid;
    public retrieveTeacherWindow(String teacherid) throws IOException {
        this.teacherid=teacherid;
        //Create window
        System.out.println("Creating teacher window");
        window=new Stage();
        window.setTitle("Teacher information");
        Parent root= FXMLLoader.load(getClass().getResource("TeacherInformationDashBoard.fxml"));

        Scene scene=new Scene(root,1200,720);
        window.setResizable(true);
        window.centerOnScreen();
        window.setMaximized(true);
        window.setScene(scene);
        window.getIcons().add(new Image("image/window_icon.png"));
        window.show();
    }
}
