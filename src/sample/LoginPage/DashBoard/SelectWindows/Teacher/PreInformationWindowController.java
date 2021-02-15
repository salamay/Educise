package sample.LoginPage.DashBoard.SelectWindows.Teacher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.DashBoard.SelectWindows.Score.ScoreRetrievedJSONentity;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class PreInformationWindowController implements Initializable {
    public JFXListView<String> teacheListView;
    public static List<TeacherNames> teacherNames;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teacheListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        new GetTeacherNamesThread().start();
        teacheListView.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->{
            int id=teacheListView.getSelectionModel().getSelectedIndex();
            try {
                new retrieveTeacherWindow(teacherNames.get(id).getTeacherid());
                PreInformationWindow.window.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

public class GetTeacherNamesThread extends Thread{
    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/retrieveTeacherNames")
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println(response);
                if (response.code()==200){
                    String rawData=new String(response.body().bytes(),"UTF-8");
                    GsonBuilder builder=new GsonBuilder();
                    builder.setPrettyPrinting();
                    builder.serializeNulls();
                    Gson gson=builder.create();
                    teacherNames=gson.fromJson(rawData, new TypeToken<List<TeacherNames>>(){}.getType());
                    ObservableList<TeacherNames> nameList= FXCollections.observableList(teacherNames);
                    for (TeacherNames name: nameList){
                        teacheListView.getItems().add(name.getFirstname()+" "+name.getMiddlename()+" "+name.getLastname());
                    }
                }else{
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[NewTeacherController]--> Connection Error,Window close");
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
      }
    }
}

