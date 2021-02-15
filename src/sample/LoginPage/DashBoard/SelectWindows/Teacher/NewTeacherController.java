package sample.LoginPage.DashBoard.SelectWindows.Teacher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
    public JFXTextField accountNumberTextField;
    public JFXTextField accountnameTextField;
    public JFXComboBox<String> MaritalStatus;
    public JFXComboBox<String> bankNameComboBox;
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

    private String FirstNameInput;
    private String LastNameInput;
    private String MiddleNameInput;
    private String ClassInput;
    private String SubjectOne;
    private String SubjectTwo;
    private String SubjectThree;
    private String SubjectFour;
    private String EmailInput;
    private String AddressInput;
    private String EntryYearInput;
    private String Gender;
    private String MaritalStatusValue;
    private String bank;
    private String bankAccount;
    private String accountName;
    private long PhoneNoInput;
    private String SchoolAttendedInput;
    private String CourseInput;
    private File file;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup=new ToggleGroup();
        Male.setToggleGroup(toggleGroup);
        Female.setToggleGroup(toggleGroup);
        ObservableList<String> list= FXCollections.observableArrayList();
        list.addAll("Single","Maried","other");
        MaritalStatus.getItems().addAll(list);
        accountnameTextField.setEditable(false);
        bankNameComboBox.getItems().addAll("First bank");
    }
    public void TeacherImageButtonClicked() throws IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Image","*.*"),
                new FileChooser.ExtensionFilter("PNG","*.PNG"),
                new FileChooser.ExtensionFilter("JPG","*JPG"));
        file=fileChooser.showOpenDialog(NewTeacherWindow.window);
        if (file!=null){
            BufferedImage bufferedImage= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bufferedImage,null);
            TeacherImageView.setImage(image);
        }
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
        if (bankNameComboBox.getSelectionModel().getSelectedItem()!=null){
            bank=bankNameComboBox.getSelectionModel().getSelectedItem();
        }else {
            new ConnectionError().Connection("Please select a bank");
        }
        if (accountNumberTextField.getText()!=null || !accountNumberTextField.getText().isEmpty()){
            bankAccount=accountNumberTextField.getText();
        }else {
            new ConnectionError().Connection("Please enter a valid account number");
        }
        if (accountnameTextField.getText()!=null || !accountnameTextField.getText().isEmpty()){
            accountName=accountnameTextField.getText();
        }else {
            new ConnectionError().Connection("Account is invalid");
        }
        if (file==null){
            new ConnectionError().Connection("Please select teacher image");
        }
        if(!FirstName.getText().isEmpty() && !LastName.getText().isEmpty() && !MiddleName.getText().isEmpty()
                && !Class.getText().isEmpty()&& !Subject1.getText().isEmpty()&& !Subject2.getText().isEmpty()
                &&!Subject3.getText().isEmpty() && !Subject4.getText().isEmpty()&&
                !Address.getText().isEmpty() && !Email.getText().isEmpty()
                && !SchoolAttended.getText().isEmpty() && !EntryYear.getText().isEmpty()
                && !PhoneNo.getText().isEmpty() && !Course.getText().isEmpty() && !accountNumberTextField.getText().isEmpty()&& !accountnameTextField.getText().isEmpty()&& bankNameComboBox.getSelectionModel().getSelectedItem()!=null&&
                !PhoneNo.getText().matches("^[a-zA-Z]*$") && Female.isSelected() || Male.isSelected() || file!=null){

            new RegisterTecherThread(FirstNameInput,LastNameInput,MiddleNameInput,ClassInput,SubjectOne,SubjectTwo,
                    SubjectThree,SubjectFour,EmailInput,AddressInput,EntryYearInput,Gender,PhoneNoInput,SchoolAttendedInput,
                    CourseInput,file,MaritalStatusValue,accountName,bankAccount,bank).start();
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
        String accountName;
        String bankAccountNumber;
        String bankname;
        long PhoneNo;
        String MaritalStatus;
        String SchoolAttended;
        String Course;
        File file;
        double counter=0.0;
        public RegisterTecherThread(  String FirstNameInput,String LastNameInput,String MiddleNameInput,String ClassInput,
                                      String Subject1,String Subject2,String Subject3,String Subject4,
                                      String EmailInput,String AddressInput,String EntryYearInput,String Gendr,
                                      long PhoneNoInput, String SchoolAttendedInput,String CourseInput, File TeacherImage,String Status,String accountName,String bankAccountNumber,String bankname){
            Platform.runLater(()->{
                try {
                    new LoadingWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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
            this.bankAccountNumber=bankAccountNumber;
            this.accountName=accountName;
            this.bankname=bankname;
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
            System.out.println("[RegisterTeacherThread]: "+bankname);
            System.out.println("[RegisterTeacherThread]: "+bankAccountNumber);
            System.out.println("[RegisterTeacherThread]: "+accountName);
        }

        @Override
        public void run() {

            try {
                NewTeacherRequestEntity newTeacherRequestEntity=new NewTeacherRequestEntity();
                newTeacherRequestEntity.setFirstname(FirstName);
                newTeacherRequestEntity.setLastname(LastName);
                newTeacherRequestEntity.setMiddlename(MiddleName);
                newTeacherRequestEntity.setClas(Clas);
                newTeacherRequestEntity.setSubjectone(SubjectOne);
                newTeacherRequestEntity.setSubjecttwo(SubjectTwo);
                newTeacherRequestEntity.setSubjectthree(SubjectThree);
                newTeacherRequestEntity.setSubjectfour(SubjectFour);
                newTeacherRequestEntity.setEmail(Email);
                newTeacherRequestEntity.setAddress(Address);
                newTeacherRequestEntity.setEntryyear(EntryYear);
                newTeacherRequestEntity.setGender(Gender);
                newTeacherRequestEntity.setPhoneno(String.valueOf(PhoneNo));
                newTeacherRequestEntity.setSchoolattended(SchoolAttended);
                newTeacherRequestEntity.setCourse(Course);
                newTeacherRequestEntity.setMaritalstatus(MaritalStatus);
                newTeacherRequestEntity.setBankname(bankname);
                newTeacherRequestEntity.setBankaccountnumber(bankAccountNumber);
                newTeacherRequestEntity.setAccountname(accountName);
                if (file!=null){
                    byte[] bytes= Files.readAllBytes(file.toPath());
                    newTeacherRequestEntity.setFile(bytes);
                }else {
                    File NotAvailableFile = new File("src/image/not_available.jpg");
                    System.out.println("[RegisterTeacherThread]: Using Default image");
                    byte[] bytes= Files.readAllBytes(NotAvailableFile.toPath());
                    newTeacherRequestEntity.setFile(bytes);
                }
                System.out.println("[NewTeacherController] :Preparing Json Body");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                String rawjson=gson.toJson(newTeacherRequestEntity);

                System.out.println("[NewTeacherController] :Setting up client");
                OkHttpClient client=new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .build();

                System.out.println("[NewTeacherController] :Preparing request body");
                RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),rawjson);
                if (Configuration.ipaddress!=null&&Configuration.port!=null){
                    Request request=new Request.Builder()
                            .post(requestBody)
                            .addHeader("Authorization","Bearer "+ LogInModel.token)
                            .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/registerteacher")
                            .build();
                    Response response=client.newCall(request).execute();
                    if (response.code()==200){
                        System.out.println("[NewTeacherController]-->Response:"+response);
                        System.out.println("[NewTeacherController]-->ResponseBody--->"+response.body());
                        Platform.runLater(()->{
                            LoadingWindow.window.close();
                            boolean error=new ConnectionError().Connection("SUCCESS");
                            if (error){

                            }
                        });
                        response.close();
                    }else{
                        String message=new String(response.body().bytes(),"UTF-8");
                        System.out.println("[NewTeacherController]--> server return error:"+response.code()+" Unable to Register Teacher");
                        Platform.runLater(()->{
                            LoadingWindow.window.close();
                            boolean error=new ConnectionError().Connection(response.code()+":"+message);
                            if (error){
                                System.out.println("[NewTeacherController]--> Connection Error,Window close");
                            }
                        });
                    }
                    response.close();
                }else{
                    Platform.runLater(()->{
                        new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
                    });
                }

            } catch ( FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("[NewTeacherController] : Teacher Registeration Error");
            } catch (IOException e) {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish :CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[NewTeacherController]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }
    }
}
