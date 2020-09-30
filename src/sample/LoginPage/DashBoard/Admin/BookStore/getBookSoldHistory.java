package sample.LoginPage.DashBoard.Admin.BookStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import java.io.IOException;
import java.util.List;

public class getBookSoldHistory extends Thread{
    private String session;
    private String term;
    private String date;
    private TableView<BookHistory> historytableview;
    private Label totalamount;
    public getBookSoldHistory(String session, String term, TableView<BookHistory> historytableview, String date, Label totalamount){
        this.session=session;
        this.term=term;
        this.date=date;
        this.historytableview=historytableview;
        this.totalamount=totalamount;
    }

    @Override
    public void run() {
        System.out.println("[getBookSoldHistory]:--> getting histories");
        System.out.println("[getBookSoldHistory]:--> Setting up client");
        OkHttpClient client=new OkHttpClient();


        Request request=new Request.Builder()
                .url("http://localhost:8080/getbookhistory/"+session+"/"+term+"/"+date)
                .addHeader("Authorization","Bearer "+ LogInModel.token)
                .build();
        try {
            Response response=client.newCall(request).execute();
            System.out.println("[getBookSoldHistory]: Retrieving response ");
            System.out.println("[getBookSoldHistory]:"+response);
            System.out.println("[getBookSoldHistory]:"+response.body());
            if (response.code()==200||response.code()==201||response.code()==212||response.code()==202){
                byte [] rawbytes = response.body().bytes();
                String rawBody=new String(rawbytes,"UTF-8");
                System.out.println("[getBookSoldHistory]: "+rawBody);
                System.out.println("[getBookSoldHistory]: Processing response Body");
                GsonBuilder builder=new GsonBuilder();
                builder.setPrettyPrinting();
                builder.serializeNulls();
                Gson gson=builder.create();
                //This parse the list of json to a list of booksoldhistory class with the help of type token
                //we cant specify directly to convert the json to booksoldhistory because rawBody variable contains
                //a list of json
                List<BookHistory> books=gson.fromJson(rawBody,new TypeToken<List<BookHistory>>(){}.getType());
                //Since table view accept observable list,we need to convert it to Observable list
                ObservableList<BookHistory> tableList= FXCollections.observableList(books);
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    historytableview.setItems(tableList);
                    int total=0;
                    for (int i=0;i<tableList.size();i++){
                        total+=Integer.parseInt(tableList.get(i).getAmountsold());
                    }
                    totalamount.setText(String.valueOf(total));
                    if (!books.isEmpty()){
                        //getting document and setting
                        System.out.println("[getBookSoldHistory]: Document: "+books.get(books.size()-1).getPdfdocumentbytes());
                        BookStoreWindowController.pdfdocumentbytes=books.get(books.size()-1).getPdfdocumentbytes();
                    }
                });
                response.close();
            }else {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to get all books");
                    if (error){
                        System.out.println("[getBookSoldHistory]--> Connection Error");
                    }
                });
                response.close();
            }
            if (response.code()==204){
              Platform.runLater(()->{
                  LoadingWindow.window.close();
                  boolean error=new ConnectionError().Connection("No history found");
                  if (error){
                      System.out.println("[getBookSoldHistory]--> Connection Error");
                  }
              });
                response.close();
            }
        } catch (IOException e) {
            Platform.runLater(()->{
                LoadingWindow.window.close();
                boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                if (error){
                    System.out.println("[getBookSoldHistory]--> Connection Error,Window close");
                }
            });
            e.printStackTrace();
        }
    }
}
