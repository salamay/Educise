package sample.LoginPage.DashBoard.SelectWindows.AcademicSession;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class SelectAcademicSession {
    public void CreateWindow() throws IOException {
        Stage window=new Stage();
        window.setTitle("Select Academic Session");
        Parent root= FXMLLoader.load(getClass().getResource("AcademicSession.fxml"));
        Scene scene=new Scene(root);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMaxHeight(800);
        window.setMaxWidth(602);
        window.centerOnScreen();
        window.setScene(scene);
        window.show();
    }
}
