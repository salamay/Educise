package sample.LoginPage.DashBoard.SelectWindows.Utility;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionAndClassWindow;
import sample.LoginPage.LogInModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
//the progressbar indicate the progress

public class GetSessionThread extends Thread{
    private JFXComboBox<String> session;
    private ProgressIndicator pgb;
    private String endpoint;
    public GetSessionThread(JFXComboBox<String> comb, ProgressIndicator progressBar,String endpoint) {
        this.session = comb;
        this.pgb = progressBar;
        this.endpoint=endpoint;
    }

    @Override
    public void run() {

        System.out.println("[GetSessionThread]: setting up okhttp client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.println("[GetSessionThread]: setting up okhttp client request");
        Request request = new Request.Builder()
                .url("http://"+ Configuration.ipaddress +":"+Configuration.port+"/"+endpoint)
                .addHeader("Authorization", "Bearer " + LogInModel.token)
                .build();

        if (Configuration.ipaddress!=null || Configuration.port!=null){
            try {
                Response response = client.newCall(request).execute();
                System.out.println("[GetSessionThread]: " + response);
                if (response.code() == 200) {
                    System.out.println("[GetSessionThread]: session retrieved");
                    ResponseBody body = response.body();
                    try {
                        byte[] bytes = body.bytes();
                        //removing bracket from response
                        String data = new String(bytes, "UTF-8");
                        String data2 = data.replace(']', ' ');
                        String data3 = data2.replace('[', ' ');
                        String data4 = data3.replaceAll(" ", "");
                        List<String> list = Arrays.stream(data4.split(",")).collect(Collectors.toList());

                        Platform.runLater(() -> {
                            session.getItems().addAll(list);
                        });
                        System.out.println(data);
                        response.close();
                        Platform.runLater(() -> {
                            pgb.setProgress(100);
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //when something wrong happens, the server send the cause of the problem as a response body
                    String message=new String(response.body().bytes(),"UTF-8");
                    //Display an Alert dialog
                    Platform.runLater(() -> {
                        boolean error = new ConnectionError().Connection(response.code() + ":"+message);
                        if (error) {
                            SelectInformationSesssionAndClassWindow.StudentWindow.close();
                            System.out.println("[GetSessionThread]--> server error,unable to get session");
                        }
                    });
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(() -> {
                    boolean error = new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error) {
                        SelectInformationSesssionAndClassWindow.StudentWindow.close();
                        System.out.println("[GetSessionThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[GetSessionThread]: Unable to get session information from server");
                e.printStackTrace();
            }
        }else {
            new ConnectionError().Connection("Invalid configuration,please configure your software properly");
        }

    }
}
