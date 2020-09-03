package sample.LoginPage.DashBoard.SelectWindows.Registeration;

import com.jfoenix.controls.JFXButton;
import javafx.animation.RotateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;

import java.util.ResourceBundle;

//this class Show the loading screen whenever backgroung task is running
public class LoadingDialogController implements Initializable {
    public Circle c1;
    public Circle c2;
    public Circle c3;
    public Circle c4;

    public static ProgressIndicator progressindicator;
    public  JFXButton cancelbutton;
    public void cancelButtonOnclicked(){
        //this close the window and the connection in the thread
      LoadingWindow.window.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rotate(c1,true,360,2000);
        Rotate(c2,true,270,1500);
        Rotate(c3,true,180,1800);
        Rotate(c4,true,145,2300);
    }
    public void Rotate(Circle c,boolean reverse,int angle,int duration){
        RotateTransition rotateTransition=new RotateTransition(new Duration(duration),c);
        rotateTransition.setByAngle(angle);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setDelay(new Duration(0));
        rotateTransition.setRate(2);
        rotateTransition.setCycleCount(rotateTransition.INDEFINITE);
        rotateTransition.play();
    }
}
