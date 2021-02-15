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
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchBookForEditLayoutThread extends Thread {
    private String bookname;
    private String session;
    private String term;
    private TableView<Book> editbooktableview;
    public SearchBookForEditLayoutThread(String bookname, String session, String term, TableView<Book> editbooktableview) {
        this.bookname=bookname;
        this.session=session;
        this.term=term;
        this.editbooktableview=editbooktableview;
    }

    @Override
    public void run() {
        System.out.println("[SearchBookForEditLayout]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/searchbook/"+bookname+"/"+session+"/"+term)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[SearchBookForEditLayout]: Retrieving response ");
                System.out.println("[SearchBookForEditLayout]:"+response);
                System.out.println("[SearchBookForEditLayout]:"+response.body());
                if (response.code()==200){
                    byte [] rawbytes = response.body().bytes();
                    String rawBody=new String(rawbytes,"UTF-8");
                    System.out.println("[SearchBookForEditLayout]: "+rawBody);
                    System.out.println("[SearchBookForEditLayout]: Processing response Body");
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
                        editbooktableview.setItems(tableList);
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+": "+message);
                        if (error){
                            System.out.println("[SearchBookForEditLayout]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[SearchBookForEditLayout]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }else{
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
