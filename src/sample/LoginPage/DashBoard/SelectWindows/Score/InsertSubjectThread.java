package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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

public class InsertSubjectThread extends Thread {
    private String subject;
    private String name;
    private String session;
    private TableView<Scores> tableView;
    private String term;
    public InsertSubjectThread(String subject, String name, String session, TableView<Scores> tableView, String term){
        this.subject=subject;
        this.name=name;
        this.session=session;
        this.tableView=tableView;
        this.term=term;
        System.out.println("[InsertSubjectThread]-->Subject: "+subject);
        System.out.println("[InsertSubjectThread]-->session: "+session);
        System.out.println("[InsertSubjectThread]-->term: "+term);
        System.out.println("[InsertSubjectThread]-->student name: "+name);
    }
    @Override
    public void run() {
        System.out.println("[InsertSubjectThread]-->Preparing to send Request");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null&&Configuration.port!=null){

        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/insertsubject/"+subject+"/"+session+"/"+name+"/"+term)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[InsertSubjectThread]--> response: "+response);
            if (response.code()==200){
                Platform.runLater(()->{
                    try {
                        byte[] bytes=response.body().bytes();
                        String rawData=new String(bytes,"UTF-8");
                        //Processing gson response to the table
                        GsonBuilder builder=new GsonBuilder();
                        builder.serializeNulls();
                        builder.setPrettyPrinting();
                        Gson gson=builder.create();
                        Scores scores=gson.fromJson(rawData,Scores.class);
                        tableView.getItems().add(scores);
                        tableView.refresh();
                        System.out.println(scores.getSubject());
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.close();
                    }
                });
            }else {
                String message=new String(response.body().bytes(),"UTF-8");
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection(response.code()+":"+message);
                    if (error){
                        System.out.println("[InsertSubjectThread]--> server error,unable to insert subject");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            System.out.println("[InsertsubjecThread]-->  Unable to save subject:Connection Error");
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection:CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[insertSubjectThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
