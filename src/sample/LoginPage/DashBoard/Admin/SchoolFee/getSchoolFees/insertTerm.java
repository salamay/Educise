package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.SchoolFeeWindowController;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class insertTerm extends Thread{
    private String studentid;
    private String term;
    private TableColumn.CellEditEvent<Fee, String> ce;
    private TableColumn<Fee, String> termcolumn;

    public insertTerm(String studentid, String term,TableColumn.CellEditEvent<Fee, String> ce, TableColumn<Fee, String> termcolumn) {
        this.studentid=studentid;
        this.term=term;
        this.ce=ce;
        this.termcolumn=termcolumn;
    }

    @Override
    public void run() {
        System.out.println("[InsertTerm]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/saveterm/"+term+"/"+studentid)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[InsertTerm]: Retrieving response ");
                System.out.println("[InsertTerm]:"+response);
                System.out.println("[InsertTerm]:"+response.body());
                if (response.code()==200){
                    String rawData=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        GsonBuilder builder=new GsonBuilder();
                        builder.setPrettyPrinting();
                        builder.serializeNulls();
                        Gson gson=builder.create();
                        Fee fee=gson.fromJson(rawData,Fee.class);
                        ObservableList<Fee> allFee,selectedFee;
                        allFee=ce.getTableView().getItems();
                        selectedFee=ce.getTableView().getSelectionModel().getSelectedItems();
                        selectedFee.forEach(allFee::remove);
                        ce.getTableView().getItems().add(fee);
                        ce.getTableView().refresh();
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+": "+message);
                        if (error){
                            ce.getRowValue().setTerm(null);
                            ce.getTableView().refresh();
                            System.out.println("[InsertTerm]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        ce.getRowValue().setTerm(null);
                        ce.getTableView().refresh();
                        System.out.println("[InsertTerm]--> Connection Error");
                    }
                });
                e.printStackTrace();
            }
        }else{
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
