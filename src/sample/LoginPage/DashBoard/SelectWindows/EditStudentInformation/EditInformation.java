package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation.InformationEntity;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EditInformation extends Thread {

    private String newValue;
    private String id;
    private String column;
    private TableView<InformationEntity> tableView;
    public EditInformation(String newValue, String id, String column, TableView<InformationEntity> tableView) {
        this.newValue=newValue;
        this.id=id;
        this.column=column.replaceAll(" ","");
        this.tableView=tableView;
    }

    @Override
    public void run() {
        System.out.println("[EditInformation]: --> newValue: "+newValue);
        System.out.println("[EditInformation]: --> Student id: "+id);
        System.out.println("[EditInformation]: --> column: "+column);
        System.out.println("[EditInformation]: Setting up client ");

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/editstudentinformation"+"/"+newValue+"/"+id+"/"+column)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[EditInformation]: Retrieving response ");
                System.out.println("[EditInformation]:"+response);
                System.out.println("[EditInformation]:"+response.body());
                if (response.code()==200){
                    byte[] bytes=response.body().bytes();
                    String rawData=new String(bytes,"UTF-8");
                    GsonBuilder builder=new GsonBuilder();
                    builder.serializeNulls();
                    builder.setPrettyPrinting();
                    Gson gson=builder.create();
                    InformationEntity informationEntity=gson.fromJson(rawData,InformationEntity.class);
                    ObservableList<InformationEntity> info= FXCollections.observableArrayList();
                    info.add(informationEntity);
                    tableView.setItems(info);
                    tableView.refresh();
                    response.close();
                }else {
                    Platform.runLater(()->{
                        String message= null;
                        try {
                            message = new String(response.body().bytes(),"UTF-8");
                            boolean error=new ConnectionError().Connection(response.code()+":"+message);
                            if (error){
                                tableView.getColumns().clear();
                                System.out.println("[EditInformation]--> Connection Error");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
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
        }else{
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }

    }
}
