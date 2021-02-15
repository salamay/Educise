package sample.LoginPage.DashBoard.SelectWindows.Information;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//This class retrieve student name,id and add it to the list view,id is excluded
//after the session and class has ben selected
//The window here is the window passed from the calling class,the window passed here is closed when an error occur
public class ClassNameThread extends Thread {
   private String classname;
   private ListView<String> listview;
   private Stage window;
   //This is made static so as to be used in other part to get the id of the selected student
   public static List<ListViewNames> list2;
    private String session;
    public ClassNameThread(String clas, String session, ListView<String> list, Stage window) {
        this.classname = clas;
        this.session=session;
        this.listview = list;
        this.window=window;
    }

    @Override
    public void run() {
        System.out.println("[ClassNameThread]: Retrieving name of student ");
        if (Configuration.ipaddress!=null || Configuration.port!=null){
            OkHttpClient client=new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build();

            System.out.println("[ClassNameThread]: preparing request ");
            Request request=new Request.Builder()
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/retrivenames/"+classname+"/"+session)
                    .build();
            System.out.println("[ClassNameThread]: preparing to send request ");
            try {
                Response response=client.newCall(request).execute();
                System.out.println("[ClassNameThread]: Request sent ");
                ///////Processing request
                System.out.println("[ClassNameThread]: processing response ");
                if (response.code()==200){
                    ResponseBody body=response.body();
                    byte[] bytes=body.bytes();
                    String list=new String(bytes,"UTF-8");
                    GsonBuilder gsonBuilder=new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    gsonBuilder.setPrettyPrinting();
                    Gson gson=gsonBuilder.create();
                    list2=gson.fromJson(list,new TypeToken<List<ListViewNames>>(){}.getType());
                    ////Processing request end
                    Platform.runLater(() -> {
                        for (int i=0;i<list2.size();i++){
                            listview.getItems().addAll(list2.get(i).getName());
                           // listview.getItems().addAll(list2.get(i).getName());
                        }
                        System.out.println(listview.toString());
                    });
                    response.close();
                }else{
                    String message=new String(response.body().bytes(),"UTF-8");
                    //Display alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            window.close();
                            System.out.println("[GetScoreThread]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }

            } catch (IOException e) {
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection:CHECK INTERNET YOUR CONNECTION");
                    if (error){
                        window.close();
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }

        }else {
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }

    }

}
