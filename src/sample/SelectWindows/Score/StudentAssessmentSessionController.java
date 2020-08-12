package sample.SelectWindows.Score;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import sample.ConnectionError;
import sample.SelectWindows.Information.ClassNameThread;
import sample.SqlConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//This class is a controller class for StudentAssessmentSession, it gets the Session and
//make a list of student name when the session is clicked
//it handles the student scores in a table view
public class StudentAssessmentSessionController implements Initializable {
    public ComboBox<String> Clas;
    public VBox vbox;
    public String clas;
    Connection conn;
    TableView<Scores> tableview;
    public ProgressIndicator pgb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

/////////This get the list of sessions /////////////
        new ClassThread(Clas,conn,pgb).start();
        System.out.println("[StudentAssessmentSessionController]: Starting To get session from database");
/////////////////////////////////////////////////////

        Clas.setOnAction((e) -> {
            JFXListView<String> listvie=new JFXListView<>();
            clas=Clas.getSelectionModel().getSelectedItem();
            System.out.println("[ClassThread]: ClassThreadFinished");
            //Creating Window
            ScrollPane layout = new ScrollPane();
            layout.setPannable(true);

            listvie.getSelectionModel().selectedItemProperty().addListener((v, OldValue,NewValue)->{
                ComboBox<String> ScoreSession=new ComboBox<>();
                ScrollPane scrollpane=new ScrollPane();
                scrollpane.setPannable(true);
                scrollpane.setPadding(new Insets(10,10,10,10));
             //  scrollpane.setFitToWidth(true);
              // scrollpane.setFitToHeight(true);

                tableview=new TableView<>();
                tableview.setEditable(true);
                tableview.setPrefHeight(Control.USE_COMPUTED_SIZE);
                tableview.setPrefWidth(Control.USE_COMPUTED_SIZE);
                //Table column
                TableColumn<Scores,String> SubjectColumn=new TableColumn<>("Subject");
                SubjectColumn.setMinWidth(100);
                SubjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
                SubjectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                final String[] SubjectInput = new String[1];
                //subject column on edit
                SubjectColumn.setOnEditCommit((ce)->{
                    ce.getRowValue().setSubject(ce.getNewValue());
                    SubjectInput[0] =ce.getNewValue();
                    System.out.println(SubjectInput[0]);
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    System.out.println("[ScoreSessionValue]:"+ScoreSessionvalue);
                    //save input score to db
                    //score session valuer here is the table name ,NewValue arg here
                    //is the name of student selected
                    new SaveScoreThread(ScoreSessionvalue,NewValue,SubjectInput[0]).start();

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

                TableColumn<Scores,Double> FirstCaColumn=new TableColumn<>("1st CA");
                FirstCaColumn.setMinWidth(100);
                FirstCaColumn.setCellValueFactory(new PropertyValueFactory<>("FirstCa"));
                FirstCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FirstCaInput = new Double[1];
                FirstCaColumn.setOnEditCommit((de)->{
                    de.getRowValue().setFirstCa(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FirstCaInput[0] =de.getNewValue();
                    System.out.println(FirstCaInput[0]);
                    System.out.println(String.valueOf(FirstCaColumn.getText()));
                    String sub=tableview.getSelectionModel().getSelectedItem().getSubject();
                    System.out.println("[SelectedRowSubject] subject= "+sub );
                    String ScoreSessionvalue=ScoreSession.getSelectionModel().getSelectedItem();
                    //save input score to db
                    //score session valuer here is the table name ,NewValue arg here
                    //is the name of student selected
                    new SaveScoreThread2(ScoreSessionvalue,NewValue,FirstCaInput[0],sub).start();

                });

                TableColumn<Scores,Double> SecondCaColumn=new TableColumn<>("2ndCA");
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
                    new SaveScoreThread3(ScoreSessionvalue,NewValue,SecondCaInput[0],sub).start();
                });

                TableColumn<Scores,Double> ThirdCaColumn=new TableColumn<>("3rd CA");
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
                    new SaveScoreThread4(ScoreSessionvalue,NewValue,ThirdCaInput[0],sub).start();


                });

                TableColumn<Scores,Double> FourthCaColumn=new TableColumn<>("4th CA");
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
                    new SaveScoreThread5(ScoreSessionvalue,NewValue,FourthCaInput[0],sub).start();

                });

                TableColumn<Scores,Double> FifthCaColumn=new TableColumn<>("5th CA");
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
                    new SaveScoreThread6(ScoreSessionvalue,NewValue,FifthCaInput[0],sub).start();

                });

                TableColumn<Scores,Double> SixthCaColumn=new TableColumn<>("6th CA");
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
                    new SaveScoreThread7(ScoreSessionvalue,NewValue,SixthCaInput[0],sub).start();

                });


                TableColumn<Scores,Double> SeventhCaColumn=new TableColumn<>("7th CA");
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
                    new SaveScoreThread8(ScoreSessionvalue,NewValue,SeventhCaInput[0],sub).start();

                });

                TableColumn<Scores,Double> EightCaColumn=new TableColumn<>("8th CA");
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
                    new SaveScoreThread9(ScoreSessionvalue,NewValue,EightCaInput[0],sub).start();


                });

                TableColumn<Scores,Double> NinethCaColumn=new TableColumn<>("9th CA");
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
                    new SaveScoreThread10(ScoreSessionvalue,NewValue,NinthCaInput[0],sub).start();


                });

                TableColumn<Scores,Double> TenthCaColumn=new TableColumn<>("10th CA");
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
                    new SaveScoreThread11(ScoreSessionvalue,NewValue,TenthCaInput[0],sub).start();

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
                    new SaveScoreThread12(ScoreSessionvalue,NewValue,ExamInput[0],sub).start();

                });

                TableColumn<Scores,Double> CummulativeColumn=new TableColumn<>("Cum");
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
                    new SaveScoreThread13(ScoreSessionvalue,NewValue,CumInput[0],sub).start();

                });


                //////////////////////////////////////////////////
               // tableview.setItems(GetScores());
                tableview.getColumns().addAll(SubjectColumn,FirstCaColumn,SecondCaColumn,ThirdCaColumn,FourthCaColumn,
                        FifthCaColumn,SixthCaColumn,SeventhCaColumn,EightCaColumn,NinethCaColumn,TenthCaColumn,
                        ExamColumn,CummulativeColumn);
                ///Button And EditText
                TextField subject=new TextField();
                subject.setPromptText("Subject");
                TextField first=new TextField();
                first.setPromptText("1st CA");
                TextField second=new TextField();
                second.setPromptText("2nd CA");
                TextField third=new TextField();
                third.setPromptText("3rd CA");
                TextField fourth=new TextField();
                fourth.setPromptText("4th CA");
                TextField fifth=new TextField();
                fifth.setPromptText("5th CA");
                TextField sixth=new TextField();
                sixth.setPromptText("6th CA");
                TextField seventh=new TextField();
                seventh.setPromptText("7th CA");
                TextField Eight=new TextField();
                Eight.setPromptText("8th CA");
                TextField Ninth=new TextField();
                Ninth.setPromptText("9th CA");
                TextField Tenth=new TextField();
                Tenth.setPromptText("10th CA");
                TextField Exam=new TextField();
                Exam.setPromptText("Exam");
                TextField cum=new TextField();
                cum.setPromptText("Cummulative");
                HBox scoreHbox=new HBox();
                scoreHbox.getChildren().addAll(subject,first,second,third,fourth,fifth,sixth,seventh,Eight,Ninth,Tenth,Exam,cum);

                Button AddButton=new Button("Add");
                ///label
                Label labelerror=new Label("Check your Input");
                AddButton.setMinWidth(100);
                AddButton.setOnAction((ev)->{
                    Scores scores=new Scores();
                    scores.setSubject(subject.getText());
                    scores.setFirstCa(Double.parseDouble(first.getText()));
                    scores.setSecondCa(Double.parseDouble(second.getText()));
                    scores.setThirdCa(Double.parseDouble(third.getText()));
                    scores.setFourthCa(Double.parseDouble(fourth.getText()));
                    scores.setFifthCa(Double.parseDouble(fifth.getText()));
                    scores.setSixthCa(Double.parseDouble(sixth.getText()));
                    scores.setSeventhCa(Double.parseDouble(seventh.getText()));
                    scores.setEightCa(Double.parseDouble(Eight.getText()));
                    scores.setNinethCa(Double.parseDouble(Ninth.getText()));
                    scores.setTenthCa(Double.parseDouble(Tenth.getText()));

                    if(second.getText().matches("^[a-zA-Z]*$") || third.getText().matches("^[a-zA-Z]*$") ||
                    fourth.getText().matches("^[a-zA-Z]*$") ||fifth.getText().matches("^[a-zA-Z]*$") ||
                            sixth.getText().matches("^[a-zA-Z]*$") ||seventh.getText().matches("^[a-zA-Z]*$")
                            ||seventh.getText().matches("^[a-zA-Z]*$") ||Eight.getText().matches("^[a-zA-Z]*$")
                            ||Ninth.getText().matches("^[a-zA-Z]*$") ||Tenth.getText().matches("^[a-zA-Z]*$")
                            ||Exam.getText().matches("^[a-zA-Z]*$") ||cum.getText().matches("^[a-zA-Z]*$")  ){
                        labelerror.setVisible(true);
                    }else {
                        labelerror.setVisible(false);
                        //Add the score to table row
                        tableview.getItems().add(scores);
                        subject.clear();
                        first.clear();
                        second.clear();
                        third.clear();
                        fourth.clear();
                        fifth.clear();
                        sixth.clear();
                        seventh.clear();
                        Eight.clear();
                        Ninth.clear();
                        Tenth.clear();
                        Exam.clear();
                        cum.clear();
                    }



                });
                Button DeleteButton=new Button("Delete");
                DeleteButton.setOnAction((ev2)->{

                    ObservableList<Scores> ScoreSelected,AllScore;
                    AllScore=tableview.getItems();
                    ScoreSelected=tableview.getSelectionModel().getSelectedItems();
                    ScoreSelected.forEach(AllScore::remove);

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
                ScoreSession.setPromptText("Select Class");
                Label label=new Label("Select Class:");
                label.setFont(Font.font("Verdana", FontWeight.MEDIUM,24));
                ScorevBox.setAlignment(Pos.TOP_CENTER);

                ScorevBox.getChildren().addAll(label,ScoreSession,tableview,scoreHbox,vb);
                scrollpane.setContent(ScorevBox);
                Scene scene=new Scene(scrollpane,1200,700);
                StudentAssessmentSession.window.setMaximized(true);
                StudentAssessmentSession.window.setScene(scene);
                new getSessionScoreFromDB(ScoreSession).start();






            });
            listvie.setMinHeight(700);
            Label label = new Label("Select Student");
            VBox box = new VBox();
            box.setAlignment(Pos.TOP_LEFT);
            box.getChildren().addAll(label, listvie);
            layout.setFitToWidth(true);
            layout.setFitToHeight(true);
            layout.setContent(box);
            layout.setPadding(new Insets(10, 10, 10, 10));
            Scene scene = new Scene(layout);
            StudentAssessmentSession.window.setMaximized(true);
            StudentAssessmentSession.window.setScene(scene);


            //Getting all studentname from db
            //clas argument here is the class selected
            new ClassNameThread(clas, listvie).start();


///////////////////////////////////////////////////////////////////////
        });





    }
    

public ObservableList<Scores> GetScores(){
        ObservableList<Scores> scores=FXCollections.observableArrayList();


        scores.add(new Scores("Mathematics",0.0,0.0,0.0,0.0,0.0,
                0.0,0.0,0.0,0.0,0.0,0.0,0.0));
return scores;
}



//class to get sessions
    public static class ClassThread extends Thread {
        public ComboBox<String> clas;
        Connection conn;
        ProgressIndicator progressBar;
        double progress=0.0;


        public ClassThread(ComboBox<String> comb,Connection conn,ProgressIndicator PgBar) {
            this.clas = comb;
            this.conn=conn;
            this.progressBar=PgBar;


        }

        @Override
        public void run() {
            conn= SqlConnection.connector();
            if(conn!=null){
                ResultSet resultSet;
                String QUERY = "Select * from SessionTable";
                try {
                    PreparedStatement prs = conn.prepareStatement(QUERY);
                    System.out.println("[StudentAssessmentSessionControllerThread]: preparing query");
                    resultSet = prs.executeQuery();
                    Platform.runLater(()->{
                    });
                    while (resultSet.next()) {
                        ObservableList<String> list = FXCollections.observableArrayList();
                        list.addAll(resultSet.getString("sessionname"));
                        System.out.println("[StudentAssessmentSessionControllerThread]: Session received from Database");
                        Platform.runLater(() -> {
                            clas.getItems().addAll(list);
                            Platform.runLater(() -> {
                                while (true) {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Platform.runLater(()->{
                                        progressBar.setProgress(progress);
                                    });
                                    progress+=1;
                                    if (progress > 1) {
                                        break;
                                    }
                                }
                            });

                        });
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }else{
                Platform.runLater(()->{
                    boolean result= new ConnectionError().Connection(conn);
                    if (result==true){
                        StudentAssessmentSession.window.close();
                    }
                });
            }


        }

    }

    public  class getSessionScoreFromDB extends  Thread{
        private ComboBox<String> ScoreCombo;
        ObservableList<String> list = FXCollections.observableArrayList();

        public  getSessionScoreFromDB(ComboBox<String> ScoreCombo){
            this.ScoreCombo=ScoreCombo;
        }
        @Override
        public void run() {
            Connection conn=SqlConnection.connector();
            if(conn!=null){
                ResultSet resultSet;
                String QUERY = "Select * from SessionScore";
                try {
                    PreparedStatement prs = conn.prepareStatement(QUERY);
                    System.out.println("[StudentAssessmentSessionControllerThread]: preparing query");
                    resultSet = prs.executeQuery();
                    while (resultSet.next()) {

                        list.add(resultSet.getString("sessionscore"));
                        System.out.println("[StudentAssessmentSessionControllerThread]: Session received from Database");

                    }
                    Platform.runLater(() -> {
                        ScoreCombo.getItems().addAll(list);
                    });

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }else{
                Platform.runLater(()->{
                  boolean result =new ConnectionError().Connection(conn);
                  if (result==true){
                      StudentAssessmentSession.window.close();
                  }
                });
            }


        }


    }
}
