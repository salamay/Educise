package sample.SelectWindows.Parent;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnectionError;
import sample.SqlConnection;
import ui.RingProgressIndicator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

//This class displays a window to select session and make the list view after a session has been clicked and also display
// the selected student parent information in a window,this happen on two window by setting the window scene to different seen
public class SelectParent {

    public static Stage window1;
    public JFXComboBox<String> Clas;
    public static String classSelected;
    private ComboBox<String> ParentSearch;
    private ProgressIndicator progressBar;
    public static String parentname;

    public SelectParent() throws IOException {

        /////////////////////THIS DISPLAYS THE WINDOW////////////////////////////
        window1=new Stage();
        Stage window=new Stage();
        window.setTitle("Select Academic Session");
        VBox classvvox=new VBox();
        String Style="-fx-background-image: url('image/avel-chuklanov-5iseEuoW7mw-unsplash.jpg');-fx-background-size:100%, 100%;";
        classvvox.setStyle(Style);
        classvvox.setMinHeight(600);
        classvvox.setMinWidth(600);
        classvvox.setAlignment(Pos.CENTER);
        VBox CenterBox=new VBox();
        CenterBox.setAlignment(Pos.CENTER);
        CenterBox.setSpacing(10);
        CenterBox.setPadding(new Insets(10,10,10,10));
        classvvox.getChildren().add(CenterBox);
         Clas=new JFXComboBox<>();
         Clas.setMinWidth(100);
         progressBar=new ProgressIndicator();
        progressBar.isIndeterminate();
        CenterBox.getChildren().addAll(progressBar,Clas);

        window1.setTitle("Hello World");
        Scene scene=new Scene(classvvox);
        window1.setScene(scene);
        window1.setMaximized(true);
        window1.setResizable(true);
        window1.show();
///////////////////////////////////Displays the window end///////////////////////////

/////////////////////This get all the session from the database and add to a combobox//////////////
        new ClassThread(Clas).start();
 //////////////////////////////////////////////////////////////////////////////////////////////
        Clas.setOnAction(event -> {
            classSelected = Clas.getSelectionModel().getSelectedItem();
            Stage window2 = new Stage();
            window2.setTitle("Select Parent");
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setMinHeight(1200);
            vBox.setMinWidth(800);
            ParentSearch = new ComboBox<>();
            ParentSearch.setEditable(true);
            ParentSearch.setMinWidth(Control.USE_COMPUTED_SIZE);
            HBox hBox = new HBox();
            Label Label = new Label("Search parent name");
            hBox.getChildren().addAll(Label, ParentSearch);
            ListView<String> Parentlist = new ListView<>();

/////////////////  parent name list view On Mouse Clicked//////////////
            //the newValue variable is a static global variable that will be later reference in the studentParentController class
            //to get the value of the class selected
            Parentlist.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                parentname=newValue;
                Parent root= null;
                try {
                    root = FXMLLoader.load(getClass().getResource("studentparent.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene parentscene = new Scene(root,800,500);
                window2.setScene(parentscene);

            });
   ////////////////////////////////////////   parent name list view On Mouse Clicked end//////////////////////////////////

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);
            scrollPane.setFitToHeight(true);
            Parentlist.setMinHeight(Control.USE_COMPUTED_SIZE);
            vBox.getChildren().addAll(hBox, Parentlist);
            scrollPane.setContent(vBox);
            Scene scene2 = new Scene(scrollPane);
            window2.setMaximized(true);
            window2.initModality(Modality.APPLICATION_MODAL);
            window2.setScene(scene2);
            window2.centerOnScreen();
            window2.show();
            //passing the class selected as an argument to the ParentList Class
            new ParentListhread(classSelected, Parentlist).start();
            ParentSearch.setOnAction(event2 -> System.out.println("Search"));
        });

    }
    ///////////////////////This class get the Sessions////////////////////////////
    private class ClassThread extends Thread {
        private ComboBox<String> clas;
        private Connection conn;


        public ClassThread(ComboBox<String> comb) {
            this.clas = comb;

        }

        @Override
        public void run() {
            conn = SqlConnection.connector();
            if (conn != null) {
                ResultSet resultSet;
                String QUERY = "Select * from SessionTable";
                try {
                    PreparedStatement prs = conn.prepareStatement(QUERY);
                        resultSet = prs.executeQuery();
                    if (conn.isClosed()){
                        new ConnectionError().Connection(conn);
                    }
                    while (resultSet.next()) {
                        ObservableList<String> list = FXCollections.observableArrayList();

                        list.addAll(resultSet.getString("sessionname"));
                        Platform.runLater(() -> Clas.getItems().addAll(list));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                new ConnectionError().Connection(conn);

            }


        }

    }

    //this class get all the parent from the session selected
    private static class ParentListhread extends Thread {
        private String classSelected;
        private ListView<String> parentListView;

        private ParentListhread(String ClassSelected, ListView<String> parentlist) {
            this.classSelected = ClassSelected;
            this.parentListView = parentlist;
        }

        @Override
        public void run() {
            Connection conn = SqlConnection.connector();
            if (conn != null) {
                String Query = "Select Fathername,Mothername from " + classSelected;
                ResultSet resultSet;
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement(Query);
                    resultSet = preparedStatement.executeQuery();
                    System.out.println("[parentListThread]: Query executed");
                    while (resultSet.next()) {
                        System.out.println("[parentListThread]: getting Result.....");
                        ObservableList<String> list = FXCollections.observableArrayList();
                        list.addAll(resultSet.getString("Fathername"));
                        list.addAll(resultSet.getString("Mothername"));
                        Platform.runLater(() -> parentListView.getItems().addAll(list));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                new ConnectionError().Connection(conn);
            }

        }
    }
    ///////////////////////////////////////////get Session End /////////////////////////////////////////////


}
