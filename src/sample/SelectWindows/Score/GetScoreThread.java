package sample.SelectWindows.Score;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.ConnectionError;
import sample.SelectWindows.Score.Scores;
import sample.SelectWindows.Score.StudentAssessmentSession;
import sample.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetScoreThread extends Thread {

    private String ScoreSessionTable;
    private String Name;
    private TableView<Scores> tableView;
    public  GetScoreThread(String ScoreSessionValue,String Name,TableView<Scores>tableView){
        this.Name=Name;
        this.ScoreSessionTable=ScoreSessionValue;
        this.tableView=tableView;
        System.out.println("[GetScoreThread]: "+Name);
        System.out.println("[GetScoreThread]: "+ScoreSessionTable);

    }
    @Override
    public void run() {
        Connection conn= SqlConnection.connector();
        if(conn!=null){
            String Query="Select * from "+ScoreSessionTable+" Where Studentname=?";

            try {
                PreparedStatement preparedStatement=conn.prepareStatement(Query);
                preparedStatement.setString(1,Name);
                ResultSet resultSet=preparedStatement.executeQuery();
                ObservableList<Scores> scores= FXCollections.observableArrayList();
                while(resultSet.next()){

                    String Subject=resultSet.getString("Subject");
                    double FirstCa=resultSet.getDouble("Firstca");
                    double SecondCa=resultSet.getDouble("Secondca");
                    double ThirdCa=resultSet.getDouble("Thirdca");
                    double FourthCa=resultSet.getDouble("FourthCa");
                    double FifthCa=resultSet.getDouble("FifthCa");
                    double SixthCa=resultSet.getDouble("Sixthca");
                    double SeventhCa=resultSet.getDouble("Seventhca");
                    double EightCa=resultSet.getDouble("Eightca");
                    double NinthCa=resultSet.getDouble("Ninthca");
                    double Tenthca=resultSet.getDouble("Tenthca");
                    double Exam=resultSet.getDouble("Exam");
                    double Cumm=resultSet.getDouble("Cummulative");
                    scores.add(new Scores(Subject,FirstCa,SecondCa,ThirdCa,FourthCa,FifthCa,SeventhCa,SixthCa,EightCa,NinthCa,Tenthca,Exam,Cumm));
                    tableView.setItems(scores);
                    System.out.println(scores);




                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{
                System.out.println("[GetScoreThread]: ThreadFinish");

            });


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


