package sample.StudentRegistration;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConnectionError;
import sample.SelectWindows.Information.StudentSelectClassInfo;
import sample.SqlConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterStudentThread extends  Thread {

    private String studentname;
    private int age;
    private String fathername;
    private String mothername;
    private String NextOfKin;
    private String address;
    private float PhoneNo;
    private String NickName;
    private String Hobbies;
    private String TurnOn;
    private String TurnOff;
    private String Club;
    private String RoleModel;
    private String FutureAmbition;
    private String Gender;
    private String Clas;
    //file
    private File file;
    private File MotherPictureFile;
    private File FatherPictureFile;
    private File NotAvailableFile=new File("src/image/not_available.jpg");
    public static Connection conn;
    private double counter=0.0;
    private boolean result;
    private int a;
    public static Stage window;
    private Parent root;

    public RegisterStudentThread(){

    }
    public RegisterStudentThread(String studentname, int age, String fathername, String mothername, String NextOfKin,
                                 String address, float PhoneNo, String NickName, String Hobbies, String TurnOn,
                                 String TurnOff, String Club, String RoleModel, String FutureAmbition, String Gender,
                                 String clas, File file,File FatherPicture,File Mother){
        this.studentname=studentname;
        this.age=age;
        this.fathername=fathername;
        this.mothername=mothername;
        this.NextOfKin=NextOfKin;
        this.address=address;
        this.PhoneNo=PhoneNo;
        this.NickName=NickName;
        this.Hobbies=Hobbies;
        this.TurnOn=TurnOn;
        this.TurnOff=TurnOff;
        this.Club=Club;
        this.RoleModel=RoleModel;
        this.FutureAmbition=FutureAmbition;
        this.Gender=Gender;
        this.Clas=clas;
        this.file=file;
        this.FatherPictureFile=FatherPicture;
        this.MotherPictureFile=Mother;
    }
    @Override
    public void run() {

        System.out.println("[RegisterStudentThread]: Starting Thread" );
        System.out.println("[RegisterStudentThread]:"+studentname );
        System.out.println("[RegisterStudentThread]:"+age);
        System.out.println("[RegisterStudentThread]:"+fathername);
        System.out.println("[RegisterStudentThread]:"+mothername );
        System.out.println("[RegisterStudentThread]:"+NextOfKin );
        System.out.println("[RegisterStudentThread]:"+address);
        System.out.println("[RegisterStudentThread]:"+PhoneNo);
        System.out.println("[RegisterStudentThread]:"+NickName);
        System.out.println("[RegisterStudentThread]:"+Hobbies);
        System.out.println("[RegisterStudentThread]:"+TurnOn);
        System.out.println("[RegisterStudentThread]:"+TurnOff);
        System.out.println("[RegisterStudentThread]:"+ Club);
        System.out.println("[RegisterStudentThread]:"+RoleModel);
        System.out.println("[RegisterStudentThread]:"+FutureAmbition);
        System.out.println("[RegisterStudentThread]:"+Gender);
        System.out.println("[RegisterStudentThread]:"+FatherPictureFile);
        System.out.println("[RegisterStudentThread]:"+MotherPictureFile);



///////////////////////////////////saving form to database/////////////////////////////
        System.out.println("[RegisterStudentThread]: Saving form to database" );
        System.out.println("[RegisterStudentThread]: Creating Query" );
        String SaveNameQuery="INSERT INTO "+Clas+"(Studentname,Phoneno,nickname,hobbies,turnon,turnoff,club,rolemodel,futureambition,age,fathername,mothername,nextofkin,address,Gender,Picture,Parentpicture,Motherpicture) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Platform.runLater(()->{

            window=new Stage();
            try {
                root= FXMLLoader.load(getClass().getResource("LoadingDialog.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene=new Scene(root,400,200);
            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.UNDECORATED);
            window.setScene(scene);
            window.centerOnScreen();
            window.show();
        });
        System.out.println("[RegisterStudentThread]: Executing Query" );
        Connection conn= SqlConnection.connector();
        if (conn!=null){


            try {
                PreparedStatement preparedStatement=conn.prepareStatement(SaveNameQuery);
                preparedStatement.setString(1,studentname);
                preparedStatement.setFloat(2,PhoneNo);
                preparedStatement.setString(3,NickName);
                preparedStatement.setString(4,Hobbies);
                preparedStatement.setString(5,TurnOn);
                preparedStatement.setString(6,TurnOff);
                preparedStatement.setString(7,Club);
                preparedStatement.setString(8,RoleModel);
                preparedStatement.setString(9,FutureAmbition);
                preparedStatement.setInt(10,age);
                preparedStatement.setString(11,fathername);
                preparedStatement.setString(12,mothername);
                preparedStatement.setString(13,NextOfKin);
                preparedStatement.setString(14,address);
                preparedStatement.setString(15,Gender);
                if (file!=null){
                    try {
                        FileInputStream fis=new FileInputStream(file);
                        preparedStatement.setBinaryStream(16,fis,(int) file.length());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        FileInputStream fis=new FileInputStream("src/image/not_available.jpg");
                        preparedStatement.setBinaryStream(16,fis, (int) NotAvailableFile.length());
                    } catch (FileNotFoundException e) {
                        System.out.println("FIle Not found");
                    }
                }
                if (FatherPictureFile!=null){
                    try {
                        FileInputStream fis2=new FileInputStream(FatherPictureFile);
                        preparedStatement.setBinaryStream(17,fis2,(int) FatherPictureFile.length());
                    } catch (FileNotFoundException e) {
                        System.out.println("FIle Not found");
                    }
                }else {
                    try {
                        FileInputStream fis2=new FileInputStream("src/image/not_available.jpg");
                        preparedStatement.setBinaryStream(17,fis2,(int) NotAvailableFile.length() );
                    } catch (FileNotFoundException e) {
                        System.out.println("FIle Not found");
                    }
                }
                if (MotherPictureFile!=null){
                    try {
                        FileInputStream fis3=new FileInputStream(MotherPictureFile);
                        preparedStatement.setBinaryStream(18,fis3,(int) MotherPictureFile.length());
                    } catch (FileNotFoundException e) {
                        System.out.println("FIle Not found");
                    }
                }else {
                    try {
                        FileInputStream fis3=new FileInputStream("src/image/not_available.jpg");
                        preparedStatement.setBinaryStream(18,fis3,(int)NotAvailableFile.length());
                    } catch (FileNotFoundException e) {
                        System.out.println("FIle Not found");
                    }
                }
                //Increase the progress bar
                while (true) {
                    System.out.println(counter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{

                    });
                    counter+=0.01;
                    if (counter > 1) {
                        break;
                    }
                }


                if (conn==null){
                    result=new ConnectionError().Connection(conn);
                    Platform.runLater(()->{
                        RegisterationWindow.window.close();
                    });
                    System.out.println("[RegisterStudentThread]: result = "+ result);

                }else {
                    a=preparedStatement.executeUpdate();
                    //check if query is executed and close loading window
                    if(a==1){
                        window.close();
                    }
                    System.out.println("[RegisterStudentThread]: Query Executed");
                }
                System.out.println("[RegisterStudentThread]: "+ a );
                System.out.println("[RegisterStudentThread]: Query executed" );

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }finally {
                try {
                    assert conn != null;
                    if (!conn.isClosed()){
                        conn.close();
                    }
                    Platform.runLater(()->{
                        window.close();
                        RegisterationWindow.window.close();
                    });



                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }else {


            Platform.runLater(()->{
                boolean result=new ConnectionError().Connection(conn);
                if (result==true){
                    window.close();
                }
            });


        }


    }
}
