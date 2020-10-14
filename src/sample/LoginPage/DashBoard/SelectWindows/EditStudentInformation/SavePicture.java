package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Information.InformationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class SavePicture extends Thread{
    private File file;
    private ImageView picture;
    private String studentname;
    private String session;
    private String tag;
    private String clas;
    private Image image;
    public SavePicture(Image image,File file, ImageView picture, String studentname, String session,String tag,String clas) {
        this.session=session;
        this.studentname=studentname;
        this.file=file;
        this.picture=picture;
        this.tag=tag;
        this.clas=clas;
        this.image=image;
    }

    @Override
    public void run() {
        System.out.println("[Savepicture]: --> session: "+session);
        System.out.println("[Savepicture]: --> studenname: "+studentname);
        System.out.println("[Savepicture]: --> tag: "+tag);
        System.out.println("[Savepicture]: --> class: "+clas);
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[Savepicture]: --> Preparing request body ");
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson=builder.create();
        EditInformationImageRequestEntity editInformationImageRequestEntity=new EditInformationImageRequestEntity();
        editInformationImageRequestEntity.setClas(clas);
        editInformationImageRequestEntity.setStudentname(studentname);
        editInformationImageRequestEntity.setTag(tag);
        editInformationImageRequestEntity.setSession(session);
        try {
            editInformationImageRequestEntity.setImage(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(()->{
                new ConnectionError().Connection("Unable to process image");
            });
        }
        String rawbody=gson.toJson(editInformationImageRequestEntity);
        RequestBody requestBody=RequestBody.create(rawbody, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://167.99.91.154:8080/editinformationimage")
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .post(requestBody)
                .build();
        Response response;
        try {
            response=client.newCall(request).execute();
            System.out.println("[SavePicture]"+response);
            if (response.code()==200||response.code()==202||response.code()==212||response.code()==201){
                Platform.runLater(()->{
                    new ConnectionError().Connection("SUCCESS");
                    picture.setImage(image);
                });
                response.close();
            }else {
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to edit information");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[SavePicture]--> Connection Error,Window close");
                        response.close();
                    }
                });
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[CreatingSessionThread]--> Access denied");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[SavePicture]--> Connection Error");
                }
            });
            System.out.println("[SavePicture]: Request failed");
            e.printStackTrace();
        }
    }
}
