package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.DeleteSchoolFee;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeleteSchoolFee extends Thread {

    private String id;
    private TableView<Fee> tableview;
    public DeleteSchoolFee(String id, TableView<Fee> tableview) {
        this.id = id;
        this.tableview=tableview;
        System.out.println("[DeleteSchoolFee]:Saving data to schoolfee table-->\r\n id:"+id);
    }

    @Override
    public void run() {
        System.out.println("[DeleteSchoolFee]: Deleting school fee");
        System.out.println("[DeleteSchoolFee]: Setting up client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null && Configuration.port!=null){

            System.out.println("[DeleteSchoolFee]: Making request");
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/deleteschoolfee/"+id)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .delete()
                    .build();
            Response response;
            try {
                response=client.newCall(request).execute();
                if (response.code()==200){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        ObservableList<Fee> selecteditem=tableview.getSelectionModel().getSelectedItems();
                        ObservableList<Fee> all=tableview.getItems();
                        selecteditem.forEach(all::remove);
                        tableview.refresh();
                    });
                    response.close();
                    System.out.println("[DeleteSchoolFee]--> OK-->"+response.code());
                }else {
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to delete");
                        if (error){
                            tableview.getItems().clear();
                            System.out.println("[DeleteSchoolFee]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection(" Unable to delete,Check internet connection");
                    if (error){
                        tableview.getItems().clear();
                        System.out.println("[DeleteSchoolFee]--> Connection error");
                    }
                });
                e.printStackTrace();
            }
        }else{
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }

    }
}
