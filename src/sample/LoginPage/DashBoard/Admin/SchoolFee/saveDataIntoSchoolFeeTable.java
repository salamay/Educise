package sample.LoginPage.DashBoard.Admin.SchoolFee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class saveDataIntoSchoolFeeTable extends Thread{


    private String studentid;
    private String entity;
    private String column;
    private TableColumn<?, ?> tableColumn;
    private TableView<Fee> tableview;
    public saveDataIntoSchoolFeeTable(String studentid, String newvalue, String columntable, TableColumn<?, ?> column, TableView<Fee> tableview) {
        this.studentid=studentid;
        //This entity is the data to be saved
        this.entity=newvalue;
        //this table correspond to a table in the schoolfee table
        //it nullify the white space to avoid error while executing script
        this.column=columntable.replaceAll(" ","");
        this.tableColumn=column;
        this.tableview=tableview;
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n  studentid: "+studentid+"\r\n column: "+ this.column +"\r\n entity: "+entity);

    }

    @Override
    public void run() {
        System.out.println("[saveDataIntoSchoolFeetable]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/savedatatoschoolfeetable/"+studentid+"/"+column+"/"+entity)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[saveDataIntoSchoolFeetable]: Retrieving response ");
                System.out.println("[saveDataIntoSchoolFeetable]:"+response);
                System.out.println("[saveDataIntoSchoolFeetable]:"+response.body());
                if (response.code()==200){
                    String rawData=new String(response.body().bytes(),"UTF-8");
                    GsonBuilder builder=new GsonBuilder();
                    builder.setPrettyPrinting();
                    builder.serializeNulls();
                    Gson gson=builder.create();
                    Fee fee=gson.fromJson(rawData,Fee.class);
                    ObservableList<Fee> allFee,selectedFee;
                    allFee=tableview.getItems();
                    selectedFee=tableview.getSelectionModel().getSelectedItems();
                    selectedFee.forEach(allFee::remove);
                    tableview.getItems().add(fee);
                    tableview.refresh();
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+": "+message);
                        if (error){
                            tableColumn.getTableView().getItems().clear();
                            System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        tableColumn.getTableView().getItems().clear();
                        System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error");
                    }
                });
                e.printStackTrace();
            }
        }else {
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }

    }
}
