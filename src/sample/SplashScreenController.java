package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {
    public Circle c1;
    public Circle c2;
    public Circle c3;
    public Circle c4;
    public Label edulabel;
    public Label ciselabel;
    public Label managemyschoollabel;
    public static Stage window1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new DisplayLoginWindow().start();
        Rotate(c1,true,360,2000);
        Rotate(c2,true,270,1500);
        Rotate(c3,true,180,1800);
        Rotate(c4,true,145,2300);
        Fade(edulabel,4000);
        Fade(ciselabel,5000);
        Fade(managemyschoollabel,7000);

    }
    public void Rotate(Circle c,boolean reverse,int angle,int duration){
        RotateTransition rotateTransition=new RotateTransition(new Duration(duration),c);
        rotateTransition.setByAngle(angle);
        rotateTransition.setAutoReverse(reverse);
        rotateTransition.setDelay(new Duration(0));
        rotateTransition.setRate(2);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.play();
    }
    public void Fade(Label label,int duration){
        FadeTransition fadeTransition=new FadeTransition(new Duration(duration),label);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
    private class DisplayLoginWindow extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{
                window1=new Stage();
                Parent root= null;
                try {
                    root = FXMLLoader.load(getClass().getResource("login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene=new Scene(root,1200,700);
                window1.setScene(scene);
                window1.setMaximized(true);
                window1.setResizable(false);
                window1.show();
                window1.setTitle("Sign in");
                window1.getIcons().add(new Image("image/window_icon.png"));
                Main.window1.close();

            });

        }
    }
}
