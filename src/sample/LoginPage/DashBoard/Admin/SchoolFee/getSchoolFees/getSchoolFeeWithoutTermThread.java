package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.SchoolFeeWindowController;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.RegisterationWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class getSchoolFeeWithoutTermThread extends Thread {
    private String clas;
    private String year;
    private String tag;
    private TableView<Fee> tableView;
    public getSchoolFeeWithoutTermThread(String clas, String year,String tag, TableView<Fee> tableview) {

        this.clas=clas;
        this.year=year;
        this.tableView=tableview;
        this.tag=tag;
    }

    @Override
    public void run() {
        System.out.println("[getSchoolFeeWithoutTermThread]:"+clas);
        System.out.println("[getSchoolFeeWithoutTermThread]:"+year);
        System.out.println("[getSchoolFeeWithoutTermThread]:"+tag);
        System.out.println("[getSchoolFeeWithoutTermThread]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Request request=new Request.Builder()
                .url("http://167.99.91.154:8080/getschoolfeewithoutterm/"+clas+"/"+year+"/"+tag)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[getSchoolFeeWithoutTermThread]: Retrieving response ");
            System.out.println("[getSchoolFeeWithoutTermThread]:"+response);
            System.out.println("[getSchoolFeeWithoutTermThread]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                byte [] rawbytes = response.body().bytes();
                String rawBody=new String(rawbytes,"UTF-8");
                System.out.println("[getSchoolFeeWithoutTermThread]: "+rawBody);
                System.out.println("[getSchoolFeeWithoutTermThread]: Processing response Body");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                //This parse the list of json to a list of  fee class with the help of type token
                //we cant specify directly to convert the json to fee because rawBody variable contains
                //a list of json
                List<Fee> schoolfees=gson.fromJson(rawBody,new TypeToken<List<Fee>>(){}.getType());
                //Since table view accept observable list,we need to convert it to Observable list
                ObservableList<Fee> tableList= FXCollections.observableList(schoolfees);
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    tableView.setItems(tableList);
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get School fee");
                    if (error){
                        System.out.println("[getSchoolFeeWithoutTermThread]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==404){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": School fee not found");
                    if (error){
                        tableView.getItems().clear();
                        System.out.println("[getSchoolFeeWithoutTermThread]--> school fee  not found on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request");
                    if (error){
                        System.out.println("[getSchoolFeeWithoutTermThread]--> Connection error");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        tableView.getItems().clear();
                        System.out.println("Access denied");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[getSchoolFeeWithoutTermThread]--> Connection Error");
                }
            });
            e.printStackTrace();
        }
    }
}
