package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.*;
import sample.Configuration.Configuration;
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
    private String tag;
    private String id;
    private Image image;
    public SavePicture(Image image,File file, ImageView picture,String tag,String id) {
        this.file=file;
        this.picture=picture;
        this.tag=tag;
        this.id=id;
        this.image=image;
    }

    @Override
    public void run() {
        System.out.println("[Savepicture]: --> student id: "+id);
        System.out.println("[Savepicture]: --> tag: "+tag);

        if (Configuration.ipaddress!=null ||Configuration.port!=null){
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
            editInformationImageRequestEntity.setId(id);
            editInformationImageRequestEntity.setTag(tag);
            try {
                editInformationImageRequestEntity.setImage(Files.readAllBytes(file.toPath()));
                String rawbody=gson.toJson(editInformationImageRequestEntity);
                RequestBody requestBody=RequestBody.create(rawbody, MediaType.parse("application/json"));
                Request request=new Request.Builder()
                        .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/editinformationimage")
                        .addHeader("Authorization","Bearer "+ LogInModel.token)
                        .post(requestBody)
                        .build();
                Response response;
                try {
                    response=client.newCall(request).execute();
                    System.out.println("[SavePicture]:Response:"+response);
                    if (response.code()==200){
                        Platform.runLater(()->{
                            new ConnectionError().Connection("SUCCESS");
                            picture.setImage(image);
                        });
                        response.close();
                    }else {
                        String message=new String(response.body().bytes(),"UTF-8");
                        //Display alert dialog
                        Platform.runLater(()->{
                            boolean error=new ConnectionError().Connection(response.code()+":"+message);
                            if (error){
                                LoadingWindow.window.close();
                                System.out.println("[SavePicture]--> Connection Error,Window close");
                                response.close();
                            }
                        });
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

            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(()->{
                    new ConnectionError().Connection("Unable to process image");
                });
            }

        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }

    }
}
