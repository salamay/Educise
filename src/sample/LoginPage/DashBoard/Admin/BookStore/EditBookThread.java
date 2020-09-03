package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;

public class EditBookThread extends Thread {

    private EditBookRequest editBookRequest;
    private String oldValue;
    private TableColumn.CellEditEvent<Book, ?> e;
    public EditBookThread(EditBookRequest editBookRequest, String oldValue, TableColumn.CellEditEvent<Book, ?> e) {
        this.editBookRequest=editBookRequest;
        this.oldValue=oldValue;
        this.e=e;
    }

    @Override
    public void run() {
        System.out.println("[EditBook]: Setting up client ");
        OkHttpClient client=new OkHttpClient();
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(editBookRequest);
        System.out.println("[EditBook]: RequestBody--> "+json);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new Request.Builder()
                .url("http://localhost:8080/editbook")
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .post(requestBody)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[EditBook]: Retrieving response ");
            System.out.println("[EditBook]:"+response);
            System.out.println("[EditBook]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                Platform.runLater(()->{

                });
            }else {
                Platform.runLater(()->{
                    e.getTableColumn().onEditCancelProperty();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to Edit book books");
                    if (error){
                        System.out.println("[EditBook]--> Connection Error");
                    }
                });
            }
            if (response.code()==404){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to edit book");
                    if (error){
                        System.out.println("[EditBook]--> unable to save school fee on the server");
                    }
                });
                response.close();
            }
            if (response.code()==422){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Server cannot process your request,check fields for invalid character");
                    if (error){
                        System.out.println("[EditBook]--> Connection error");
                    }
                });
                response.close();
            }
            if (response.code()==403){
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Access denied");
                    if (error){
                        System.out.println("Access denied");
                    }
                });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[EditBook]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }

    }
}
