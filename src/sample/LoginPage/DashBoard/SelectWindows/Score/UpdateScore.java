package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UpdateScore extends Thread {

    private String id;
    //data instance is the score of the student
    private double data;
    //column instance here is the corresponding column you want to update,it correspond to a table in the databese
    private String column;
    private TableView<Scores> tableview;
    public UpdateScore(String id, double data, String column, TableView<Scores> tableview) {
        this.tableview=tableview;
        this.id = id;
        this.data = data;
        //remove white space to avoid sql syntax error
        this.column=column.replaceAll(" ","");
    }


    @Override
    public void run() {
        UpdateScoreRequestEntity updateScoreRequestEntity=new UpdateScoreRequestEntity();
        updateScoreRequestEntity.setCa(column);
        updateScoreRequestEntity.setScore(data);
        updateScoreRequestEntity.setId(id);
        System.out.println("[UpdateScore]:Updating Score--> Preparing json body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String jsonbody=gson.toJson(updateScoreRequestEntity);
        RequestBody rawbody=RequestBody.create(MediaType.parse("application/json"),jsonbody);
        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            RequestBody requestbody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file","file.gson",rawbody)
                    .build();
            System.out.println("[UpdateScore]:Updating Score--> Setting up client");
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            System.out.println("[UpdateScore]:Updating Score--> Making Request");
            Request request=new Request.Builder()
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .post(requestbody)
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/updatescore")
                    .build();
            System.out.println("[UpdateScore]:Updating Score--> Sending Request");
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[UpdateScore]:Response--> "+response);
                if (response.code()==200){
                    byte[] data=response.body().bytes();
                    String rawData=new String(data,"UTF-8");
                    GsonBuilder builder1=new GsonBuilder();
                    builder1.serializeNulls();
                    Gson gson1=builder1.create();
                    Scores scores=gson1.fromJson(rawData,Scores.class);
                    ObservableList<Scores> ScoreSelected, AllScore;
                    AllScore = tableview.getItems();
                    ScoreSelected = tableview.getSelectionModel().getSelectedItems();
                    ScoreSelected.forEach(AllScore::remove);
                    tableview.getItems().add(scores);
                    tableview.refresh();
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    System.out.println("[UpdateScore]--> server return error "+response.code()+": Unable to get score");
                    //Display alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[UpdateScore]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }

            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[UpdateScore]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
    }
}
