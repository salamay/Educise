package sample.LoginPage.DashBoard.Admin.BookStore;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.List;

public class DeleteBookHistory extends Thread {
    private ObservableList<BookHistory> bookselectd;
    private int id;
    private TableView<BookHistory> historytableview;
    public DeleteBookHistory(ObservableList<BookHistory> bookselectd, int id, TableView<BookHistory> historytableview) {
        this.bookselectd=bookselectd;
        this.id=id;
        this.historytableview=historytableview;
    }

    @Override
    public void run() {
//        System.out.println("[DeleteBookHistory]: id: "+id);
//        OkHttpClient client=new OkHttpClient();
//        Request request=new Request.Builder()
//                .url("http://localhost:8080/deletebookHistory/"+id)
//                .addHeader("Authorization","Bearer "+ LogInModel.token)
//                .delete()
//                .build();
//        try {
//            Response response=client.newCall(request).execute();
//            System.out.println("[DeleteBookHistory]: Retrieving response ");
//            System.out.println("[DeleteBookHistory]:"+response);
//            System.out.println("[DeleteBookHistory]:"+response.body());
//            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
//                ObservableList<BookHistory> bookHistories=historytableview.getItems();
//                bookselectd.forEach(bookHistories::remove);
//                historytableview.refresh();
//
//                response.close();
//            }else {
//                Platform.runLater(()->{
//                    LoadingWindow.window.close();
//                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to delet history");
//                    if (error){
//                        System.out.println("[DeleteBookHistory]--> Connection Error");
//                    }
//                });
//                response.close();
//            }
//            if (response.code()==404){
//                LoadingWindow.window.close();
//                boolean error=new ConnectionError().Connection("Book not found");
//                if (error){
//                    System.out.println("[DeleteBookHistory]--> Connection Error");
//                }
//                response.close();
//            }
//        } catch (IOException e) {
//            Platform.runLater(()->{
//                LoadingWindow.window.close();
//                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
//                if (error){
//                    System.out.println("[getBookSoldHistory]--> Connection Error,");
//                }
//            });
//            e.printStackTrace();
//        }
    }
}
