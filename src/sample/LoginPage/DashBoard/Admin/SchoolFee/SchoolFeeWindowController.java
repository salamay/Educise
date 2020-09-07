package sample.LoginPage.DashBoard.Admin.SchoolFee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.DeleteSchoolFee.DeleteSchoolFee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.getSchoolFeeThread;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.getSchoolFeeWithoutTermThread;
import sample.LoginPage.DashBoard.Admin.SchoolFee.getSchoolFees.insertTerm;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SchoolFeeWindowController implements Initializable {
    public JFXComboBox<String> classcombobox;
    public JFXComboBox<String> termcombobox;
    public JFXComboBox<String> sessioncombobox;
    public JFXComboBox<String> modeofpaymentcombobox;
    public JFXComboBox<String> tagcombobox;
    public JFXTextField namefield;
    public JFXTextField amountfield;
    public JFXTextField datefield;
    public JFXTextField depositorname;
    public JFXTextField transactionidfield;
    public JFXButton savebutton;
    public JFXButton fetchbutton;
    public TableView<Fee> tableview;
    public Label classerror;
    public Label termerror;
    public Label yearerror;
    public Label modeofpaymenterror;
    public Label nameerror;
    public Label amounterror;
    public Label dateerror;
    public Label depositorerror;
    public Label transactioniderror;
    public Label tagerror;
    private String clas;
    private String term;
    private String session;
    private String tag;
    private String modeofpayment;
    private String studentname;
    private int amount;
    private String date;
    private String depositor;
    private String transactionid;
    //this column are made public and static so the it will be referenced from the insertTerm Thread
    public static TableColumn<Fee,String> namecolumn;
    public static TableColumn<Fee,String> amountcolumn;
    public static TableColumn<Fee,String> yearcolumn;
    public static TableColumn<Fee,String> modeofpaymentcolumn;
    public static  TableColumn<Fee,String> tagcolumn;
    public static TableColumn<Fee,String> classcolumn;
    public static TableColumn<Fee,String> datecolumn;
    public static TableColumn<Fee,String> depositorcolumn;
    public static TableColumn<Fee,String> transactionidcolumn;
    public static TableColumn<Fee,String> termcolumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classcombobox.getItems().addAll("Nursery 1","Nursery 2","Primary 1","Primary 2","Primary 3","Primary 4","Primary 5","Jss 1","Jss 2","Jss 3","SS 1","SS 2","SS 3");
        tagcombobox.getItems().addAll("DAY","BOARDER");
        new ClassThread(sessioncombobox,null).start();
        termcombobox.getItems().addAll("1","2","3");

        modeofpaymentcombobox.getItems().addAll("Bank Slip","Bursary","Bank Transfer");
        ////////Set table view Editable
        tableview.setEditable(true);
        //////////////////////////////////////////////////Setting up table column
        //Student name Column
        namecolumn=new TableColumn<>("Student name");
        namecolumn.setMinWidth(180);
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
                saveDataToSchoolfeetable(amountcolumn,e);
                e.getRowValue().setAmount(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
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
            }
        });

        //Year column
        yearcolumn=new TableColumn<>("Year");
        yearcolumn.setMinWidth(100);
        yearcolumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearcolumn.setOnEditCommit((e)->{
            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(yearcolumn,e);
                e.getRowValue().setYear(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
            }
        });

        //Class column
        modeofpaymentcolumn=new TableColumn<>("Mode of payment");
        modeofpaymentcolumn.setMinWidth(150);
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
            }

        });
        //Tag column
        tagcolumn=new TableColumn<>("Tag");
        tagcolumn.setMinWidth(10);
        tagcolumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        tagcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tagcolumn.setOnEditCommit((e)->{

            //get the row value
            Fee rowvalue=e.getRowValue();
            boolean state=CheckTermColumn(rowvalue,e);
            if (!state){
                saveDataToSchoolfeetable(tagcolumn,e);
                e.getRowValue().setTag(e.getNewValue());
            }else {
                boolean error=new ConnectionError().Connection("Pls enter term field first");
                System.out.println("Pls enter term field first");
            }
        });

        //datecolumn column
        datecolumn=new TableColumn<>("payment date");
        datecolumn.setMinWidth(50);
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
            }
        });

        //depositor column
        depositorcolumn=new TableColumn<>("Depositor");
        depositorcolumn.setMinWidth(180);
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
            String newvalue=ce.getNewValue().replaceAll("/&?","");
            System.out.println("SchoolFeeWindowController: Term: "+newvalue);
            if (!newvalue.isEmpty()){
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
                boolean err=new ConnectionError().Connection("Please provide a value to the field");
                System.out.println("SchoolFeeWindowController: Please provide a value to the field");
            }
        });
        tableview.getColumns().addAll(namecolumn,amountcolumn,classcolumn,tagcolumn,termcolumn,yearcolumn,modeofpaymentcolumn,transactionidcolumn,datecolumn,depositorcolumn);
    }

    public void SaveButtonClicked() throws IOException {
        System.out.println("SchoolFeeWindowController: Save button pressed-->getting input");
        //getting value
        clas=classcombobox.getValue();
        session=sessioncombobox.getValue();
        term=termcombobox.getValue();
        modeofpayment=modeofpaymentcombobox.getValue();
        //Checking input for error
        //Checking class combo box
        if (clas!=null){
            classerror.setVisible(false);
        }
        else {
            classerror.setVisible(true);
        }
        //Checking term combo box
        if (term!=null){
            termerror.setVisible(false);
        }
        else {
            termerror.setVisible(true);
        }
        //Checking year combo box
        if (session!=null){
            yearerror.setVisible(false);
            session=sessioncombobox.getSelectionModel().getSelectedItem();
        }
        else {
            yearerror.setVisible(true);
        }
        //Checking mode of payment combobox
        if (modeofpayment!=null){
            modeofpaymenterror.setVisible(false);
        }
        else {
            modeofpaymenterror.setVisible(true);
        }
        //Checking name text field
        if (namefield.getText().isEmpty()&&namefield.getText().matches("^[0-9]*$")){
            nameerror.setVisible(true);
        }else {
            nameerror.setVisible(false);
        }
        //Checking amount text field
        if (amountfield.getText().isEmpty()&&amountfield.getText().matches("^[a-zA-Z]*$")){
            amounterror.setVisible(true);
        }else {
            try {
                amount=Integer.parseInt(amountfield.getText());
            }catch (NumberFormatException e){
                e.printStackTrace();
                amounterror.setVisible(true);
            }
            amounterror.setVisible(false);
        }
        //Checking date text field
        if (datefield.getText().isEmpty()&&datefield.getText().matches("^[a-zA-Z]*$")){
            dateerror.setVisible(true);
        }else {
            dateerror.setVisible(false);
        }
        //Checking depositor text field
        if (depositorname.getText().isEmpty()&&depositorname.getText().matches("^[0-9]*$")){
            depositorerror.setVisible(true);
        }else {
            depositorerror.setVisible(false);
        }
        //Checking depositor text field
        if (transactionidfield.getText().isEmpty()){
            transactioniderror.setVisible(true);
        }else {
            transactioniderror.setVisible(false);
        }


        if (clas!=null&&term!=null&& session!=null&&modeofpayment!=null &&!namefield.getText().isEmpty() &&!amountfield.getText().isEmpty()&&!datefield.getText().isEmpty()&&!depositorname.getText().isEmpty()&&!transactionidfield.getText().isEmpty()){
            new LoadingWindow();
            studentname=namefield.getText();
            amount=Integer.parseInt(amountfield.getText());
            date=datefield.getText();
            depositor=depositorname.getText();
            transactionid=transactionidfield.getText();
            if (!studentname.contains("/&?")&&!date.contains("/&?")&&!depositor.contains("/?&")&&!transactionid.contains("/?&")){
                System.out.println("SchoolFeeWindowController: All input are Ok");
                System.out.println("SchoolFeeWindowController: Student name: "+studentname);
                System.out.println("SchoolFeeWindowController: class: "+clas);
                System.out.println("SchoolFeeWindowController: Term: "+term);
                System.out.println("SchoolFeeWindowController: year: "+session);
                System.out.println("SchoolFeeWindowController: Amount: "+amount);
                System.out.println("SchoolFeeWindowController: Date: "+date);
                System.out.println("SchoolFeeWindowController: Mode of payment: "+modeofpayment);
                System.out.println("SchoolFeeWindowController: Depositor name: "+depositor);
                ///////////////////////////////////Setting table data////////////////////////////////
                System.out.println("SchoolFeeWindowController: Setting Table data ");
                Fee fee=new Fee();
                fee.setStudentname(studentname);
                fee.setAmount(String.valueOf(amount));
                fee.setClas(clas);
                fee.setDate(date);
                fee.setDepositorname(depositor);
                fee.setModeofpayment(modeofpayment);
                fee.setTerm(term);
                fee.setYear(session);
                fee.setId(transactionid);;
                //Starting the thread to save data
                //if everything goes right the thread set the table data
                new SaveSchoolFeeThread(fee,tableview).start();
                //Clearing the field
                namefield.clear();
                depositorname.clear();
                amountfield.clear();
                datefield.clear();
            }else {
                new ConnectionError().Connection("Invalid Character");
            }
        }else {
            new ConnectionError().Connection("Invalid input, Check your input");
        }
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
        if (clas!=null&&term!=null&& session!=null&&tag!=null){
            new LoadingWindow();
            new getSchoolFeeThread(clas,term,session,tableview).start();
        }
        //if only class and year is present, it will fetch the school fee for all the term
        if (clas!=null && session!=null &&tag!=null && term==null){
            new LoadingWindow();
            System.out.println("SchoolFeeWindowController:Fetch Button pressed--> getting all term schoolfees");
            new getSchoolFeeWithoutTermThread(clas,session,tag,tableview).start();
        }
    }
    public void deleteButtonClicked() throws IOException {
        ///this method will delete the data in the selected column and leave the name Column
        //getting value
        Fee fee=tableview.getSelectionModel().getSelectedItem();
        System.out.println("FEE:name to delete-->"+fee.getStudentname());
        System.out.println("FEE:-->"+fee.getTerm());
        System.out.println("FEE:-->"+fee.getClas());
        System.out.println("FEE:-->"+fee.getYear());
        if (fee.getClas()!=null&&fee.getTerm()!=null&&fee.getYear()!=null&&fee.getStudentname()!=null){
            new LoadingWindow();
            new DeleteSchoolFee(clas,fee.getYear(),fee.getTerm(),fee.getStudentname(),tableview).start();
        }else {
            new ConnectionError().Connection("Selected items cannot be deleted,some importance field are missing");
        }

    }

    //Saving data to school fee table
    public void saveDataToSchoolfeetable(TableColumn<?, ?> column, TableColumn.CellEditEvent<Fee, ?> e){
        String newvalue=e.getNewValue().toString().replaceAll("/&?","-");
        System.out.println("SchoolFeeWindowController: entity: "+newvalue);
       if (!newvalue.isEmpty()){
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
           boolean error=new ConnectionError().Connection("Please provide a value for the field");
           System.out.println("SchoolFeeWindowController: Please provide a value for the field");
       }
    }

    //this method Check term column
    //Since the term is mandatory for each row ,whenever you edit a column it first checks the term parameter it present,
    //if it present,then it proceed to the server
    private boolean CheckTermColumn(Fee rowvalue, TableColumn.CellEditEvent<Fee, String> e){
        System.out.println(e.getRowValue().getTerm());
        if (e.getRowValue().getTerm()==null){
            return true;
        }else {
            System.out.println("false"+rowvalue.getTerm());
            return false;
        }
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
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5MzAzNjk4LCJleHAiOjE1OTk0ODM2OTh9.PhAyaBtsbOAVrBevhjAYLD3B7ZoqXYhsB_CCp_LakyA")
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
