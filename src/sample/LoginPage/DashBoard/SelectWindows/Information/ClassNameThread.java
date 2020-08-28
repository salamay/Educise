package sample.LoginPage.DashBoard.SelectWindows.Information;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import okhttp3.*;
import sample.ConnectionError;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
        OkHttpClient client=new OkHttpClient();
        System.out.println("[ClassNameThread]: Receiving name ");
        System.out.println("[ClassNameThread]: preparing request ");
        Request request=new Request.Builder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
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
                String list2=list.replace('[',' ');
                String list3=list2.replace(']',' ');
                String list4=list3.replaceAll(" ","");
                System.out.println(list4);
                List<String> namelist= Arrays.stream(list4.split(",")).collect(Collectors.toList());
                ////Processing request end
                Platform.runLater(() -> {
                    listview.getItems().addAll(namelist);
                    System.out.println(listview);
                });
                response.close();
                body.close();
            }else{
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  retrieve names");
                    if (error){
                        window.close();
                        System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
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
