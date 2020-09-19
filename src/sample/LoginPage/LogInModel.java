package sample.LoginPage;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.DashboardController;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.Main;
import sample.SqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInModel extends Thread{
    private String email;
    private String password;
    public static String token;
    private Label loginError;

    public LogInModel(String email, String password, Label loginError) {
        this.email=email;
        this.password=password;
        this.loginError=loginError;
    }

    @Override
    public void run() {
        System.out.println("[LoginModel]-->Signing in");
        OkHttpClient client=new OkHttpClient();
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
                        Main.window1.close();
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
                        window2.isResizable();
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
                       System.out.println("[getBookSoldHistory]--> Connection Error");
                   }
               });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to connect to internet");
                if (error){
                    System.out.println("[LoginModel]--> Connection Error");
                }
            });

            e.printStackTrace();
        }
    }
}


