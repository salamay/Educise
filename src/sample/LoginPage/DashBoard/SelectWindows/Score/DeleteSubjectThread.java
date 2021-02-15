package sample.LoginPage.DashBoard.SelectWindows.Score;

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

public class DeleteSubjectThread extends Thread {
    private String id;
    private TableView<Scores> tableview;

    public DeleteSubjectThread( String id, TableView<Scores> tableview) {
        this.id=id;
        this.tableview=tableview;
        System.out.println("[DeleteSubjectThread]-->id: "+id);
    }

    @Override
    public void run() {
        System.out.println("[DeleteSubjectThread]-->Preparing to send Request");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress != null && Configuration.port != null) {
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + LogInModel.token)
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/deletesubject/"+ id)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                System.out.println("[InsertSubjectThread]--> response: " + response);
                if (response.code() == 200) {
                    Platform.runLater(() -> {
                        ObservableList<Scores> ScoreSelected, AllScore;
                        AllScore = tableview.getItems();
                        ScoreSelected = tableview.getSelectionModel().getSelectedItems();
                        ScoreSelected.forEach(AllScore::remove);
                    });
                    response.close();
                } else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(() -> {
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error) {
                            System.out.println("[InsertSubjectThread]--> server error,unable to delete subject");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                System.out.println("[InsertSubjectThread]--> Unable to save subject:Connection Error");
                Platform.runLater(() -> {
                    boolean error = new ConnectionError().Connection("Unable to establish:CHECK INTERNET CONNECTION");
                    if (error) {
                        System.out.println("[InsertSubjectThread]--> Connection error,unable to insert subject");
                    }
                });
                e.printStackTrace();
            }
        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
    }
}
