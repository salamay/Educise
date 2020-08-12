package sample.SelectWindows.Score;

import javafx.application.Platform;
import sample.ConnectionError;
import sample.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveScoreThread extends Thread {

    String Studentname;
    private String ScoreTable;
    private String Subject;


    public SaveScoreThread(String clas,String studentname,String Subject) {
        this.ScoreTable=clas;
        Studentname = studentname;
        this.Subject=Subject;
        System.out.println("[SaveScoreThread]: "+ ScoreTable+","+Studentname+","+Subject);
    }

    @Override
    public void run() {
        Connection conn= SqlConnection.connector();
        if(conn==null){
          Platform.runLater(()->{
              boolean result=new ConnectionError().Connection(conn);
              if (result==true){
                  StudentAssessmentSession.window.close();
              }
          });
        }else{

            ////this Query is for inserting subject
            String Query="insert into "+ScoreTable+"(Studentname,Subject) values (?,?)";
            try {
                PreparedStatement preparedStatement=conn.prepareStatement(Query);
                preparedStatement.setString(1,Studentname);
                preparedStatement.setString(2,Subject);
                int i=preparedStatement.executeUpdate();
                System.out.println("[SaveScoreThread]: "+ i);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{

            });
        }

    }

}

