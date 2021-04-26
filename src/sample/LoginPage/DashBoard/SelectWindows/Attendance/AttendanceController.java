package sample.LoginPage.DashBoard.SelectWindows.Attendance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.Configuration.Configuration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetClassThread;
import sample.LoginPage.DashBoard.SelectWindows.Utility.GetSessionThread;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AttendanceController implements Initializable {
    public JFXComboBox<String> classComboBox;
    public JFXComboBox<String> sessionComboBox;
    public JFXComboBox<String> termComboBox;
    public JFXComboBox<String> daytimeComboBox;
    public JFXComboBox<String> genderComboBox;
    public JFXComboBox<String> weekComboBox;
    public GridPane attendanceGridPane;
    public ProgressIndicator ProgressBar = new ProgressIndicator();
    private String classelected;
    private String session;
    private String term;
    private String daytime;
    private String gender;
    private String week;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendanceGridPane.setGridLinesVisible(true);
        termComboBox.getItems().addAll("1","2","3");
        daytimeComboBox.getItems().addAll("Morning","Afternoon","Evening");
        genderComboBox.getItems().addAll("Male","Female");
        weekComboBox.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11","12","13");
        ////////////Starting to get sessions from server
        //the progressbar is added to avoid null pointer exception
        new GetSessionThread(sessionComboBox, ProgressBar, "retrievesession").start();
        ///////////Starting to get class end
        new GetClassThread(classComboBox, ProgressBar, "retrieveclasses").start();
        ///This fires when the continue button is clicked,it displays a listview of student name
    }

    public void showAttendanceButtonClicked() {
        if (classComboBox.getValue() != null && sessionComboBox.getValue() != null&& termComboBox.getValue() != null&&daytimeComboBox.getValue()!=null&&genderComboBox.getValue()!=null&&weekComboBox.getValue()!=null) {
            classelected = classComboBox.getSelectionModel().getSelectedItem();
            session = sessionComboBox.getSelectionModel().getSelectedItem();
            term=termComboBox.getSelectionModel().getSelectedItem();
            daytime=daytimeComboBox.getSelectionModel().getSelectedItem();
            gender=genderComboBox.getSelectionModel().getSelectedItem();
            week=weekComboBox.getSelectionModel().getSelectedItem();
            new GetAttentanceThread().start();
        } else {
            new ConnectionError().Connection("One of the field is missing");
        }

    }


    public class GetAttentanceThread extends Thread {
        @Override
        public void run() {
            Platform.runLater(() -> {
                try {
                    new LoadingWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (Configuration.ipaddress != null && Configuration.port != null) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .readTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + Configuration.ipaddress + ":" + Configuration.port + "/getattendance/" + classelected + "/" + session+"/"+term+"/"+daytime+"/"+gender+"/"+week)
                        .get()
                        .header("Authorization", "Bearer " + LogInModel.token)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    System.out.println("[Getattendance] >> Response:"+response);
                    byte[] bytes=response.body().bytes();
                    String rawData=new String(bytes,"UTF-8");
                    GsonBuilder builder=new GsonBuilder();
                    builder.serializeNulls();
                    builder.setPrettyPrinting();
                    Gson gson=builder.create();
                    System.out.println(rawData);
                    ArrayList<AttendanceEntity> attendanceEntity=gson.fromJson(rawData,new TypeToken<List<AttendanceEntity>>(){}.getType());

                    //Filter attendance by daytime
                    ArrayList<AttendanceEntity> filteredByDaytimeAttendance=filterAttendanceByDaytime(attendanceEntity,daytime);
                    if (!filteredByDaytimeAttendance.isEmpty()){
                        //This block convert the retrieved data to a processable data
                        //it convert the filtered attendance list to map by placing each list studentname in rawname list to avoid
                        //duplicate name since the esponse contain duplicate name.the studentname is used as key in the map while
                        // the value is an Arraylist of days present
                        if (filteredByDaytimeAttendance!=null){
                            ArrayList<String> rawName=new ArrayList<>();
                            Map<String,List<String>> map=new HashMap<>();
                            for (int i=0;i<=filteredByDaytimeAttendance.size();i++){
                                //getting the first name on the list and assigning it in Firstname variable
                                String FirstnameOnList=filteredByDaytimeAttendance.get(0).getStudentname();
                                //Add the name to the rawName list, this name has an index of zero on the list
                                rawName.add(FirstnameOnList);
                                final ArrayList<String> daysPresent=new ArrayList<>();
                                //checking if the next student name is the same as the previous,if it true,that means
                                // the studentname has another days present,if not, add the data i.e weekdays gotten to the
                                //daysPresent List which corresspond to Student name on rawname

                                filteredByDaytimeAttendance.stream().filter(a ->a.getStudentname().equals(FirstnameOnList)).forEach(a->{
                                    daysPresent.add(a.getWeekday());
                                });
                                map.put(FirstnameOnList,daysPresent);
                                filteredByDaytimeAttendance.removeIf(a->a.getStudentname().equals(FirstnameOnList));
                            }
                            for (int i=0;i<rawName.size();i++){
                                System.out.println(map.get(rawName.get(i)));
                            }
                            Platform.runLater(()->{
                                for (int row=0;row<map.size();row++){
                                    HBox hBox=new HBox();
                                    hBox.setSpacing(7);
                                    attendanceGridPane.add(new Label(rawName.get(row)),1,row+1);
                                    attendanceGridPane.add(new Label(String.valueOf(row+1)),0,row+1);
                                    for (int daysindex=0;daysindex<map.get(rawName.get(row)).size();daysindex++){
                                        Separator separator=new Separator();
                                        separator.setOrientation(Orientation.VERTICAL);
                                        Circle circle=new Circle(10);
                                        circle.setFill(Color.GREEN);
                                        Label label=new Label(map.get(rawName.get(row)).get(daysindex));
                                        hBox.getChildren().addAll(label,circle,separator);
                                    }
                                    attendanceGridPane.add(hBox,2,row+1);
                                }
                                LoadingWindow.window.close();
                            });
                        }else {
                            LoadingWindow.window.close();
                            new ConnectionError().Connection("Unable to parse attendance");
                            attendanceGridPane.getChildren().clear();
                        }
                    }else {
                       Platform.runLater(()->{
                           LoadingWindow.window.close();
                           new ConnectionError().Connection("Attendance not found");
                           attendanceGridPane.getChildren().clear();
                       });
                    }

                } catch (IOException e) {
                    //Display an Alert dialog
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                        if (error){
                            System.out.println("[SaveScoreThread]--> Connection Error,Window close");
                        }
                    });
                    e.printStackTrace();
                }

            } else {
                Platform.runLater(() -> {
                    LoadingWindow.window.close();
                    new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
                });
            }
        }
        public ArrayList<AttendanceEntity> filterAttendanceByDaytime(ArrayList<AttendanceEntity> attendanceEntities,String daytime){
            if (daytime.equals("Morning")){
                return getMorningAttendance(attendanceEntities);
            } else if (daytime.equals("Afternoon")){
                return getAfternoonAttendance(attendanceEntities);
            }else if (daytime.equals("Evening")){
                return getEveningAttendance(attendanceEntities);
            }else {
                return null;
            }
        }
        private ArrayList<AttendanceEntity> getMorningAttendance(ArrayList<AttendanceEntity>attendanceEntities){
            ArrayList<AttendanceEntity> morningAttendance=new ArrayList<>();
            attendanceEntities.stream().filter(a->a.getDaytime().equals("Morning")).forEach(morningAttendance::add);
            return morningAttendance;
        }
        private ArrayList<AttendanceEntity> getAfternoonAttendance(ArrayList<AttendanceEntity>attendanceEntities){
            ArrayList<AttendanceEntity> afternoonAttendance=new ArrayList<>();
            attendanceEntities.stream().filter(a->a.getDaytime().equals("Afternoon")).forEach(afternoonAttendance::add);
            return afternoonAttendance;
        }
        private ArrayList<AttendanceEntity> getEveningAttendance(ArrayList<AttendanceEntity>attendanceEntities){
            ArrayList<AttendanceEntity> eveningAttendance=new ArrayList<>();
            attendanceEntities.stream().filter(a->a.getDaytime().equals("Evening")).forEach(eveningAttendance::add);
            return eveningAttendance;
        }
    }
}

