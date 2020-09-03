package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;

public class UpdateScore extends Thread {

    private String Studentname;
    private String ScoreTable;
    //data instance is the score of the student
    private double data;
    private String Subject;
    //column instance here is the corresponding column you want to update,it correspond to a table in the databese
    private String column;
    private String term;
    public UpdateScore(String scoreTable, String studentname, double data, String Subject, String column,String term) {
        this.Studentname = studentname;
        this.ScoreTable = scoreTable;
        this.data = data;
        this.Subject=Subject;
        this.term=term;
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
        updateScoreRequestEntity.setTerm(term);
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
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .post(requestbody)
                .url("http://localhost:8080/updatescore")
                .build();
        System.out.println("[UpdateScore]:Updating Score--> Sending Request");
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[UpdateScore]:Response--> "+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){

            }else {
                System.out.println("[UpdateScore]--> server return error "+response.code()+": Unable to get score");
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to update score");
                    if (error){
                        System.out.println("[UpdateScore]--> Connection Error,Window close");
                    }
                });
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{

                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check for invalid characters");
                    if (error){
                        System.out.println("[UpdateScore]--> Server error ,server cannot process request");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Bad request,check field for invalid characters");
                    if (error){
                        System.out.println("[GetScoreThread]--> server error,bad request");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        System.out.println("Access denied");
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
    }
}
