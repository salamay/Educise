package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;

import java.io.IOException;

/////This class Save the Subject to the Database,the scores are exclude

public class UpdateSubjectThread extends Thread {
    //The student name to save score to
    private String Studentname;
    //The table which store scores and subject
    //its is the value selected from the combo box
    private String ScoreTable;
    //The subject to save ,it is stored where ScoreTable equals to the Studentname
    private String Subject;
    private SaveSubjectRequestEntity saveSubjectRequestEntity;
    private String oldSubject;
    public UpdateSubjectThread(String clas, String studentname, String Subject, String oldSubject) {
        this.ScoreTable=clas;
        this.Studentname = studentname;
        this.Subject=Subject;
        this.oldSubject=oldSubject;
        System.out.println("[SaveSubject]: Entity to store --> "+ ScoreTable+","+Studentname+","+Subject+",Old subject-->"+oldSubject);
    }

    @Override
    public void run() {
        saveSubjectRequestEntity=new SaveSubjectRequestEntity();
        saveSubjectRequestEntity.setName(Studentname);
        saveSubjectRequestEntity.setTable(ScoreTable);
        saveSubjectRequestEntity.setSubject(Subject);
        saveSubjectRequestEntity.setOldsubject(oldSubject);
        System.out.println("[SaveSubject]--> Preparing Json body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(saveSubjectRequestEntity);
        System.out.println("[SaveSubject]-->body to be saved--> "+ json);
        if (json!=null){
            System.out.println("[SaveSubject]-->json body processed successfully");
        }
        System.out.println("[SaveSubject]--> Setting up Connection");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[SaveSubject]-->Preparing Request body");
        RequestBody jsonbody=RequestBody.create(MediaType.parse("application/json"),json);
        System.out.println("[SaveSubject]--> Setting up RequestBody");
        RequestBody body=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("json","json.json",jsonbody)
                .build();
        Request request=new Request.Builder()
                .post(body)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                .url("http://localhost:8080/updatesubject")
                .build();
        System.out.println("[SaveSubject]--> Request sent");
        try {
            Response response=client.newCall(request).execute();
            if (response.code()==201||response.code()==200||response.code()==202){
                System.out.println("[SaveSubject]-->Response:"+response);
                System.out.println("[SaveSubject]-->ResponseBody--->"+response.body());
            }else{
                System.out.println("[SaveSubject]--> server return error:"+response.code()+" Unable to save subject");
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  get data:CHECK INTERNET CONNECTION");
                    if (error){
                        StudentSelectAssessmentSessionWindow.window.close();
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
            }

        } catch (IOException e) {
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish :CHECK INTERNET CONNECTION");
                if (error){
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }

}

