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
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

//this class does the fetching of Student information from database
public class ClassInformationThread extends  Thread {

    private Object studentid;
    private Label ageLabel;
    private Label phoneNoLabel;
    private Label parentPhoneNoLabel;
    private Label fatherNameLabel;
    private Label motherNameLabel;
    private Label otherNameLabel;
    private Label addressLabel;
    private Label nextOfKinLabel;
    private Label genderLabel;
    private Label clubLabel;
    private Label rOleModelLabel;
    private Label academicPerformance;
    private Label namelabel;
    private Label MotherName;
    private Label FatherName;
    private Label class2;
    private Label sessionLabel;
    private Label tag2;
    private Label ambition2;
    private ImageView imageView;
    private ImageView FatherImageView;
    private ImageView MotherImageView;
    private ImageView otherImageView;
    //Buffered Image
    BufferedImage bufferedImage2;
    BufferedImage bufferedImage3;
    BufferedImage bufferedImage=null;

        public ClassInformationThread( String id, Label namelabel, Label ageLabel, Label phoneNoLabel, Label parentPhoneNoLabel, Label fatherNameLabel,
                                      Label motherNameLabel, Label addressLabel, Label nextOfKinLabel, Label genderLabel,
                                      Label clubLabel, Label rOleModelLabel, ImageView image, ImageView Father,
                                      ImageView Mother, ImageView otherImage, Label FatherName, Label MotherName, Label otherName, Label class2, Label tag2, Label ambition2, Label session2){
            this.studentid=id;

            this.namelabel=namelabel;
            this.ageLabel=ageLabel;
            this.phoneNoLabel=phoneNoLabel;
            this.parentPhoneNoLabel=parentPhoneNoLabel;
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
            this.otherImageView=otherImage;
            this.FatherName=FatherName;
            this.MotherName=MotherName;
            this.otherNameLabel=otherName;
            this.class2=class2;
            this.sessionLabel=session2;
            this.tag2=tag2;
            this.ambition2=ambition2;
            System.out.println(" [ClassInformationThread] >> Student id: "+studentid);
        }
        @Override
        public void run() {
            Platform.runLater(()->{
                try {
                    new LoadingWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (Configuration.ipaddress!=null && Configuration.port!=null){
                OkHttpClient client=new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .build();

                Request request=new Request.Builder()
                        .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/retrievestudentinformation/"+studentid)
                        .addHeader("Authorization","Bearer "+ LogInModel.token)
                        .build();
                Response response;
                try {
                    response=client.newCall(request).execute();
                    System.out.println("[ClassInformationThread] >> Response:"+response);
                    if (response.code()==200){
                        System.out.println("[ClassInformationThread]"+response.body());
                        /////Retrieving and processing body,The response contains images and sting value
                        //Images is received as bytes and converted back from json to respective object
                        byte[] bytes=response.body().bytes();
                        System.out.println("[ClassInformationThread]: byte retrieved from server");
                        String data=new String(bytes,"UTF-8");
                        System.out.println("[ClassInformationThread] >> Response body:"+response.body());
                        System.out.println("[ClassInformationThread]:Converting Json body");
                        GsonBuilder builder=new GsonBuilder();
                        builder.setPrettyPrinting();
                        builder.serializeNulls();
                        Gson gson=builder.create();
                        RetrieveResponseEntity retrieveResponseEntity=gson.fromJson(data,RetrieveResponseEntity.class);
                        byte[] student=retrieveResponseEntity.getStudent();
                        byte[] father=retrieveResponseEntity.getFather();
                        byte[] mother=retrieveResponseEntity.getMother();
                        byte[] other=retrieveResponseEntity.getOther();
                        Path path=Paths.get(System.getProperty("user.dir")+"/MyChildSchool");
                        Files.createDirectories(path);
                        System.out.println("[ClassInformationThread]: Creating path on the Pc to store Images");
                        if (Files.exists(path)){
                            System.out.println("[ClassInformationThread]:Creating new Path");
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
                                Files.createDirectories(path);
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
                                System.out.println("[ClassInformationThread]:Processing mother image successful");
                            }
                            else {
                                System.out.println("[ClassInformationThread]:mother image is null cannot read file");
                            }
                            ///Processing Mother image
                            System.out.println("[ClassInformationThread]:Processing other image ");
                            File otherimage=new File(System.getProperty("user.dir")+"/MyChildSchool/other");
                            FileOutputStream oout=new FileOutputStream(otherimage);
                            oout.write(other);
                            if (otherimage!=null){
                                System.out.println("[ClassInformationThread]:other image is valid");
                                bufferedImage3=ImageIO.read(otherimage);
                                Image o=SwingFXUtils.toFXImage(bufferedImage3,null);
                                System.out.println("[ClassInformationThread]:Displaying other image");
                                Platform.runLater(()->{
                                    otherImageView.setImage(o);
                                });
                                System.out.println("[ClassInformationThread]:Processing other image successful");
                            }
                            else {
                                System.out.println("[ClassInformationThread]:other image is null cannot read file");
                            }
                            Platform.runLater(()->{
                                MotherName.setWrapText(true);
                                fatherNameLabel.setWrapText(true);
                                otherNameLabel.setWrapText(true);
                                ageLabel.isWrapText();
                                phoneNoLabel.isWrapText();
                                addressLabel.isWrapText();
                                nextOfKinLabel.isWrapText();
                                genderLabel.isWrapText();
                                clubLabel.isWrapText();
                                rOleModelLabel.isWrapText();
                                namelabel.isWrapText();
                                MotherName.setText(retrieveResponseEntity.getMothername());
                                FatherName.setText(retrieveResponseEntity.getFathername());
                                ageLabel.setText(String.valueOf(retrieveResponseEntity.getAge()));
                                phoneNoLabel.setText(retrieveResponseEntity.getPhoneno());
                                parentPhoneNoLabel.setText(retrieveResponseEntity.getParentphoneno());
                                fatherNameLabel.setText(retrieveResponseEntity.getFathername());
                                motherNameLabel.setText(retrieveResponseEntity.getMothername());
                                otherNameLabel.setText(retrieveResponseEntity.getGuardianname());
                                addressLabel.setText(retrieveResponseEntity.getAddress());
                                nextOfKinLabel.setText(retrieveResponseEntity.getNextofkin());
                                genderLabel.setText(retrieveResponseEntity.getGender());
                                clubLabel.setText(retrieveResponseEntity.getClub());
                                rOleModelLabel.setText(retrieveResponseEntity.getRolemodel());
                                namelabel.setText(retrieveResponseEntity.getStudentname());
                                tag2.setText(retrieveResponseEntity.getTag());
                                class2.setText(retrieveResponseEntity.getClas());
                                sessionLabel.setText(retrieveResponseEntity.getSession());
                                ambition2.setText(retrieveResponseEntity.getFutureambition());
                                LoadingWindow.window.close();
                            });

                        }else {
                            System.out.println("[ClassInformationThread]:path to store images does not exists" );
                        }
                        response.close();
                    }else {
                        //when something wrong happens, the server send the cause of the problem as a response body
                        String message=new String(response.body().bytes(),"UTF-8");
                        //Display alert dialog
                        Platform.runLater(()->{
                            boolean error=new ConnectionError().Connection(response.code()+":"+message);
                            if (error){
                                LoadingWindow.window.close();
                                InformationWindow.window1.close();
                                System.out.println("[ClassInformationThread]--> Connection Error,Window close");
                                response.close();
                            }
                        });

                    }
                } catch (IOException e) {
                    //Display an Alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                        if (error){
                            LoadingWindow.window.close();
                            InformationWindow.window1.close();
                            System.out.println("[ClassInformationThread]--> Connection Error,Window close");
                        }
                    });
                    System.out.println("[ClassInformationThread]: Request failed");
                    e.printStackTrace();
                }
            }else{
                Platform.runLater(()->{
                    new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
                });
            }
        }

}
