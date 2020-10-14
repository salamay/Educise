package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.debtors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.SchoolFeeWindowController;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class getDebtorsthread extends Thread{
    private String clas;
    private String session;
    private String tag;
    private String term;
    private int minimum;
    private TableView<Fee> tableview;
    public getDebtorsthread(String clas, String session, String tag, String term, int minimum, TableView<Fee> tableview) {
        this.clas=clas;
        this.session=session;
        this.tag=tag;
        this.term=term;
        this.minimum=minimum;
        this.tableview=tableview;
        System.out.println("[getschoolfee]: Class: "+clas);
        System.out.println("[getschoolfee]: term: "+term);
        System.out.println("[getschoolfee]: year: "+session);
        System.out.println("[getschoolfee]: minimum: "+minimum);
        System.out.println("[getschoolfee]: tag: "+tag);
    }

    @Override
    public void run() {
        System.out.print("[GetDebtorsThread]: Setting up client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.print("[GetDebtorsThread]: Setting up Request");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://167.99.91.154:8080/getdebtors/"+clas+"/"+term+"/"+session+"/"+minimum+"/"+tag)
                .build();
        try {
            System.out.print("[GetDebtorsThread]: Retrieving response");
            Response response=client.newCall(request).execute();
            System.out.print("[GetDebtorsThread]: Retrieving response-->"+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                ResponseBody responseBody =response.body();
                byte[] bytes=responseBody.bytes();
                String json=new String(bytes,"UTF-8");
                System.out.println("[GetDebtorsThread]: Processing response Body");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                //This parse the list of json to a list of  fee class with the help of type token
                //we cant specify directly to convert the json to fee because rawBody variable contains
                //a list of json
                List<Fee> schoolfees=gson.fromJson(json,new TypeToken<List<Fee>>(){}.getType());
                //Since table view accept observable list,we need to convert it to Observable list
                ObservableList<Fee> tableList= FXCollections.observableList(schoolfees);
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    tableview.setItems(tableList);
                });
                if (!schoolfees.isEmpty()){
                    //D
                    SchoolFeeWindowController.pdf=schoolfees.get(schoolfees.size()-1).getPdf();
                    System.out.println("Document:"+schoolfees.get(schoolfees.size()-1).getPdf());
                }
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get debtors");
                    if (error){
                        System.out.println("[GetDebtorsThread]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==404){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": unable to get debtors");
                    if (error){
                        System.out.println("[GetDebtorsThread]--> unable to get debtors on the server");
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
                        System.out.println("[GetDebtorsThread]--> Connection error");
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
                    System.out.println("[GetDebtorsThread]--> Connection error");
                }
            });
            e.printStackTrace();
        }
    }
}