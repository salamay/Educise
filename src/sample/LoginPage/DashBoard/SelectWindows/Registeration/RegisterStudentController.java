package sample.LoginPage.DashBoard.SelectWindows.Registeration;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetClassThread;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetSessionThread;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterStudentController implements Initializable {
    ObservableList<String> sessionlist;

    //file
    private File file;
    private File MotherImageFile;
    private File FatherImageFile;
    private File OtherImageFile;
    private String studentname;
    private String session;
    private String clas;
    private int age;
    private String fathername;
    private String tag;
    private String mothername;
    private String guardianName;
    private String nextofkin;
    private String address;
    private String Gender;
    private int PhoneNo;
    private int parentPhoneNumber;
    private String NickName;
    private String Hobbies;
    private String TurnOn;
    private String TurnOff;
    private String Club;
    private String RoleModel;
    private String FutureAmbition;
    public  Button RegButton;
    public TextField StudentName;
    public JFXComboBox<String> SessionComboBox;
    public JFXComboBox<String> classCombobox;
    public JFXComboBox<String> tagComboBox;
    public TextField Age;
    public TextField FatherName;
    public TextField MotherName;
    public JFXTextField GuardianName;
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
    public JFXTextField parentPhoneNumberTextField;

    public RadioButton Female;
    public RadioButton Male;
    public Label nameerror;
    public Label ageerror;
    public Label fathernameerror;
    public Label mothernameerror;
    public Label nextofkinerror;
    public Label guadianError;
    public Label addresserror;
    public Label gendererror;
    public Label SessionError;
    public Label classerror;
    public Label tagerror;
    public Label PhoneNoError;
    public Label NickNameError;
    public Label HobbiesError;
    public Label TurnOnError;
    public Label ParentPhoneNumberError;
    public Label TurnOffError;
    public Label ClubError;
    public Label RoleModelError;
    public Label FutureAmbitionError;
    public ImageView StudentPhoto;
    public ImageView MotherPicture;
    public ImageView FatherPicture;
    public ImageView OtherPicture;
    private BufferedImage bufferedImage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisterationWindow.window.setOnCloseRequest((e)->{
            //
        });
        tagComboBox.getItems().addAll("DAY","BOARDER");
        ProgressIndicator progressIndicator=new ProgressIndicator();
        new GetSessionThread(SessionComboBox,progressIndicator,"retrievesession").start();
        new GetClassThread(classCombobox,progressIndicator,"retrieveclasses").start();
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
        if (file.length()>=1e+6){
            new ConnectionError().Connection("Please choose a picture with lower size(< 1 MB)");
        }
        if (file!=null &&file.length()<=1e+6){
            bufferedImage=null;
            try {
                bufferedImage= ImageIO.read(file);
                Image image= SwingFXUtils.toFXImage(bufferedImage,null);
                StudentPhoto.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (MotherImageFile.length()>=1e+6){
            new ConnectionError().Connection("Please choose a picture with lower size( < 1 MB");
        }
        if (MotherImageFile!=null&&MotherImageFile.length()<=1e+6){
            BufferedImage bufferedImage=ImageIO.read(MotherImageFile);
            Image image=SwingFXUtils.toFXImage(bufferedImage,null);
            MotherPicture.setImage(image);
        }
    }

    public void fatherPhotoButtonClicked() throws IOException {
//        File Chooser
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Image","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("JPG","*.JPG"));
//        Image Processing
        FatherImageFile=fileChooser.showOpenDialog(new Stage());
        if (FatherImageFile.length()>=1e+6){
            new ConnectionError().Connection("Please choose a picture with lower size( < 1 MB");
        }
        if (FatherImageFile!=null&&FatherImageFile.length()<=1e+6){
            BufferedImage bufferedImage=ImageIO.read(FatherImageFile);
            Image image=SwingFXUtils.toFXImage(bufferedImage,null);
            FatherPicture.setImage(image);
        }
    }

    public void OtherPictureButtonClicked(){
        //Image File Chooser

        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All images","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("jpg","*.jpg")
        );
        OtherImageFile=fileChooser.showOpenDialog(new Stage());
        System.out.println(OtherImageFile);
        if (OtherImageFile.length()>=1e+6){
            new ConnectionError().Connection("Please choose a picture with lower size(< 1 MB)");
        }
        if (OtherImageFile!=null &&OtherImageFile.length()<=1e+6){
            bufferedImage=null;
            try {
                BufferedImage bufferedImage= ImageIO.read(OtherImageFile);
                Image image= SwingFXUtils.toFXImage(bufferedImage,null);
                OtherPicture.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void RegButtonClicked(){
        /////////////////////////////////////////////////////////////////
        //Checking input
        /////////////////////////////////////////////////////////////////
        //getting data
        System.out.println("[RegButtonClick()]: getting text");
        studentname=StudentName.getText();
        if(studentname.isEmpty()||!studentname.matches("^[A-z[ ]a-z]*$")){
            nameerror.setText("invalid name");
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
        System.out.println("[RegButtonClick()]: Getting guardian name");
        guardianName=GuardianName.getText();
        if(GuardianName.getText().isEmpty()){
            GuardianName.setText("Guardian's name field is empty");
            guadianError.setVisible(true);
        }else{
            gendererror.setVisible(false);
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
        System.out.println("[RegButtonClick()]: Getting session Combo value");
        session=SessionComboBox.getValue();
        if(session==null){
            SessionError.setVisible(true);
        }else {
            SessionError.setVisible(false);
        }
        clas=classCombobox.getValue();
        if(clas==null){
            classerror.setVisible(true);
        }else {
            classerror.setVisible(false);
        }
        tag=tagComboBox.getValue();
        if(tag==null){
            tagerror.setVisible(true);
        }else {
            tagerror.setVisible(false);
        }

        if (Female.isSelected()){
            gendererror.setVisible(false);
            System.out.println("[RegButtonClick()]: Female selected");
            Gender="Female";
        }else {
            gendererror.setText("*");
            gendererror.setVisible(true);
        }
        if (Male.isSelected()){
            gendererror.setVisible(false);
            System.out.println("[RegButtonClick()]: Male Selected");
            Gender="Male";
        }else {
            gendererror.setVisible(true);
        }

        if(!Male.isSelected()&&!Female.isSelected()){
            gendererror.setVisible(true);
        }else{
            gendererror.setVisible(false);
        }

        System.out.println("[RegButtonClick()]: Getting PhoneNo");

        try {
            System.out.println("[RegButtonClick()]: student phone number");
            PhoneNo=Integer.parseInt(PhoneNumberTextField.getText());
            if(PhoneNumberTextField.getText().isEmpty()){
                PhoneNoError.setVisible(true);
            }else{
                PhoneNoError.setVisible(false);
            }
        }catch(NumberFormatException e) {
            System.out.println("[RegButtonClick]: student phone number input error");
            PhoneNoError.setVisible(true);
        }

        try {
            System.out.println("[RegButtonClick()]: Parent phone number");
            parentPhoneNumber=Integer.parseInt(parentPhoneNumberTextField.getText());
            if(parentPhoneNumberTextField.getText().isEmpty()){
                ParentPhoneNumberError.setText("Parent number field error");
                ParentPhoneNumberError.setVisible(true);
            }else{
                ParentPhoneNumberError.setVisible(false);
            }
        }catch(NumberFormatException e){
            System.out.println("[RegButtonClick]: parent phone number input error");
            ParentPhoneNumberError.setText("Parent number contain error");
            ParentPhoneNumberError.setVisible(true);
        }

        System.out.println("[RegButtonClick]: Getting Nickname");
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
        if (file==null){
            new ConnectionError().Connection("Please select student image");
        }
///////////////Checked//////////////////////////////////////////////////////////////////////////////////////

            if(!StudentName.getText().isEmpty() && !Age.getText().isEmpty()  && !FatherName.getText().isEmpty()
                    && !MotherName.getText().isEmpty()&&!GuardianName.getText().isEmpty()&& !NextOfKin.getText().isEmpty()&& !Address.getText().isEmpty()
                    &&!PhoneNumberTextField.getText().isEmpty() && !NickNameTextField.getText().isEmpty()&&
                    !HobbiesTextField.getText().isEmpty() && !TurnOnTextField.getText().isEmpty()
                    && !TurnOffTextField.getText().isEmpty() && !ClubTextField.getText().isEmpty()
                    && !RoleModelTextField.getText().isEmpty() && !FutureAmbitionTextField.getText().isEmpty()&& clas!=null &&session!=null&&
                    age!=0 &&  !PhoneNumberTextField.getText().matches("^[a-zA-Z]*$")&&  !parentPhoneNumberTextField.getText().matches("^[a-zA-Z]*$")&&studentname.matches("^[A-Z[ ]a-z]*$")
                    && clas!=null&& file!=null &&tag!=null
            ){
                if (Female.isSelected() || Male.isSelected()){
                    // Registration Thread
                    new RegisterStudentThread(studentname,age,fathername,mothername,guardianName,nextofkin,address,PhoneNo,parentPhoneNumber,NickName,Hobbies,TurnOn,TurnOff,Club,RoleModel,FutureAmbition,Gender,session,file,FatherImageFile,MotherImageFile,OtherImageFile,clas,tag).start();
                }
            }
    }

}
