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
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/////This class Save the Subject to the Database,the scores are excluded

public class UpdateSubjectThread extends Thread {
    private String id;


    //The subject to save ,it is stored where table equals to the id
    private String Subject;
    private SaveSubjectRequestEntity saveSubjectRequestEntity;
    private TableView<Scores> tableview;
    public UpdateSubjectThread(String id, String Subject, TableView<Scores> tableview) {
        this.tableview=tableview;
        this.id = id;
        this.Subject=Subject;
        System.out.println("[UpdateSubjectThread]: Entity to store --> id:"+id+"\r\n"+",Subject:-->"+Subject);
    }

    @Override
    public void run() {
        saveSubjectRequestEntity=new SaveSubjectRequestEntity();
        saveSubjectRequestEntity.setId(id);
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
        if (Configuration.ipaddress!=null&&Configuration.port!=null){
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
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/updatesubject")
                    .build();
            System.out.println("[UpdateSubjectThread]--> Request sent");
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[UpdateSubjectThread]-->Response:"+response);
                System.out.println("[UpdateSubjectThread]-->ResponseBody--->"+response.body());
                if (response.code()==200){
                    byte[] data=response.body().bytes();
                    String rawData=new String(data,"UTF-8");
                    GsonBuilder builder1=new GsonBuilder();
                    builder1.serializeNulls();
                    Gson gson1=builder1.create();
                    Scores scores=gson1.fromJson(rawData,Scores.class);
                    ObservableList<Scores> allScore, selectedScore;
                    allScore=tableview.getItems();
                    selectedScore=tableview.getSelectionModel().getSelectedItems();
                    selectedScore.forEach(allScore::remove);
                    tableview.getItems().add(scores);
                    tableview.refresh();
                    response.close();
                }else{
                    String message=new String(response.body().bytes(),"UTF-8");
                    System.out.println("[SaveSubject]--> server return error:"+response.code()+" Unable to save subject");
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[UpdateSubjectThread]--> Server error,unable to save subject");
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
        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }
    }

}

