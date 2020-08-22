package sample.SelectWindows.Information;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.StudentInformation.InformationWindow;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


//This class is a contoller for SudentSelectClassInfo that when it load  get list of session from the ClassThread class
public class SelectClassController implements Initializable {

    public JFXComboBox<String> Clas;
    public VBox vbox;
    public String clas;
    public ProgressIndicator ProgressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            ////////////Starting to get sessions from server
                 new ClassThread(Clas,ProgressBar).start();
                 ///////////Starting to get sessions end
        Clas.setOnAction((e) -> {
            //on session selected ,this displays a listview
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
            ////Starting to get name from the session selected table ////////
            new ClassNameThread(clas, listview).start();
            ///Sarting to get name from session selected end/////
            System.out.println("[ClassNamethread]: ClassThreadFinished");
        });
    }


    public class ClassThread extends Thread {
        private ComboBox<String> clas;
        private ProgressIndicator pgb;

        double progress=0.0;

        public ClassThread(ComboBox<String> comb,ProgressIndicator progressBar) {
            this.clas = comb;
            this.pgb=progressBar;

        }

        @Override
        public void run() {
            System.out.println("[Retrieving session]: setting up okhttp client");
            OkHttpClient client=new OkHttpClient();

            System.out.println("[Retrieving session]: setting up okhttp client request");
            Request request=new Request.Builder()
                    .url("http://localhost:8080/retrieveinformationsession")
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4MDc2MzgyLCJleHAiOjE1OTgxMTIzODJ9.OJrRife0Z7GLD-kg-GR2qmkLBLaSNhom0gHFXaHFDV8")
                    .build();
            Response response= null;
            try {
                response = client.newCall(request).execute();
                System.out.println("[Retrieving session]: "+response);
            } catch (IOException e) {
                System.out.println("[Retrieving session]: Unable to get session information from server");
                e.printStackTrace();
            }
            if (response.code()==200|| response.code()==212){

                    System.out.println("[Retrieving session]: session retrieved");
                    ResponseBody body=response.body();
                try {
                    byte [] bytes=body.bytes();
                    //removing bracket from response
                    String data=new String(bytes,"UTF-8");
                    String data2=data.replace(']',' ');
                    String data3=data2.replace('[',' ');
                    List<String> list=Arrays.stream(data3.split(",")).collect(Collectors.toList());

                    Platform.runLater(()->{
                        Clas.getItems().addAll(list);
                    });
                    System.out.println(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

}
