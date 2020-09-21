package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.DoubleStringConverter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Information.ClassNameThread;
import sample.LoginPage.DashBoard.SelectWindows.Information.ListViewNames;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

//This class is a controller class for StudentAssessmentSession, it gets the Session and
//make a list of student name when the session is clicked
//it handles the student scores in a table view
public class StudentSelectAssessmentSessionWindowController implements Initializable {
    public JFXComboBox<String> Clas;
    public VBox vbox;
    public String clas;
    TableView<Scores> tableview;
    public ProgressIndicator pgb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /////////This get the list of score sessions,This class Thread is an an Inner class /////////////
        new ClassThread(Clas,pgb).start();
        System.out.println("[StudentAssessmentSessionController]: Starting To get session from database");
        /////////////////////////////////////////////////////
        ///
        ////////////////////////////////////////////////
        //Whenever the Clas combox value is selected it loads the listview below
        ////////////////////////////////////////////

        Clas.setOnAction((e) -> {
            ////////////////////////////////////LIST VIEW START HERE//////////////////////////////////////////////
            ////////////////////////////////////List view Load Here//////////////////////////////////////////////
            JFXListView<String> listvie=new JFXListView<>();
            listvie.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            clas=Clas.getSelectionModel().getSelectedItem();
            System.out.println("[ClassThread]: ClassThreadFinished");
            //Creating Window
            ScrollPane layout = new ScrollPane();
            layout.setPannable(true);

            //This Listview Contains the names of student,The ClassnameThread retrieve the names from the database,the class name thread takes in
            //the value selected from the Class combobox and fetch in from the value,the value is a table in the Database


            ///////////////////////////////////////Assessment Table START//////////////////////////////////////////////////////
            //////////////////////assessment table Loads Here//////////////////////////////////////////////////////////////////
            listvie.getSelectionModel().selectedItemProperty().addListener((v, OldValue,NewValue)->{
                /////Score session here is the ComboBox on top of the screen,it loads the respective session score when its value is selected
                ComboBox<String> ScoreSession=new ComboBox<>();
                ///This ScoreSession below is a combobox on top of the Table,it load the scores when its value is selected
                new getScoreSessionThread(ScoreSession).start();
                ScrollPane scrollpane=new ScrollPane();
                scrollpane.setPannable(true);
                scrollpane.setPadding(new Insets(10,10,10,10));
                scrollpane.setFitToWidth(true);
                scrollpane.setFitToHeight(true);
                tableview=new TableView<>();
                tableview.setEditable(true);
                tableview.setPrefHeight(Control.USE_COMPUTED_SIZE);
                tableview.setPrefWidth(Control.USE_COMPUTED_SIZE);

                //////////////Table column
                TableColumn<Scores,String> SubjectColumn=new TableColumn<>("Subject");
                SubjectColumn.setMinWidth(100);
                SubjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
                SubjectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                final String[] SubjectInput = new String[1];

                //////subject column on edit
                SubjectColumn.setOnEditCommit((ce)->{
                    //ce.getRowValue().setSubject set the subject In the Score Class,ce.getRowValue().setSubject(ce.getNewValue()) set the
                    //SubjectField to the new Value typed in
                    String oldValue=ce.getOldValue();
                    ce.getRowValue().setSubject(ce.getNewValue());
                    SubjectInput[0] =ce.getNewValue();
                    System.out.println(SubjectInput[0]);
                    //This get the selected value of the Combo Box and pass it to the SaveScore Thread.The value selected coresspond to a table
                    //In the Database
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    System.out.println("[ScoreSessionValue]:"+ScoreSessionvalue);
                    //This save input score to Database
                    //score session valuer here is the table name ,NewValue arg here
                    //is the name of student selected
                    //Subjectinput is the enity to save,it is the Subject typed into the Subject Column
                    //oldvalue is the old value
                    new UpdateSubjectThread(ScoreSessionvalue,NewValue,SubjectInput[0],oldValue).start();
                });
                ///////////////////////////////////ScoreSession Handler
                //Score session is a combo box on top of the screen.It loads the score
                //for respective student when clicked
                ScoreSession.setOnAction((s)->{
                    String ScoreSessionTable=ScoreSession.getSelectionModel().getSelectedItem();
                    //this get the student selected score from database
                    new GetScoreThread(ScoreSessionTable,NewValue,tableview).start();
                });


                ////////////////////////////////////////////////////

                TableColumn<Scores,Double> FirstCaColumn=new TableColumn<>("first ca");
                FirstCaColumn.setMinWidth(100);
                FirstCaColumn.setCellValueFactory(new PropertyValueFactory<>("FirstCa"));
                FirstCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FirstCaInput = new Double[1];
                FirstCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setFirstCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FirstCaInput[0] =de.getNewValue();
                    System.out.println(FirstCaInput[0]);
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRowSubject] subject= "+sub );
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //FirstCaInput is the entity to save,it is the ca value typed into the FirstCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,FirstCaInput[0],sub,FirstCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> SecondCaColumn=new TableColumn<>("second ca");
                SecondCaColumn.setMinWidth(100);
                SecondCaColumn.setCellValueFactory(new PropertyValueFactory<>("SecondCa"));
                SecondCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SecondCaInput = new Double[1];
                SecondCaColumn.setOnEditCommit((ff)->{
                    ff.getRowValue().setSecondCa(Double.parseDouble(String.valueOf(ff.getNewValue())));
                    SecondCaInput[0] =ff.getNewValue();
                    System.out.println(SecondCaInput[0]);
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRowSubject] subject= "+sub );
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //SecondCaInput is the entity to save,it is the ca value typed into the SecondCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,SecondCaInput[0],sub,SecondCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> ThirdCaColumn=new TableColumn<>("third ca");
                ThirdCaColumn.setMinWidth(100);
                ThirdCaColumn.setCellValueFactory(new PropertyValueFactory<>("ThirdCa"));
                ThirdCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] ThirdCaInput = new Double[1];
                ThirdCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setThirdCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    ThirdCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(ThirdCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //ThirdCaInput is the entity to save,it is the ca value typed into the ThirdCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,ThirdCaInput[0],sub,ThirdCaColumn.getText()).start();

                });

                TableColumn<Scores,Double> FourthCaColumn=new TableColumn<>("fourth ca");
                FourthCaColumn.setMinWidth(100);
                FourthCaColumn.setCellValueFactory(new PropertyValueFactory<>("FourthCa"));
                FourthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FourthCaInput = new Double[1];
                FourthCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setFourthCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FourthCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(FirstCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //ThirdCaInput is the entity to save,it is the ca value typed into the FourthCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,FourthCaInput[0],sub,FourthCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> FifthCaColumn=new TableColumn<>("fifth ca");
                FifthCaColumn.setMinWidth(100);
                FifthCaColumn.setCellValueFactory(new PropertyValueFactory<>("FifthCa"));
                FifthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FifthCaInput = new Double[1];
                FifthCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setFifthCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FifthCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(FifthCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //FifthCaInput is the entity to save,it is the ca value typed into the FifthCA Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,FifthCaInput[0],sub,FifthCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> SixthCaColumn=new TableColumn<>("sixth ca");
                SixthCaColumn.setMinWidth(100);
                SixthCaColumn.setCellValueFactory(new PropertyValueFactory<>("SixthCa"));
                SixthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SixthCaInput = new Double[1];
                SixthCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setSixthCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    SixthCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(SixthCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //SixthCaInput is the entity to save,it is the ca value typed into the SixthXCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,SixthCaInput[0],sub,SixthCaColumn.getText()).start();
                });


                TableColumn<Scores,Double> SeventhCaColumn=new TableColumn<>("seventhca");
                SeventhCaColumn.setMinWidth(100);
                SeventhCaColumn.setCellValueFactory(new PropertyValueFactory<>("SeventhCa"));
                SeventhCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SeventhCaInput = new Double[1];
                SeventhCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setSeventhCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    SeventhCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(SeventhCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //SeventhCaInput is the entity to save,it is the ca value typed into the Seventh Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,SeventhCaInput[0],sub,SeventhCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> EightCaColumn=new TableColumn<>("eight ca");
                EightCaColumn.setMinWidth(100);
                EightCaColumn.setCellValueFactory(new PropertyValueFactory<>("EightCa"));
                EightCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] EightCaInput = new Double[1];
                EightCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setEightCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    EightCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(EightCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //EightCaInput is the entity to save,it is the ca value typed into the EightCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,EightCaInput[0],sub,EightCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> NinethCaColumn=new TableColumn<>("ninth ca");
                NinethCaColumn.setMinWidth(100);
                NinethCaColumn.setCellValueFactory(new PropertyValueFactory<>("NinthCa"));
                NinethCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] NinthCaInput = new Double[1];
                NinethCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setNinethCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    NinthCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(NinthCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //NinethCaInput is the entity to save,it is the ca value typed into the Nineth Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,NinthCaInput[0],sub,NinethCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> TenthCaColumn=new TableColumn<>("tenth ca");
                TenthCaColumn.setMinWidth(100);
                TenthCaColumn.setCellValueFactory(new PropertyValueFactory<>("TenthCa"));
                TenthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] TenthCaInput = new Double[1];
                TenthCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setTenthCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    TenthCaInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(TenthCaInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //TenthCaInput is the entity to save,it is the ca value typed into the TenthCa Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,TenthCaInput[0],sub,TenthCaColumn.getText()).start();
                });

                TableColumn<Scores,Double> ExamColumn=new TableColumn<>("Exam");
                ExamColumn.setMinWidth(100);
                ExamColumn.setCellValueFactory(new PropertyValueFactory<>("Exam"));
                ExamColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] ExamInput = new Double[1];
                ExamColumn.setOnEditCommit((de)->{
                    de.getRowValue().setExam(Double.parseDouble(String.valueOf(de.getNewValue())));
                    ExamInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(ExamInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg here is the name of student selected
                    //ExamInput is the entity to save,it is the exam value typed into the Exam Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,ExamInput[0],sub,ExamColumn.getText()).start();
                });

                TableColumn<Scores,Double> CummulativeColumn=new TableColumn<>("Cummulative");
                CummulativeColumn.setMinWidth(100);
                CummulativeColumn.setCellValueFactory(new PropertyValueFactory<>("Cumulative"));
                CummulativeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] CumInput = new Double[1];
                CummulativeColumn.setOnEditCommit((de)->{
                    de.getRowValue().setCumulative(Double.parseDouble(String.valueOf(de.getNewValue())));
                    CumInput[0] =de.getNewValue();
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRow] subject= "+sub );
                    System.out.println(CumInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //This save input score to Database
                    //scoresessionvalue here is the table name ,NewValue arg hereis the name of student selected
                    //Cumlative Input is the entity to save,it is the cummulative value typed into the cummulative Column
                    //sub is the subject
                    new UpdateScore(ScoreSessionvalue,NewValue,CumInput[0],sub,CummulativeColumn.getText()).start();

                });


                //////////////////////////////////////////////////
               // tableview.setItems(GetScores());
                tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                tableview.getColumns().addAll(SubjectColumn,FirstCaColumn,SecondCaColumn,ThirdCaColumn,FourthCaColumn,
                        FifthCaColumn,SixthCaColumn,SeventhCaColumn,EightCaColumn,NinethCaColumn,TenthCaColumn,
                        ExamColumn,CummulativeColumn);
                ///Button And EditText
                TextField subject=new TextField();
                subject.setPromptText("Add Subject");
                HBox scoreHbox=new HBox();
                scoreHbox.getChildren().addAll(subject);

                Button AddButton=new Button("Add");
                ///label
                Label labelerror=new Label("Check your Input");
                AddButton.setMinWidth(100);
                AddButton.setOnAction((ev)->{
                    String sub=subject.getText();
                    if(sub.isEmpty()){
                        labelerror.setVisible(true);
                    }
                    if (!sub.matches("^[A-Za-z]*$")){
                        new ConnectionError().Connection("Subject field contains invalid character");
                    }
                    if(ScoreSession.getValue()==null){
                        new ConnectionError().Connection("Please select session on top of the screen");
                    }
                    if (!sub.isEmpty() && sub.matches("^[A-Za-z]*$")&&ScoreSession.getValue()!=null){
                        ///This thread does the actual adding
                        new InsertSubjectThread(sub,NewValue,ScoreSession.getSelectionModel().getSelectedItem(),tableview).start();
                        labelerror.setVisible(false);
                        subject.clear();
                    }

                });
                Button DeleteButton=new Button("Delete");
                DeleteButton.setOnAction((ev2)->{
                    ObservableList<Scores> ScoreSelected;
                    ScoreSelected=tableview.getSelectionModel().getSelectedItems();
                    if(ScoreSession.getValue()==null){
                        new ConnectionError().Connection("Please select session on top of the screen");
                    }
                    if (ScoreSelected.isEmpty()){
                        new ConnectionError().Connection("Please select score to delete");
                    }
                    if (ScoreSession.getValue()!=null||!ScoreSelected.isEmpty()){
                        //This Thread does the actual deleting
                        new DeleteSubjectThread(ScoreSelected.get(0).getSubject(),NewValue,ScoreSession.getSelectionModel().getSelectedItem(),tableview).start();
                    }
                });
                DeleteButton.setMinWidth(100);
                labelerror.setVisible(false);
                String Style="-fx-text-fill:#EE1628;";
                labelerror.setStyle(Style);
                VBox vb=new VBox();
                vb.setAlignment(Pos.CENTER);
                vb.setSpacing(10);
                vb.getChildren().addAll(AddButton,DeleteButton,labelerror);


                ////////////////////////////
                VBox ScorevBox=new VBox();
                ScorevBox.setPadding(new Insets(10,10,10,10));
                ScorevBox.setSpacing(10);
                ScoreSession.setPromptText("Select Session");
                Label label=new Label("Select Session:");
                label.setStyle("-fx-text-fill:#D5D5D5;-fx-background-color:#004487;");
                label.setFont(Font.font("Verdana", FontWeight.BOLD,24));
                ScorevBox.setAlignment(Pos.TOP_CENTER);

                ScorevBox.getChildren().addAll(label,ScoreSession,tableview,scoreHbox,vb);
                scrollpane.setContent(ScorevBox);
                Scene scene=new Scene(scrollpane);
                StudentSelectAssessmentSessionWindow.window.setMaximized(true);
                StudentSelectAssessmentSessionWindow.window.setScene(scene);
            });

            ///////////////////////////////////////ASSESSMENT Table END//////////////////////////////////////////////////////
            //////////////////////assessment table END Here///////////////////////////////////////////////////////////////




            listvie.setMinHeight(700);
            Label label = new Label("Select Student");
            VBox box = new VBox();
            box.setAlignment(Pos.TOP_LEFT);
            box.getChildren().addAll(label, listvie);
            layout.setFitToWidth(true);
            layout.setFitToHeight(true);
            layout.setContent(box);
            layout.setPannable(true);
            layout.setPadding(new Insets(10, 10, 10, 10));
            StudentSelectAssessmentSessionWindow.window.setMaximized(true);
            Scene scene = new Scene(layout);
            StudentSelectAssessmentSessionWindow.window.setScene(scene);


            //Getting all studentname from db and adding it to the list view,adding names to the listview is done in this thread
            //clas argument here is the session in the combobox
            new ClassNameThread(clas, listvie,StudentSelectAssessmentSessionWindow.window).start();
            ///////////////////////////////////////////////////////////////////////
        });
        ////////////////////////////////////LIST VIEW END HERE//////////////////////////////////////////////
        ////////////////////////////////////List view stop Here//////////////////////////////////////////////
    }


    //////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
    //the progressbar indicate the progress
    public static class ClassThread extends Thread {
        public JFXComboBox<String> clas;
        public static List<String> list;
        ProgressIndicator progressBar;
        double progress=0.0;


        public ClassThread(JFXComboBox<String> comb,ProgressIndicator PgBar) {
            this.clas = comb;
            this.progressBar=PgBar;

        }

       public void run() {
        System.out.println("[Retrieving information session]: setting up okhttp client");
        OkHttpClient client=new OkHttpClient();

        System.out.println("[Retrieving information session]: setting up okhttp client request");
        Request request=new Request.Builder()
                .url("http://localhost:8080/retrieveinformationsession")
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("[Retrieving information session]: "+response);
            if (response.code()==200|| response.code()==212){

                System.out.println("[Retrieving information session]: session retrieved");
                ResponseBody body=response.body();
                try {
                    byte [] bytes=body.bytes();
                    //removing bracket from response
                    String data=new String(bytes,"UTF-8");
                    String data2=data.replace(']',' ');
                    String data3=data2.replace('[',' ');
                    String data4=data3.replaceAll(" ","");
                    list= Arrays.stream(data4.split(",")).collect(Collectors.toList());
                    Platform.runLater(()->{
                        clas.getItems().addAll(list);
                        progressBar.setProgress(1);
                    });
                    System.out.println(data);
                    response.close();
                } catch (IOException e) {
                    response.close();
                    e.printStackTrace();
                }

            }else {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Server:error"+response.code()+",Unable to retrieve session");
                    if (error){
                        StudentSelectAssessmentSessionWindow.window.close();
                        System.out.println("[SelectClassThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    StudentSelectAssessmentSessionWindow.window.close();
                    System.out.println("[SelectClassThread]--> Connection Error,Window close");
                }
            });
            System.out.println("[Retrieving information session]: Unable to get session information from server");
            e.printStackTrace();
        }

    }

    }
}
