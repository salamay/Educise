package sample.SelectWindows.Score;
import javafx.application.Platform;
import sample.ConnectionError;
import sample.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveScoreThread10 extends Thread {

    private String Studentname;
    private String ScoreTable;
    private double data;
    private String Subject;

    public SaveScoreThread10(String scoreTable,String studentname,  double data,String Subject) {
        Studentname = studentname;
        ScoreTable = scoreTable;
        this.data = data;
        this.Subject=Subject;
    }


    @Override
    public void run() {
        Connection conn= SqlConnection.connector();
        if (conn!=null){
            String Query="update "+ScoreTable+" set ninthca=? where studentname=? and Subject=?";
            try {
                PreparedStatement preparedStatement=conn.prepareStatement(Query);
                preparedStatement.setDouble(1,data);
                preparedStatement.setString(2,Studentname);
                preparedStatement.setString(3,Subject);

                int i=preparedStatement.executeUpdate();
                System.out.println("[SaveScoreThread]: "+ i);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                boolean result=new ConnectionError().Connection(conn);
                if (result==true){
                    StudentAssessmentSession.window.close();
                }
            });
        }
    }
}