package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.StudentRegistration.RegisterationWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewTeacherController implements Initializable {
    public JFXTextField FirstName;
    public JFXTextField LastName;
    public JFXTextField MiddleName;
    public JFXTextField Class;
    public JFXTextField Subject1;
    public JFXTextField Subject3;
    public JFXTextField Subject2;
    public JFXTextField Subject4;
    public JFXTextField Address;
    public JFXTextField Email;
    public JFXTextField EntryYear;
    public JFXTextField PhoneNo;
    public JFXTextField SchoolAttended;
    public JFXTextField Course;
    public JFXComboBox<String> MaritalStatus;
    public ImageView TeacherImageView;
    public JFXRadioButton Female;
    public JFXRadioButton Male;

    public Label LastNameError;
    public Label FirstNameError;
    public Label MiddleNameError;
    public Label ClassError;
    public Label Subject1Error;
    public Label Subject2Error;
    public Label Subject3Error;
    public Label Subject4Error;
    public Label AddressError;
    public Label EmailError;
    public Label PhoneNoError;
    public Label SchoolError;
    public Label CourseError;
    public Label MaritalError;
    public Label EntryYearError;
    public Label ImageError;
    public Label GenderError;
    public Label ErrorLabel;

    String FirstNameInput;
    String LastNameInput;
    String MiddleNameInput;
    String ClassInput;
    String SubjectOne;
    String SubjectTwo;
    String SubjectThree;
    String SubjectFour;
    String EmailInput;
    String AddressInput;
    String EntryYearInput;
    String Gender;
    String MaritalStatusValue;
    long PhoneNoInput;
    String SchoolAttendedInput;
    String CourseInput;
    File file;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup=new ToggleGroup();
        Male.setToggleGroup(toggleGroup);
        Female.setToggleGroup(toggleGroup);
        ObservableList<String> list= FXCollections.observableArrayList();
        list.addAll("Single","Maried","other");
        MaritalStatus.getItems().addAll(list);

    }
    public void TeacherImageButtonClicked() throws IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Image","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("JPG","*JPG"));
        file=fileChooser.showOpenDialog(NewTeacherWindow.window);
        BufferedImage bufferedImage= ImageIO.read(file);
        Image image= SwingFXUtils.toFXImage(bufferedImage,null);
        TeacherImageView.setImage(image);
    }

    public void RegButtonClicked(){
        MaritalStatusValue=MaritalStatus.getSelectionModel().getSelectedItem();
        if (FirstName.getText().isEmpty()){
            FirstNameError.setVisible(true);

        }else {
            FirstNameError.setVisible(false);
            FirstNameInput=FirstName.getText();
        }

        if (LastName.getText().isEmpty()){
            LastNameError.setVisible(true);
        }else {
            LastNameInput=LastName.getText();
            LastNameError.setVisible(false);
        }

        if (MiddleName.getText().isEmpty()){
            MiddleNameError.setVisible(true);
        }else {
            MiddleNameInput=MiddleName.getText();
            MiddleNameError.setVisible(false);
        }

        if (Class.getText().isEmpty()){
            ClassError.setVisible(true);
        }else {
            ClassInput=Class.getText();
            ClassError.setVisible(false);
        }
        if (Subject1.getText().isEmpty()){
            Subject1Error.setVisible(true);
        }else {
            SubjectOne=Subject1.getText();
            Subject1Error.setVisible(false);

        }
        if (Subject2.getText().isEmpty()){
            Subject2Error.setVisible(true);
        }else {
            SubjectTwo=Subject2.getText();
            Subject2Error.setVisible(false);

        }
        if (Subject3.getText().isEmpty()){
            Subject3Error.setVisible(true);
        }else {
            SubjectThree=Subject3.getText();
            Subject3Error.setVisible(false);
        }
        if (Subject4.getText().isEmpty()){
            Subject4Error.setVisible(true);
        }else {
            SubjectFour=Subject4.getText();
            Subject3Error.setVisible(false);
        }
        if (Address.getText().isEmpty()){
            AddressError.setVisible(true);
        }else {
            AddressInput=Address.getText();
            AddressError.setVisible(false);
        }
        if (Email.getText().isEmpty()){
            EmailError.setVisible(true);
        }else {
            EmailInput=Email.getText();
            EmailError.setVisible(false);
        }
        if (EntryYear.getText().isEmpty()){
            EntryYearError.setVisible(true);

        }else {
            EntryYearInput= String.valueOf(EntryYear.getText());
            EntryYearError.setVisible(false);
        }
        if (PhoneNo.getText().isEmpty()){
            PhoneNoError.setVisible(true);
        }else {
          try{
              PhoneNoInput=Long.parseLong(PhoneNo.getText());
          }catch (NumberFormatException e){
              PhoneNoError.setVisible(false);
          }
        }
        if (SchoolAttended.getText().isEmpty()){
            SchoolError.setVisible(true);
        }else {
            SchoolAttendedInput=SchoolAttended.getText();
            SchoolError.setVisible(false);
        }
        if (Course.getText().isEmpty()){
            CourseError.setVisible(true);
        }else {
            CourseInput=Course.getText();
            CourseError.setVisible(false);
        }
        if (Male.isSelected()){
            Gender="Male";
        }else {
            Gender="Female";
        }


        if(!FirstName.getText().isEmpty() && !LastName.getText().isEmpty() && !MiddleName.getText().isEmpty()
                && !Class.getText().isEmpty()&& !Subject1.getText().isEmpty()&& !Subject2.getText().isEmpty()
                &&!Subject3.getText().isEmpty() && !Subject4.getText().isEmpty()&&
                !Address.getText().isEmpty() && !Email.getText().isEmpty()
                && !SchoolAttended.getText().isEmpty() && !EntryYear.getText().isEmpty()
                && !PhoneNo.getText().isEmpty() && !Course.getText().isEmpty() &&
                !PhoneNo.getText().matches("^[a-zA-Z]*$") && Female.isSelected() || Male.isSelected() || file!=null){

            new RegisterTecherThread(FirstNameInput,LastNameInput,MiddleNameInput,ClassInput,SubjectOne,SubjectTwo,
                    SubjectThree,SubjectFour,EmailInput,AddressInput,EntryYearInput,Gender,PhoneNoInput,SchoolAttendedInput,
                    CourseInput,file,MaritalStatusValue).start();
        }else {
            ErrorLabel.setVisible(true);
        }
    }
    public class RegisterTecherThread extends Thread{
        String FirstName;
        String LastName;
        String MiddleName;
        String Clas;
        String SubjectOne;
        String SubjectTwo;
        String SubjectThree;
        String SubjectFour;
        String Email;
        String Address;
        String EntryYear;
        String Gender;
        long PhoneNo;
        String MaritalStatus;
        String SchoolAttended;
        String Course;
        File file;
        Stage window;
        JFXProgressBar rpi;
        double counter=0.0;
        public RegisterTecherThread(  String FirstNameInput,String LastNameInput,String MiddleNameInput,String ClassInput,
                                      String Subject1,String Subject2,String Subject3,String Subject4,
                                      String EmailInput,String AddressInput,String EntryYearInput,String Gendr,
                                      long PhoneNoInput, String SchoolAttendedInput,String CourseInput, File TeacherImage,String Status){
            this.FirstName=FirstNameInput;
            this.LastName=LastNameInput;
            this.MiddleName=MiddleNameInput;
            this.Clas=ClassInput;
            this.SubjectOne=Subject1;
            this.SubjectTwo=Subject2;
            this.SubjectThree=Subject3;
            this.SubjectFour=Subject4;
            this.Email=EmailInput;
            this.Address=AddressInput;
            this.EntryYear=EntryYearInput;
            this.Gender=Gendr;
            this.PhoneNo=PhoneNoInput;
            this.SchoolAttended=SchoolAttendedInput;
            this.Course=CourseInput;
            this.file=TeacherImage;
            this.MaritalStatus=Status;
            System.out.println("[RegisterTeacherThread]: "+FirstName);
            System.out.println("[RegisterTeacherThread]: "+LastName);
            System.out.println("[RegisterTeacherThread]: "+MiddleName);
            System.out.println("[RegisterTeacherThread]: "+Clas);
            System.out.println("[RegisterTeacherThread]: "+SubjectOne);
            System.out.println("[RegisterTeacherThread]: "+SubjectTwo);
            System.out.println("[RegisterTeacherThread]: "+SubjectThree);
            System.out.println("[RegisterTeacherThread]: "+SubjectFour);
            System.out.println("[RegisterTeacherThread]: "+Email);
            System.out.println("[RegisterTeacherThread]: "+Address);
            System.out.println("[RegisterTeacherThread]: "+EntryYear);
            System.out.println("[RegisterTeacherThread]: "+Gender);
            System.out.println("[RegisterTeacherThread]: "+PhoneNo);
            System.out.println("[RegisterTeacherThread]: "+SchoolAttended);
            System.out.println("[RegisterTeacherThread]: "+Course);
            System.out.println("[RegisterTeacherThread]: "+MaritalStatus);
        }

        @Override
        public void run() {

            Connection conn=SqlConnection.connector();
            Platform.runLater(()->{
                window=new Stage();
                VBox vbox=new VBox();
                vbox.setSpacing(10);
                String Style="-fx-background-color:#0066CB;";
                window.setOnCloseRequest(event -> {
                    try {
                        if (conn!=null){conn.close();}
                        RegisterationWindow.window.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                vbox.setPadding(new Insets(10,10,10,10));
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setStyle(Style);
                Label label=new Label("Saving..");
                label.setFont(Font.font("Verdana", FontWeight.LIGHT,38));
                rpi=new JFXProgressBar();
                rpi.isIndeterminate();
                vbox.getChildren().addAll(label,rpi);
                Scene scene=new Scene(vbox,200,150);
                window.setScene(scene);
                window.centerOnScreen();
                window.show();
            });


            if (conn!=null){

            try {
                String Query="insert into TeacherInformation(FirstName,LastName,MiddleName,Class,SubjectOne,SubjectTwo,SubjectThree,SubjectFour,Email,Address,EntryYear,Gender,PhoneNo,SchoolAttended,Course,MaritalStatus,Picture) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement=conn.prepareStatement(Query);
                preparedStatement.setString(1,FirstName);
                preparedStatement.setString(2,LastName);
                preparedStatement.setString(3,MiddleName);
                preparedStatement.setString(4,Clas);
                preparedStatement.setString(5,SubjectOne);
                preparedStatement.setString(6,SubjectTwo);
                preparedStatement.setString(7,SubjectThree);
                preparedStatement.setString(8,SubjectFour);
                preparedStatement.setString(9,Email);
                preparedStatement.setString(10,Address);
                preparedStatement.setString(11,EntryYear);
                preparedStatement.setString(12,Gender);
                preparedStatement.setLong(13,PhoneNo);
                preparedStatement.setString(14,SchoolAttended);
                preparedStatement.setString(15,Course);
                preparedStatement.setString(16,MaritalStatus);

                if (file!=null){
                    FileInputStream fis=new FileInputStream(file);
                    preparedStatement.setBinaryStream(17,fis,(int) file.length());

                }else {
                    FileInputStream fis=new FileInputStream("src/image/not_available.jpg");
                    preparedStatement.setBinaryStream(17,fis,(int) file.length());
                    System.out.println("[RegisterTeacherThread]: Using Default image");
                }

                int a=preparedStatement.executeUpdate();
                while (true) {
                    System.out.println(counter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{
                        rpi.setProgress(counter);
                    });
                    counter+=0.01;
                    if (counter > 1) {
                        break;
                    }
                }

                System.out.println("[RegisterTeacherThread]: "+a);
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("[RegisterStudentThread] : Registeration Error");
            }finally {
                try {
                    if (conn!=null){
                        conn.close();
                    }
                    NewTeacherWindow.window.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            }else {
                Platform.runLater(()->{
                    boolean result=new ConnectionError().Connection(conn);
                    if (result==true){
                        NewTeacherWindow.window.close();
                    }
                });
            }

        }
    }
}
