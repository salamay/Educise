package sample.SelectWindows.Parent;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.ConnectionError;
import sample.SqlConnection;
import sample.StudentRegistration.LoadingWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentParentController implements Initializable {

    public ImageView FatherImage;
    public ImageView MotherImage;
    public ImageView Children1;
    public ImageView Children2;
    public Label fatherlabel;
    public Label motherlabel;
    public Label ChildrenLabel1;
    public Label ChildrenLabel2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ParentInformationThread(SelectParent.classSelected, SelectParent.parentname, FatherImage, fatherlabel, MotherImage, motherlabel, Children1, ChildrenLabel1,
                Children2, ChildrenLabel2).start();
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
        private BufferedImage fatherBufferedImage;
        private BufferedImage motherBufferedImage;
        private BufferedImage AssociateChildBufferedImage;
        private ObservableList<String> associatechild1= FXCollections.observableArrayList();
        private String mothername;
        private String fathername;
        private int i=0;

        private ParentInformationThread(String clas, String parent, ImageView Fatherphoto, Label fatherlabel, ImageView Motherphoto, Label motherlabel, ImageView ChildrenPhoto1, Label ChildrenLabel1,
                                        ImageView ChildrenPhoto2, Label ChildrenLabel2) {
            /////Display loading window//////////
            try {
                new LoadingWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ////////////////////////////////////
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
                    //if resultset is not equals to null then close the loading window
                    if (resultSet!=null){
                       Platform.runLater(()-> LoadingWindow.window.close());
                    }
                    ///////////////////////////////////////////////
                    System.out.println("[ParentInfoThread]: Query Executed");
                    while (resultSet.next()) {
                        fathername = resultSet.getString("Fathername");
                        mothername = resultSet.getString("Mothername");
                        associatechild1.add(resultSet.getString("StudentName"));
                        Blob fatherpicture = resultSet.getBlob("Parentpicture");
                        Blob motherpicture = resultSet.getBlob("Motherpicture");
                        Blob associateChild = resultSet.getBlob("Picture");

                        //Path to save image
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

                        //////////Update the UI//////////////////////////
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

                        });
                        ///////////////////////Update the UI end////////////////////////

                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            } else {
                Platform.runLater(()->{
                    new ConnectionError().Connection(conn);
                });
            }
        }
    }


}
