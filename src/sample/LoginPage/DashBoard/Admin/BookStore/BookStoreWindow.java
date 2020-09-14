package sample.LoginPage.DashBoard.Admin.BookStore;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BookStoreWindow {
    public BookStoreWindow() throws IOException {
        Stage window=new Stage();
        window.setTitle("BOOK STORE");
        Parent root= FXMLLoader.load(getClass().getResource("bookstore.fxml"));
        Scene scene=new Scene(root);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMaximized(true);
        window.setResizable(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();
    }
}
