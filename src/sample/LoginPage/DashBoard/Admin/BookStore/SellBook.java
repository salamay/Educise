package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SellBook  extends Thread{
    private TableView<Book> sellBookTableView;
    private ObservableList<Book> bookselected;
    private String buyer;
    private String date;
    public SellBook(TableView<Book> sellBookTableView, ObservableList<Book> bookselected, String buyer, String date) {
        this.sellBookTableView=sellBookTableView;
        this.bookselected=bookselected;
        this.buyer=buyer;
        this.date=date;
        System.out.println("[SellBook]:Book to be sold-->"+bookselected.toString());
        System.out.println("[SellBook]:Buyer-->"+buyer);
    }

    @Override
    public void run() {
        Book book=new Book();
        book=bookselected.get(0);
        String bookname=bookselected.get(0).title;
        String session=bookselected.get(0).year;
        String term=bookselected.get(0).term;
        //Setting date
        book.setDate(date);
        System.out.println("[SellBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[SellBook]: Setting up requestbody");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(book);
        System.out.println("[SellBook]: Setting up requestbody-->"+json);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new Request.Builder()
                .url("http://167.99.91.154:8080/sellbook/"+bookname+"/"+term+"/"+session+"/"+buyer)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            System.out.println("[SellBook]: selling book ");
            System.out.println("[SellBook]:"+response);
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    new ConnectionError().Connection("SUCCESS");
                    int copies=sellBookTableView.getSelectionModel().getSelectedItem().getCopies();
                    sellBookTableView.getSelectionModel().getSelectedItem().setCopies(copies-1);
                    sellBookTableView.refresh();
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to sell book");
                    if (error){
                        sellBookTableView.refresh();
                        System.out.println("[SellBook]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==400){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Cannot sell book");
                    if (error){
                        System.out.println("[SellBook]--> unable to save school fee on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        System.out.println("[SellBook]--> Connection error");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        System.out.println("Access denied");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    sellBookTableView.refresh();
                    System.out.println("[SellBook]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
