package sample.LoginPage.DashBoard.Admin.BookStore;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.SchoolFee.Fee;
import sample.LoginPage.DashBoard.Admin.SchoolFee.saveDataIntoSchoolFeeTable;
import sample.LoginPage.DashBoard.AreYouSure.AreYouSureWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookStoreWindowController implements Initializable {
    //Add book layout
    public JFXTextField addbooktitle;
    public JFXTextField addbookauthor;
    public JFXTextField addbookprice;
    public JFXTextField addbookcopies;
    public TableView<Book> addbooktableview;
    public JFXComboBox<String> addbuttonsessioncombobox;
    public JFXComboBox<String> addbooktermcombobox;
    //sellbook layout
    public JFXTextField booknamefield;
    public JFXComboBox<String> sellbooktermcombobox;
    public JFXComboBox<String> sellbooksessioncombobox;
    public JFXTextField sellbookbuyernamefield;
    public TableView<Book> SellBookTableView;
    public TableColumn<Book, Integer> sellbookidcolumn;
    public TableColumn<Book, String> sellbooktitlecolumn;
    public TableColumn<Book, String> sellbookauthorcolumn;
    public TableColumn<Book, String> sellbookpricecolumn;
    public TableColumn<Book, String> sellbooksessioncolumn;
    public TableColumn<Book, String> sellbooktermcolumn;
    public TableColumn<Book, String> sellbookcopiescolumn;
    public HBox sellbookhbox;
    public JFXDatePicker sellbookDatePicker;

    ///Edit book layout
    public JFXTextField editbooktitletextfield;
    public JFXComboBox<String> editbooksessioncombobox;
    public JFXComboBox<String> editbooktermcombobox;
    public TableView<Book> editbooktableview;
    public TableColumn<Book,Integer> editbookidcolumn;
    public TableColumn<Book,String> editbooktitlecolumn;
    public TableColumn<Book,String> editbookauthorcolumn;
    public TableColumn<Book,Integer> editbookpricecolumn;
    public TableColumn<Book,String> editbooksessioncolumn;
    public TableColumn<Book,String> editbooktermcolumn;
    public TableColumn<Book,Integer> editbookcopiescolumn;
    ///Edit book layout end

    ////History layout
    public JFXComboBox<String> historytermcombobox;
    public JFXComboBox<String> historysessioncombobox;
    public HBox historytophbox;
    public TableView<BookHistory> historytableview;
    public TableColumn<Book, Integer> historyidcolumn;
    public TableColumn<Book, String> historytitlecolumn;
    public TableColumn<Book, String> historyauthorcolumn;
    public TableColumn<Book, String> historypricecolumn;
    public TableColumn<Book, String> historydatecolumn;
    public TableColumn<Book, String> historysessioncolumn;
    public TableColumn<Book, String> historytermcolumn;
    public TableColumn<Book, String> historybuyercolumn;
    public JFXDatePicker datePicker;
    public Label dateselected;
    public Label totalamount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellbookDatePicker = new JFXDatePicker();
        sellbookDatePicker.setStyle("-fx-background-color:#D5D5D5;");
        Label todaysdate = new Label("Select today's date:");
        todaysdate.setStyle("-fx-text-fill:#FFFFFF;");
        sellbookhbox.getChildren().addAll(todaysdate, sellbookDatePicker);
        addbooktermcombobox.getItems().addAll("1", "2", "3");
        addbuttonsessioncombobox.getItems().addAll("2019-2020", "2020-2021", "2021-2022", "2023-2024", "2024-2025", "2025-2026", "2026-2027", "2027-2028");
        sellbooktermcombobox.getItems().addAll("1", "2", "3");
        sellbooksessioncombobox.getItems().addAll("2019-2020", "2020-2021", "2021-2022", "2023-2024", "2024-2025", "2025-2026", "2026-2027", "2027-2028");
        historytermcombobox.getItems().addAll("1", "2", "3");
        historysessioncombobox.getItems().addAll("2019-2020", "2020-2021", "2021-2022", "2023-2024", "2024-2025", "2025-2026", "2026-2027", "2027-2028");
        editbooksessioncombobox.getItems().addAll("2019-2020", "2020-2021", "2021-2022", "2023-2024", "2024-2025", "2025-2026", "2026-2027", "2027-2028");
        editbooktermcombobox.getItems().addAll("1", "2", "3");
        //History layout

        historytitlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        historyidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        historypricecolumn.setCellValueFactory(new PropertyValueFactory<>("amountsold"));
        historyauthorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        historysessioncolumn.setCellValueFactory(new PropertyValueFactory<>("session"));
        historytermcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        historydatecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        historybuyercolumn.setCellValueFactory(new PropertyValueFactory<>("buyer"));

        datePicker = new JFXDatePicker();
        datePicker.setStyle("-fx-background-color:#D5D5D5;");
        datePicker.setOnAction((event -> {
            LocalDate date = datePicker.getValue();
            dateselected.setText(String.valueOf(date));
        }));
        dateselected = new Label("No date Selected");
        dateselected.setStyle("-fx-text-fill:#DD5145;");

        historytophbox.getChildren().addAll(datePicker, dateselected);
        ///History layout end

        ////////////////////////////////////Edit book layout///////////////////////////////////////////////////////////
        editbooktableview.setEditable(true);
        editbooktitlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        editbooktitlecolumn.setOnEditCommit((e)->{
            try {
                EditBook(e);
                e.getRowValue().setTitle(e.getNewValue());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        editbooktitlecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        editbookidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        editbookpricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        editbookpricecolumn.setOnEditCommit((e)->{
            try {
                EditBook(e);
                e.getRowValue().setPrice(e.getNewValue());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        editbookpricecolumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        editbookauthorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        editbookauthorcolumn.setOnEditCommit((e)->{
            try {
                EditBook(e);
                e.getRowValue().setAuthor(e.getNewValue());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        editbookauthorcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        editbooksessioncolumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        editbooktermcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));


        editbookcopiescolumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
        editbookcopiescolumn.setOnEditCommit((e)->{
            try {
                EditBook(e);
                e.getRowValue().setCopies(e.getNewValue());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        editbookcopiescolumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        //////////////////////////////////Editbook layout end//////////////////////////////////////////////////////////
        //Setting up Table view
        //Id column
        TableColumn<Book, String> idcolumn = new TableColumn<>("ID");
        idcolumn.setMinWidth(70);
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //Title Column
        TableColumn<Book, String> titlecolumn = new TableColumn<>("Title");
        titlecolumn.setMinWidth(300);
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        //Author column
        TableColumn<Book, String> authorcolumn = new TableColumn<>("Author");
        authorcolumn.setMinWidth(300);
        authorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        //Price column
        TableColumn<Book, String> pricecolumn = new TableColumn<>("Price");
        pricecolumn.setMinWidth(200);
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //copies column
        TableColumn<Book, String> copiescolumn = new TableColumn<>("Copies");
        copiescolumn.setMinWidth(150);
        copiescolumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
        //session column
        TableColumn<Book, String> sessioncolumn = new TableColumn<>("Session");
        sessioncolumn.setMinWidth(120);
        sessioncolumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        //Term column
        TableColumn<Book, String> termcolumn = new TableColumn<>("Term");
        termcolumn.setMinWidth(100);
        termcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        addbooktableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addbooktableview.getColumns().addAll(titlecolumn, authorcolumn, pricecolumn, copiescolumn, sessioncolumn, termcolumn, idcolumn);
        /////////////////////// sell book layout settings///////////////////////////
        SellBookTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sellbookidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sellbooktitlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        sellbookauthorcolumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        sellbookpricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        sellbookcopiescolumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
        sellbooksessioncolumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        sellbooktermcolumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        /////////////////////////////////Sell book layout setting end////////////////

        try {
            new LoadingWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////Starting to get all books from server
        new GetAllBooks(addbooktableview,editbooktableview).start();
    }

    public void addbookbuttonClicked() throws IOException {
        Book book = new Book();
        String title = addbooktitle.getText();
        String author = addbookauthor.getText();
        String price = addbookprice.getText();
        String copies = addbookcopies.getText();
        String session = addbuttonsessioncombobox.getValue();
        String term = addbooktermcombobox.getValue();
        System.out.println("[BookStoreWindowController]:Title->" + title);
        System.out.println("[BookStoreWindowController]:Author->" + author);
        System.out.println("[BookStoreWindowController]:price->" + price);
        System.out.println("[BookStoreWindowController]:Copies->" + copies);
        System.out.println("[BookStoreWindowController]:Session->" + session);
        System.out.println("[BookStoreWindowController]:Term->" + term);
        if (title.isEmpty() || !title.matches("^[A-Z[ ]a-z]*$")) {
            System.out.println("HJGDHJJSKJSKS");
            book.setTitle(null);
            new ConnectionError().Connection("Please provide valid title,if symbol is present,delete it");
        } else {
            book.setTitle(title);
        }
        if (author.isEmpty() || !author.matches("^[A-Z[ ]a-z]*$")) {
            book.setAuthor(null);
            new ConnectionError().Connection("Please provide valid author,if symbol is present,delete it");
        } else {
            book.setAuthor(author);
        }
        if (price.isEmpty() || !price.matches("^[0-9]*$")) {
            book.setPrice(0);
            new ConnectionError().Connection("Please provide valid price,if symbol is present,delete it");
        } else {
            try {
                book.setPrice(Integer.parseInt(price));
            } catch (NumberFormatException e) {
                book.setPrice(0);
                new ConnectionError().Connection("Please provide valid price,if symbol is present,delete it");
            }
        }
        if (copies.isEmpty() || !copies.matches("^[0-9]*$")) {
            book.setCopies(0);
            new ConnectionError().Connection("Please provide valid copies,if symbol is present,delete it");
        } else {
            try {
                book.setCopies(Integer.parseInt(copies));
            } catch (NumberFormatException e) {
                book.setCopies(0);
                new ConnectionError().Connection("Please provide valid Copies");
            }
        }
        if (session==null) {
            new ConnectionError().Connection("Select academic session");
        } else {
            book.setYear(session);
        }
        if (term==null) {
            new ConnectionError().Connection("Select term");
        } else {
            book.setTerm(term);
        }
        if (book.getTitle() != null && book.getAuthor() != null && book.getCopies() != 0 && book.getPrice() != 0 && book.getYear() != null && book.getTerm() != null) {
            new LoadingWindow();
            ObservableList<Book> books = FXCollections.observableArrayList();
            books.add(book);
            new SaveBook(books, book, addbooktableview).start();

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////SELL BOOK START/////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sellbookGoButtonClicked() throws IOException {
        //Getting name of the book
        System.out.println("[BookStoreWindowController]-->[Sell Book]:Getting and checking name");
        String bookname = booknamefield.getText();
        String session = sellbooksessioncombobox.getValue();
        String term = sellbooktermcombobox.getValue();
        if (bookname==null||bookname.isEmpty()) {
            new ConnectionError().Connection("Enter book name");
        }
        if (!bookname.matches("^[A-Z[ ]a-z]*$")) {
            new ConnectionError().Connection("Name of book contains invalid character");
        }
        if (session==null || term==null) {
            new ConnectionError().Connection("Session and term must be selected");
        }
        if (!bookname.isEmpty() &&bookname!=null&& bookname.matches("^[A-Z[ ]a-z]*$") && session!=null && term!=null) {
            new LoadingWindow();
            new SearchBook(bookname, session, term, SellBookTableView).start();
        } else {
            new ConnectionError().Connection("Please provide valid information");
        }
    }

    public void sellbookbuttonClicked() throws IOException {
        String buyer = sellbookbuyernamefield.getText();
        LocalDate localdate = sellbookDatePicker.getValue();
        String date = String.valueOf(localdate);
        if (buyer.isEmpty()||buyer==null) {
            new ConnectionError().Connection("Enter buyer name");
        }
        System.out.println("hghsjfhf"+date);
        if (localdate==null) {
            new ConnectionError().Connection("Select todays date");
        }
        if (!buyer.matches("^[A-Z[ ]a-z]*$")){
            new ConnectionError().Connection("Name of Buyer contains invalid character");
        }

        //This get the selected item in the table and deduct 1 from the number of copies from the server
        ObservableList<Book> bookselected = SellBookTableView.getSelectionModel().getSelectedItems();
        if (bookselected.get(0)==null){
            new ConnectionError().Connection("Select a book");
        }
        if (!bookselected.isEmpty() && bookselected != null && !buyer.isEmpty() &&buyer!=null &&buyer.matches("^[A-Z[ ]a-z]*$")&&localdate!=null) {
            if (bookselected.get(0).getCopies()==0){
                new ConnectionError().Connection(" 0 "+bookselected.get(0).getTitle()+" left in store");
            }else {
                boolean descision = new AreYouSureWindow().LoadScreen("Are you sure you want to sell book");
                if (descision) {
                    new LoadingWindow();
                    new SellBook(SellBookTableView, bookselected, buyer, date).start();
                } else {

                }
            }

        }

    }

    ///////////////////////////////////////////////////////////SELL BOOK END///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Editbook
    //Editing book
    public void editbooksearchbuttonClicked() throws IOException {
        String bookname=editbooktitletextfield.getText();
        String term=editbooktermcombobox.getValue();
        String session=editbooksessioncombobox.getValue();
        if (bookname==null||bookname.isEmpty()){
            new ConnectionError().Connection("Please provide the book title to search");
        }
        if (!bookname.matches("^[A-Z[ ]a-z]*$")) {
            new ConnectionError().Connection("Name of book contains invalid character");
        }
        if (term==null||session==null){
            new ConnectionError().Connection("Please select term and session of the book");
        }
        if (bookname!=null &&!bookname.isEmpty()&&bookname.matches("^[A-Z[ ]a-z]*$")&&term!=null&&session!=null){
            new LoadingWindow();
            new SearchBookForEditLayoutThread(bookname,session,term,editbooktableview).start();
        }
    }
    public void EditBook(TableColumn.CellEditEvent<Book, ?> e) throws IOException {
        String oldValue= e.getOldValue().toString();
        String entity= e.getNewValue().toString();
        //Columnname instance here correspond to a table column in the database
        String columnname=e.getTableColumn().getText();
        int id=e.getRowValue().getId();
        System.out.println("[Editing book]: entity:"+entity+"\n"+"column:"+columnname+"\n"+"id:"+id);
        if (entity!=null &&entity.matches("^[A-Z[- ]a-z0-9]*$")&&id!=0){
            new LoadingWindow();
            EditBookRequest editBookRequest=new EditBookRequest();
            editBookRequest.setEntity(entity);
            editBookRequest.setColumn(columnname);
            editBookRequest.setId(id);
            new EditBookThread(editBookRequest,oldValue,e).start();
        }
       else {
            boolean error=new ConnectionError().Connection("Please provide a valid input for the field");
            System.out.println("EditBook: Please provide a valid input for the field");
        }
    }
    /////Delete book
    public void editBookDeletButtonClicked() throws IOException {
        ObservableList<Book> bookselected=editbooktableview.getSelectionModel().getSelectedItems();
        if (bookselected.get(0)==null||bookselected.get(0).getId()==0){
            new ConnectionError().Connection("please select book to delete");
        }

        if (bookselected.get(0)!=null&&bookselected.get(0).getId()!=0){
            new LoadingWindow();
            new DeleteBook(bookselected.get(0).getId(),editbooktableview).start();
        }
    }

    //Editbook End

    ///////////////////////////////////////////////////History tab view//////////////////////////////////////////////////////////////////
    public void sortHistoryButtonClicked() throws IOException {
        String term = historytermcombobox.getValue();
        String session = historysessioncombobox.getValue();
        LocalDate localdate=datePicker.getValue();
        String date=String.valueOf(localdate);

        System.out.println("[getting history]-->"+term);
        System.out.println("[getting history]-->"+session);
        System.out.println("[getting history]-->"+date);

        if (term==null){
            new ConnectionError().Connection("Please select term");
        }
        if (session==null){
            new ConnectionError().Connection("Please select session");
        }
        if (localdate==null){
            new ConnectionError().Connection("Please select Date");
        }
        if (term!=null&&session!=null&&localdate!=null){
            new LoadingWindow();
            new getBookSoldHistory(session,term,historytableview,date,totalamount).start();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////History tab view End/////////////////////////////////////////////////////////////////
}
