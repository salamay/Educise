package sample.SelectWindows.Parent;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnectionError;
import sample.SqlConnection;
import ui.RingProgressIndicator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

//This class displays a window to select session and make the list view after a session has been clicked and also display
// the selected student parent information in a window,this happen on two window by setting the window scene to different seen
public class SelectParent {

    Stage window1;
    public ComboBox<String> Clas;
    private String classSelected;
    ComboBox<String> ParentSearch;
    ProgressIndicator progressBar;

    public SelectParent() throws IOException {

        /////////////////////THIS DISPLAYS THE WINDOW////////////////////////////
        window1=new Stage();
        Stage window=new Stage();
        window.setTitle("Select Academic Session");
        VBox classvvox=new VBox();
        String Style="-fx-background-image: url('image/avel-chuklanov-5iseEuoW7mw-unsplash.jpg');-fx-background-size:100%, 100%;";
        classvvox.setStyle(Style);
        classvvox.setMinHeight(600);
        classvvox.setMinWidth(600);
        classvvox.setAlignment(Pos.CENTER);
        VBox CenterBox=new VBox();
        CenterBox.setAlignment(Pos.CENTER);
        CenterBox.setSpacing(10);
        CenterBox.setPadding(new Insets(10,10,10,10));
        classvvox.getChildren().add(CenterBox);
         Clas=new ComboBox<>();
         Clas.setMinWidth(100);
         progressBar=new ProgressIndicator();
        progressBar.isIndeterminate();
        CenterBox.getChildren().addAll(progressBar,Clas);

        window1.setTitle("Hello World");
        Scene scene=new Scene(classvvox,700,700);
        window1.setScene(scene);
        window1.setMaximized(true);
        window1.setResizable(true);
        window1.show();
//////////////////////////////////////////////////////////////

/////////////////////This get all the session from the database and add to a combobox//////////////
        new ClassThread(Clas,window1).start();
 //////////////////////////////////////////////////////////////////////////////////////////////
        Clas.setOnAction(event -> {
            classSelected = Clas.getSelectionModel().getSelectedItem();
            Stage window2 = new Stage();
            window2.setTitle("Select Parent");
            window2.setMaximized(true);
            window2.initModality(Modality.APPLICATION_MODAL);
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setMinHeight(1200);
            vBox.setMinWidth(800);
            ParentSearch = new ComboBox<>();
            ParentSearch.setEditable(true);

            ParentSearch.setMinWidth(Control.USE_COMPUTED_SIZE);

            HBox hBox = new HBox();
            Label Label = new Label("Search parent name");
            hBox.getChildren().addAll(Label, ParentSearch);
            ListView<String> Parentlist = new ListView<>();

//            list view On Mouse Clicked
            Parentlist.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {

//             Information Window
                BorderPane borderPane = new BorderPane();
                borderPane.setStyle("-fx-background-color:#004487;");
                borderPane.setPadding(new Insets(10, 10, 10, 10));
                VBox TopVbox = new VBox();
                TopVbox.setMinWidth(Control.USE_COMPUTED_SIZE);
                TopVbox.setMinHeight(150);
                TopVbox.setSpacing(10);
                TopVbox.setPadding(new Insets(5, 5, 5, 5));
                TopVbox.setStyle("-fx-background-color:#0066CB;");
                HBox topParentImageContainer = new HBox();
                String Style2 = "-fx-background-color:#F0F0F0;";
                Label TopLabel = new Label("Parent Information");
                TopLabel.setStyle(Style2);
                TopLabel.setFont(Font.font("verdana", FontWeight.MEDIUM, 28));

//             father Imageview and name
                ImageView FatherImage = new ImageView();
                FatherImage.setFitWidth(90.0);
                FatherImage.setFitHeight(90.0);
                Label fatherlabel = new Label("Name");
                fatherlabel.setStyle(Style);

//             mother ImageView and name

                ImageView MotherImage = new ImageView();
                MotherImage.setFitWidth(90.0);
                MotherImage.setFitHeight(90.0);
                Label motherlabel = new Label("Name");
                motherlabel.setStyle(Style);
                topParentImageContainer.getChildren().addAll(FatherImage, fatherlabel, MotherImage, motherlabel);
                TopVbox.getChildren().addAll(TopLabel, topParentImageContainer);

//             setting borderpane top
                borderPane.setTop(TopVbox);

//             Bottom Properties
//             Bottom node
                VBox BottomVbox = new VBox();
                BottomVbox.setSpacing(10);
                BottomVbox.setPadding(new Insets(5, 5, 5, 5));
                BottomVbox.setStyle("-fx-background-color:#0066CB;");
                BottomVbox.setMinWidth(Control.USE_COMPUTED_SIZE);
                BottomVbox.setMinHeight(500);
                HBox associateChildrenContainer = new HBox();
                associateChildrenContainer.setSpacing(10);

//             Associate Children properties
                Label BottomLabel = new Label("Associate Children:");
                BottomLabel.setStyle(Style);
                BottomLabel.setFont(Font.font("verdana", FontWeight.MEDIUM, 18));
                ImageView Children1 = new ImageView();
                Children1.setFitWidth(90.0);
                Children1.setFitHeight(90.0);
                Label ChildrenLabel1 = new Label("Name");
                ChildrenLabel1.setStyle(Style);

//             Associate children properties
                ImageView Children2 = new ImageView();
                Children2.setFitWidth(90.0);
                Children2.setFitHeight(90.0);
                Label ChildrenLabel2 = new Label("Name");
                ChildrenLabel2.setStyle(Style);
                associateChildrenContainer.getChildren().addAll(Children1, ChildrenLabel1, Children2, ChildrenLabel2);
                BottomVbox.getChildren().addAll(BottomLabel, associateChildrenContainer);

//             Setting Borderpane Bottom layout
                borderPane.setBottom(BottomVbox);

//             left Region
                Region leftregion = new Region();
                leftregion.setMinWidth(200);
                leftregion.setMinHeight(300);

//             Bottom Region
                Region Rightregion = new Region();
                Rightregion.setMinWidth(200);
                Rightregion.setMinHeight(300);

//             setting Borderpane left and right properties
                borderPane.setLeft(leftregion);
                borderPane.setRight(Rightregion);

//                Setting Center of Borderpane
                RingProgressIndicator rpi=new RingProgressIndicator();
                rpi.setRingWidth(200);
                rpi.makeIndeterminate();
                borderPane.setCenter(rpi);
                Scene parentscene = new Scene(borderPane);
                window1.setScene(parentscene);
                new ParentInformationThread(classSelected, newValue, FatherImage, fatherlabel, MotherImage, motherlabel, Children1, ChildrenLabel1,
                        Children2, ChildrenLabel2,rpi,window1).start();


            });
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setMinHeight(800);
            scrollPane.setFitToWidth(true);
            scrollPane.setPannable(true);
            Parentlist.setMinHeight(Control.USE_COMPUTED_SIZE);


            vBox.getChildren().addAll(hBox, Parentlist);
            scrollPane.setContent(vBox);
            Scene scene2 = new Scene(scrollPane);
            window1.setScene(scene2);
            window1.show();
            new ParentListhread(classSelected, Parentlist,window1).start();
            ParentSearch.setOnAction(event2 -> System.out.println("Search"));


        });

    }
    //This class get the Sessions
    private class ClassThread extends Thread {
        private ComboBox<String> clas;
        Connection conn;
        private Stage window;

        public ClassThread(ComboBox<String> comb,Stage window) {
            this.clas = comb;
            this.window=window;


        }

        @Override
        public void run() {
            conn = SqlConnection.connector();
            if (conn != null) {
                ResultSet resultSet;
                String QUERY = "Select * from SessionTable";
                try {
                    PreparedStatement prs = conn.prepareStatement(QUERY);
                        resultSet = prs.executeQuery();
                    if (conn.isClosed()){
                        new ConnectionError().Connection(conn);
                    }
                    while (resultSet.next()) {
                        ObservableList<String> list = FXCollections.observableArrayList();

                        list.addAll(resultSet.getString("sessionname"));
                        Platform.runLater(() -> Clas.getItems().addAll(list));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                new ConnectionError().Connection(conn);

            }


        }

    }

    //this class get all the parent from the session selected
    private static class ParentListhread extends Thread {
        private String classSelected;
        private ListView<String> parentListView;
        private Stage window;

        private ParentListhread(String ClassSelected, ListView<String> parentlist,Stage window) {
            this.classSelected = ClassSelected;
            this.parentListView = parentlist;
            this.window=window;
        }

        @Override
        public void run() {
            Connection conn = SqlConnection.connector();
            if (conn != null) {
                String Query = "Select Fathername,Mothername from " + classSelected;
                ResultSet resultSet;
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement(Query);
                    resultSet = preparedStatement.executeQuery();
                    System.out.println("[parentListThread]: Query executed");
                    while (resultSet.next()) {
                        System.out.println("[parentListThread]: getting Result.....");
                        ObservableList<String> list = FXCollections.observableArrayList();
                        list.addAll(resultSet.getString("Fathername"));
                        list.addAll(resultSet.getString("Mothername"));
                        Platform.runLater(() -> parentListView.getItems().addAll(list));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                new ConnectionError().Connection(conn);
            }

        }
    }
    //This class get all the parent information selected
    private static class ParentInformationThread extends Thread {
        private String classSelected;
        private String parentname;
        private ImageView FatherImage;
        private Label fatherlabel;
        private ImageView MotherImage;
        private Label motherlabel;
        private ImageView ChildrenPhoto1;
        private Label ChildrenLabel1;
        private ImageView ChildrenPhoto2;
        private Label ChildrenLabel2;
        BufferedImage fatherBufferedImage;
        BufferedImage motherBufferedImage;
        BufferedImage AssociateChildBufferedImage;
        ObservableList<String> associatechild1=FXCollections.observableArrayList();
        String mothername;
        String fathername;
        int i=0;
        int p=0;
        RingProgressIndicator rpi;
        private Stage window;



        private ParentInformationThread(String clas, String parent, ImageView Fatherphoto, Label fatherlabel, ImageView Motherphoto, Label motherlabel, ImageView ChildrenPhoto1, Label ChildrenLabel1,
                                        ImageView ChildrenPhoto2, Label ChildrenLabel2,RingProgressIndicator ringProgressIndicator,Stage PassedWindow) {
            this.classSelected = clas;
            this.parentname = parent;
            this.FatherImage = Fatherphoto;
            this.fatherlabel = fatherlabel;
            this.MotherImage = Motherphoto;
            this.motherlabel = motherlabel;
            this.ChildrenPhoto1 = ChildrenPhoto1;
            this.ChildrenLabel1 = ChildrenLabel1;
            this.ChildrenPhoto2 = ChildrenPhoto2;
            this.ChildrenLabel2 = ChildrenLabel2;
            this.rpi=ringProgressIndicator;
            this.window=PassedWindow;

        }

        @Override
        public void run() {
            System.out.println("[ParentInfoThread]: " + classSelected);
            System.out.println("[ParentInfoThread]: " + parentname);
            System.out.println("[ParentInfoThread]: Thread Started");
                Connection conn = SqlConnection.connector();


//            Checking Conection
            if (conn != null) {
                String Query = "Select Studentname, Mothername,Fathername,Parentpicture,MotherPicture,picture from " + classSelected + " where Fathername =? or Mothername=?";
                ResultSet resultSet;

                try {
                    PreparedStatement preparedStatement = conn.prepareStatement(Query);
                    preparedStatement.setString(1, parentname);
                    preparedStatement.setString(2, parentname);

                    resultSet = preparedStatement.executeQuery();

                    System.out.println("[ParentInfoThread]: Query Executed");
                    while (resultSet.next()) {
                        fathername = resultSet.getString("Fathername");
                        mothername = resultSet.getString("Mothername");
                        associatechild1.add(resultSet.getString("StudentName"));
                        Blob fatherpicture = resultSet.getBlob("Parentpicture");
                        Blob motherpicture = resultSet.getBlob("Motherpicture");
                        Blob associateChild = resultSet.getBlob("Picture");

                        Path path = Paths.get("C:\\users/Dell/AppData/Local/VXSchool/images/");
                        System.out.println("[ParentInformationThread]:Path Initiated");

//                        Father picture stream

                        if (Files.exists(path)) {
                            System.out.println("[ParentInformationThread]:path Exist");
                            if (fatherpicture!=null){
                                byte[] b;
                                b = fatherpicture.getBytes(1, (int) fatherpicture.length());
                                File fatherpicturefile = new File("C:\\users/Dell/AppData/Local/VXSchool/images/Fatherpicture.png");
                                FileOutputStream ffos = new FileOutputStream(fatherpicturefile);
                                ffos.write(b);
                                fatherBufferedImage = ImageIO.read(fatherpicturefile);
                                System.out.println("[ParentInformationThread]:Father File Gotten");
                            }

                            //Mother picture stream
                            if (motherpicture!=null){
                                byte[] motherbyte;
                                motherbyte = motherpicture.getBytes(1, (int) motherpicture.length());

                                File Motherpicturefile = new File("C:\\users/Dell/AppData/Local/VXSchool/images/motherpicture.png");
                                FileOutputStream mfos = new FileOutputStream(Motherpicturefile);
                                mfos.write(motherbyte);
                                motherBufferedImage = ImageIO.read(Motherpicturefile);
                                System.out.println("[ParentInformationThread]:Mother file Gotten");
                            }


                            if (associateChild!=null){
//                                Associate picture stream
                                byte[] associatechildbytes;
                                associatechildbytes = associateChild.getBytes(1, (int) associateChild.length());

                                File Associatechildpicture = new File("C:\\users/Dell/AppData/Local/VXSchool/images/Associatechild.png");
                                FileOutputStream afos = new FileOutputStream(Associatechildpicture);
                                afos.write(associatechildbytes);
                                AssociateChildBufferedImage = ImageIO.read(Associatechildpicture);
                                System.out.println("[ParentInformationThread]:Child file gotten");
                            }

                        } else {
                            System.out.println("[ParentInformationThread]:path Not Exist");
                            Files.createDirectories(path);
                            System.out.println("[ParentInformationThread]:Path Created");
                        }

                        while (true) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(()->{
                                rpi.setProgress(p);
                            });
                            p+=1;
                            if (p > 100) {
                                break;
                            }
                        }
                        conn.close();
                        Platform.runLater(() -> {



                            System.out.println("[ParentInfoThread]: RunLater method called");
                            if (fatherpicture!=null && fathername!=null){
                                Image fI = SwingFXUtils.toFXImage(fatherBufferedImage, null);
                                FatherImage.setImage(fI);
                                fatherlabel.setText(fathername);
                            }



                            if (motherpicture!=null && mothername!=null){
                                Image MI = SwingFXUtils.toFXImage(motherBufferedImage, null);
                                MotherImage.setImage(MI);
                                motherlabel.setText(mothername);
                            }

                            if (associateChild!=null && associatechild1!=null){
                                Image AI = SwingFXUtils.toFXImage(AssociateChildBufferedImage, null);
                                ChildrenPhoto1.setImage(AI);
                                ChildrenLabel1.setText(String.valueOf(associatechild1.get(i)));
                            }


                            i+=1;
                        });

                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }

            } else {
                Platform.runLater(()->{
                    new ConnectionError().Connection(conn);
                });
            }
        }
    }

}
