package sample;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;


public class ConnectionError {
    boolean IsPresed;
    Stage window;
    public boolean Connection(Connection conn){
        JFXButton button = new JFXButton("OK");
        if(conn==null) {

            button.setAlignment(Pos.CENTER);
            window = new Stage();
            Label label = new Label();
            label.setText("No internet connection or Server failure");
            label.setAlignment(Pos.CENTER);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setResizable(false);
            window.setTitle("Connection Failed");
            window.setOnCloseRequest(event -> {
                event.consume();
            });
            button.setOnAction(e -> {
                IsPresed=true;
                window.close();
            });
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(0,0,0,0));
            vbox.setSpacing(10);
            button.setMinWidth(100);
            String style="-fx-background-radius: 20 20 20 20;-fx-background-color=##0066CB";
            button.setStyle(style);
            vbox.getChildren().addAll(label, button);
            Scene scene = new Scene(vbox, 200, 80);
            window.initStyle(StageStyle.UNDECORATED);
            window.setScene(scene);
            window.centerOnScreen();
            window.showAndWait();

        }

        return IsPresed;
    }


}
