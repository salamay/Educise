package sample.LoginPage.DashBoard.SelectWindows.Information;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//this class does the fetching of Student information from database
public class ClassInformationThread extends  Thread {

        String classname;
        String studentName;
    Label ageLabel;
    Label phoneNoLabel;
    Label fatherNameLabel;
    Label motherNameLabel;
    Label addressLabel;
    Label nextOfKinLabel;
    Label genderLabel;
    Label clubLabel;
    Label rOleModelLabel;
    Label academicPerformance;
    Label namelabel;
    private  Label MotherName;
    private Label FatherName;

    private ImageView imageView;
    private ImageView FatherImageView;
    private ImageView MotherImageView;
    //Buffered Image
    BufferedImage bufferedImage2;
    BufferedImage bufferedImage3;
    BufferedImage bufferedImage=null;

        public ClassInformationThread(String clas, String name, Label namelabel, Label ageLabel, Label phoneNoLabel, Label fatherNameLabel,
                                      Label motherNameLabel, Label addressLabel, Label nextOfKinLabel, Label genderLabel,
                                      Label clubLabel, Label rOleModelLabel, Label academicPerformance, ImageView image,ImageView Father,
                                      ImageView Mother,Label FatherName,Label MotherName){
            this.studentName=name;
            this.classname=clas;
            this.namelabel=namelabel;
            this.ageLabel=ageLabel;
            this.phoneNoLabel=phoneNoLabel;
            this.fatherNameLabel=fatherNameLabel;
            this.motherNameLabel=motherNameLabel;
            this.addressLabel=addressLabel;
            this.nextOfKinLabel=nextOfKinLabel;
            this.genderLabel=genderLabel;
            this.clubLabel=clubLabel;
            this.rOleModelLabel=rOleModelLabel;
            this.academicPerformance=academicPerformance;
            this.imageView=image;
            this.FatherImageView=Father;
            this.MotherImageView=Mother;
            this.FatherName=FatherName;
            this.MotherName=MotherName;
            System.out.println(" [ClassInformationThread]: "+studentName);
            System.out.println("[ClassInformationThread]: "+classname);
        }
        @Override
        public void run() {
            OkHttpClient client=new OkHttpClient();

            Request request=new Request.Builder()
                    .url("http://localhost:8080/retrievestudentinformation/"+studentName+"/"+classname)
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                    .build();
            try {
                Response response=client.newCall(request).execute();
                if (response.code()==200||response.code()==204){
                    System.out.println("[ClassInformationThread]"+response);
                    ResponseBody responseBody=response.body();
                    System.out.println("[ClassInformationThread]"+responseBody);
                    /////Retrieving and processing body,The response contains images and sting value
                    //Images is received as bytes and converted back from json to respective object
                    byte[] bytes=responseBody.bytes();
                    System.out.println("[ClassInformationThread]: byte retrieved from server");
                    String data=new String(bytes,"UTF-8");
                    System.out.println("[ClassInformationThread]"+responseBody);
                    System.out.println("[ClassInformationThread]:Converting Json body");
                    GsonBuilder builder=new GsonBuilder();
                    builder.setPrettyPrinting();
                    builder.serializeNulls();
                    Gson gson=builder.create();
                    RetrieveResponseEntity retrieveResponseEntity=gson.fromJson(data,RetrieveResponseEntity.class);
                    System.out.println("[ClassInformationThread]"+retrieveResponseEntity.getAddress());
                    byte[] student=retrieveResponseEntity.getStudent();
                    byte[] father=retrieveResponseEntity.getFather();
                    byte[] mother=retrieveResponseEntity.getMother();
                    Path path=Paths.get(System.getProperty("user.dir")+"/MyChildSchool");
                    System.out.println("[ClassInformationThread]: Creating path on the Pc to store Images");
                    if (Files.exists(path)){
                        System.out.println("[ClassInformationThread]:Creating new Path");
                        Files.createDirectories(path);
                        System.out.println("[ClassInformationThread]:Processing student image");
                        File studentimage=new File(System.getProperty("user.dir")+"/MyChildSchool/student");
                        FileOutputStream sout=new FileOutputStream(studentimage);
                        sout.write(student);
                        if (studentimage!=null){
                            System.out.println("[ClassInformationThread]:student image is valid");
                            bufferedImage=ImageIO.read(studentimage);
                            Image s=SwingFXUtils.toFXImage(bufferedImage,null);
                            System.out.println("[ClassInformationThread]:Displaying student image");
                            Platform.runLater(()->{
                                imageView.setImage(s);
                            });
                            System.out.println("[ClassInformationThread]:Processing student image successful");
                        }
                        else {
                            System.out.println("[ClassInformationThread]:student image is null cannot read file");
                        }
                        //////Processing Father Image
                        System.out.println("[ClassInformationThread]:Processing father image ");
                        File fatherimage=new File(System.getProperty("user.dir")+"/MyChildSchool/father");
                        FileOutputStream fout=new FileOutputStream(fatherimage);
                        fout.write(father);
                        if (fatherimage!=null){
                            System.out.println("[ClassInformationThread]:father image is valid");
                            bufferedImage2=ImageIO.read(fatherimage);
                            Image f=SwingFXUtils.toFXImage(bufferedImage2,null);
                            System.out.println("[ClassInformationThread]:Displaying Father image");
                            Platform.runLater(()->{
                                FatherImageView.setImage(f);
                            });
                            System.out.println("[ClassInformationThread]:Processing father image successful");
                        }else {
                            System.out.println("[ClassInformationThread]:father image is null cannot read file");
                        }
                        ///Processing Mother image
                        System.out.println("[ClassInformationThread]:Processing mother image ");
                        File motherimage=new File(System.getProperty("user.dir")+"/MyChildSchool/mother");
                        FileOutputStream mout=new FileOutputStream(motherimage);
                        mout.write(mother);
                        if (motherimage!=null){
                            System.out.println("[ClassInformationThread]:mother image is valid");
                            bufferedImage3=ImageIO.read(motherimage);
                            Image m=SwingFXUtils.toFXImage(bufferedImage3,null);
                            System.out.println("[ClassInformationThread]:Displaying mother image");
                            Platform.runLater(()->{
                                MotherImageView.setImage(m);
                            });
                            System.out.println("[ClassInformationThread]:Processing father image successful");
                        }
                        else {
                            System.out.println("[ClassInformationThread]:mother image is null cannot read file");
                        }
                    }else {
                        System.out.println("[ClassInformationThread]:path to store images does not exists" );
                    }
                    response.close();
                }else {
                    //Display alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  get Information");
                        if (error){
                            InformationWindow.window1.close();
                            System.out.println("[GetScoreThread]--> Connection Error,Window close");
                            response.close();
                        }
                    });

                }

            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                    if (error){
                        InformationWindow.window1.close();
                        System.out.println("[SaveScoreThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[ClassInformationThread]: Request failed");
                e.printStackTrace();
            }

        }

}
