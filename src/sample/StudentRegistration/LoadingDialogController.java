package sample.StudentRegistration;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import sample.StudentRegistration.RegisterStudentThread;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//this class Show the loading screen whenever backgroung task is running
public class LoadingDialogController implements Initializable {
    public static ProgressIndicator progressindicator;
    public  JFXButton cancelbutton;
    public void cancelButtonOnclicked(){
        //this close the window and the connection in the thread
      LoadingWindow.window.close();
        try {
            RegisterStudentThread.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
