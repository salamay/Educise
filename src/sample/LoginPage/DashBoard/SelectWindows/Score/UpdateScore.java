package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;

import java.io.IOException;

public class UpdateScore extends Thread {

    private String Studentname;
    private String ScoreTable;
    //data instance is the score of the student
    private double data;
    private String Subject;
    //column instance here is the corresponding column you want to update,it correspond to a table in the databese
    private String column;

    public UpdateScore(String scoreTable, String studentname, double data, String Subject, String column) {
        this.Studentname = studentname;
        this.ScoreTable = scoreTable;
        this.data = data;
        this.Subject=Subject;
        //remove white space to avoid sql sythax error
        this.column=column.replaceAll(" ","");
    }


    @Override
    public void run() {
        UpdateScoreRequestEntity updateScoreRequestEntity=new UpdateScoreRequestEntity();
        updateScoreRequestEntity.setName(Studentname);
        updateScoreRequestEntity.setTable(ScoreTable);
        updateScoreRequestEntity.setSubject(Subject);
        updateScoreRequestEntity.setCa(column);
        updateScoreRequestEntity.setScore(data);
        System.out.println("[UpdateScore]:Updating Score--> Preparing json body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String jsonbody=gson.toJson(updateScoreRequestEntity);
        RequestBody rawbody=RequestBody.create(MediaType.parse("application/json"),jsonbody);
        RequestBody requestbody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","file.gson",rawbody)
                .build();
        System.out.println("[UpdateScore]:Updating Score--> Setting up client");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[UpdateScore]:Updating Score--> Making Request");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                .post(requestbody)
                .url("http://localhost:8080/updatescore")
                .build();
        System.out.println("[UpdateScore]:Updating Score--> Sending Request");
        try {
            Response response=client.newCall(request).execute();
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){

            }else {
                System.out.println("[GetScoreThread]--> server return error "+response.code()+": Unable to get score");
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to update score");
                    if (error){
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[SaveScoreThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
