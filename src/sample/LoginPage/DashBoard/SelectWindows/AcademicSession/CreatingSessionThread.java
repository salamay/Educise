package sample.LoginPage.DashBoard.SelectWindows.AcademicSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.sql.Connection;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CreatingSessionThread  extends Thread{
    private String jss1sessionName;
    private String jss2sessionName;
    private  String jss3sessionName;
    private  String ss1sessionName;
    private String ss2sessionName;
    private String ss3sessionName;
    private String pr1sessionName;
    private String pr2sessionName;
    private  String pr3sessionName;
    private String pr4sessionName;
    private String pr5sessionName;
    private String nur1sessionName;
    private String nur2sessionName;
    public CreatingSessionThread(String jss1Name, String jss2Name, String jss3Name, String ss1Name, String ss2Name, String ss3Name, String pr1sessionName, String pr2sessionName, String pr3sessionName, String pr4sessionName, String pr5sessionName, String nur1sessionName, String nur2sessionName){
        this.jss1sessionName=jss1Name;
        this.jss2sessionName=jss2Name;
        this.jss3sessionName=jss3Name;
        this.ss1sessionName=ss1Name;
        this.ss2sessionName=ss2Name;
        this.ss3sessionName=ss3Name;
        this.pr1sessionName=pr1sessionName;
        this.pr2sessionName=pr2sessionName;
        this.pr3sessionName=pr3sessionName;
        this.pr4sessionName=pr4sessionName;
        this.pr5sessionName=pr5sessionName;
        this.nur1sessionName=nur1sessionName;
        this.nur2sessionName=nur2sessionName;
        System.out.println("[CreatingSessionThread]"+jss1sessionName);
        System.out.println("[CreatingSessionThread]"+jss2sessionName);
        System.out.println("[CreatingSessionThread]"+jss3sessionName);
        System.out.println("[CreatingSessionThread]"+ss1sessionName);
        System.out.println("[CreatingSessionThread]"+ss2sessionName);
        System.out.println("[CreatingSessionThread]"+ss3sessionName);
        System.out.println("[CreatingSessionThread]"+this.pr1sessionName);
        System.out.println("[CreatingSessionThread]"+this.pr2sessionName);
        System.out.println("[CreatingSessionThread]"+this.pr3sessionName);
        System.out.println("[CreatingSessionThread]"+this.pr4sessionName);
        System.out.println("[CreatingSessionThread]"+this.pr5sessionName);
        System.out.println("[CreatingSessionThread]"+this.nur1sessionName);
        System.out.println("[CreatingSessionThread]"+this.nur2sessionName);
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
        createSessionRequestEntity.setPr1(pr1sessionName);
        createSessionRequestEntity.setPr2(pr2sessionName);
        createSessionRequestEntity.setPr3(pr3sessionName);
        createSessionRequestEntity.setPr4(pr4sessionName);
        createSessionRequestEntity.setPr5(pr5sessionName);
        createSessionRequestEntity.setNur1(nur1sessionName);
        createSessionRequestEntity.setNur2(nur2sessionName);
        System.out.println("[CreatingSessionThread]: Preparing JSON body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String rawjson=gson.toJson(createSessionRequestEntity);
        System.out.println("[CreatingSessionThread]: Setting up client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[CreatingSessionThread]: Preparing Request Body");
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),rawjson);
        System.out.println("[CreatingSessionThread]: Sending Request");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://127.0.0.1:8080/createsession")
                .post(requestBody)
                .build();
        try {
            Response response=client.newCall(request).execute();
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    new ConnectionError().Connection("SUCCESS");
                });
                response.close();
            }else{
                System.out.println("[CreatingSessionThread]--> server return error "+response.code()+": Unable to get score");
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to update score");
                    if (error){
                        System.out.println("[CreatingSessionThread]--> Connection Error,Window close");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,Contact the developer to fix the error");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[CreatingSessionThread]--> Server error ,server cannot process request");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[CreatingSessionThread]--> Access denied");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Bad request,check field for invalid characters");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[CreatingSessionThread]--> server error,bad request");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[CreatingSessionThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}

