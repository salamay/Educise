package sample.LoginPage.DashBoard.SelectWindows.Parent;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sample.ConnectionError;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//This class displays a window to select session and make the list view after a session has been clicked and also display
// the selected student parent information in a window,this happen on two window by setting the window scene to different scene
public class SelectParent {

    public static Stage window1;
    public JFXComboBox<String> Clas;
    public static String classSelected;
    private JFXComboBox<String> ParentSearch;
    private ProgressIndicator progressBar;
    public static String parentname;

    public SelectParent() throws IOException {

        /////////////////////THIS DISPLAYS THE WINDOW////////////////////////////
        window1=new Stage();
        VBox classvvox=new VBox();
        String Style="-fx-background-image: url('image/avel-chuklanov-5iseEuoW7mw-unsplash.jpg');-fx-background-size:100%, 100%;";
        classvvox.setStyle(Style);
        classvvox.setMinHeight(600);
        classvvox.setMinWidth(1000);
        classvvox.setAlignment(Pos.CENTER);
        VBox CenterBox=new VBox();
        CenterBox.setAlignment(Pos.CENTER);
        CenterBox.setSpacing(10);
        CenterBox.setPadding(new Insets(10,10,10,10));
        classvvox.getChildren().add(CenterBox);
         Clas=new JFXComboBox<>();
         Clas.setMinWidth(100);
         progressBar=new ProgressIndicator();
        progressBar.isIndeterminate();
        CenterBox.getChildren().addAll(progressBar,Clas);

        Scene scene=new Scene(classvvox);
        window1.setScene(scene);
        window1.setMaximized(true);
        window1.setResizable(true);
        window1.setMinWidth(1200);
        window1.setMinHeight(700);
        window1.centerOnScreen();
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.show();
///////////////////////////////////Displays the window end///////////////////////////

/////////////////////This get all the session from the database and add to a combobox//////////////
        new ClassThread(Clas,progressBar).start();
 //////////////////////////////////////////////////////////////////////////////////////////////
        Clas.setOnAction(event -> {
            classSelected = Clas.getSelectionModel().getSelectedItem();
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setMinHeight(700);
            vBox.setMinWidth(1000);
            ParentSearch = new JFXComboBox<>();
            ParentSearch.setEditable(true);
            ParentSearch.setMinWidth(Control.USE_COMPUTED_SIZE);
            HBox hBox = new HBox();
            Label Label = new Label("Search parent name");
            hBox.getChildren().addAll(Label, ParentSearch);
            ListView<String> Parentlist = new ListView<>();

/////////////////  parent name list view On Mouse Clicked//////////////
            //the newValue variable is a static global variable that will be later reference in the studentParentController class
            //to get the value of the class selected
            Parentlist.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                parentname=newValue;
                Parent root= null;
                try {
                    root = FXMLLoader.load(getClass().getResource("studentparent.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene parentscene = new Scene(root);
                SelectParent.window1.setScene(parentscene);

            });
   ////////////////////////////////////////   parent name list view On Mouse Clicked end//////////////////////////////////

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);
            scrollPane.setFitToHeight(true);
            vBox.getChildren().addAll(hBox, Parentlist);
            scrollPane.setContent(vBox);
            Scene scene2 = new Scene(scrollPane);
            SelectParent.window1.setScene(scene2);
            //passing the session selected as an argument to the ParentList Class
            //this enable the database to know which session to fetch parent name
            new ParentListhread(classSelected, Parentlist).start();
            ParentSearch.setOnAction(event2 -> System.out.println("Search"));
        });

    }
    //////////This class get the information sessions and set the value gotten to the Combobox passed in from the parent class
    //the progressbar indicate the progress
    public static class ClassThread extends Thread {
        public JFXComboBox<String> clas;
        //this list here is static,its value will be needed later in thr StudentAssessmentSessionController class
        public static List<String> list;
        ProgressIndicator progressBar;
        double progress=0.0;


        public ClassThread(JFXComboBox<String> comb,ProgressIndicator PgBar) {
            this.clas = comb;
            this.progressBar=PgBar;

        }

        public void run() {
            System.out.println("[Retrieving information session]: setting up okhttp client");
            OkHttpClient client=new OkHttpClient();

            System.out.println("[Retrieving information session]: setting up okhttp client request");
            Request request=new Request.Builder()
                    .url("http://localhost:8080/retrieveinformationsession")
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA5MDU2LCJleHAiOjE1OTg2ODkwNTZ9.rJnYfPefYX8tapQkTmyKuv15tprmJEyYl6FHvB21AZg")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println("[Retrieving information session]: "+response);
                if (response.code()==200|| response.code()==212){

                    System.out.println("[Retrieving information session]: session retrieved");
                    ResponseBody body=response.body();
                    try {
                        byte [] bytes=body.bytes();
                        //removing bracket from response
                        String data=new String(bytes,"UTF-8");
                        String data2=data.replace(']',' ');
                        String data3=data2.replace('[',' ');
                        String data4=data3.replaceAll(" ","");
                        list= Arrays.stream(data4.split(",")).collect(Collectors.toList());
                        Platform.runLater(()->{
                            clas.getItems().addAll(list);
                        });
                        System.out.println(data);
                        response.close();
                    } catch (IOException e) {
                        response.close();
                        e.printStackTrace();
                    }

                }else {
                    //Display an Alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("Server:error"+response.code()+",Unable to retrieve session");
                        if (error){
                            SelectParent.window1.close();
                            System.out.println("[SelectClassThread]--> Connection Error,Window close");
                        }
                    });
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        SelectParent.window1.close();
                        System.out.println("[SelectClassThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[Retrieving score session]: Unable to get session information from server");
                e.printStackTrace();
            }

        }

    }

    //this class get all the parent from the session selected
    //it fetch the names of parent and add it to the list
    private static class ParentListhread extends Thread {
        private String classSelected;
        private ListView<String> parentListView;

        private ParentListhread(String ClassSelected, ListView<String> parentlist) {
            this.classSelected = ClassSelected;
            this.parentListView = parentlist;
        }

        @Override
        public void run() {
            System.out.println("[SelectParent]: Retrieving name");
            OkHttpClient client=new OkHttpClient();

            System.out.println("[SelectParent]: setting up okhttp client request");
            Request request=new Request.Builder()
                    .url("http://localhost:8080/retrieveparent/"+classSelected)
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA5MDU2LCJleHAiOjE1OTg2ODkwNTZ9.rJnYfPefYX8tapQkTmyKuv15tprmJEyYl6FHvB21AZg")
                    .build();

            try {
                List<String> rawlist;
                ObservableList<String> list=FXCollections.observableArrayList();
                Response response = client.newCall(request).execute();
                System.out.println("[SelectParent]: "+response);
                if (response.code()==200|| response.code()==212||response.code()==201){

                    System.out.println("[SelectParent]: session retrieved");
                    ResponseBody body=response.body();
                    try {
                        byte [] bytes=body.bytes();
                        //removing bracket from response
                        String data=new String(bytes,"UTF-8");
                        String data2=data.replace(']',' ');
                        String data3=data2.replace('[',' ');
                        String data4=data3.replaceAll(" ","");
                        rawlist= Arrays.stream(data4.split(",")).collect(Collectors.toList());
                        ObservableList<String> names=FXCollections.observableArrayList(rawlist);
                        Platform.runLater(()->{
                            parentListView.getItems().addAll(names);
                        });
                        System.out.println(data);
                        response.close();
                    } catch (IOException e) {
                        response.close();
                        e.printStackTrace();
                    }

                }else {
                    //Display an Alert dialog
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("Server:error"+response.code()+",Unable to retrieve session");
                        if (error){
                            SelectParent.window1.close();
                            System.out.println("[SelectParent]--> Connection Error,Window close");
                        }
                    });
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        SelectParent.window1.close();
                        System.out.println("[SelectParent]--> Connection Error,Window close");
                    }
                });
                System.out.println("[SelectParent]: Unable to get Parent names from server");
                e.printStackTrace();
            }

        }
    }
    ///////////////////////////////////////////get Session End /////////////////////////////////////////////


}
