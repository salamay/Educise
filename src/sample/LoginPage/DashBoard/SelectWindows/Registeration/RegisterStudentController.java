package sample.LoginPage.DashBoard.SelectWindows.Registeration;


import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionWindow;
import sample.LoginPage.LogInModel;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RegisterStudentController implements Initializable {
    ObservableList<String> sessionlist;

    //file
    private File file;
    private File MotherImageFile;
    private File FatherImageFile;
    private String studentname;
    private String session;
    private String clas;
    private int age;
    private String fathername;
    private String tag;
    private String mothername;
    private String nextofkin;
    private String address;
    private String Gender;
    private String PhoneNo;
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
    public Label SessionError;
    public Label classerror;
    public Label tagerror;
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
    private BufferedImage bufferedImage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classCombobox.getItems().addAll("Nursery 1","Nursery 2","Primary 1","Primary 2","Primary 3","Primary 4","Primary 5","JSS 1","JSS 2","JSS 3","SS 1","SS 2","SS 3");
        tagComboBox.getItems().addAll("DAY","BOARDER");
        ProgressIndicator progressIndicator=new ProgressIndicator();
        new ClassThread(SessionComboBox,progressIndicator).start();
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
        if (file!=null){
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
        if (MotherImageFile!=null){
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
        if (FatherImageFile!=null){
            BufferedImage bufferedImage=ImageIO.read(FatherImageFile);
            Image image=SwingFXUtils.toFXImage(bufferedImage,null);
            FatherPicture.setImage(image);
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
        PhoneNo=PhoneNumberTextField.getText();
            if(PhoneNumberTextField.getText().isEmpty()){
                PhoneNoError.setVisible(true);
            }else{
                PhoneNoError.setVisible(false);
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
        if (file==null){
            new ConnectionError().Connection("Please select student image");
        }
///////////////Checked//////////////////////////////////////////////////////////////////////////////////////

            if(!StudentName.getText().isEmpty() && !Age.getText().isEmpty()  && !FatherName.getText().isEmpty()
                    && !MotherName.getText().isEmpty()&& !NextOfKin.getText().isEmpty()&& !Address.getText().isEmpty()
                    &&!PhoneNumberTextField.getText().isEmpty() && !NickNameTextField.getText().isEmpty()&&
                    !HobbiesTextField.getText().isEmpty() && !TurnOnTextField.getText().isEmpty()
                    && !TurnOffTextField.getText().isEmpty() && !ClubTextField.getText().isEmpty()
                    && !RoleModelTextField.getText().isEmpty() && !FutureAmbitionTextField.getText().isEmpty()&&
                    age!=0 &&  !PhoneNumberTextField.getText().matches("^[a-zA-Z]*$")&&studentname.matches("^[A-Z[ ]a-z]*$")
                    && clas!=null&& file!=null &&tag!=null
            ){
                if (Female.isSelected() || Male.isSelected()){
                    // Registration Thread
                    new RegisterStudentThread(studentname,age,fathername,mothername,nextofkin,address,PhoneNo,NickName,Hobbies,TurnOn,TurnOff,Club,RoleModel,FutureAmbition,Gender,session,file,FatherImageFile,MotherImageFile,clas,tag).start();
                }
            }
    }
    //////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
    //the progressbar indicate the progress
    public class ClassThread extends Thread {
        private JFXComboBox<String> clas;
        private ProgressIndicator pgb;
        public ClassThread(JFXComboBox<String> comb, ProgressIndicator progressBar) {
            this.clas = comb;
            this.pgb = progressBar;
        }

        @Override
        public void run() {

            System.out.println("[ClassThread]: setting up okhttp client");
            OkHttpClient client = new OkHttpClient();

            System.out.println("[ClassThread]: setting up okhttp client request");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/retrieveinformationsession")
                    .addHeader("Authorization", "Bearer " + LogInModel.token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println("[ClassThread]: " + response);
                if (response.code() == 200 || response.code() == 212 || response.code() == 201) {

                    System.out.println("[ClassThread]: session retrieved");
                    ResponseBody body = response.body();
                    try {
                        byte[] bytes = body.bytes();
                        //removing bracket from response
                        String data = new String(bytes, "UTF-8");
                        String data2 = data.replace(']', ' ');
                        String data3 = data2.replace('[', ' ');
                        String data4 = data3.replaceAll(" ", "");
                        List<String> list = Arrays.stream(data4.split(",")).collect(Collectors.toList());

                        Platform.runLater(() -> {
                            clas.getItems().addAll(list);
                        });
                        System.out.println(data);
                        response.close();
                        Platform.runLater(() -> {
                            pgb.setProgress(100);
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //Display an Alert dialog
                    Platform.runLater(() -> {
                        boolean error = new ConnectionError().Connection("server:error " + response.code() + " Unable to get session,CHECK INTERNET CONNECTION");
                        if (error) {
                            SelectInformationSesssionWindow.StudentWindow.close();
                            System.out.println("[ClassThread]--> server error,unable to get session");
                        }
                    });
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(() -> {
                    boolean error = new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error) {
                        SelectInformationSesssionWindow.StudentWindow.close();
                        System.out.println("[ClassThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[ClassThread]: Unable to get session information from server");
                e.printStackTrace();
            }

        }

    }
}
