package sample.StudentInformation;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.ConnectionError;
import sample.SqlConnection;
import sample.StudentInformation.InformationWindow;

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
            conn= SqlConnection.connector();
            String Query="Select * from "+classname+" where studentname=?";
            if (conn==null){
                Platform.runLater(()->{
                    boolean result=new ConnectionError().Connection(conn);
                    if (result==true){
                        InformationWindow.window1.close();
                    }
                });
            }else {
                try {
                    PreparedStatement preparedStatement=conn.prepareStatement(Query);
                    preparedStatement.setString(1,studentName);
                    ResultSet resultSet=preparedStatement.executeQuery();
                    while (resultSet.next()){
                        name=resultSet.getString("StudentName");
                        age=resultSet.getString("Age");
                        phono=resultSet.getFloat("Phoneno");
                        fathername=resultSet.getString("fathername");
                        mothername=resultSet.getString("mothername");
                        address=resultSet.getString("Address");
                        nextofkin=resultSet.getString("NextOfKin");
                        gender=resultSet.getString("Gender");
                        club=resultSet.getString("club");
                        rolemodel=resultSet.getString("RoleModel");
                        blob=resultSet.getBlob("picture");
                        blob2=resultSet.getBlob("Parentpicture");
                        blob3=resultSet.getBlob("Motherpicture");

                        //Directory path

                        Path path= Paths.get("C:\\users/Dell/AppData/Local/VXSchool/images/");
                        System.out.println("[ClassInformationThread]:Path Initiated");
                        //Geting Images//////////////////////////////////////

                        if (Files.exists(path)){
                            //Student image
                            if (blob!=null){
                                byte[] b;
                                b=blob.getBytes(1,(int) blob.length());
                                File imagefile=new File("C:\\users/Default/Pictures/imag.png");
                                FileOutputStream fos=new FileOutputStream(imagefile);
                                fos.write(b);
                                bufferedImage=ImageIO.read(imagefile);
                            }
                        }else {
                            System.out.println("[ClassInformationThread]: Directory not Exists");
                        }



                        //Checking Directory
                        System.out.println("[ClassInformationThread]:Checking Directory");
                        try{
                            if (!Files.exists(path)){
                                Files.createDirectories(path);

                            }else {
                                System.out.println("[ClassInformationThread]:Directory Exists");
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                        if (Files.exists(path)){
                            System.out.println("[ClassInformationThread]: Directory Exists");
                            File FatherImage=new File("C:\\users/Dell/AppData/Local/VXSchool/images/fatherimage.jpg");
                           if (blob2!=null){
                               //Father image
                               byte[] b2;
                               b2=blob2.getBytes(1,(int) blob2.length());
                               try{
                                   FileOutputStream fos2=new FileOutputStream(FatherImage);
                                   fos2.write(b2);
                               }catch (FileNotFoundException e){
                                   e.printStackTrace();
                               }

                               bufferedImage2=ImageIO.read(FatherImage);
                           }

                        }else{
                            System.out.println("[ClassInformationThread]: Directory not Exists");
                        }
                        //Mother Image Processing

                        if (Files.exists(path)){
                            System.out.println("[ClassInformationThread]: Directory Exists");
                            File MotherImage=new File("C:\\users/Dell/AppData/Local/VXSchool/images/fatherimage.jpg");
                        if (blob3!=null){
                            //Father image
                            byte[] b3;
                            b3=blob2.getBytes(1,(int) blob2.length());
                            try{
                                FileOutputStream fos3=new FileOutputStream(MotherImage);
                                fos3.write(b3);
                            }catch (FileNotFoundException e){
                                e.printStackTrace();
                            }

                            bufferedImage3=ImageIO.read(MotherImage);
                        }

                        }else {
                            System.out.println("[ClassInformationThread]: Directory not Exists");
                        }






                    }
                    Platform.runLater(()->{
                        System.out.println(age);
                        System.out.println("[ClassInformationThread]: Runlater-> Setting Layout");
                        namelabel.setText(name);
                        ageLabel.setText(age);
                        phoneNoLabel.setText(String.valueOf(phono));
                        fatherNameLabel.setText(fathername);
                        FatherName.setText(fathername);
                        motherNameLabel.setText(mothername);
                        MotherName.setText(mothername);
                        addressLabel.setText(address);
                        nextOfKinLabel.setText(nextofkin);
                        genderLabel.setText(gender);
                        clubLabel.setText(club);
                        rOleModelLabel.setText(rolemodel);
                        if (blob!=null){
                            Image image=SwingFXUtils.toFXImage(bufferedImage,null);
                            imageView.setImage(image);
                        }
                       if (blob2!=null){
                           Image image2=SwingFXUtils.toFXImage(bufferedImage2,null);
                           FatherImageView.setImage(image2);
                       }
                       if (blob3!=null){
                           Image image3=SwingFXUtils.toFXImage(bufferedImage3,null);
                           MotherImageView.setImage(image3);
                       }
                    });

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

}
