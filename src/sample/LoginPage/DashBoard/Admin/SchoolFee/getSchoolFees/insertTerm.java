package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.SchoolFeeWindowController;


import java.io.IOException;
import java.util.List;

public class insertTerm extends Thread{
    private String studentname;
    private String session;
    private String clas;
    private String tag;
    private String term;
    private TableColumn.CellEditEvent<Fee, String> ce;
    private TableColumn<Fee, String> termcolumn;
    public insertTerm(String studentnameInTheColumn, String session, String clas, String tag, String term, TableColumn.CellEditEvent<Fee, String> ce, TableColumn<Fee, String> termcolumn) {
        this.studentname=studentnameInTheColumn;
        this.session=session;
        this.clas=clas;
        this.tag=tag;
        this.term=term;
        this.ce=ce;
        this.termcolumn=termcolumn;
    }
    @Override
    public void run() {

        System.out.println("[InsertTerm]: Setting up client ");
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://localhost:8080/saveterm/"+term+"/"+studentname+"/"+clas+"/"+session+"/"+tag)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5Nzk5OTY2LCJleHAiOjE2MDAxNTk5NjZ9.qwompSN9WRoyHTixemTubuVvPGZL9iN07ER0jpY-Ikc")
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[InsertTerm]: Retrieving response ");
            System.out.println("[InsertTerm]:"+response);
            System.out.println("[InsertTerm]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    //set the term column to new value
                    ce.getRowValue().setTerm(term);
                });
            }else {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to insert term,CHECK INTERNET CONNECTION");
                    if (error){
                        ce.getRowValue().setTerm(null);
                        ce.getTableView().refresh();
                        System.out.println("[InsertTerm]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    ce.getRowValue().setTerm(null);
                    ce.getTableView().refresh();
                    System.out.println("[InsertTerm]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
