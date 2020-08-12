package sample;

import sample.ConnectionError;
import sample.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInModel {
     Connection conn;

    public LogInModel() {
        conn= SqlConnection.connector();
        new ConnectionError().Connection(conn);
        if (conn==null){

            System.out.println("fail to set up connection");
        }

    }
    public void isDBconnected(){
        try {
            if(conn.isClosed()){
                System.out.println("connection is closed");
                }
            else{
                System.out.println("Connection is not closed");
            }
            }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }
public boolean loginQuery(String email,String passwd){

        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String query="SELECT * FROM studentinfo where username= ? and password= ?";
    try {
        preparedStatement=conn.prepareStatement(query);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,passwd);
        resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }else {
return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }


}
    }


