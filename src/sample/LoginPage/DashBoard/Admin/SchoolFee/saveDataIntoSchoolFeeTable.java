package sample.LoginPage.DashBoard.Admin.SchoolFee;

import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import java.io.IOException;

public class saveDataIntoSchoolFeeTable extends Thread{

    private  String clas;
    private String session;
    private String studentname;
    private String tag;
    private String entity;
    private String column;
    private String term;
    private TableColumn<?, ?> tableColumn;
    public saveDataIntoSchoolFeeTable(String clas, String session, String studentnameInTheColumn, String tag, String newvalue, String columntable, String term, TableColumn<?, ?> column) {
        this.clas=clas;
        this.session=session;
        this.studentname=studentnameInTheColumn;
        this.tag=tag;
        //This entity is the data to be saved
        this.entity=newvalue;
        //this table correspond to a table in the schoolfee table
        //it nullify the white space to avoid error while executing script
        this.column=columntable.replaceAll(" ","");
        this.term=term;
        this.tableColumn=column;
        System.out.println("[Controller]:Saving data to schoolfee table-->\r\n class: "+clas+"\r\n session: "+session+"\r\n term: "+term+"\r\n name: "+studentname+"\r\n tag: "+tag+"\r\n column: "+ this.column +"\r\n entity: "+entity);

    }

    @Override
    public void run() {
        System.out.println("[saveDataIntoSchoolFeetable]: Setting up client ");
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://localhost:8080/savedatatoschoolfeetable/"+term+"/"+studentname+"/"+clas+"/"+session+"/"+tag+"/"+column+"/"+entity)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5MzAzNjk4LCJleHAiOjE1OTk0ODM2OTh9.PhAyaBtsbOAVrBevhjAYLD3B7ZoqXYhsB_CCp_LakyA")
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[saveDataIntoSchoolFeetable]: Retrieving response ");
            System.out.println("[saveDataIntoSchoolFeetable]:"+response);
            System.out.println("[saveDataIntoSchoolFeetable]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){

            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to save data,CHECK INTERNET CONNECTION");
                    if (error){
                        tableColumn.getTableView().getItems().clear();
                        System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    tableColumn.getTableView().getItems().clear();
                    System.out.println("[saveDataIntoSchoolFeetable]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
