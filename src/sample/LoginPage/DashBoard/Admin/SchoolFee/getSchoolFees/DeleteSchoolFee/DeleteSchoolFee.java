package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.DeleteSchoolFee;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DeleteSchoolFee extends Thread {

    private String id;
    TableView<Fee> tableview;
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

        System.out.println("[DeleteSchoolFee]: Making request");
        Request request=new Request.Builder()
                .url("http://localhost:8080/deleteschoolfee/"+id)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .delete()
                .build();
        Response response;
        try {
            response=client.newCall(request).execute();
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
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
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": unable to delete school fee");
                    if (error){
                        System.out.println("[DeleteSchoolFee]--> unable to delete school fee on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        System.out.println("[DeleteSchoolFee]--> Connection error");
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
    }
}
