package sample.LoginPage.DashBoard.Admin.SchoolFee;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SchoolFeeWindow {
    public static Stage window;
    public SchoolFeeWindow() throws IOException {
        window=new Stage();
        window.setTitle("ADMIN DASHBOARD");
        Parent root= FXMLLoader.load(getClass().getResource("school.fxml"));
        Scene scene=new Scene(root);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(600);
        window.setMinWidth(1000);
        window.setMaximized(true);
        window.setResizable(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();
    }
}
