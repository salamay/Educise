package sample.LoginPage.DashBoard.SelectWindows.Attendance;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendanceWindow {
    public static Stage window;
    public AttendanceWindow() throws IOException {
        window=new Stage();
        window.setTitle("Student attendance");
        window.initModality(Modality.APPLICATION_MODAL);
        Parent root= FXMLLoader.load(getClass().getResource("attendance.fxml"));
        Scene scene=new Scene(root);
        window.setScene(scene);
        window.isMaximized();
        window.setMinHeight(760);
        window.setMinWidth(1360);
        window.centerOnScreen();
        window.getIcons().add(new Image("image/window_icon.png"));
        window.show();

    }
}
