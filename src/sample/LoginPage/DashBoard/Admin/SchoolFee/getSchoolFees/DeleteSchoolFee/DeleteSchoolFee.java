package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.DeleteSchoolFee;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;

public class DeleteSchoolFee extends Thread {

    private String clas;
    private String session;
    private String term;
    private String studentname;
    TableView<Fee> tableview;
    public DeleteSchoolFee(String clas, String session, String term, String studentname, TableView<Fee> tableview) {
        this.clas = clas;
        this.session = session;
        this.term = term;
        this.studentname=studentname;
        this.tableview=tableview;
        System.out.println("[DeleteSchoolFee]:Saving data to schoolfee table-->\r\n class: "+clas+"\r\n session: "+session+"\r\n term: "+term+"\r\n name: "+studentname);
    }

    @Override
    public void run() {
        System.out.println("[DeleteSchoolFee]: Deleting school fee");
        System.out.println("[DeleteSchoolFee]: Setting up client");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[DeleteSchoolFee]: Making request");
        Request request=new Request.Builder()
                .url("http://localhost:8080/deleteschoolfee/"+clas+"/"+session+"/"+term+"/"+studentname)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5MzAzNjk4LCJleHAiOjE1OTk0ODM2OTh9.PhAyaBtsbOAVrBevhjAYLD3B7ZoqXYhsB_CCp_LakyA")
                .delete()
                .build();
        Response response;
        try {
            response=client.newCall(request).execute();
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    Fee fee=tableview.getSelectionModel().getSelectedItem();
                    fee.setAmount("0");
                    fee.setModeofpayment(null);
                    fee.setDate(null);
                    fee.setDepositorname(null);
                    fee.setTerm(null);
                    tableview.refresh();
                });
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
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection(" Unable to delete,Check internet connection");
                if (error){
                    tableview.getItems().clear();
                    System.out.println("[DeleteSchoolFee]--> Connection Error");
                }
            });
            e.printStackTrace();
        }

    }
}
