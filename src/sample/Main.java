package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage window1;
    @Override
    public void start(Stage primaryStage) throws Exception{

        window1=new Stage();
        window1=primaryStage;
        Parent root= FXMLLoader.load(getClass().getResource("splashscreen.fxml"));
        Scene scene=new Scene(root,1200,700);
        window1.setScene(scene);
        window1.initStyle(StageStyle.UNDECORATED);
        window1.setMaximized(true);
        window1.setResizable(false);
        window1.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
