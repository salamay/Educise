package sample.LoginPage.DashBoard.AreYouSure;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class AreYouSureWindow {
    public static Stage window;
    private boolean isPressed;
    public boolean LoadScreen(String message) throws IOException {
        window=new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        VBox vBox=new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Label label=new Label(message);
        label.setWrapText(true);
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(20);
        JFXButton yesbutton=new JFXButton("Yes");
        JFXButton nobutton=new JFXButton("No");
        String style="-fx-background-radius: 20 20 20 20; -fx-background-color:#0066CB;";
        yesbutton.setStyle(style);
        nobutton.setStyle(style);
        yesbutton.setMinWidth(100);
        nobutton.setMinWidth(100);
        yesbutton.setOnAction((e)->{
            isPressed=true;
            window.close();
        });
        nobutton.setOnAction((event -> {
            isPressed=false;
            window.close();
        }));
        hBox.getChildren().addAll(yesbutton,nobutton);
        vBox.getChildren().addAll(label,hBox);
        Scene scene=new Scene(vBox,300,100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        return isPressed;
    }
}
