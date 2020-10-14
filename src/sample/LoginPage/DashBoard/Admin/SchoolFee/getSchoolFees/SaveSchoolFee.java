package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

//This class save student to school fees table
public class SaveSchoolFee extends Thread {
   private String clas;
   private String session;
   private String tag;
   private String studentname;
   private String term;
   private TableView<Fee> tableview;
   private JFXTextField studentnameTextField;
    public SaveSchoolFee(String clas, String session, String tag, String studentname, String term, TableView<Fee> tableview, JFXTextField studentnameTextField) {
        this.clas=clas;
        this.session=session;
        this.tag=tag;
        this.studentname=studentname;
        this.term=term;
        this.tableview=tableview;
        this.studentnameTextField=studentnameTextField;
    }

    @Override
    public void run() {
        System.out.println("[SaveSchoolFee]: Student name:"+studentname);
        System.out.println("[SaveSchoolFee]: class:"+clas);
        System.out.println("[SaveSchoolFee]: session:"+session);
        System.out.println("[SaveSchoolFee]: tag:"+tag);
        System.out.println("[SaveSchoolFee]: term:"+term);
        SaveSchoolFeeRequestEntity schoolFeeRequestEntity=new SaveSchoolFeeRequestEntity();
        schoolFeeRequestEntity.setStudentname(studentname);
        schoolFeeRequestEntity.setClas(clas);
        schoolFeeRequestEntity.setYear(session);
        schoolFeeRequestEntity.setTag(tag);
        schoolFeeRequestEntity.setTerm(term);
        System.out.println("[SaveSchoolFee]: Setting up client ");

        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.println("[SaveSchoolFee]: Preparing JSON Body ");
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson=builder.create();
        String json=gson.toJson(schoolFeeRequestEntity);
        System.out.println(json);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://167.99.91.154:8080/savestudentnametoschoolfee")
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .post(body)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[SaveSchoolFee]: Retrieving response ");
            System.out.println("[SaveSchoolFee]:"+response);
            System.out.println("[SaveSchoolFee]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    studentnameTextField.clear();
                    Fee fee=new Fee();
                    fee.setTerm(term);
                    fee.setStudentname(studentname);
                    fee.setYear(session);
                    fee.setTag(tag);
                    fee.setClas(clas);
                    tableview.getItems().add(fee);
                    tableview.refresh();
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to SaveSchoolFee term,CHECK INTERNET CONNECTION");
                    if (error){

                        System.out.println("[SaveSchoolFee]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": unable to insert term");
                    if (error){
                        System.out.println("[SaveSchoolFee]--> school fee  not found on the server");
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
                        System.out.println("[SaveSchoolFee]--> Connection error");
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

                    System.out.println("[SaveSchoolFee]--> Connection Error");
                }
            });
            e.printStackTrace();
        }
    }
}
