package sample.LoginPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginStatusWindow {
    public static Stage window;
    public LoginStatusWindow() throws IOException {
        window=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("loginstatus.fxml"));
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        Scene scene=new Scene(root,200,100);
        window.setScene(scene);
        window.show();
    }
}
