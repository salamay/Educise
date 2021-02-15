package sample.LoginPage.DashBoard.Admin.BookStore;

import javafx.application.Platform;
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
import java.util.concurrent.TimeUnit;

public class DeleteBook extends Thread{
    private TableView<Book> editbooktableview;
    private String id;
    public DeleteBook(String id, TableView<Book> editbooktableview) {
        this.id=id;
        this.editbooktableview=editbooktableview;
    }

    @Override
    public void run() {
        System.out.println("[DeleteBook]: id: "+id);
        System.out.println("[DeleteBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        if (Configuration.ipaddress!=null&&Configuration.port!=null){
            Request request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/deletebook/"+id)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .delete()
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[DeleteBook]: Retrieving response ");
                System.out.println("[DeleteBook]:"+response);
                System.out.println("[DeleteBook]:"+response.body());
                if (response.code()==200){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        ObservableList<Book> selecteditem=editbooktableview.getSelectionModel().getSelectedItems();
                        ObservableList<Book> allBooks=editbooktableview.getItems();
                        selecteditem.forEach(allBooks::remove);
                    });
                    response.close();
                }else {
                    String message=new String(response.body().bytes(),"UTF-8");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[DeleteBook]--> Connection Error");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[DeleteBook]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }else {
            new ConnectionError().Connection("Invalid configuration settings, Configure your software in the login page");
        }
    }
}
