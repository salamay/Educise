package sample.LoginPage.DashBoard.SelectWindows.Score;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class getScoreSessionThread extends Thread {
    private ComboBox<String> sessionBox;
    private List<String> list;
    public getScoreSessionThread(ComboBox<String> sessionBox){
        this.sessionBox=sessionBox;
    }
    @Override
    public void run() {
        System.out.println("[Retrieving information session]: setting up okhttp client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.println("[Retrieving information session]: setting up okhttp client request");
        Request request=new Request.Builder()
                .url("http://167.99.91.154:8080/retrievescoresession")
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("[Retrieving information session]: "+response);
            if (response.code()==200|| response.code()==212){

                System.out.println("[Retrieving information session]: session retrieved");
                ResponseBody body=response.body();
                try {
                    byte [] bytes=body.bytes();
                    //removing bracket from response
                    String data=new String(bytes,"UTF-8");
                    String data2=data.replace(']',' ');
                    String data3=data2.replace('[',' ');
                    String data4=data3.replaceAll(" ","");
                    list= Arrays.stream(data4.split(",")).collect(Collectors.toList());
                    Platform.runLater(()->{
                        sessionBox.getItems().addAll(list);
                    });
                    System.out.println(data);
                    response.close();
                } catch (IOException e) {
                    response.close();
                    e.printStackTrace();
                }

            }else {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Server:error"+response.code()+",Unable to retrieve session");
                    if (error){
                        StudentSelectAssessmentSessionWindow.window.close();
                        System.out.println("[SelectClassThread]--> Connection Error,Window close");
                    }
                });
                response.close();
            }
            if (response.code()==404){
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Server:error"+response.code()+": session not found");
                    if (error){
                        StudentSelectAssessmentSessionWindow.window.close();
                        System.out.println("[SelectClassThread]--> Connection Error,Window close");
                    }
                });
                response.close();
            }

        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    StudentSelectAssessmentSessionWindow.window.close();
                    System.out.println("[SelectClassThread]--> Connection Error,Window close");
                }
            });
            System.out.println("[Retrieving information session]: Unable to get session information from server");
            e.printStackTrace();
        }

    }
}

