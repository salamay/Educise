package sample.LoginPage.DashBoard.Admin.SchoolFee;

import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    public saveDataIntoSchoolFeeTable(String studentid, String newvalue, String columntable, TableColumn<?, ?> column) {
        this.studentid=studentid;
        //This entity is the data to be saved
        this.entity=newvalue;
        //this table correspond to a table in the schoolfee table
        //it nullify the white space to avoid error while executing script
        this.column=columntable.replaceAll(" ","");
        this.tableColumn=column;
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n  studentid: "+studentid+"\r\n column: "+ this.column +"\r\n entity: "+entity);

    }

    @Override
    public void run() {
        System.out.println("[saveDataIntoSchoolFeetable]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Request request=new Request.Builder()
                .url("http://localhost:8080/savedatatoschoolfeetable/"+studentid+"/"+column+"/"+entity)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[saveDataIntoSchoolFeetable]: Retrieving response ");
            System.out.println("[saveDataIntoSchoolFeetable]:"+response);
            System.out.println("[saveDataIntoSchoolFeetable]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                response.close();
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to save data,CHECK INTERNET CONNECTION");
                    if (error){
                        tableColumn.getTableView().getItems().clear();
                        System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": unable to save school fee");
                    if (error){
                        tableColumn.getTableView().getItems().clear();
                        System.out.println("[saveDataIntoSchoolFeetable]--> unable to save school fee on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        System.out.println("[saveDataIntoSchoolFeetable]--> Connection error");
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
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    tableColumn.getTableView().getItems().clear();
                    System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error");
                }
            });
            e.printStackTrace();
        }
    }
}
