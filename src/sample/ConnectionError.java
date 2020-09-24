package sample;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class ConnectionError {
    boolean IsPresed;
    Stage window;
    public boolean Connection(String message){
        JFXButton button = new JFXButton("OK");
            button.setAlignment(Pos.CENTER);
            window = new Stage();
            Label label = new Label();
            if (message=="SUCCESS"){
                label.setStyle("-fx-text-fill:#1ABD1A;");
            }else {
                label.setStyle("-fx-text-fill:#FD5B6A;");
            }
            label.setText(message);
            label.setFont(Font.font("Verdana", FontWeight.BOLD,14));
            label.setAlignment(Pos.CENTER);
            window.initStyle(StageStyle.UNDECORATED);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setResizable(false);
            window.setOnCloseRequest(event -> {
                event.consume();
            });
            button.setOnAction(e -> {
                IsPresed=true;
                window.close();
            });
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(10,10,10,10));
            vbox.setSpacing(10);
            button.setMinWidth(100);
            String style="-fx-background-radius: 20 20 20 20; -fx-background-color:#0066CB;";
            button.setStyle(style);
            vbox.getChildren().addAll(label, button);
            Scene scene = new Scene(vbox, 800, 80);
            window.initStyle(StageStyle.UNDECORATED);
            window.setScene(scene);
            window.centerOnScreen();
            window.showAndWait();

        return IsPresed;
    }


}
