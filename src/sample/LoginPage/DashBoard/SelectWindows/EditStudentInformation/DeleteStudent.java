package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeleteStudent extends Thread {
    private String  id;
    private TableView<InformationEntity> tableView;
    public DeleteStudent(String id, TableView<InformationEntity> tableView) {
        this.id=id;
        this.tableView=tableView;
    }

    @Override
    public void run() {
        System.out.println("[DeleteStudent]: Student id:"+id);
        System.out.println("[DeleteStudent]: Setting up client ");

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/"+id)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .delete()
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[DeleteStudent]: Retrieving response ");
                System.out.println("[DeleteStudent]:"+response);
                System.out.println("[DeleteStudent]:"+response.body());
                if (response.code()==200){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        ObservableList<InformationEntity> selecteditem=tableView.getSelectionModel().getSelectedItems();
                        ObservableList<InformationEntity> allBooks=tableView.getItems();
                        selecteditem.forEach(allBooks::remove);
                        tableView.refresh();
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[DeleteStudent]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[DeleteStudent]--> Connection Error");
                    }
                });
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }

    }
}
