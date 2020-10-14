package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/////This class Save the Subject to the Database,the scores are excluded

public class UpdateSubjectThread extends Thread {
    private String id;

    //The table which store scores and subject
    //its is the value selected from the combo box
    private String ScoreTable;
    //The subject to save ,it is stored where ScoreTable equals to the id
    private String Subject;
    private SaveSubjectRequestEntity saveSubjectRequestEntity;

    public UpdateSubjectThread(String clas, String id, String Subject) {
        this.ScoreTable=clas;
        this.id = id;
        this.Subject=Subject;
        System.out.println("[UpdateSubjectThread]: Entity to store --> Table:"+ ScoreTable+"\r\nid:"+id+"\r\n"+",Subject:-->"+Subject);
    }

    @Override
    public void run() {
        saveSubjectRequestEntity=new SaveSubjectRequestEntity();
        saveSubjectRequestEntity.setId(id);
        saveSubjectRequestEntity.setTable(ScoreTable);
        saveSubjectRequestEntity.setSubject(Subject);
        System.out.println("[UpdateSubjectThread]--> Preparing Json body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(saveSubjectRequestEntity);
        System.out.println("[UpdateSubjectThread]-->body to be saved--> "+ json);
        if (json!=null){
            System.out.println("[UpdateSubjectThread]-->json body processed successfully");
        }
        System.out.println("[UpdateSubjectThread]--> Setting up Connection");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        System.out.println("[UpdateSubjectThread]-->Preparing Request body");
        RequestBody jsonbody=RequestBody.create(MediaType.parse("application/json"),json);
        System.out.println("[UpdateSubjectThread]--> Setting up RequestBody");
        RequestBody body=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("json","json.json",jsonbody)
                .build();
        Request request=new Request.Builder()
                .post(body)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://167.99.91.154:8080/updatesubject")
                .build();
        System.out.println("[UpdateSubjectThread]--> Request sent");
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[UpdateSubjectThread]-->Response:"+response);
            System.out.println("[UpdateSubjectThread]-->ResponseBody--->"+response.body());
            if (response.code()==201||response.code()==200||response.code()==202){
                response.close();
            }else{
                System.out.println("[SaveSubject]--> server return error:"+response.code()+" Unable to save subject");
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to update subject,check field for invalid character");
                    if (error){
                        StudentSelectAssessmentSessionWindow.window.close();
                        System.out.println("[UpdateSubjectThread]--> Server error,unable to save subject");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check for invalid characters");
                    if (error){
                        System.out.println("[UpdateSubjectThread]--> Server error ,server cannot process request");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Bad request,check field for invalid characters");
                    if (error){
                        System.out.println("[UpdateSubjectThread]--> server error,bad request");
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
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish :CHECK INTERNET CONNECTION");
                if (error){
                        System.out.println("[UpdateSubjectThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }

}

