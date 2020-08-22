package sample.StudentRegistration;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import java.net.URL;

import java.util.ResourceBundle;

//this class Show the loading screen whenever backgroung task is running
public class LoadingDialogController implements Initializable {
    public static ProgressIndicator progressindicator;
    public  JFXButton cancelbutton;
    public void cancelButtonOnclicked(){
        //this close the window and the connection in the thread
      LoadingWindow.window.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
