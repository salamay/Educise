package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

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

public class DeleteStudent extends Thread {
    private int id;
    private String session;
    private TableView<InformationEntity> tableView;
    public DeleteStudent(int id, String session, TableView<InformationEntity> tableView) {
        this.id=id;
        this.session=session;
        this.tableView=tableView;
    }

    @Override
    public void run() {
        System.out.println("[DeleteStudent]: id:"+id);
        System.out.println("[DeleteStudent]: session: "+session);
        System.out.println("[DeleteStudent]: Setting up client ");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Request request=new Request.Builder()
                .url("http://localhost:8080/deletestudent/"+id+"/"+session)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .delete()
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[DeleteStudent]: Retrieving response ");
            System.out.println("[DeleteStudent]:"+response);
            System.out.println("[DeleteStudent]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    ObservableList<InformationEntity> selecteditem=tableView.getSelectionModel().getSelectedItems();
                    ObservableList<InformationEntity> allBooks=tableView.getItems();
                    selecteditem.forEach(allBooks::remove);
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to delete student");
                    if (error){
                        System.out.println("[DeleteStudent]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        LoadingWindow.window.close();
                        System.out.println("[CreatingSessionThread]--> Access denied");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Cannot process your request");
                    if (error){
                        System.out.println("[DeleteStudent]--> Cannot process your request");
                    }
                    response.close();
                });

            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[DeleteStudent]--> Connection Error");
                }
            });
            e.printStackTrace();
        }
    }
}
