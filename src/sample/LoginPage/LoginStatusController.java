package sample.LoginPage;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginStatusController implements Initializable {
    public Circle blue;
    public Circle red;
    public Circle green;
    public Circle yellow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //blue
        FadeTransition fadeTransition=new FadeTransition();
        fadeTransition.setDuration(new Duration(10));
        fadeTransition.setCycleCount(100);
        fadeTransition.isAutoReverse();
        fadeTransition.setNode(blue);
        fadeTransition.play();
        ScaleTransition scaleTransition=new ScaleTransition();
        scaleTransition.setByX(1.0);
        scaleTransition.setByX(1.0);
        scaleTransition.setDuration(new Duration(100));
        scaleTransition.setAutoReverse(true);
        scaleTransition.setNode(blue);
        scaleTransition.play();
        //red
        FadeTransition fadeTransition1=new FadeTransition();
        fadeTransition1.setDuration(new Duration(200));
        fadeTransition1.setCycleCount(100);
        fadeTransition1.isAutoReverse();
        fadeTransition1.setNode(red);
        fadeTransition1.play();
        ScaleTransition scaleTransition1=new ScaleTransition();
        scaleTransition1.setByX(1.0);
        scaleTransition1.setByX(1.0);
        scaleTransition1.setDuration(new Duration(200));
        scaleTransition1.setAutoReverse(true);
        scaleTransition1.setNode(red);
        scaleTransition1.play();
        //red
        FadeTransition fadeTransition2=new FadeTransition();
        fadeTransition2.setDuration(new Duration(200));
        fadeTransition2.setCycleCount(100);
        fadeTransition2.setNode(green);
        fadeTransition2.play();
        ScaleTransition scaleTransition2=new ScaleTransition();
        scaleTransition2.setByX(1.0);
        scaleTransition2.setByX(1.0);
        fadeTransition2.isAutoReverse();
        scaleTransition2.setDuration(new Duration(400));
        scaleTransition2.setAutoReverse(true);
        scaleTransition2.setNode(green);
        scaleTransition2.play();
        //red
        FadeTransition fadeTransition3=new FadeTransition();
        fadeTransition3.setDuration(new Duration(600));
        fadeTransition3.setCycleCount(100);
        fadeTransition3.isAutoReverse();
        fadeTransition3.setNode(yellow);
        fadeTransition3.play();
        ScaleTransition scaleTransition3=new ScaleTransition();
        scaleTransition3.setByX(1.0);
        scaleTransition3.setByX(1.0);
        scaleTransition3.setDuration(new Duration(600));
        scaleTransition3.setAutoReverse(true);
        scaleTransition3.setNode(yellow);
        scaleTransition3.play();
    }
}

