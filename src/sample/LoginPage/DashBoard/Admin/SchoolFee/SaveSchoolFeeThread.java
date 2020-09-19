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
import sample.LoginPage.LogInModel;

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
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[SchoolFeeThread]: Retrieving response ");
            System.out.println("[SchoolFeeThread]:"+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
              Platform.runLater(()->{
                  LoadingWindow.window.close();
                  ObservableList<Fee> data= tableView.getItems();
                  data.add(fee);
                  tableView.setItems(data);
                  response.close();
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
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": unable to save school fee");
                    if (error){
                        System.out.println("[SchoolFeeThread]--> unable to save school fee on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        System.out.println("[SchoolFeeThread]--> Connection error");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    tableView.getItems().clear();
                    System.out.println("[SchoolFeeThread]--> Connection error");
                }
            });
            e.printStackTrace();
        }
    }
}
