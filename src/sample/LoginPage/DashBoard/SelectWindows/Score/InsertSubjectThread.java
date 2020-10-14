package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        System.out.println("[InsertSubjectThread]-->student name: "+term);
        System.out.println("[InsertSubjectThread]-->student name: "+name);


    }
    @Override
    public void run() {
        System.out.println("[InsertSubjectThread]-->Preparing to send Request");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://167.99.91.154:8080/insertsubject/"+subject+"/"+session+"/"+name+"/"+term)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[InsertSubjectThread]--> response: "+response);
            if (response.code()==200||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    Scores scores=new Scores();
                    scores.setSubject(subject);
                    scores.setTerm(term);
                    //Add the score to table row
                    tableView.getItems().add(scores);
                    response.close();
                });
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to insert subject:check field for invalid characters");
                    if (error){
                        tableView.getColumns().clear();
                        System.out.println("[InsertSubjectThread]--> server error,unable to insert subject");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check for invalid characters");
                    if (error){
                        System.out.println("[InsertSubjectThread]--> Server error ,server cannot process request");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Bad request,check field for invalid characters");
                    if (error){
                        System.out.println("[InsertSubjectThread]--> server error,bad request");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        System.out.println("Access denied");
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
