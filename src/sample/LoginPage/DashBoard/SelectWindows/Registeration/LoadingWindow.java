package sample.LoginPage.DashBoard.SelectWindows.Registeration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoadingWindow {
    public static Stage window;
    public LoadingWindow() throws IOException {
        window=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("LoadingDialog.fxml"));
        Scene scene=new Scene(root,400,200);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }
}
