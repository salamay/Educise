package sample.LoginPage.DashBoard.SelectWindows.Information;

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
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


//This class is a contoller for SudentSelectClassInfo that when it load  get list of session from the ClassThread class
public class SelectInformationSessionController implements Initializable {

    public JFXComboBox<String> Clas;
    public VBox vbox;
    public String clas;
    public ProgressIndicator ProgressBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ////////////Starting to get sessions from server
        new ClassThread(Clas, ProgressBar).start();
        ///////////Starting to get sessions end
        Clas.setOnAction((e) -> {
            //on session selected ,this displays a listview
            clas = Clas.getSelectionModel().getSelectedItem();
            System.out.println("[ClassThread]: ClassThreadFinished");
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
                new InformationWindow(clas, NewValue);
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
            SelectInformationSesssionWindow.StudentWindow.setScene(scene);
            ///////////////////////////////////////////////////////////////////////
            System.out.println("[Class]: " + clas);
            ////Starting to get name from the session selected table ////////
            new ClassNameThread(clas, listview, SelectInformationSesssionWindow.StudentWindow).start();
            ///Starting to get name from session selected end/////
            System.out.println("[ClassNamethread]: ClassThreadFinished");
        });
    }

    //////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
    //the progressbar indicate the progress
    public class ClassThread extends Thread {
        private ComboBox<String> clas;
        private ProgressIndicator pgb;

        public ClassThread(ComboBox<String> comb, ProgressIndicator progressBar) {
            this.clas = comb;
            this.pgb = progressBar;

        }

        @Override
        public void run() {

            System.out.println("[ClassThread]: setting up okhttp client");
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            System.out.println("[ClassThread]: setting up okhttp client request");
            Request request = new Request.Builder()
                    .url("http://167.99.91.154:8080/retrieveinformationsession")
                    .addHeader("Authorization", "Bearer " + LogInModel.token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println("[ClassThread]: " + response);
                if (response.code() == 200 || response.code() == 212 || response.code() == 201) {

                    System.out.println("[ClassThread]: session retrieved");
                    ResponseBody body = response.body();
                    try {
                        byte[] bytes = body.bytes();
                        //removing bracket from response
                        String data = new String(bytes, "UTF-8");
                        String data2 = data.replace(']', ' ');
                        String data3 = data2.replace('[', ' ');
                        String data4 = data3.replaceAll(" ", "");
                        List<String> list = Arrays.stream(data4.split(",")).collect(Collectors.toList());

                        Platform.runLater(() -> {
                            Clas.getItems().addAll(list);
                        });
                        System.out.println(data);

                            Platform.runLater(() -> {
                                pgb.setProgress(1);
                            });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //Display an Alert dialog
                    Platform.runLater(() -> {
                        boolean error = new ConnectionError().Connection("server:error " + response.code() + " Unable to get session,CHECK INTERNET CONNECTION");
                        if (error) {
                            SelectInformationSesssionWindow.StudentWindow.close();
                            System.out.println("[ClassThread]--> server error,unable to get session");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(() -> {
                    boolean error = new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error) {
                        SelectInformationSesssionWindow.StudentWindow.close();
                        System.out.println("[ClassThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[ClassThread]: Unable to get session information from server");
                e.printStackTrace();
            }

        }

    }

}
