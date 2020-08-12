package sample;

import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import sample.SelectWindows.Score.StudentAssessmentSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreatingSessionThread  extends Thread{
    String jss1sessionName;
    String jss2sessionName;
    String jss3sessionName;
    String ss1sessionName;
    String ss2sessionName;
    String ss3sessionName;
    Connection conn;
    JFXSpinner LoadingSpinner;
    double counter=0.0;
    public CreatingSessionThread(String jss1Name, String jss2Name, String jss3Name, String ss1Name, String ss2Name, String
            ss3Name, JFXSpinner bar){
        this.jss1sessionName=jss1Name;
        this.jss2sessionName=jss2Name;
        this.jss3sessionName=jss3Name;
        this.ss1sessionName=ss1Name;
        this.ss2sessionName=ss2Name;
        this.ss3sessionName=ss3Name;
        this.LoadingSpinner=bar;
        System.out.println("[CreatingSessionThread]"+jss1sessionName);
        System.out.println("[CreatingSessionThread]"+jss2sessionName);
        System.out.println("[CreatingSessionThread]"+jss3sessionName);
        System.out.println("[CreatingSessionThread]"+ss1sessionName);
        System.out.println("[CreatingSessionThread]"+ss2sessionName);
        System.out.println("[CreatingSessionThread]"+ss3sessionName);
    }
    @Override
    public void run() {
        conn= SqlConnection.connector();
        //information section
        String Query="Create table if not exists "+jss1sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";


        //jss2Query
        String jss2Query="Create table if not exists "+jss2sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";


        //jss3Query
        String jss3Query="Create table if not exists "+jss3sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";


        String ss1Query="Create table if not exists "+ss1sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";


        //ss2Query
        String ss2Query="Create table if not exists "+ss2sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float ,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";


        //ss3Query
        String ss3Query="Create table if not exists "+ss3sessionName+"information"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"varchar(20),"+
                "PhoneNo "+"float,"+
                "Nickname "+"varchar(10),"+
                "Hobbies "+"varchar(20),"+
                "TurnOn "+"varchar(20),"+
                "TurnOff "+"varchar(20),"+
                "Gender "+"varchar(10),"+
                "Club "+"varchar(20),"+
                "RoleModel "+"varchar(20),"+
                "FutureAmbition "+"varchar(25),"+
                "AcademicPerformance "+"varchar(20),"+
                "Age "+"int(20),"+
                "Fathername "+"varchar(30),"+
                "Mothername "+"varchar(30),"+
                "Nextofkin "+"varchar(20),"+
                "Address "+"varchar(40),"+
                "Picture "+"Blob,"+
                "Parentpicture "+"Blob,"+
                "Motherpicture "+"Blob,"+
                "primary key(id)"+
                ")";

        ////////////////////////Score session
        String Score1Query="Create table if not exists "+jss1sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";

        String Score2Query="Create table if not exists "+jss2sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";

        String Score3Query="Create table if not exists "+jss3sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";

        String Score4Query="Create table if not exists "+ss1sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";

        String Score5Query="Create table if not exists "+ss2sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";

        String Score6Query="Create table if not exists "+ss3sessionName+"Score"+"(" +
                "id " +"int auto_increment,"+
                "StudentName "+"TEXT,"+
                "Subject "+"TEXT,"+
                "firstca "+"Double,"+
                "secondca "+"Double,"+
                "thirdca "+"Double,"+
                "fourthca "+"Double,"+
                "fifthca "+"Double,"+
                "sixthca "+"Double,"+
                "seventhca "+"Double,"+
                "Eightca "+"Double,"+
                "Ninthca "+"Double,"+
                "Tenthca "+"Double,"+
                "Exam "+"Double,"+
                "Cummulative "+"Double,"+
                "primary key(id)"+
                ")";



        System.out.println("[CreatingSessionThread]:"+jss1sessionName+"information");

        if(conn!=null){
            try {

                //jss1 Query
                PreparedStatement preparedStatement=conn.prepareStatement(Query);

                int i=preparedStatement.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 1 executed");
                System.out.println("[CreatingSessionThread]: "+i);


               //Jss2 query
                PreparedStatement preparedStatement1=conn.prepareStatement(jss2Query);
                int j=preparedStatement1.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 2 executed");
                System.out.println("[CreatingSessionThread]: "+j);
                Platform.runLater(()->{
                    while (true) {
                        System.out.println(counter);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()->{
                            LoadingSpinner.setProgress(counter);
                        });
                        counter+=0.01;
                        if (counter > 0.02) {
                            break;
                        }
                    }
                });
                //Jss3 query
                PreparedStatement preparedStatement2=conn.prepareStatement(jss3Query);
                int k=preparedStatement2.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 3 executed");
                System.out.println("[CreatingSessionThread]: "+k);

                //ss1 query
                PreparedStatement preparedStatement3=conn.prepareStatement(ss1Query);
                int l=preparedStatement3.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 4 executed");
                System.out.println("[CreatingSessionThread]: "+l);

                //ss2query
                PreparedStatement preparedStatement4=conn.prepareStatement(ss2Query);
                int m=preparedStatement4.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 5 executed");
                System.out.println("[CreatingSessionThread]: "+m);

                //ss3 query
                PreparedStatement preparedStatement5=conn.prepareStatement(ss3Query);
                int n=preparedStatement5.executeUpdate();

                System.out.println("[CreatingSessionThread]: Query 6 executed");
                System.out.println("[CreatingSessionThread]: "+n);


                ///////////////////////////////////////////////////////////
                //Adding session name to database
                String SaveSessionQuery="INSERT INTO SessionTable (sessionname)  VALUES(?)";

                //session query1
                String SaveSessionQuery1="INSERT INTO SessionTable (sessionname)  VALUES(?)";
                //Session query 2
                String SaveSessionQuery2="INSERT INTO SessionTable (sessionname)  VALUES(?)";
                //session query 3
                String SaveSessionQuery3="INSERT INTO SessionTable (sessionname)  VALUES(?)";
                // session query 4
                String SaveSessionQuery4="INSERT INTO SessionTable (sessionname)  VALUES(?)";
                // session query 5
                String SaveSessionQuery5="INSERT INTO SessionTable (sessionname)  VALUES(?)";


                ///////////////////////////////////////////////////////////
                //Adding sessionScore name to database
                String SaveSessionScoreQuery="INSERT INTO SessionScore (sessionscore)  VALUES(?)";

                //session query1
                String SaveSessionScoreQuery1="INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                //Session query 2
                String SaveSessionScoreQuery2="INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                //session query 3
                String SaveSessionScoreQuery3="INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 4
                String SaveSessionScoreQuery4="INSERT INTO SessionScore (sessionscore)  VALUES(?)";
                // session query 5
                String SaveSessionScoreQuery5="INSERT INTO SessionScore (sessionscore)  VALUES(?)";


                ///////////////////Saving to database
                PreparedStatement preparedStatement6=conn.prepareStatement(SaveSessionQuery);
                preparedStatement6.setString(1,jss1sessionName+"information");
                int o=preparedStatement6.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 7 executed");
                System.out.println("[CreatingSessionThread]: "+o);


                PreparedStatement preparedStatement7=conn.prepareStatement(SaveSessionQuery1);
                preparedStatement7.setString(1,jss2sessionName+"information");
                int p=preparedStatement7.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 8 executed");
                System.out.println("[CreatingSessionThread]: "+p);
                Platform.runLater(()->{
                    while (true) {
                        System.out.println(counter);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()->{
                            LoadingSpinner.setProgress(counter);
                        });
                        counter+=0.01;
                        if (counter > 0.04) {
                            break;
                        }
                    }
                });

                PreparedStatement preparedStatement8=conn.prepareStatement(SaveSessionQuery2);
                preparedStatement8.setString(1,jss3sessionName+"information");
                int q=preparedStatement8.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 9 executed");
                System.out.println("[CreatingSessionThread]: "+q);

                PreparedStatement preparedStatement9=conn.prepareStatement(SaveSessionQuery3);
                preparedStatement9.setString(1,ss1sessionName+"information");
                int r=preparedStatement9.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 10 executed");
                System.out.println("[CreatingSessionThread]: "+r);

                PreparedStatement preparedStatement10=conn.prepareStatement(SaveSessionQuery4);
                preparedStatement10.setString(1,ss2sessionName+"information");
                int s=preparedStatement10.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 11 executed");
                System.out.println("[CreatingSessionThread]: "+s);

                PreparedStatement preparedStatement11=conn.prepareStatement(SaveSessionQuery5);
                preparedStatement11.setString(1,ss3sessionName+"information");
                int t=preparedStatement11.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query 12 executed");
                System.out.println("[CreatingSessionThread]: "+t);

                PreparedStatement preparedStatement12=conn.prepareStatement(Score1Query);
                int u=preparedStatement12.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score1 executed");
                System.out.println("[CreatingSessionThread]: "+u);
                Platform.runLater(()->{
                    while (true) {
                        System.out.println(counter);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()->{
                            LoadingSpinner.setProgress(counter);
                        });
                        counter+=0.01;
                        if (counter > 0.05) {
                            break;
                        }
                    }
                });

                PreparedStatement preparedStatement13=conn.prepareStatement(Score2Query);
                int v=preparedStatement13.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score2 executed");
                System.out.println("[CreatingSessionThread]: "+v);

                PreparedStatement preparedStatement14=conn.prepareStatement(Score3Query);
                int w=preparedStatement14.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score3 executed");
                System.out.println("[CreatingSessionThread]: "+w);

                PreparedStatement preparedStatement15=conn.prepareStatement(Score4Query);
                int x=preparedStatement15.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score4 executed");
                System.out.println("[CreatingSessionThread]: "+x);

                PreparedStatement preparedStatement16=conn.prepareStatement(Score5Query);
                int y=preparedStatement16.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score5 executed");
                System.out.println("[CreatingSessionThread]: "+y);

                PreparedStatement preparedStatement17=conn.prepareStatement(Score6Query);
                int z=preparedStatement17.executeUpdate();
                System.out.println("[CreatingSessionThread]: Query Score6 executed");
                System.out.println("[CreatingSessionThread]: "+z);

                //////////Saving Session Score To database
                System.out.println("Saving Session Score To Database");

                PreparedStatement preparedStatement18=conn.prepareStatement(SaveSessionScoreQuery);
                preparedStatement18.setString(1,jss1sessionName+"Score");
                int a=preparedStatement18.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 1 executed");
                System.out.println("[CreatingSessionThread]: "+a);

                PreparedStatement preparedStatement19=conn.prepareStatement(SaveSessionScoreQuery1);
                preparedStatement19.setString(1,jss2sessionName+"Score");
                int b=preparedStatement19.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 2 executed");
                System.out.println("[CreatingSessionThread]: "+b);
                Platform.runLater(()->{
                    while (true) {
                        System.out.println(counter);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(()->{
                            LoadingSpinner.setProgress(counter);
                        });
                        counter+=0.01;
                        if (counter > 0.1) {
                            break;
                        }
                    }
                });

                PreparedStatement preparedStatement20=conn.prepareStatement(SaveSessionScoreQuery2);
                preparedStatement20.setString(1,jss3sessionName+"Score");
                int c=preparedStatement20.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 3 executed");
                System.out.println("[CreatingSessionThread]: "+c);

                PreparedStatement preparedStatement21=conn.prepareStatement(SaveSessionScoreQuery3);
                preparedStatement21.setString(1,ss1sessionName+"Score");
                int d=preparedStatement21.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 4 executed");
                System.out.println("[CreatingSessionThread]: "+d);

                PreparedStatement preparedStatement22=conn.prepareStatement(SaveSessionScoreQuery4);
                preparedStatement22.setString(1,ss2sessionName+"Score");
                int e=preparedStatement22.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 5 executed");
                System.out.println("[CreatingSessionThread]: "+e);

                PreparedStatement preparedStatement23=conn.prepareStatement(SaveSessionScoreQuery5);
                preparedStatement23.setString(1,ss3sessionName+"Score");
                int f=preparedStatement23.executeUpdate();
                System.out.println("[CreatingSessionThread]: SessionScoreQuery 6 executed");
                System.out.println("[CreatingSessionThread]: "+f);
                Platform.runLater(()->{
                    while (true) {
                        System.out.println(counter);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ee) {
                            ee.printStackTrace();
                        }
                        Platform.runLater(()->{
                            LoadingSpinner.setProgress(counter);
                        });
                        counter+=0.01;
                        if (counter > 1) {
                            break;
                        }
                    }
                });
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                boolean result=new ConnectionError().Connection(conn);
                if (result==true){
                    //StudentAssessmentSession.window.close();
                }
            });
        }
    }
}

