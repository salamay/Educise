package sample.LoginPage.DashBoard.Admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminWindow {

    public AdminWindow() throws IOException {
        Stage window=new Stage();
        window.setTitle("ADMIN DASHBOARD");
        Parent root= FXMLLoader.load(getClass().getResource("admindashBoard.fxml"));
        Scene scene=new Scene(root);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMaximized(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();
    }
}
