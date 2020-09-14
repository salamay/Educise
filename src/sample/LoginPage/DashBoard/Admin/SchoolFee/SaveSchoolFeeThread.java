package sample.LoginPage.DashBoard.Admin.SchoolFee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.RegisterationWindow;
import java.io.IOException;

//This class save the school fee data to the server
public class SaveSchoolFeeThread extends Thread {

    private Fee fee;
    private TableView<Fee> tableView;
    public SaveSchoolFeeThread(Fee fee, TableView<Fee> tableView){
        this.fee=fee;
        this.tableView=tableView;
    }

    @Override
    public void run() {
        System.out.println("[SchoolFeeThread]: Processing json body ");
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson=builder.create();
        String rawjson=gson.toJson(fee);
        System.out.println("[SchoolFeeThread]: Json boddy processed Succesfully="+rawjson);
        System.out.println("[SchoolFeeThread]: Setting up client ");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[SchoolFeeThread]: preparing Request body ");
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),rawjson);
        System.out.println("[SchoolFeeThread]: Preparing to send request ");
        Request request=new Request.Builder()
                .post(requestBody)
                .url("http://localhost:8080/saveschoolfee")
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NzI2OTQxLCJleHAiOjE1OTg5MDY5NDF9.bJd8e4fkhpmaPdRNUMNnldNdEivacdZgSpZSkzVPgPQ")
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[SchoolFeeThread]: Retrieving response ");
            System.out.println("[SchoolFeeThread]:"+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
              Platform.runLater(()->{
                  LoadingWindow.window.close();
                  ObservableList<Fee> data= FXCollections.observableArrayList();
                  data.add(fee);
                  tableView.setItems(data);
              });
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to save book");
                    if (error){
                        tableView.getItems().clear();
                        System.out.println("[SchoolFeeThread]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    tableView.getItems().clear();
                    System.out.println("[SchoolFeeThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
