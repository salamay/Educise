package sample.LoginPage.DashBoard.Admin.SchoolFee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.DeleteSchoolFee.DeleteSchoolFee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.debtors.getDebtorsthread;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.getSchoolFeeThread;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.getSchoolFeeWithoutTermThread;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.insertTerm;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SchoolFeeWindowController implements Initializable {
    public JFXComboBox<String> classcombobox;
    public JFXComboBox<String> termcombobox;
    public JFXComboBox<String> sessioncombobox;
    public JFXComboBox<String> tagcombobox;
    public TextField minimumamount;
    public JFXButton fetchbutton;
    public TableView<Fee> tableview;
    public Label classerror;
    public Label termerror;
    public Label yearerror;
    public Label tagerror;
    private String clas;
    private String term;
    private String session;
    private String tag;
    public static byte[] pdf;
    public TableColumn<Fee,String> namecolumn;
    public TableColumn<Fee,String> amountcolumn;
    public TableColumn<Fee,String> yearcolumn;
    public TableColumn<Fee,String> modeofpaymentcolumn;
    public TableColumn<Fee,String> tagcolumn;
    public TableColumn<Fee,String> classcolumn;
    public TableColumn<Fee,String> datecolumn;
    public TableColumn<Fee,String> depositorcolumn;
    public TableColumn<Fee,String> transactionidcolumn;
    public TableColumn<Fee,String> termcolumn;
    public JFXTextArea printingTextArea;
    public JFXButton getschoolfeeWithoutTermButton;
    public JFXButton getdebtorsbutton;
    public JFXButton deleteschoolfeebutton;
    public JFXButton printbutton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchbutton.setOnMouseEntered(event -> {
            ScaleTrans(fetchbutton);
        });
        fetchbutton.setOnMouseExited(event -> {
            RestoreScale(fetchbutton);
        });
        getschoolfeeWithoutTermButton.setOnMouseEntered(event -> {
            ScaleTrans(getschoolfeeWithoutTermButton);
        });
        getschoolfeeWithoutTermButton.setOnMouseExited(event -> {
            RestoreScale(getschoolfeeWithoutTermButton);
        });
        getdebtorsbutton.setOnMouseEntered(event -> {
            ScaleTrans(getdebtorsbutton);
        });
        getdebtorsbutton.setOnMouseExited(event -> {
            RestoreScale(getdebtorsbutton);
        });
        printbutton.setOnMouseEntered(event -> {
            ScaleTrans(printbutton);
        });
        printbutton.setOnMouseExited(event -> {
            RestoreScale(printbutton);
        });
        deleteschoolfeebutton.setOnMouseEntered(event -> {
            ScaleTrans(deleteschoolfeebutton);
        });
        deleteschoolfeebutton.setOnMouseExited(event -> {
            RestoreScale(deleteschoolfeebutton);
        });

        classcombobox.getItems().addAll("Nursery 1","Nursery 2","Primary 1","Primary 2","Primary 3","Primary 4","Primary 5","Jss 1","Jss 2","Jss 3","SS 1","SS 2","SS 3");
        tagcombobox.getItems().addAll("DAY","BOARDER");
        new ClassThread(sessioncombobox,null).start();
        termcombobox.getItems().addAll("Cancel","1","2","3");
        termcombobox.getSelectionModel().selectFirst();
        ////////Set table view Editable
        tableview.setEditable(true);
        //////////////////////////////////////////////////Setting up table column
        //Student name Column
        namecolumn=new TableColumn<>("Student name");
        namecolumn.setMinWidth(160);
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("studentname"));

        //Amount column
        amountcolumn=new TableColumn<>("Amount");
        amountcolumn.setMinWidth(40);
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        amountcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                if (e.getNewValue().matches("^[0-9]*$")){
                    saveDataToSchoolfeetable(amountcolumn,e);
                    e.getRowValue().setAmount(e.getNewValue());
                }else{
                    new ConnectionError().Connection("Invalid character detected,delete the character");
                }
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }
        });
        //Class column
        classcolumn=new TableColumn<>("Class");
        classcolumn.setMinWidth(10);
        classcolumn.setCellValueFactory(new PropertyValueFactory<>("clas"));

        classcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(classcolumn,e);
                e.getRowValue().setClas(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }
        });

        //Year column
        yearcolumn=new TableColumn<>("Year");
        yearcolumn.setMinWidth(100);
        yearcolumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        //Class column
        modeofpaymentcolumn=new TableColumn<>("Mode of payment");
        modeofpaymentcolumn.setMinWidth(120);
        modeofpaymentcolumn.setCellValueFactory(new PropertyValueFactory<>("modeofpayment"));
        modeofpaymentcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modeofpaymentcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(modeofpaymentcolumn,e);
                e.getRowValue().setModeofpayment(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }

        });
        //Tag column
        tagcolumn=new TableColumn<>("Tag");
        tagcolumn.setMinWidth(10);
        tagcolumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        //datecolumn column
        datecolumn=new TableColumn<>("payment date");
        datecolumn.setMinWidth(100);
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        datecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        datecolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(datecolumn,e);
                e.getRowValue().setDate(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }
        });

        //depositor column
        depositorcolumn=new TableColumn<>("Depositor");
        depositorcolumn.setMinWidth(160);
        depositorcolumn.setCellValueFactory(new PropertyValueFactory<>("depositorname"));
        depositorcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        depositorcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(depositorcolumn,e);
                e.getRowValue().setDepositorname(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }
        });

        //Transaction id  column
        transactionidcolumn=new TableColumn<>("Transaction id");
        transactionidcolumn.setMinWidth(100);
        transactionidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactionidcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        transactionidcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(transactionidcolumn,e);
                e.getRowValue().setId(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
                tableview.getColumns().clear();
            }
        });

        //Term column
        termcolumn=new TableColumn<>("Term");
        termcolumn.setMinWidth(20);
        termcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        termcolumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //////whenever the user want to input schoolfee information,the term is crucial,it must be first specified before
        /////other column will be ready to accept data
        termcolumn.setOnEditCommit((ce)->{
            String newvalue=ce.getNewValue();
            System.out.println("SchoolFeeWindowController: Term: "+newvalue);
            if (!newvalue.isEmpty()&&newvalue.matches("^[0-9]*$")){
                //get the student name in the selected row
                String studentnameInTheColumn=ce.getRowValue().getStudentname();
                System.out.println("SchoolFeeWindowController: student name: "+studentnameInTheColumn);
                //get session from the selected row
                String session=ce.getRowValue().getYear();
                System.out.println("SchoolFeeWindowController: Session: "+session);
                //get the class for the selected row
                String clas=ce.getRowValue().getClas();
                System.out.println("SchoolFeeWindowController: class: "+clas);
                //get the tag from the selcted row
                String tag=ce.getRowValue().getTag();
                System.out.println("SchoolFeeWindowController: tag: "+tag);
                //Start the thread to insert term
                //newValue instace is the term
                new insertTerm(studentnameInTheColumn,session,clas,tag,newvalue,ce,termcolumn).start();
            }else {
                ce.getRowValue().setTerm(null);
                boolean err=new ConnectionError().Connection("Please provide a number to the field");
                System.out.println("SchoolFeeWindowController: Please provide a anumber to the field");
                tableview.getColumns().clear();
            }
        });
        tableview.getColumns().addAll(namecolumn,amountcolumn,classcolumn,tagcolumn,termcolumn,yearcolumn,modeofpaymentcolumn,transactionidcolumn,datecolumn,depositorcolumn);
    }


    public void FetchButtonClicked() throws IOException {
        //Getting input
        System.out.println("SchoolFeeWindowController:Fetch Button pressed--> getting input");
        clas=classcombobox.getValue();
        session=sessioncombobox.getValue();
        term=termcombobox.getValue();
        tag=tagcombobox.getValue();
        //Checking input for error
        //Checking class combobox
        if (clas!=null){
            classerror.setVisible(false);
        }
        else {
            classerror.setVisible(true);
        }
        //Checking term combobox
        if (term!=null){
            termerror.setVisible(false);
        }
        else {
            termerror.setVisible(false);
        }
        //Checking year combobox
        if (session!=null){
            yearerror.setVisible(false);
            session=sessioncombobox.getSelectionModel().getSelectedItem();
        }
        else {
            yearerror.setVisible(true);
        }
        if (tag!=null){
            tagerror.setVisible(false);
        }
        else {
            tagerror.setVisible(true);
        }
        //Checking data
        //if class,term and year is present,it will fetch the school fee connected with the term
        if (clas!=null&&!term.contains("Cancel")&& session!=null&&tag!=null){
            new LoadingWindow();
            new getSchoolFeeThread(clas,term,session,tableview).start();
        }else {
            new ConnectionError().Connection("Select a term instead of cancel");
        }

    }
    public void getSchoolfeeWithoutTerm() throws IOException {
        //Getting input
        System.out.println("SchoolFeeWindowController:get button without term pressed--> getting input");
        clas=classcombobox.getValue();
        session=sessioncombobox.getValue();
        term=termcombobox.getValue();
        tag=tagcombobox.getValue();
        //Checking input for error
        //Checking class combobox
        if (clas!=null){
            classerror.setVisible(false);
        }
        else {
            classerror.setVisible(true);
        }

        //Checking year combobox
        if (session!=null){
            yearerror.setVisible(false);
        }
        else {
            yearerror.setVisible(true);
        }
        if (tag!=null){
            tagerror.setVisible(false);
        }
        else {
            tagerror.setVisible(true);
        }
        if (!term.equals("Cancel")){
            new ConnectionError().Connection("Select 'Cancel' from term");
        }
        //if only class and year is present, it will fetch the school fee for all the term
        if (clas!=null && session!=null &&tag!=null &&term.equals("Cancel")){
            new LoadingWindow();
            System.out.println("SchoolFeeWindowController:get button without term pressed--> getting all term schoolfees");
            new getSchoolFeeWithoutTermThread(clas,session,tag,tableview).start();
        }
    }
    public void fetchDebtors() throws IOException {
        //Getting input
        System.out.println("SchoolFeeWindowController:Fetch Button pressed--> getting input");
        int minimum=0;
        try {
            minimum=Integer.parseInt(minimumamount.getText());
        }catch (NumberFormatException e){
            new ConnectionError().Connection("Invalid input,check your input");
        }
        clas=classcombobox.getValue();
        session=sessioncombobox.getValue();
        term=termcombobox.getValue();
        tag=tagcombobox.getValue();
        //Checking input for error
        //Checking class combobox
        if (clas!=null){
            classerror.setVisible(false);
        }
        else {
            classerror.setVisible(true);
        }
        //Checking term combobox
        if (term!=null){
            termerror.setVisible(false);
        }
        else {
            termerror.setVisible(false);
        }
        //Checking year combobox
        if (session!=null){
            yearerror.setVisible(false);
            session=sessioncombobox.getSelectionModel().getSelectedItem();
        }
        else {
            yearerror.setVisible(true);
        }
        if (tag!=null){
            tagerror.setVisible(false);
        }else {
            tagerror.setVisible(true);
        }
        if (minimum==0){
            new ConnectionError().Connection("Please provide minimum amount");
        }
        if (term.contains("Cancel")){
            new ConnectionError().Connection("Select a term instead of 'cancel' ");
        }
        if (clas!=null && session!=null &&tag!=null && !term.contains("Cancel")&&minimum!=0){
            new LoadingWindow();
            System.out.println("SchoolFeeWindowController:Feth debtors button pressed--> getting debtors");
            new getDebtorsthread(clas,session,tag,term,minimum,tableview).start();
        }else{
            new ConnectionError().Connection("Please provide the requirement information");
        }
    }
    public  void printButtonClicked() throws IOException {

        System.out.println("[SchoolFeeWindowController]: Printing debtors");
        System.out.println("[SchoolFeeWindowController]: Pdf: "+pdf);
        if (pdf!=null){
            Path path= Paths.get(System.getProperty("user.dir")+"/MyChildSchool");
            try {
                Files.createDirectories(path);
                if (Files.exists(path)){
                    File pdffile=new File(path+"/debtors.pdf");
                    FileOutputStream fileOutputStream=new FileOutputStream(pdffile);
                    fileOutputStream.write(pdf);
                    fileOutputStream.close();
                    fileOutputStream.flush();
                    FileInputStream fileInputStream=new FileInputStream(pdffile);
                    DocFlavor flavor=DocFlavor.INPUT_STREAM.AUTOSENSE;
                    PrintRequestAttributeSet asset=new HashPrintRequestAttributeSet();
                    asset.add(MediaSizeName.ISO_A4);
                    asset.add(Sides.DUPLEX);
                    PrintService[] printsServices=PrintServiceLookup.lookupPrintServices(flavor,null);
                    PrintService defaultPrintService=PrintServiceLookup.lookupDefaultPrintService();
                    for (int i=0;i<printsServices.length;i++){
                        System.out.println("[SchoolFeeWindowController]: Printers: "+printsServices[i].getName());
                    }
                    if (printsServices.length==0){
                       if (defaultPrintService!=null){
                           new LoadingWindow();
                           System.out.println("[SchoolFeeWindowController]: Using default printer");
                           Doc debtors=new SimpleDoc(fileInputStream,flavor,null);
                           DocPrintJob job=defaultPrintService.createPrintJob();
                           System.out.println("[SchoolFeeWindowController]: default printer: "+defaultPrintService.getName());
                           if (job!=null){
                               job.addPrintJobListener(new PrintJobListener() {
                                   @Override
                                   public void printDataTransferCompleted(PrintJobEvent pje) {
                                       System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Data Transfer completed");
                                       printingTextArea.setText(pje.getPrintEventType()+": Data transfer completed\n");
                                       LoadingWindow.window.close();
                                   }

                                   @Override
                                   public void printJobCompleted(PrintJobEvent pje) {
                                       System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": print Job completed");
                                       printingTextArea.setText(pje.getPrintEventType()+": Print job completed\n");
                                       LoadingWindow.window.close();
                                   }

                                   @Override
                                   public void printJobFailed(PrintJobEvent pje) {
                                       System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Print job failed");
                                       printingTextArea.setText(pje.getPrintEventType()+": Print job failed\n");
                                       LoadingWindow.window.close();
                                   }

                                   @Override
                                   public void printJobCanceled(PrintJobEvent pje) {
                                       System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Print job cancel");
                                       printingTextArea.setText(pje.getPrintEventType()+": Print job cancelled\n");
                                       LoadingWindow.window.close();
                                   }

                                   @Override
                                   public void printJobNoMoreEvents(PrintJobEvent pje) {
                                       LoadingWindow.window.close();
                                   }

                                   @Override
                                   public void printJobRequiresAttention(PrintJobEvent pje) {
                                       LoadingWindow.window.close();
                                   }
                               });
                               try {
                                   System.out.println("[SchoolFeeWindowController]: Doc: "+debtors.getStreamForBytes());
                                   job.print(debtors,asset);
                                   System.out.println("[SchoolFeeWindowController]: Printed");
                                   LoadingWindow.window.close();
                               } catch (PrintException e) {
                                   LoadingWindow.window.close();
                                   e.printStackTrace();
                               }
                           }else {
                               LoadingWindow.window.close();
                               return;
                           }
                       }else {
                           LoadingWindow.window.close();
                           new ConnectionError().Connection("No default printer on this PC");
                       }
                    }else {
                        System.out.println("[SchoolFeeWindowController]: geting all printers");
                        PrintService service=ServiceUI.printDialog(null,200,200,printsServices,defaultPrintService,flavor,asset);
                        if (service!=null){
                            new LoadingWindow();
                            Doc pdf=new SimpleDoc(fileInputStream,flavor,null);
                            try {
                                DocPrintJob job1=service.createPrintJob();
                                if (job1!=null){
                                    job1.addPrintJobListener(new PrintJobListener() {
                                        @Override
                                        public void printDataTransferCompleted(PrintJobEvent pje) {
                                            System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Data Transfer completed");
                                            printingTextArea.appendText(pje.getPrintEventType()+": Data transfer completed \n");
                                            LoadingWindow.window.close();
                                        }

                                        @Override
                                        public void printJobCompleted(PrintJobEvent pje) {
                                            System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": print Job completed");
                                            printingTextArea.appendText(pje.getPrintEventType()+": Print job completed\n");
                                            LoadingWindow.window.close();
                                        }

                                        @Override
                                        public void printJobFailed(PrintJobEvent pje) {
                                            System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Print job failed");
                                            printingTextArea.appendText(pje.getPrintEventType()+": Print job failed\n");
                                            LoadingWindow.window.close();
                                        }

                                        @Override
                                        public void printJobCanceled(PrintJobEvent pje) {
                                            System.out.println("[SchoolFeeWindowController]: Status: "+pje.getPrintEventType()+": Print job cancel");
                                            printingTextArea.appendText(pje.getPrintEventType()+": Print job cancelled\n");
                                            LoadingWindow.window.close();
                                        }

                                        @Override
                                        public void printJobNoMoreEvents(PrintJobEvent pje) {
                                            LoadingWindow.window.close();
                                        }

                                        @Override
                                        public void printJobRequiresAttention(PrintJobEvent pje) {
                                            LoadingWindow.window.close();
                                        }
                                    });
                                    job1.print(pdf,asset);
                                    LoadingWindow.window.close();
                                }else {
                                    LoadingWindow.window.close();
                                }

                            } catch (PrintException e) {
                                LoadingWindow.window.close();
                                e.printStackTrace();
                            }
                            System.out.println("[SchoolFeeWindowController]: Printed");
                            LoadingWindow.window.close();
                        }else {
                            LoadingWindow.window.close();
                            new ConnectionError().Connection("Printing cancelled");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            LoadingWindow.window.close();
            new ConnectionError().Connection("Document not found");
        }
    }
    public void deleteButtonClicked() throws IOException {
        ///this method will delete the data in the selected column and leave the name Column
        //getting value
        Fee fee=tableview.getSelectionModel().getSelectedItem();

       if (fee!=null){
           if (fee.getClas()!=null&&fee.getTerm()!=null&&fee.getYear()!=null&&fee.getStudentname()!=null){
               System.out.println("FEE:name to delete-->"+fee.getStudentname());
               System.out.println("FEE:-->"+fee.getTerm());
               System.out.println("FEE:-->"+fee.getClas());
               System.out.println("FEE:-->"+fee.getYear());
               new LoadingWindow();
               new DeleteSchoolFee(clas,fee.getYear(),fee.getTerm(),fee.getStudentname(),tableview).start();
           }else {
               new ConnectionError().Connection("Selected items cannot be deleted,some importance field are missing");
           }

       }else {
           new ConnectionError().Connection("Please select a fee to delete");
       }

    }

    //Saving data to school fee table
    public void saveDataToSchoolfeetable(TableColumn<?, ?> column, TableColumn.CellEditEvent<Fee, ?> e){
        String newvalue=e.getNewValue().toString().replaceAll("/","-");
        System.out.println("SchoolFeeWindowController: entity: "+newvalue);
       if (!newvalue.isEmpty()&&newvalue.matches("^[A-Za-z[-/ ]0-9]*$")){
           //get the student name in the selected row
           String studentnameInTheColumn=e.getRowValue().getStudentname();
           System.out.println("SchoolFeeWindowController: student name: "+studentnameInTheColumn);
           //get session from the selected row
           String session=e.getRowValue().getYear();
           System.out.println("SchoolFeeWindowController: Session: "+session);
           //get the class for the selected row
           String clas=e.getRowValue().getClas();
           System.out.println("SchoolFeeWindowController: class: "+clas);
           //get the tag from the selcted row
           String tag=e.getRowValue().getTag();
           System.out.println("SchoolFeeWindowController: tag: "+tag);
           //Get column table of the selected
           String columntable=column.getText();
           System.out.println("SchoolFeeWindowController: column: "+columntable);
           //get term from the selected row
           String term=e.getRowValue().getTerm();
           System.out.println("SchoolFeeWindowController: term: "+term);
           new saveDataIntoSchoolFeeTable(clas,session,studentnameInTheColumn,tag,newvalue,columntable,term,column).start();
       }else {
           boolean error=new ConnectionError().Connection("Check for invalid symbol, symbol '-' is only allow in date field,delet or change the symbol");
           System.out.println("SchoolFeeWindowController: Please provide valid value for the field");
       }
    }

    //this method Check term column
    //Since the term is mandatory for each row ,whenever you edit a column it first checks the term parameter it present,
    //if it present,then it proceed to the server
    private boolean CheckTermColumn(Fee rowvalue, TableColumn.CellEditEvent<Fee, String> e){
        System.out.println(e.getRowValue().getTerm());
        if (e.getRowValue().getTerm()==null||e.getRowValue().getTerm().contains(" ")||e.getRowValue().getTerm().isEmpty()){
            return true;
        }else {
            System.out.println("false"+rowvalue.getTerm());
            return false;
        }
    }


    public void ScaleTrans(Button button){
        ScaleTransition scaleTransition=new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setFromY(1);
        scaleTransition.setToY(1.2);
        scaleTransition.play();

    }
    public void RestoreScale(Button button){
        ScaleTransition scaleTransition=new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1.2);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(1.2);
        scaleTransition.setToY(1);
        scaleTransition.play();

    }
    //////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
    //the progressbar indicate the progress
    public class ClassThread extends Thread {
        private ComboBox<String> clas;
        private ProgressIndicator pgb;
        double progress=0.0;

        public ClassThread(ComboBox<String> comb,ProgressIndicator progressBar) {
            this.clas = comb;
            this.pgb=progressBar;

        }

        @Override
        public void run() {
            System.out.println("[ClassThread]: setting up okhttp client");
            OkHttpClient client=new OkHttpClient();

            System.out.println("[ClassThread]: setting up okhttp client request");
            Request request=new Request.Builder()
                    .url("http://localhost:8080/retrieveinformationsession")
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println("[ClassThread]: "+response);
                if (response.code()==200|| response.code()==212||response.code()==201){

                    System.out.println("[ClassThread]: session retrieved");
                    ResponseBody body=response.body();
                    try {
                        byte [] bytes=body.bytes();
                        //removing bracket from response
                        String data=new String(bytes,"UTF-8");
                        String data2=data.replace(']',' ');
                        String data3=data2.replace('[',' ');
                        String data4=data3.replaceAll(" ","");
                        List<String> list= Arrays.stream(data4.split(",")).collect(Collectors.toList());

                        Platform.runLater(()->{
                            clas.getItems().addAll(list);
                        });
                        System.out.println(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    //Display an Alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get session,CHECK INTERNET CONNECTION");
                        if (error){
                            SchoolFeeWindow.window.close();
                            System.out.println("[ClassThread]--> Connection Error,Window close");
                        }
                    });
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){

                        SchoolFeeWindow.window.close();
                        System.out.println("[ClassThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[ClassThread]: Unable to get session information from server");
                e.printStackTrace();
            }

        }

    }
}
