package sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.Configuration.Configuration;
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
        if (Configuration.ipaddress!=null&&Configuration.port!=null){

            Request request=new Request.Builder()
                    .url("http:"+Configuration.ipaddress+":"+Configuration.port+"/savestudentnametoschoolfee")
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .post(body)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[SaveSchoolFee]: Retrieving response ");
                System.out.println("[SaveSchoolFee]:"+response);
                System.out.println("[SaveSchoolFee]:"+response.body());
                if(response.code()==200){
                    String rawData=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        studentnameTextField.clear();
                        GsonBuilder builder1=new GsonBuilder();
                        builder1.serializeNulls();
                        builder1.setPrettyPrinting();
                        Gson gson1=builder1.create();
                        Fee fee=gson1.fromJson(rawData,Fee.class);
                        ObservableList<Fee> allFee,selectedFee;
                        allFee=tableview.getItems();
                        selectedFee=tableview.getSelectionModel().getSelectedItems();
                        selectedFee.forEach(allFee::remove);
                        tableview.getItems().add(fee);
                        tableview.refresh();
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[SaveSchoolFee]--> Connection Error");
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

        }else{
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
