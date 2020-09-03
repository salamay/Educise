package sample.LoginPage.DashBoard.Admin;

import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.BookStore.BookStoreWindow;
import sample.LoginPage.DashBoard.Admin.SchoolFee.SchoolFeeWindow;

import java.io.IOException;

public class AdminWindowController {

    public void SchoolFeeButtonClicked() throws IOException {
        new SchoolFeeWindow();
    }
    public void BookStoreButtonClicked()throws IOException{
        new BookStoreWindow();
    }
    public void ExamButtonClicked(){
        new ConnectionError().Connection("Not available right now");
    }

}
