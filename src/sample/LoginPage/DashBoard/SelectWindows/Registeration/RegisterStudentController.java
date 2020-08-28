package sample.LoginPage.DashBoard.SelectWindows.Registeration;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.SqlConnection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterStudentController implements Initializable {
    ObservableList<String> sessionlist;

    //file
    private File file;
    private File MotherImageFile;
    private File FatherImageFile;
    private String studentname;
    private String clas;
    private int age;
    private String fathername;
    private String mothername;
    private String nextofkin;
    private String address;
    private String Gender;
    private float PhoneNo;
    private String NickName;
    private String Hobbies;
    private String TurnOn;
    private String TurnOff;
    private String Club;
    private String RoleModel;
    private String FutureAmbition;
    public  Button RegButton;
    public TextField StudentName;
    public ComboBox<String> ClassComboBox;
    public TextField Age;
    public TextField FatherName;
    public TextField MotherName;
    public TextField NextOfKin;
    public TextField Address;
    public TextField PhoneNumberTextField;
    public TextField NickNameTextField;
    public TextField HobbiesTextField;
    public TextField TurnOnTextField;
    public TextField TurnOffTextField;
    public TextField ClubTextField;
    public TextField RoleModelTextField;
    public TextField FutureAmbitionTextField;

    public RadioButton Female;
    public RadioButton Male;

    public Label nameerror;
    public Label ageerror;
    public Label fathernameerror;
    public Label mothernameerror;
    public Label nextofkinerror;
    public Label addresserror;
    public Label gendererror;
    public Label classerror;
    public Label PhoneNoError;
    public Label NickNameError;
    public Label HobbiesError;
    public Label TurnOnError;
    public Label TurnOffError;
    public Label ClubError;
    public Label RoleModelError;
    public Label FutureAmbitionError;
    public ImageView StudentPhoto;
    public ImageView MotherPicture;
    public ImageView FatherPicture;
    BufferedImage bufferedImage;
    Connection conn;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn= SqlConnection.connector();
        if(conn!=null){
            String QUERY= "Select * from SessionTable";
            try {

                PreparedStatement preparedStatement=conn.prepareStatement(QUERY);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    sessionlist= FXCollections.observableArrayList();
                    System.out.println("[looping]"+ sessionlist);
                    sessionlist.addAll(resultSet.getString("sessionname"));
                    ClassComboBox.getItems().addAll(sessionlist);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
//            boolean result=new ConnectionError().Connection(conn);
//            if (result==true){
//                RegisterationWindow.window.close();
//            }
        }

        //Initializing combobox
        System.out.println("[Initializing()]: Initializing ComboBox");
        System.out.println("[Initializing()]: "+ sessionlist);


        ToggleGroup toggleGroup=new ToggleGroup();
        Male.setToggleGroup(toggleGroup);
        Female.setToggleGroup(toggleGroup);
    }
    public void StudentPhotoButtonClicked(){
        //Image File Chooser

        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All images","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("jpg","*.jpg")
        );
        file=fileChooser.showOpenDialog(new Stage());
        System.out.println(file);
        bufferedImage=null;
        try {
            bufferedImage= ImageIO.read(file);
           Image image= SwingFXUtils.toFXImage(bufferedImage,null);
           StudentPhoto.setImage(image);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void MotherPictureButtonClicked() throws IOException {
        //        File Chooser
        System.out.println("Mother Button Clicked");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Image","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("JPG","*.JPG"));
//        Image Processing
        MotherImageFile=fileChooser.showOpenDialog(new Stage());
        BufferedImage bufferedImage=ImageIO.read(MotherImageFile);
        Image image=SwingFXUtils.toFXImage(bufferedImage,null);
        MotherPicture.setImage(image);

    }

    public void fatherPhotoButtonClicked() throws IOException {
//        File Chooser
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Image","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("JPG","*.JPG"));
//        Image Processing
        FatherImageFile=fileChooser.showOpenDialog(new Stage());
        BufferedImage bufferedImage=ImageIO.read(FatherImageFile);
        Image image=SwingFXUtils.toFXImage(bufferedImage,null);
        FatherPicture.setImage(image);


    }
    public void RegButtonClicked(){
        /////////////////////////////////////////////////////////////////
        //Checking input
        /////////////////////////////////////////////////////////////////
        //getting data
        System.out.println("[RegButtonClick()]: getting text");
        studentname=StudentName.getText();
        if(StudentName.getText().isEmpty()){
            nameerror.setText("class field is empty");
            nameerror.setVisible(true);
        }else {
            nameerror.setVisible(false);
        }
        try {
            System.out.println("[RegButtonClick()]: Getting age");

            age = Integer.parseInt(Age.getText());
            if(Age.getText().isEmpty()){
                ageerror.setText("Age field error");
                ageerror.setVisible(true);
            }else{
                ageerror.setVisible(false);
        }
        }catch(NumberFormatException e){
            System.out.println("[RegButtonClick()]: input error");
            ageerror.setText("Age field error");
            ageerror.setVisible(true);
        }
        System.out.println("[RegButtonClick()]: Getting Father name");
        fathername=FatherName.getText();
        if(FatherName.getText().isEmpty()){
            fathernameerror.setText("Father's name field is empty");
            fathernameerror.setVisible(true);
        }else{
            fathernameerror.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting mother name");
        mothername=MotherName.getText();
        if(MotherName.getText().isEmpty()){
            mothernameerror.setText("Mother's name field is empty");
            mothernameerror.setVisible(true);
        }else{
            mothernameerror.setVisible(false);
        }
        System.out.println("[RegButtonClick()]: Getting next of kin name");

        nextofkin=NextOfKin.getText();
        if(NextOfKin.getText().isEmpty()){
            nextofkinerror.setText("Next of kin field is empty");
            nextofkinerror.setVisible(true);
        }else {
            nextofkinerror.setVisible(false);
        }
        System.out.println("[RegButtonClick()]: Getting Address");
        address=Address.getText();
        if(Address.getText().isEmpty()){
            addresserror.setText("Address field is empty");
            addresserror.setVisible(true);
        }
        else{
            addresserror.setVisible(false);
        }
        System.out.println("[RegButtonClick()]: Getting Class Combo value");
        clas=ClassComboBox.getValue();
        if(clas==null){
            classerror.setVisible(true);
        }else {
            classerror.setVisible(false);
        }

        if (Female.isSelected()){
            System.out.println("[RegButtonClick()]: Female selected");
            Gender="Female";
        }
        if (Male.isSelected()){
            System.out.println("[RegButtonClick()]: Male Selected");
            Gender="Male";
        }

        if(!Male.isSelected()&&!Female.isSelected()){
            gendererror.setVisible(true);
        }else{
            gendererror.setVisible(false);
        }




        System.out.println("[RegButtonClick()]: Getting PhoneNo");
        try {
            PhoneNo = Float.parseFloat(PhoneNumberTextField.getText());
            if(PhoneNumberTextField.getText().isEmpty()){

                PhoneNoError.setVisible(true);
            }else{
                PhoneNoError.setVisible(false);
            }
        }catch(NumberFormatException e){
            System.out.println("[RegButtonClick()]: Phone number input error");
            PhoneNoError.setVisible(true);
        }

        System.out.println("[RegButtonClick()]: Getting Nickname");
         NickName=NickNameTextField.getText();
        if(NickNameTextField.getText().isEmpty()){
            NickNameError.setVisible(true);
        }
        else{
            NickNameError.setVisible(false);
        }


        System.out.println("[RegButtonClick()]: Getting Hobbies");
        Hobbies =HobbiesTextField.getText();
        if(HobbiesTextField.getText().isEmpty()){
            HobbiesError.setVisible(true);
        }
        else{
            HobbiesError.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting TurnOn");
        TurnOn =TurnOnTextField.getText();
        if(TurnOnTextField.getText().isEmpty()){
            TurnOnError.setVisible(true);
        }
        else{
            TurnOnError.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting TurnOff");
        TurnOff=TurnOffTextField.getText();
        if(TurnOffTextField.getText().isEmpty()){
            TurnOffError.setVisible(true);
        }
        else{
            TurnOffError.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting Club");
        Club =ClubTextField.getText();
        if(ClubTextField.getText().isEmpty()){
            ClubError.setVisible(true);
        }
        else{
            ClubError.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting RoleModel");
        RoleModel =RoleModelTextField.getText();
        if(RoleModelTextField.getText().isEmpty()){
            RoleModelError.setVisible(true);
        }
        else{
            RoleModelError.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting FutureAmbition");
        FutureAmbition =FutureAmbitionTextField.getText();
        if(FutureAmbitionTextField.getText().isEmpty()){
            FutureAmbitionError.setVisible(true);
        }
        else{
            FutureAmbitionError.setVisible(false);
        }

///////////////Checked//////////////////////////////////////////////////////////////////////////////////////

        if (conn==null){
//            boolean result=new ConnectionError().Connection(conn);
//            if (result==true){
//                RegisterationWindow.window.close();
//            }
//            System.out.println("Connection Error");

        }else{

            if(!StudentName.getText().isEmpty() && !Age.getText().isEmpty()  && !FatherName.getText().isEmpty()
                    && !MotherName.getText().isEmpty()&& !NextOfKin.getText().isEmpty()&& !Address.getText().isEmpty()
                    &&!PhoneNumberTextField.getText().isEmpty() && !NickNameTextField.getText().isEmpty()&&
                    !HobbiesTextField.getText().isEmpty() && !TurnOnTextField.getText().isEmpty()
                    && !TurnOffTextField.getText().isEmpty() && !ClubTextField.getText().isEmpty()
                    && !RoleModelTextField.getText().isEmpty() && !FutureAmbitionTextField.getText().isEmpty() &&
                    !Age.getText().matches("^[a-zA-Z]*$") &&  !PhoneNumberTextField.getText().matches("^[a-zA-Z]*$")
                    && !clas.isEmpty() && Female.isSelected() || Male.isSelected() || file!=null
            )  {

                // Registration Thread
                new RegisterStudentThread(studentname,age,fathername,mothername,nextofkin,address,PhoneNo,
                        NickName,Hobbies,TurnOn,TurnOff,Club,RoleModel,FutureAmbition,Gender,clas,file,FatherImageFile,MotherImageFile).start();
            }


        }



    }

}
