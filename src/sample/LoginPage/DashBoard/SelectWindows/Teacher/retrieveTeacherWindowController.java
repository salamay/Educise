package sample.LoginPage.DashBoard.SelectWindows.Teacher;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class retrieveTeacherWindowController implements Initializable {
    public Label teacherNameLabel;
    public Label classHeldLabel;
    public Label subjectOneLabel;
    public Label subjectTwoLabel;
    public Label subjectThreeLabel;
    public Label subjectFourLabel;
    public Label emailLabel;
    public Label addressLabel;
    public Label entryYearLabel;
    public Label genderLabel;
    public Label maritalStatuslabel;
    public Label schoolAttendedLabel;
    public Label phoneNumberLabel;
    public Label courseLabel;
    public ImageView teacherImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[retrieveTeacherWindowController]: Retrieving teacher information");
        new retrieveTeacherInformationThread().start();
    }

    public class retrieveTeacherInformationThread extends Thread{
        @Override
        public void run() {
            System.out.println("[retrieveTeacherWindowController] :Setting up client");

            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();
            if
            (Configuration.ipaddress!=null&& Configuration.port!=null){
                Request request=new Request.Builder()
                        .addHeader("Authorization","Bearer "+ LogInModel.token)
                        .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/retrieveteacherinformation/"+retrieveTeacherWindow.teacherid)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    System.out.println("[retrieveTeacherWindowController] :Response->"+response);
                    if (response.code()==200){
                        String rawData=new String(response.body().bytes(),"UTF-8");
                        GsonBuilder builder=new GsonBuilder();
                        builder.serializeNulls();
                        builder.setPrettyPrinting();
                        Gson gson=builder.create();
                        //NewTeacherRequestEntity class is used to retrieve teacher information because it corresponds to the data being retrieved
                        NewTeacherRequestEntity newTeacherRequestEntity=gson.fromJson(rawData,NewTeacherRequestEntity.class);
                        byte[] bytes=newTeacherRequestEntity.getFile();
                        File teacher=new File(System.getProperty("user.dir")+"/MyChildSchool/teacher");
                        FileOutputStream fileOutputStream=new FileOutputStream(teacher);
                        fileOutputStream.write(bytes);
                        BufferedImage teacherimage= ImageIO.read(teacher);
                        Image t= SwingFXUtils.toFXImage(teacherimage,null);
                        Platform.runLater(()->{
                            teacherNameLabel.setText(newTeacherRequestEntity.getLastname()+" "+newTeacherRequestEntity.getMiddlename()+" "+newTeacherRequestEntity.getLastname());
                            classHeldLabel.setText(newTeacherRequestEntity.getClas());
                            subjectOneLabel.setText(newTeacherRequestEntity.getSubjectone());
                            subjectTwoLabel.setText(newTeacherRequestEntity.getSubjecttwo());
                            subjectThreeLabel.setText(newTeacherRequestEntity.getSubjectthree());
                            subjectFourLabel.setText(newTeacherRequestEntity.getSubjectfour());
                            emailLabel.setText(newTeacherRequestEntity.getEmail());
                            addressLabel.setText(newTeacherRequestEntity.getAddress());
                            entryYearLabel.setText(newTeacherRequestEntity.getEntryyear());
                            genderLabel.setText(newTeacherRequestEntity.getGender());
                            maritalStatuslabel.setText(newTeacherRequestEntity.getMaritalstatus());
                            schoolAttendedLabel.setText(newTeacherRequestEntity.getSchoolattended());
                            phoneNumberLabel.setText(newTeacherRequestEntity.getPhoneno());
                            courseLabel.setText(newTeacherRequestEntity.getCourse());
                            teacherImageView.setImage(t);
                        });
                    }else {
                        String message=new String(response.body().bytes(),"UTF-8");
                        Platform.runLater(()->{
                            new ConnectionError().Connection(response.code()+":"+message);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Platform.runLater(()->{
                    new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
                });
            }
        }
    }
}
