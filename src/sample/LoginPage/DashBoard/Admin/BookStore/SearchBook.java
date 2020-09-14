package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;
import java.util.List;

public class SearchBook extends Thread {
    private String bookname;
    private String session;
    private String term;
    private TableView<Book> sellBookTableView;
    public SearchBook(String bookname, String session, String term, TableView<Book> sellBookTableView) {
        this.bookname=bookname;
        this.session=session;
        this.term=term;
        this.sellBookTableView=sellBookTableView;
    }

    @Override
    public void run() {
        System.out.println("[SearchBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("http://localhost:8080/searchbook/"+bookname+"/"+session+"/"+term)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk5Nzk5OTY2LCJleHAiOjE2MDAxNTk5NjZ9.qwompSN9WRoyHTixemTubuVvPGZL9iN07ER0jpY-Ikc")
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[SearchBook]: Retrieving response ");
            System.out.println("[SearchBook]:"+response);
            System.out.println("[SearchBook]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                byte [] rawbytes = response.body().bytes();
                String rawBody=new String(rawbytes,"UTF-8");
                System.out.println("[SearchBook]: "+rawBody);
                System.out.println("[SearchBook]: Processing response Body");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                //This parse the list of json to a list of  book class with the help of type token
                //we cant specify directly to convert the json to book because rawBody variable contains
                //a list of json
                List<Book> books=gson.fromJson(rawBody,new TypeToken<List<Book>>(){}.getType());
                //Since table view accept observable list,we need to convert it to Observable list
                ObservableList<Book> tableList= FXCollections.observableList(books);
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    sellBookTableView.setItems(tableList);
                });
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get abook");
                    if (error){
                        System.out.println("[SearchBook]--> Connection Error");
                    }
                });
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[SearchBook]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }

}
