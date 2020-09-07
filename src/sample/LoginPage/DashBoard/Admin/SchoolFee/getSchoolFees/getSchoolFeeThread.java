package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.RegisterationWindow;
import java.io.IOException;
import java.util.List;

public class getSchoolFeeThread extends Thread {
    private String clas;
    private String term;
    private String year;
    private TableView<Fee> tableView;
    public getSchoolFeeThread(String clas, String term, String year, TableView<Fee> tableview) {
        this.clas=clas;
        this.term=term;
        this.year=year;
        this.tableView=tableview;
        System.out.println("[getschoolfee]: Class: "+clas);
        System.out.println("[getschoolfee]: term: "+term);
        System.out.println("[getschoolfee]: year: "+year);
    }

    @Override
    public void run() {

        System.out.println("[SchoolFeeThread]: Setting up client ");
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://localhost:8080/getschoolfee/"+clas+"/"+term+"/"+year)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4OTU1ODM2LCJleHAiOjE1OTkxMzU4MzZ9.VhM5uk9VMifPBoFz0yhZGGzYP3CTN4lNbMeJiJ2PVAM")
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[SchoolFeeThread]: Retrieving response ");
            System.out.println("[SchoolFeeThread]:"+response);
            System.out.println("[SchoolFeeThread]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                byte [] rawbytes = response.body().bytes();
                String rawBody=new String(rawbytes,"UTF-8");
                System.out.println("[SchoolFeeThread]: "+rawBody);
                System.out.println("[SchoolFeeThread]: Processing response Body");
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
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get School fee");
                    if (error){
                        System.out.println("[SchoolFeeThread]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[SchoolFeeThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
