package sample.StudentInformation;

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
import sample.SqlConnection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

//this class does the fetching of Student information from database
public class ClassInformationThread extends  Thread {

        String classname;
        String studentName;
        Connection conn;

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

    private String name;
    private String age;
    float phono;
    private String fathername;
    private String mothername;
    private String nextofkin;
    private String address;
    private String gender;
    private String rolemodel;
    private String club;
    private ImageView imageView;
    private ImageView FatherImageView;
    private ImageView MotherImageView;
    //Buffered Image
    BufferedImage bufferedImage2;
    BufferedImage bufferedImage3;
    BufferedImage bufferedImage=null;
    Blob blob;
    Blob blob2;
    Blob blob3;
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
                    .url("http://localhost:8080/retrievestudentinformation")
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4MDc2MzgyLCJleHAiOjE1OTgxMTIzODJ9.OJrRife0Z7GLD-kg-GR2qmkLBLaSNhom0gHFXaHFDV8")
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println(response.body().toString());
            } catch (IOException e) {
                System.out.println("[ClassInformationThread]: Request failed");
                e.printStackTrace();
            }

        }

}
