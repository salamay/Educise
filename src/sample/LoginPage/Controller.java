package sample.LoginPage;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.DashboardController;
import sample.Main;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class Controller extends Main implements Initializable {
    public Button LogInButton;
    public JFXTextField usernameTextField;
    public JFXPasswordField passwordTextField;
    public Label loginError;


    public void LogInButtonClicked() throws IOException {
        goToDashBoard();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

  public void goToDashBoard() throws IOException {
        String email = usernameTextField.getText();
        String passwd = passwordTextField.getText();
        if (email.isEmpty()||passwd.isEmpty()){
            new ConnectionError().Connection("One of the field is missing");
        }
        if (!email.matches("^[A-Za-z0-9]*$")){
            new ConnectionError().Connection("Invalid email,check for invalid character");
        }
        if (!email.isEmpty()&&!passwd.isEmpty()&&email.matches("^[A-Za-z0-9]*$")){
            new LogInModel(email,passwd,loginError).start();
        }

    }
}
