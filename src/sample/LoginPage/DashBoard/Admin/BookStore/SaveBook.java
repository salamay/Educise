package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;

public class SaveBook extends Thread {
    private ObservableList<Book> books;
    private TableView<Book> addbooktableview;
    private Book book;
    public SaveBook(ObservableList<Book> books, Book book, TableView<Book> addbooktableview) {
        this.books=books;
        this.book=book;
        this.addbooktableview=addbooktableview;
    }

    @Override
    public void run() {
        System.out.println("[SaveBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient();
        System.out.println("[SaveBook]: Setting up requestbody ");
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson=new Gson();
        String body=gson.toJson(book);
        System.out.println("[SaveBook]: "+body);
        RequestBody requestBody=RequestBody.create(body, MediaType.parse("application/json"));

        Request request=new Request.Builder()
                .post(requestBody)
                .url("http://localhost:8080/savebook")
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5Nzk5OTY2LCJleHAiOjE2MDAxNTk5NjZ9.qwompSN9WRoyHTixemTubuVvPGZL9iN07ER0jpY-Ikc")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            System.out.println("[SaveBook]: Saving book ");
            System.out.println("[SaveBook]:"+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    new ConnectionError().Connection("SUCCESS");
                    addbooktableview.getItems().add(book);
                });
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to save book");
                    if (error){
                        addbooktableview.refresh();
                        System.out.println("[SchoolFeeThread]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    addbooktableview.refresh();
                    System.out.println("[SchoolFeeThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
