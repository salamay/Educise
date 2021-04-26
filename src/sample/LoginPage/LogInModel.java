package sample.LoginPage;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.SplashScreenController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class LogInModel extends Thread{
    private String schoolid;
    private String staffid;
    private String password;
    public static String token;
    private Label loginError;
    private VBox vBox;
    private StackPane stackPane;
    private Circle c1;
    private Circle c2;
    private Circle c3;
    private Circle c4;
    public LogInModel(String schoolid, String staffid,String password, Label loginError, VBox vbox, StackPane stackpane, Circle c1, Circle c2, Circle c3, Circle c4) {
        this.vBox=vbox;
        this.stackPane=stackpane;
        this.c1=c1;
        this.c2=c2;
        this.c3=c3;
        this.c4=c4;
        this.schoolid=schoolid;
        this.staffid=staffid;
        this.password=password;
        this.loginError=loginError;
    }

    @Override
    public void run() {
        System.out.println("[LoginModel]-->Signing in");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.println("[LoginModel]-->Setting up request body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        LoginCredentials loginCredentials=new LoginCredentials();
        //it send the username and schoolid so as to authenticate with schoolid also
        loginCredentials.setStaffid(staffid+","+schoolid);
        loginCredentials.setPassword(password);
        String json=gson.toJson(loginCredentials);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);

        if (Configuration.ipaddress!=null ||Configuration.port!=null){
            Request request=new Request.Builder()
                    .url("http://"+ Configuration.ipaddress +":"+Configuration.port+"/authenticate")
                    .post(requestBody)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                if (response.code()==200){
                    loginError.setVisible(false);
                    String jwt=new String(response.body().bytes(),"UTF-8");
                    GsonBuilder builder1=new GsonBuilder();
                    Gson gson1=builder1.create();
                    Jwt j=gson1.fromJson(jwt,Jwt.class);
                    token=j.getJwt();
                    System.out.println("token: "+token);
                    if (token!=null){
                        Platform.runLater(()->{
                            SplashScreenController.window1.close();
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("Dash.fxml"));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            Stage window2 = new Stage();
                            window2.setTitle("welcome to management Board");
                            window2.setMinWidth(800);
                            window2.setMinHeight(700);
                            window2.setMaximized(true);
                            window2.isMaximized();
                            window2.setResizable(false);
                            window2.getIcons().add(new Image("image/window_icon.png"));
                            Scene scene = new Scene(root,1200,700);
                            window2.setScene(scene);
                            window2.show();
                        });
                    }
                }else {
                    //when something wrong happens, the server send the cause of the problem as a response body
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        RestoreAnimation();
                        new ConnectionError().Connection(message);
                    });
                }
                response.close();
            } catch (IOException e) {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to connect to internet");
                    if (error){
                        RestoreAnimation();
                        System.out.println("[LoginModel]--> Connection Error");
                    }
                });
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
    }
    public void RestoreAnimation(){
        Platform.runLater(()->{
            TranslateTransition hboxTransition=new TranslateTransition(new Duration(2000),vBox);
            hboxTransition.setToY(50);
            ParallelTransition parallelTransition=new ParallelTransition(hboxTransition);
            parallelTransition.play();
            TranslateTransition stackpaneTransition=new TranslateTransition(new Duration(1000),stackPane);
            stackpaneTransition.setToY(10);
            stackpaneTransition.play();
            stackPane.setVisible(false);
        });
    }
}


