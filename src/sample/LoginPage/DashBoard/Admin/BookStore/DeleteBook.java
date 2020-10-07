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

        Request request=new Request.Builder()
                .url("http://localhost:8080/deletebook/"+id)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .delete()
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[DeleteBook]: Retrieving response ");
            System.out.println("[DeleteBook]:"+response);
            System.out.println("[DeleteBook]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    ObservableList<Book> selecteditem=editbooktableview.getSelectionModel().getSelectedItems();
                    ObservableList<Book> allBooks=editbooktableview.getItems();
                    selecteditem.forEach(allBooks::remove);
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to delete books");
                    if (error){
                        System.out.println("[DeleteBook]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Cannot process your request");
                if (error){
                    System.out.println("[DeleteBook]--> Cannot process your request");
                }
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
                    System.out.println("[DeleteBook]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
