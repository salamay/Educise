package sample.LoginPage.DashBoard.SelectWindows.AcademicSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;

import java.io.IOException;
import java.sql.Connection;

public class CreatingSessionThread  extends Thread{
    private String jss1sessionName;
    private String jss2sessionName;
    private  String jss3sessionName;
    private  String ss1sessionName;
    private String ss2sessionName;
    private String ss3sessionName;
    private Connection conn;
    private JFXSpinner LoadingSpinner;
    double counter=0.0;
    public CreatingSessionThread(String jss1Name, String jss2Name, String jss3Name, String ss1Name, String ss2Name, String
            ss3Name, JFXSpinner bar){
        this.jss1sessionName=jss1Name;
        this.jss2sessionName=jss2Name;
        this.jss3sessionName=jss3Name;
        this.ss1sessionName=ss1Name;
        this.ss2sessionName=ss2Name;
        this.ss3sessionName=ss3Name;
        this.LoadingSpinner=bar;
        System.out.println("[CreatingSessionThread]"+jss1sessionName);
        System.out.println("[CreatingSessionThread]"+jss2sessionName);
        System.out.println("[CreatingSessionThread]"+jss3sessionName);
        System.out.println("[CreatingSessionThread]"+ss1sessionName);
        System.out.println("[CreatingSessionThread]"+ss2sessionName);
        System.out.println("[CreatingSessionThread]"+ss3sessionName);
    }
    @Override
    public void run() {
        CreateSessionRequestEntity createSessionRequestEntity=new CreateSessionRequestEntity();
        createSessionRequestEntity.setJss1(jss1sessionName);
        createSessionRequestEntity.setJss2(jss2sessionName);
        createSessionRequestEntity.setJss3(jss3sessionName);
        createSessionRequestEntity.setSs1(ss1sessionName);
        createSessionRequestEntity.setSs2(ss2sessionName);
        createSessionRequestEntity.setSs3(ss3sessionName);
        System.out.println("[CreatingSessionThread]: Preparing JSON body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String rawjson=gson.toJson(createSessionRequestEntity);
        System.out.println("[CreatingSessionThread]: Setting up client");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[CreatingSessionThread]: Preparing Request Body");
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),rawjson);
        System.out.println("[CreatingSessionThread]: Sending Request");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4OTU1ODM2LCJleHAiOjE1OTkxMzU4MzZ9.VhM5uk9VMifPBoFz0yhZGGzYP3CTN4lNbMeJiJ2PVAM")
                .url("http://localhost:8080/createsession")
                .post(requestBody)
                .build();
        try {
            Response response=client.newCall(request).execute();
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){

            }else{
                System.out.println("[CreatingSessionThread]--> server return error "+response.code()+": Unable to get score");
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to update score");
                    if (error){
                        System.out.println("[CreatingSessionThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[CreatingSessionThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}

