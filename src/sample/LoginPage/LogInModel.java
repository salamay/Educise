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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.*;
import sample.ConnectionError;
import sample.SplashScreenController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class LogInModel extends Thread{
    private String email;
    private String password;
    public static String token;
    private Label loginError;
    private VBox vBox;
    private StackPane stackPane;
    private Circle c1;
    private Circle c2;
    private Circle c3;
    private Circle c4;
    public LogInModel(String email, String password, Label loginError, VBox vbox, StackPane stackpane, Circle c1, Circle c2, Circle c3, Circle c4) {
        this.vBox=vbox;
        this.stackPane=stackpane;
        this.c1=c1;
        this.c2=c2;
        this.c3=c3;
        this.c4=c4;
        this.email=email;
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
        loginCredentials.setUsername(email);
        loginCredentials.setPassword(password);
        String json=gson.toJson(loginCredentials);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);

        Request request=new Request.Builder()
                .url("http://localhost:8080/authenticate")
                .post(requestBody)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[LogInModel]: response--> "+response);
            if (response.code()==200||response.code()==201||response.code()==202||response.code()==212){
                loginError.setVisible(false);
                byte[] rawbody=response.body().bytes();
                System.out.println("RawBody: "+rawbody);
                String jwt=new String(rawbody,"UTF-8");
                System.out.println("JWT: "+jwt);
                Jwt j=new Jwt();
                GsonBuilder builder1=new GsonBuilder();
                j=gson.fromJson(jwt,Jwt.class);
                token=j.getJwt();
                if (token!=null){
                    Platform.runLater(()->{
                        SplashScreenController.window1.close();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("DashBoard/DB.fxml"));
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
                        Scene scene = new Scene(root,1200,700);
                        window2.setScene(scene);
                        window2.show();
                    });
                }
                response.close();
            }
            if (response.code()==404){
               Platform.runLater(()->{
                   loginError.setVisible(true);
                   //LoginStatusWindow.window.close();
                   boolean error=new ConnectionError().Connection("User not found,provide correct credentials");
                   if (error){
                       RestoreAnimation();
                       System.out.println("[getBookSoldHistory]--> Connection Error");
                   }
               });
            }
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


