package sample.SelectWindows.Information;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import sample.ConnectionError;
import sample.SqlConnection;
import sample.StudentInformation.InformationWindow;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


//This class is a contoller for SudentSelectClassInfo that when it load  get list of session from the ClassThread class
public class SelectClassController implements Initializable {

    public JFXComboBox<String> Clas;
    public VBox vbox;
    public String clas;
    public ProgressIndicator ProgressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

                 new ClassThread(Clas,ProgressBar).start();
        Clas.setOnAction((e) -> {
             clas = Clas.getSelectionModel().getSelectedItem();
            System.out.println("[ClassThread]: ClassThreadFinished");
            //Creating Window
            JFXListView<String> listview;
            ScrollPane layout = new ScrollPane();
            layout.setPannable(true);
            listview = new JFXListView<>();
            listview.getSelectionModel().selectedItemProperty().addListener((v, OldValue,NewValue)->{
                new InformationWindow(clas,NewValue);

            });
            Label label = new Label("Select Student");
            VBox box = new VBox();
            box.setAlignment(Pos.TOP_LEFT);
            box.getChildren().addAll(label, listview);
            layout.setFitToWidth(true);
            layout.setFitToWidth(true);
            layout.setContent(box);
            layout.setPadding(new Insets(10, 10, 10, 10));
            Scene scene = new Scene(layout);
            StudentSelectClassInfo.StudentWindow.setScene(scene);

///////////////////////////////////////////////////////////////////////

            System.out.println("[Class]: " + clas);

            new ClassNameThread(clas, listview).start();
            System.out.println("[ClassNamethread]: ClassThreadFinished");

        });
    }


    public class ClassThread extends Thread {
        public ComboBox<String> clas;
        ProgressIndicator pgb;

        double progress=0.0;

        public ClassThread(ComboBox<String> comb,ProgressIndicator progressBar) {
            this.clas = comb;
            this.pgb=progressBar;

        }

        @Override
        public void run() {
            ResultSet resultSet;
            Connection conn= SqlConnection.connector();
            if (conn==null){
                Platform.runLater(()->{
                    boolean result=new ConnectionError().Connection(conn);
                    if (result==true){
                        StudentSelectClassInfo.StudentWindow.close();
                    }
                });
            }else {
                String QUERY = "Select * from SessionTable where 1";
                try {
                    PreparedStatement prs = conn.prepareStatement(QUERY);
                    Platform.runLater(()->{
                        boolean result=new ConnectionError().Connection(conn);
                        if (result==true){
                            StudentSelectClassInfo.StudentWindow.close();
                        }
                    });
                    resultSet = prs.executeQuery();
                    Platform.runLater(() -> {
                        while (true) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(()->{
                                pgb.setProgress(progress);
                            });
                            progress+=1;
                            if (progress > 1) {
                                break;
                            }
                        }
                    });

                    while (resultSet.next()) {
                        ObservableList<String> list= FXCollections.observableArrayList();
                        list.addAll(resultSet.getString("sessionname"));
                        Platform.runLater(() -> {
                            System.out.println("RunLater called");
                            Clas.getItems().addAll(list);
                        });

                    }







                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }


        }

    }

}
