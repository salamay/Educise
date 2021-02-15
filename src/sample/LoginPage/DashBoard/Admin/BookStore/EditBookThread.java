package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EditBookThread extends Thread {

    private EditBookRequest editBookRequest;
    private TableColumn.CellEditEvent<Book, ?> e;
    public EditBookThread(EditBookRequest editBookRequest, TableColumn.CellEditEvent<Book, ?> e) {
        this.editBookRequest=editBookRequest;
        this.e=e;
    }

    @Override
    public void run() {
        System.out.println("[EditBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(editBookRequest);
        System.out.println("[EditBook]: RequestBody--> "+json);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
        if (Configuration.ipaddress!=null){
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/editbook")
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .post(requestBody)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[EditBook]: Retrieving response ");
                System.out.println("[EditBook]:"+response);
                System.out.println("[EditBook]:"+response.body());
                if (response.code()==200){
                    String rawData=new String(response.body().bytes(),"UTF-8");
                    GsonBuilder builder1=new GsonBuilder();
                    builder1.setPrettyPrinting();
                    builder1.serializeNulls();
                    Gson gson1=builder1.create();
                    Book book=gson1.fromJson(rawData,Book.class);
                    System.out.println(">>>>>>>>"+book.getId());
                    System.out.println(">>>>>>>>"+book.getCopies());
                    ObservableList<Book> selectedBook,allBooks;
                    selectedBook=e.getTableView().getSelectionModel().getSelectedItems();
                    allBooks=e.getTableView().getItems();
                    Platform.runLater(()->{
                        selectedBook.forEach(allBooks::remove);
                        e.getTableView().refresh();
                        e.getTableView().getItems().add(book);
                    });
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            e.getTableView().getItems().clear();
                            System.out.println("[EditBook]--> Connection Error");
                        }
                    });
                }
            } catch (IOException ex) {
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        e.getTableView().getItems().clear();
                        System.out.println("[EditBook]--> Connection Error,Window close");
                    }
                });
                ex.printStackTrace();
            }
        }else {
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
