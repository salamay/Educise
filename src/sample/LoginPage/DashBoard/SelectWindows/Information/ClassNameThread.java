package sample.LoginPage.DashBoard.SelectWindows.Information;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//This class retrieve student name and add it to the list view
//after the session has ben selected
//The window here is the window passed from the calling class,the window passed here is closed when an error occur
public class ClassNameThread extends Thread {
   private String classname;
   private ListView<String> listview;
   private Stage window;

    public ClassNameThread(String clas, ListView<String> list, Stage window) {
        this.classname = clas;
        this.listview = list;
        this.window=window;
    }

    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
        System.out.println("[ClassNameThread]: Receiving name ");
        System.out.println("[ClassNameThread]: preparing request ");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .url("http://localhost:8080/retrivenames/"+classname)
                .build();
        System.out.println("[ClassNameThread]: preparing to send request ");
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[ClassNameThread]: Request sent ");
            ///////Processing request
            System.out.println("[ClassNameThread]: processing response ");
            if (response.code()==200||response.code()==212||response.code()==201){
                ResponseBody body=response.body();
                byte[] bytes=body.bytes();
                String list=new String(bytes,"UTF-8");
                GsonBuilder gsonBuilder=new GsonBuilder();
                gsonBuilder.serializeNulls();
                gsonBuilder.setPrettyPrinting();
                Gson gson=gsonBuilder.create();
                List<ListViewNames> list2=gson.fromJson(list,new TypeToken<List<ListViewNames>>(){}.getType());

                ////Processing request end
                Platform.runLater(() -> {
                    for (int i=0;i<list2.size();i++){
                        listview.getItems().addAll(list2.get(i).getName());
                    }
                    System.out.println(listview.toString());
                });
                response.close();
            }else{
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  retrieve names");
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
    }

}
