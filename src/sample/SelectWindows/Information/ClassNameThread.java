package sample.SelectWindows.Information;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import sample.ConnectionError;
import sample.SelectWindows.Information.StudentSelectClassInfo;
import sample.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        conn= SqlConnection.connector();
        if (conn==null){
          Platform.runLater(()->{
              boolean result=new ConnectionError().Connection(conn);
              if (result==true){
                  StudentSelectClassInfo.StudentWindow.close();
              }
          });
        }else {
            String Query = "Select StudentName from " + classname + " Where 1";
            try {
                NameList = FXCollections.observableArrayList();
                System.out.println("[ClassNameThread]: Getting name from database");
                PreparedStatement preparedStatement = conn.prepareStatement(Query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    NameList.add(resultSet.getString("Studentname"));
                    System.out.println("[ClassNameThread]: " + NameList);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

            Platform.runLater(() -> {
                listview.getItems().addAll(NameList);
                System.out.println(listview);
            });

    }

}
