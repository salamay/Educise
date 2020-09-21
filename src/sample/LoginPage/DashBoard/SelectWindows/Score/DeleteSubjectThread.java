package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

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
                .addHeader("Authorization","Bearer "+ LogInModel.token)
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
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to delete subject");
                    if (error){
                        System.out.println("[InsertSubjectThread]--> server error,unable to delete subject");
                    }
                });
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
        } catch (IOException e) {
            System.out.println("[InsertSubjectThread]--> Unable to save subject:Connection Error");
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish:CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[InsertSubjectThread]--> Connection error,unable to insert subject");
                }
            });
            e.printStackTrace();
        }
    }
}
