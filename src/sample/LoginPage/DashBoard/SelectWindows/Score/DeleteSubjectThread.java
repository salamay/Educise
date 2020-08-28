package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;

import java.io.IOException;

public class DeleteSubjectThread extends Thread {
    private String subject;
    private String name;
    private String session;
    private TableView<Scores> tableview;


    public DeleteSubjectThread(String subject, String name, String session, TableView<Scores> tableview) {
        this.subject=subject;
        this.name=name;
        this.session=session;
        this.tableview=tableview;
        System.out.println("[DeleteSubjectThread]-->Subject: "+subject);
        System.out.println("[DeleteSubjectThread]-->session: "+session);
        System.out.println("[DeleteSubjectThread]-->student name: "+name);
    }

    @Override
    public void run() {
        System.out.println("[DeleteSubjectThread]-->Preparing to send Request");
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                .url("http://localhost:8080/deletesubject/"+subject+"/"+session+"/"+name)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[InsertSubjectThread]--> response: "+response);
            if (response.code()==200||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    ObservableList<Scores> ScoreSelected,AllScore;
                    AllScore=tableview.getItems();
                    ScoreSelected=tableview.getSelectionModel().getSelectedItems();
                    ScoreSelected.forEach(AllScore::remove);
                });
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  get data:CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("[DeleteSubjectThread]-->  Unable to save subject:Connection Error");
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish:CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[DeleteSubjectThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
