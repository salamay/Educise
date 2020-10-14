package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

//This class create a window to select session
public class StudentSelectAssessmentSessionWindow {
    static Stage window;
    public StudentSelectAssessmentSessionWindow() throws IOException {
        window=new Stage();
        window.setTitle("Select Academic Session");
        Parent root= FXMLLoader.load(getClass().getResource("SelectAssessmentAcademicSession.fxml"));
        Scene scene=new Scene(root);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setResizable(true);
        window.setMaximized(true);
        window.isMaximized();
        window.centerOnScreen();
        window.getIcons().add(new Image("image/window_icon.png"));
        window.setMinHeight(700);
        window.setMinWidth(1200);
        window.show();

    }


}
