package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.Configuration.Configuration;
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
        Book book=bookselected.get(0);
        //Setting date
        book.setDate(date);
        System.out.println("[SellBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            System.out.println("[SellBook]: Setting up requestbody");
            GsonBuilder builder=new GsonBuilder();
            builder.setPrettyPrinting();
            builder.serializeNulls();
            Gson gson=builder.create();
            String json=gson.toJson(book);
            System.out.println("[SellBook]: Setting up requestbody-->"+json);
            RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/sellbook/"+book.getId()+"/"+buyer+"/"+bookselected.get(0).getYear())
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .post(requestBody)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                System.out.println("[SellBook]: selling book ");
                System.out.println("[SellBook]:"+response);
                if (response.code()==200){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        new ConnectionError().Connection("SUCCESS");
                        int copies=sellBookTableView.getSelectionModel().getSelectedItem().getCopies();
                        sellBookTableView.getSelectionModel().getSelectedItem().setCopies(copies-1);
                        sellBookTableView.refresh();
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+ message);
                        if (error){
                            sellBookTableView.refresh();
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
        }else {
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
