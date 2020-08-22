package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;

public class SqlConnection {

    public static Connection connector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url=String.format("jdbc:mysql://127.0.0.1:3306/my_spring_user");
            Connection conn= DriverManager.getConnection(url,"root","salamay");
            System.out.println("connecting");

            return conn;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
