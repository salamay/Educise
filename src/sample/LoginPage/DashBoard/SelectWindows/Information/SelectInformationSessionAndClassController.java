package sample.LoginPage.DashBoard.SelectWindows.Information;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetClassThread;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetSessionThread;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

///This serve as a controller for SelectClass.fxml ,it featches the class and session when loaded
public class SelectInformationSessionAndClassController implements Initializable {

    public JFXComboBox<String> Clas;
    public JFXComboBox<String> SessionComBoBox;
    public JFXButton continueButtonPressed;
    public VBox vbox;
    private String clas;
    private String session;
    public ProgressIndicator ProgressBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ////////////Starting to get sessions from server
        new GetSessionThread(SessionComBoBox,ProgressBar,"retrievesession").start();
        ///////////Starting to get class end
        new GetClassThread(Clas,ProgressBar,"retrieveclasses").start();
        ///This fires when the continue button is clicked,it displays a listview of student name
        continueButtonPressed.setOnAction((e)->{
            if (clas!=null&& session!=null){
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
                    String indexSelected=String.valueOf(listview.getSelectionModel().getSelectedIndex());
                    List<ListViewNames> list=ClassNameThread.list2;
                    String id=list.get(Integer.parseInt(indexSelected)).getId();
                    //This id is passed to Information window to serve as a parameter to fetch data from database
                    new InformationWindow(id);
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
                SelectInformationSesssionAndClassWindow.StudentWindow.setScene(scene);
                ///////////////////////////////////////////////////////////////////////
                System.out.println("Class: " + clas);
                System.out.println("Session: " + session);
                System.out.println("Starting to get student name for the listview");
                new ClassNameThread(clas,session, listview, SelectInformationSesssionAndClassWindow.StudentWindow).start();
            }else {
                new ConnectionError().Connection("One of the field is missing, please select an option to proceed");
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
