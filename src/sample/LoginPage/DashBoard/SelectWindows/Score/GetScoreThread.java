package sample.LoginPage.DashBoard.SelectWindows.Score;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
import sample.ConnectionError;


import java.io.IOException;
import java.util.List;


///This class get the Scores from the Database
//The ScoreSessionTable is the value selected from the combobox,it correspond to a table that stores scores in the database
//Name variable contains the name of the student,it will fetch the scores of the student
//after fetching the datas, it set the table to the corresponding column
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
        GetScoreRequestEntity getScoreRequestEntity=new GetScoreRequestEntity();
        getScoreRequestEntity.setName(Name);
        getScoreRequestEntity.setTable(ScoreSessionTable);
        System.out.println("[GetScoreThread]--> Preparing Json body");
        GsonBuilder builder=new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson=builder.create();
        String json=gson.toJson(getScoreRequestEntity);
        System.out.println("[GetScoreThread]--> entity to send"+json);
        System.out.println("[GetScoreThread]--> Setting up connection");
        OkHttpClient client=new OkHttpClient();
        Response response;
        ///This make the request body for the json
        RequestBody jsonbody=RequestBody.create(MediaType.parse("application/json"),json);
        RequestBody body=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("jsonbody","json.gson",jsonbody)
                .build();
        Request request=new Request.Builder()
                .post(body)
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA4NTczLCJleHAiOjE1OTg2ODg1NzN9.9nK-QCA6cxUmsU1qBiE8CEhiAMoBqfLuSehQQA9yJbU")
                .url("http://localhost:8080/getstudentscores")
                .build();
        System.out.println("[GetScoreThread]: Setting up request body");
        System.out.println("[GetScoreThread]--> Sending request");
        try {
            response=client.newCall(request).execute();
            System.out.println(response);
            System.out.println(response.body());
            if (response.code()==201||response.code()==200||response.code()==202){
                System.out.println("[GetScoreThread]--> Response:"+response.body());
                System.out.println("[GetScoreThread]--> Processing response");
                byte[] bytes=response.body().bytes();
                String raw=new String(bytes,"UTF-8");
                System.out.println("[GetScoreThread]-->"+raw);
                GsonBuilder builder1=new GsonBuilder();
                builder1.setPrettyPrinting();
                builder1.serializeNulls();
                Gson gson1=builder1.create();
                //This parse the list of json to a list of  ScoreRetrievedJSONentity class with the help of type token
                //we cant specify directly to convert the json to ScoreRetrievedJSONentity because raw variable contains
                //a list of json
                List<ScoreRetrievedJSONentity> listofscores=gson1.fromJson(raw,new TypeToken<List<ScoreRetrievedJSONentity>>(){}.getType());
                //Since tableview accecpt observable list,we need to convert it to Observable list
                ObservableList<ScoreRetrievedJSONentity> tableList= FXCollections.observableList(listofscores);
                //This looped through the tableList instance and set the table column to its respective data
                ObservableList<Scores> scores= FXCollections.observableArrayList();
                for (ScoreRetrievedJSONentity s:tableList){
                    System.out.println("[GetScoreThread]--> processing data to table");
                    scores.add(new Scores(s.getSubject(),s.getFirstca(),
                            s.getSecondca(),s.getThirdca(),
                            s.getFourthca(),s.getFifthca(),
                            s.getSeventhca(),s.getSixthca(),
                            s.getEightca(),s.getNinthca(),
                            s.getTenthca(),s.getExam(),
                            s.getCumulative()));
                    tableView.setItems(scores);
                    response.close();
                }

            }else {
                System.out.println(response);
                response.close();
                System.out.println("[GetScoreThread]--> server return error "+response.code()+": Unable to get score");
                //Display alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("server return error "+response.code()+": Unable to  get data");
                    if (error){
                            System.out.println("[GetScoreThread]--> Connection Error,Window close");
                    }
                });
            }
        } catch (IOException e) {
            //Display an Alert dialog
            Platform.runLater(()->{
                boolean error=new ConnectionError().Connection("Unable to establish,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[SaveScoreThread]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }

    }
}


