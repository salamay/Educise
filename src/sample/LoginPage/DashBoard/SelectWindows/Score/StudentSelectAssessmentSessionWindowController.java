package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Printing.PrinterManager;
import sample.LoginPage.DashBoard.SelectWindows.Information.ClassNameThread;
import sample.LoginPage.DashBoard.SelectWindows.Information.ListViewNames;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetClassThread;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetSessionThread;
import sample.LoginPage.LogInModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//This class is a controller class for StudentAssessmentSession, it gets the Session and
//make a list of student name when the session is clicked
//it handles the student scores in a table view
public class StudentSelectAssessmentSessionWindowController implements Initializable {
    public JFXComboBox<String> Clas;
    public JFXComboBox<String> SessionComboBox;
    public JFXButton continueButtonPressed;
    public VBox vbox;
    public String clas;
    public String session;
    TableView<Scores> tableview;

    public ProgressIndicator pgb;
    public static byte[] pdfdocumentbytes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[StudentAssessmentSessionController]: Starting To get session and class from database");
        ////////////Starting to get sessions from server
        new GetSessionThread(SessionComboBox, pgb, "retrievesession").start();
        ///////////Starting to get class end
        new GetClassThread(Clas, pgb, "retrieveclasses").start();

        System.out.println("[StudentAssessmentSessionController]: Starting To get session and class from database");
        /////////////////////////////////////////////////////
        //This fires when the continueButton is pressed to get the name of students
        continueButtonPressed.setOnAction(event -> {
            ////////////////////////////////////LIST VIEW START HERE//////////////////////////////////////////////
            ////////////////////////////////////List view Load Here//////////////////////////////////////////////
            JFXListView<String> listvie = new JFXListView<>();
            listvie.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            //Creating Window
            ScrollPane layout = new ScrollPane();
            layout.setPannable(true);

            //This Listview Contains the names of student,The ClassnameThread retrieve the names from the database,the class name thread takes in
            //the value selected from the combo boxes and fetch in from the value


            ///////////////////////////////////////Assessment Table START//////////////////////////////////////////////////////
            //////////////////////assessment table Loads Here//////////////////////////////////////////////////////////////////
            listvie.getSelectionModel().selectedItemProperty().addListener((v, OldValue, NewValue) -> {
                /////Score session here is the ComboBox on top of the screen,it loads the respective session score when its value is selected
                ComboBox<String> term = new ComboBox<>();
                term.getItems().addAll("1", "2", "3");
                ScrollPane scrollpane = new ScrollPane();
                scrollpane.setPannable(true);
                scrollpane.setPadding(new Insets(10, 10, 10, 10));
                scrollpane.setFitToWidth(true);
                scrollpane.setFitToHeight(true);
                tableview = new TableView<>();
                tableview.setEditable(true);
                tableview.setPrefHeight(Control.USE_COMPUTED_SIZE);
                tableview.setPrefWidth(Control.USE_COMPUTED_SIZE);

                //////////////Table column
                TableColumn<Scores, String> idcolumn = new TableColumn<>("id");
                idcolumn.setMinWidth(150);
                idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Scores, String> termcolumn = new TableColumn<>("Term");
                termcolumn.setMinWidth(10);
                termcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));


                TableColumn<Scores, String>
                        SubjectColumn = new TableColumn<>("Subject");
                SubjectColumn.setMinWidth(150);
                SubjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
                SubjectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                //////subject column on edit
                SubjectColumn.setOnEditCommit((ce) -> {
                    final String[] SubjectInput = new String[1];
                    String id = ce.getRowValue().getId();
                    //This is the new value input by the user
                    SubjectInput[0] = ce.getNewValue();
                    //Subjectinput is the enity to save,it is the Subject typed into the Subject Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateSubjectThread(id, SubjectInput[0],tableview).start();
                    } else {
                        tableview.refresh();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }
                });


                TableColumn<Scores, Double> FirstCaColumn = new TableColumn<>("first ca");
                FirstCaColumn.setMinWidth(100);
                FirstCaColumn.setCellValueFactory(new PropertyValueFactory<>("firstca"));
                FirstCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FirstCaInput = new Double[1];
                FirstCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setFirstca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FirstCaInput[0] = de.getNewValue();
                    System.out.println(FirstCaInput[0]);
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRowSubject] id= " + id);
                    //This save input score to Database
                    //FirstCaInput is the entity to save,it is the ca value typed into the FirstCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, FirstCaInput[0], FirstCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }


                });

                TableColumn<Scores, Double> SecondCaColumn = new TableColumn<>("second ca");
                SecondCaColumn.setMinWidth(100);
                SecondCaColumn.setCellValueFactory(new PropertyValueFactory<>("secondca"));
                SecondCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SecondCaInput = new Double[1];
                SecondCaColumn.setOnEditCommit((ff) -> {
                    ff.getRowValue().setSecondca(Double.parseDouble(String.valueOf(ff.getNewValue())));
                    SecondCaInput[0] = ff.getNewValue();
                    System.out.println(SecondCaInput[0]);
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRowSubject] id= " + id);

                    //This save input score to Database
                    //SecondCaInput is the entity to save,it is the ca value typed into the SecondCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, SecondCaInput[0], SecondCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> ThirdCaColumn = new TableColumn<>("third ca");
                ThirdCaColumn.setMinWidth(100);
                ThirdCaColumn.setCellValueFactory(new PropertyValueFactory<>("thirdca"));
                ThirdCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] ThirdCaInput = new Double[1];
                ThirdCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setThirdca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    ThirdCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(ThirdCaInput[0]);
                    //This save input score to Database
                    //ThirdCaInput is the entity to save,it is the ca value typed into the ThirdCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, ThirdCaInput[0], ThirdCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> FourthCaColumn = new TableColumn<>("fourth ca");
                FourthCaColumn.setMinWidth(100);
                FourthCaColumn.setCellValueFactory(new PropertyValueFactory<>("fourthca"));
                FourthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FourthCaInput = new Double[1];
                FourthCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setFourthca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FourthCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(FirstCaInput[0]);
                    //This save input score to Database
                    //ThirdCaInput is the entity to save,it is the ca value typed into the FourthCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, FourthCaInput[0], FourthCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> FifthCaColumn = new TableColumn<>("fifth ca");
                FifthCaColumn.setMinWidth(100);
                FifthCaColumn.setCellValueFactory(new PropertyValueFactory<>("fifthca"));
                FifthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] FifthCaInput = new Double[1];
                FifthCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setFifthca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    FifthCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(FifthCaInput[0]);
                    //This save input score to Database
                    //FifthCaInput is the entity to save,it is the ca value typed into the FifthCA Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, FifthCaInput[0], FifthCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> SixthCaColumn = new TableColumn<>("sixth ca");
                SixthCaColumn.setMinWidth(100);
                SixthCaColumn.setCellValueFactory(new PropertyValueFactory<>("sixthca"));
                SixthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SixthCaInput = new Double[1];
                SixthCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setSixthca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    SixthCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(SixthCaInput[0]);
                    //This save input score to Database
                    //SixthCaInput is the entity to save,it is the ca value typed into the SixthXCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, SixthCaInput[0], SixthCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });


                TableColumn<Scores, Double> SeventhCaColumn = new TableColumn<>("seventhca");
                SeventhCaColumn.setMinWidth(100);
                SeventhCaColumn.setCellValueFactory(new PropertyValueFactory<>("seventhca"));
                SeventhCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] SeventhCaInput = new Double[1];
                SeventhCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setSeventhca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    SeventhCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(SeventhCaInput[0]);
                    //This save input score to Database
                    //SeventhCaInput is the entity to save,it is the ca value typed into the Seventh Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, SeventhCaInput[0], SeventhCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> EightCaColumn = new TableColumn<>("eight ca");
                EightCaColumn.setMinWidth(100);
                EightCaColumn.setCellValueFactory(new PropertyValueFactory<>("eightca"));
                EightCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] EightCaInput = new Double[1];
                EightCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setEightca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    EightCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(EightCaInput[0]);
                    //This save input score to Database
                    //EightCaInput is the entity to save,it is the ca value typed into the EightCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, EightCaInput[0], EightCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> NinethCaColumn = new TableColumn<>("ninth ca");
                NinethCaColumn.setMinWidth(100);
                NinethCaColumn.setCellValueFactory(new PropertyValueFactory<>("ninthca"));
                NinethCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] NinthCaInput = new Double[1];
                NinethCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setNinthca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    NinthCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(NinthCaInput[0]);
                    //This save input score to Database
                    //NinethCaInput is the entity to save,it is the ca value typed into the Nineth Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, NinthCaInput[0], NinethCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> TenthCaColumn = new TableColumn<>("tenth ca");
                TenthCaColumn.setMinWidth(100);
                TenthCaColumn.setCellValueFactory(new PropertyValueFactory<>("tenthca"));
                TenthCaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] TenthCaInput = new Double[1];
                TenthCaColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setTenthca(Double.parseDouble(String.valueOf(de.getNewValue())));
                    TenthCaInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(TenthCaInput[0]);
                    //This save input score to Database
                    //scoresessionvalue here is the table name
                    //TenthCaInput is the entity to save,it is the ca value typed into the TenthCa Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, TenthCaInput[0], TenthCaColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }
                });

                TableColumn<Scores, Double> ExamColumn = new TableColumn<>("Exam");
                ExamColumn.setMinWidth(100);
                ExamColumn.setCellValueFactory(new PropertyValueFactory<>("exam"));
                ExamColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] ExamInput = new Double[1];
                ExamColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setExam(Double.parseDouble(String.valueOf(de.getNewValue())));
                    ExamInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(ExamInput[0]);
                    //This save input score to Database
                    //scoresessionvalue here is the table name
                    //ExamInput is the entity to save,it is the exam value typed into the Exam Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, ExamInput[0], ExamColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });

                TableColumn<Scores, Double> CummulativeColumn = new TableColumn<>("Cummulative");
                CummulativeColumn.setMinWidth(100);
                CummulativeColumn.setCellValueFactory(new PropertyValueFactory<>("cumulative"));
                CummulativeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                final Double[] CumInput = new Double[1];
                CummulativeColumn.setOnEditCommit((de) -> {
                    de.getRowValue().setCumulative(Double.parseDouble(String.valueOf(de.getNewValue())));
                    CumInput[0] = de.getNewValue();
                    String id = tableview.getSelectionModel().getSelectedItem().getId();
                    System.out.println("[SelectedRow] id= " + id);
                    System.out.println(CumInput[0]);
                    //This save input score to Database
                    //scoresessionvalue here is the table name
                    //Cumlative Input is the entity to save,it is the cummulative value typed into the cummulative Column
                    if (tableview.getSelectionModel().getSelectedItem().getId() != null) {
                        new UpdateScore(id, CumInput[0], CummulativeColumn.getText(),tableview).start();
                    } else {
                        tableview.getItems().clear();
                        new ConnectionError().Connection("id is not present,Reload score to get id");
                    }

                });


                //////////////////////////////////////////////////
                // tableview.setItems(GetScores());
                tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                tableview.getColumns().addAll(idcolumn, SubjectColumn, termcolumn, FirstCaColumn, SecondCaColumn, ThirdCaColumn, FourthCaColumn,
                        FifthCaColumn, SixthCaColumn, SeventhCaColumn, EightCaColumn, NinethCaColumn, TenthCaColumn,
                        ExamColumn, CummulativeColumn);
                ///Button And EditText
                TextField subject = new TextField();
                subject.setPromptText("Add Subject");
                HBox scoreHbox = new HBox();
                scoreHbox.getChildren().addAll(subject);

                Button AddButton = new Button("Add");
                ///label
                Label labelerror = new Label("Check your Input");
                AddButton.setMinWidth(100);
                AddButton.setOnAction((ev) -> {
                    String sub = subject.getText();
                    if (sub.isEmpty()) {
                        labelerror.setVisible(true);
                    }
                    if (!sub.matches("^[A-Z[ ]0-9a-z]*$")) {
                        new ConnectionError().Connection("Subject field contains invalid character");
                    }
                    if (session == null) {
                        new ConnectionError().Connection("Please select session on top of the screen");
                    }
                    if (!sub.isEmpty() && sub.matches("^[A-Z[ ]0-9a-z]*$") && session!= null&&term.getSelectionModel().getSelectedItem()!=null) {
                        ///This thread does the actual adding
                        new InsertSubjectThread(sub, NewValue, session, tableview, term.getSelectionModel().getSelectedItem()).start();
                        labelerror.setVisible(false);
                        subject.clear();
                    }

                });
                Button DeleteButton = new Button("Delete");
                DeleteButton.setOnAction((ev2) -> {
                    ObservableList<Scores> ScoreSelected;
                    ScoreSelected = tableview.getSelectionModel().getSelectedItems();
                    if (session == null) {
                        new ConnectionError().Connection("Please select session on top of the screen");
                    }
                    if (ScoreSelected.isEmpty()) {
                        new ConnectionError().Connection("Please select score to delete");
                    }
                    if (session!= null || !ScoreSelected.isEmpty()) {
                        //This Thread does the actual deleting
                        if (ScoreSelected.get(0).getId() != null) {
                            new DeleteSubjectThread(ScoreSelected.get(0).getId(), tableview).start();
                        } else {
                            tableview.getItems().clear();
                            new ConnectionError().Connection("id is not present,Reload score to get id");
                        }
                    }
                });
                DeleteButton.setMinWidth(100);
                labelerror.setVisible(false);
                String Style = "-fx-text-fill:#EE1628;";
                labelerror.setStyle(Style);
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);
                vb.setSpacing(10);
                vb.getChildren().addAll(AddButton, DeleteButton, labelerror);

                ////////////////////////////
                VBox ScorevBox = new VBox();
                ScorevBox.setPadding(new Insets(10, 10, 10, 10));
                ScorevBox.setSpacing(10);
                term.setPromptText("Select term");
                Label label = new Label("Select Session:");
                label.setStyle("-fx-text-fill:#D5D5D5;-fx-background-color:#004487;");
                label.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
                ScorevBox.setAlignment(Pos.TOP_CENTER);
                JFXButton button = new JFXButton("get scores");
                button.setStyle("-fx-background-color:#4C7B9E;-fx-text-fill:#FFFFFF;-fx-background-radius:10 10 10 10;");
                button.setOnAction((butt) -> {
                    if (term.getSelectionModel().getSelectedItem() != null && session != null) {
                        //this get the student selected score from database
                        new GetScoreThread(session, NewValue, tableview, term.getSelectionModel().getSelectedItem()).start();
                    } else {
                        new ConnectionError().Connection("select term and session");
                    }
                });
                JFXTextArea textArea = new JFXTextArea();
                textArea.setMaxHeight(40);
                textArea.setMinWidth(150);

                JFXButton printbutton = new JFXButton(" ");
                printbutton.setStyle("-fx-background-image: url('icon/drawable-xxxhdpi/ic_print_light_blue_700_18dp.png');-fx-background-size:100%, 100%;");
                printbutton.setMinHeight(60);
                printbutton.setMinWidth(60);
                printbutton.setOnMouseEntered(e -> {
                    ScaleTrans(printbutton);
                });
                printbutton.setOnMouseExited(ev -> {
                    RestoreScale(printbutton);
                });
                printbutton.setOnAction((print) -> {
                    Path path = Paths.get(System.getProperty("user.dir") + "/MyChildSchool");
                    File pdffile = new File(path + "/studentscores.ser");
                    if (pdfdocumentbytes != null) {
                        new PrinterManager(pdfdocumentbytes, pdffile).start();
                    } else {
                        new ConnectionError().Connection("Document not available, get student scores before printing");
                    }
                });
                ScorevBox.getChildren().addAll(label, term, button, tableview, textArea, printbutton, scoreHbox, vb);
                scrollpane.setContent(ScorevBox);
                Scene scene = new Scene(scrollpane);
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
            StudentSelectAssessmentSessionWindow.window.setTitle("Student score");
            StudentSelectAssessmentSessionWindow.window.setScene(scene);


//            Getting all studentname from db and adding it to the list view,adding names to the listview is done in this thread
//            clas argument here is the session in the combobox
             new ClassNameThread(clas,session, listvie,StudentSelectAssessmentSessionWindow.window).start();
        });

        Clas.setOnAction((e) -> {
            clas=Clas.getValue();
        });
        SessionComboBox.setOnAction(event -> {
            session=SessionComboBox.getValue();
        });
        ////////////////////////////////////LIST VIEW END HERE//////////////////////////////////////////////
        ////////////////////////////////////List view stop Here//////////////////////////////////////////////
    }

    public void ScaleTrans(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setFromY(1);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            scaleTransition.stop();
        });
    }

    public void RestoreScale(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1.2);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(1.2);
        scaleTransition.setToY(1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            scaleTransition.stop();
        });
    }
}
