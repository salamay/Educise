package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;

public class SqlConnection {

    public static Connection connector(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url=String.format("jdbc:mysql://35.232.230.189:3306/_mystudentdb");
            Connection conn= DriverManager.getConnection(url,"salam1","oluwole");
            System.out.println("connecting");

            return conn;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
