package sample.LoginPage.DashBoard.SelectWindows.Registeration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation.InformationEntity;
import sample.LoginPage.LogInModel;

import java.io.IOException;

public class EditInformation extends Thread {

    private String newValue;
    private String oldValue;
    private String column;
    private String session;
    private String studentname;
    private TableView<InformationEntity> tableView;
    public EditInformation(String newValue, String oldValue, String column, String session, String studentname, TableView<InformationEntity> tableView) {
        this.newValue=newValue;
        this.oldValue=oldValue;
        this.column=column.replaceAll(" ","");
        this.session=session;
        this.studentname=studentname;
        this.tableView=tableView;
    }

    @Override
    public void run() {
        System.out.println("[EditInformation]: --> newValue: "+newValue);
        System.out.println("[EditInformation]: --> oldvalue: "+oldValue);
        System.out.println("[EditInformation]: --> column: "+column);
        System.out.println("[EditInformation]: --> session: "+session);
        System.out.println("[EditInformation]: --> studenname: "+studentname);
        System.out.println("[EditInformation]: Setting up client ");
        OkHttpClient client=new OkHttpClient();


        Request request=new Request.Builder()
                .url("http://localhost:8080/editstudentinformation/"+studentname+"/"+session+"/"+newValue+"/"+oldValue+"/"+column)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[EditInformation]: Retrieving response ");
            System.out.println("[EditInformation]:"+response);
            System.out.println("[EditInformation]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){

                response.close();
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to Edit student information");
                    if (error){
                        tableView.getColumns().clear();
                        System.out.println("[EditInformation]--> Connection Error");
                    }
                });
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        tableView.getColumns().clear();
                        System.out.println("[EditInformation]--> Connection error");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[EditInformation]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
