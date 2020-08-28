package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;

import java.io.IOException;

public class InsertSubjectThread extends Thread {
    private String subject;
    private String name;
    private String session;
    private TableView<Scores> tableView;
    public InsertSubjectThread(String subject, String name, String session, TableView<Scores> tableView){
        this.subject=subject;
        this.name=name;
        this.session=session;
        this.tableView=tableView;
        System.out.println("[InsertSubjectThread]-->Subject: "+subject);
        System.out.println("[InsertSubjectThread]-->session: "+session);
        System.out.println("[InsertSubjectThread]-->student name: "+name);
    }
    @Override
    public void run() {
        System.out.println("[InsertSubjectThread]-->Preparing to send Request");
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                .url("http://localhost:8080/insertsubject/"+subject+"/"+session+"/"+name)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[InsertSubjectThread]--> response: "+response);
            if (response.code()==200||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    Scores scores=new Scores();
                    scores.setSubject(subject);
                    //Add the score to table row
                    tableView.getItems().add(scores);
                });
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  get data:CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[InsertSubjectThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("[InsertsubjecThread]-->  Unable to save subject:Connection Error");
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish:CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[insertSubjectThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
