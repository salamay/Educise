package sample;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DashBoard.DashboardController;
import sample.StudentRegistration.LoadingWindow;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class Controller extends Main implements Initializable {
    Stage window2;
    public Button LogInButton;
    public TextField usernameTextField;
    public TextField passwordTextField;

    LogInModel loginmodel;
    public Label loginError;


    public void LogInButtonClicked() throws IOException {

goToDashBoard();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginmodel = new LogInModel();

    }

  public void goToDashBoard() throws IOException {
        loginmodel.isDBconnected();
        loginError.setVisible(false);
        String email = usernameTextField.getText();
        String passwd = passwordTextField.getText();
        if (loginmodel.loginQuery(email, passwd)) {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("DB.fxml"));
            window2 = new Stage();
            window2.initModality(Modality.APPLICATION_MODAL);
            window2.setTitle("welcome to management Board");
            window2.setMaximized(true);
            window2.setMinWidth(800);
            window2.setMinHeight(700);
            Scene scene = new Scene(root,1200,720);
            DashboardController dashboardController=new DashboardController();
            window2.setScene(scene);
            window2.show();

        } else {
            loginError.setVisible(true);
            loginError.setText("Invalid Credentials");
        }
    }


}
