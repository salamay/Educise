package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Information.ClassNameThread;
import sample.LoginPage.DashBoard.SelectWindows.Information.ListViewNames;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionAndClassWindow;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetClassThread;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetSessionThread;
import sample.LoginPage.LogInModel;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EditStudentInformationController implements Initializable {
    public ProgressIndicator ProgressBar;
    public JFXComboBox<String> Clas;
    public JFXComboBox<String> SessionComBoBox;
    public JFXButton continueButtonPressed;
    private String clas;
    private String session;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ////////////Starting to get sessions from server
        new GetSessionThread(SessionComBoBox,ProgressBar,"retrievesession").start();
        ///////////Starting to get class end
        new GetClassThread(Clas,ProgressBar,"retrieveclasses").start();
        ///This fires when the continue button is clicked,it displays a listview of student name
        continueButtonPressed.setOnAction((e)->{
            if (clas!=null && session!=null){
                //Creating Window
                JFXListView<String> listview;
                ScrollPane layout = new ScrollPane();
                layout.setPannable(true);
                layout.setFitToHeight(true);
                layout.setFitToWidth(true);

                listview = new JFXListView<>();
                listview.setExpanded(true);
                listview.setMinHeight(600);
                listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                listview.isExpanded();
                listview.getSelectionModel().selectedItemProperty().addListener((v, OldValue, NewValue) -> {
                    EditstudentInformationWindow.StudentWindow.close();
                    //This load the exact edition window layout whivh is hard coded
                    String indexSelected=String.valueOf(listview.getSelectionModel().getSelectedIndex());
                    List<ListViewNames> list=ClassNameThread.list2;
                    String id=list.get(Integer.parseInt(indexSelected)).getId();
                    //This id is passed to Information window to serve as a parameter to fetch data from database
                    new EditStudentInformationLayoutWindow().loadWindow(id);
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
                EditstudentInformationWindow.StudentWindow.setScene(scene);
                ///////////////////////////////////////////////////////////////////////
                System.out.println("[Class]: " + clas);
                ////Starting to get name from the session selected table ////////
                new ClassNameThread(clas,session,listview, SelectInformationSesssionAndClassWindow.StudentWindow).start();
                ///Starting to get name from session selected end/////
                System.out.println("[EditStudentInformation]: ClassThreadFinished");
            }
        });
        Clas.setOnAction((e) -> {
            clas = Clas.getSelectionModel().getSelectedItem();
        });
        SessionComBoBox.setOnAction((e) -> {
            session = SessionComBoBox.getSelectionModel().getSelectedItem();
        });
    }


}
