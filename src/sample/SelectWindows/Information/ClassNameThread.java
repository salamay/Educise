package sample.SelectWindows.Information;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import okhttp3.*;
import sample.ConnectionError;
import sample.SelectWindows.Information.StudentSelectClassInfo;
import sample.SqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//This class retrieve student name and add it to the list view
//after the session has ben selected
public class ClassNameThread extends Thread {
    String classname;
    Connection conn;
    ObservableList<String> NameList;
    ListView<String> listview;


    public ClassNameThread(String clas, ListView<String> list) {
        this.classname = clas;
        this.listview = list;
    }

    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient();
        System.out.println("[ClassNameThread]: Receiving name ");
        System.out.println("[ClassNameThread]: preparing request ");
        Request request=new Request.Builder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4MDc2MzgyLCJleHAiOjE1OTgxMTIzODJ9.OJrRife0Z7GLD-kg-GR2qmkLBLaSNhom0gHFXaHFDV8")
                .url("http://localhost:8080/retrivenames/"+classname)
                .build();
        System.out.println("[ClassNameThread]: preparing to send request ");
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[ClassNameThread]: Request send ");
            ///////Processing request
            System.out.println("[ClassNameThread]: processing request ");
            ResponseBody body=response.body();
            byte[] bytes=body.bytes();
            String list=new String(bytes,"UTF-8");
            String list2=list.replace('[',' ');
            String list3=list2.replace(']',' ');
            System.out.println(list3);
            List<String> namelist= Arrays.stream(list3.split(",")).collect(Collectors.toList());
            ////Processing request end
            Platform.runLater(() -> {
                listview.getItems().addAll(namelist);
                System.out.println(listview);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
