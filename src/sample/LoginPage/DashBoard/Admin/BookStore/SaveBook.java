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
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[SaveBook]: Setting up requestbody ");
        GsonBuilder builder=new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();
        Gson gson=new Gson();
        String body=gson.toJson(book);
        System.out.println("[SaveBook]: "+body);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"), body);
        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .post(requestBody)
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/savebook")
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                System.out.println("[SaveBook]: Saving book ");
                System.out.println("[SaveBook]:"+response);
                if (response.code()==200){
                    String rawBody=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        new ConnectionError().Connection("SUCCESS");
                        GsonBuilder builder1=new GsonBuilder();
                        builder1.serializeNulls();
                        builder1.setPrettyPrinting();
                        Gson gson1=builder1.create();
                        Book book=gson1.fromJson(rawBody,Book.class);
                        addbooktableview.getItems().add(book);
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+": "+message);
                        if (error){
                            addbooktableview.refresh();
                            System.out.println("[SchoolFeeThread]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        addbooktableview.refresh();
                        System.out.println("[SaveBook]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }else{
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
