package sample.LoginPage.DashBoard.SelectWindows.Parent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import okhttp3.*;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;
import sample.LoginPage.LogInModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class StudentSelectParentController implements Initializable {

    public ImageView FatherImage;
    public ImageView MotherImage;
    public Label fatherlabel;
    public Label motherlabel;
    public GridPane GridPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ParentInformationThread(SelectParent.classSelected, SelectParent.parentname, FatherImage, fatherlabel, MotherImage, motherlabel,GridPane).start();
    }

    //This class get all the parent information selected
    private static class ParentInformationThread extends Thread {
        private String classSelected;
        private String parentname;
        private ImageView FatherImage;
        private Label fatherlabel;
        private ImageView MotherImage;
        private Label motherlabel;
        private GridPane gridPane;
        private BufferedImage fatherBufferedImage;
        private BufferedImage motherBufferedImage;
        private BufferedImage AssociateChildBufferedImage;
        private String mothername;
        private String fathername;
        private int i=0;
        private ParentInformationResponseEntity parentInformationResponseEntity;
        private ParentInformationThread(String clas, String parent, ImageView Fatherphoto, Label fatherlabel, ImageView Motherphoto, Label motherlabel, GridPane gridPane) {
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
            this.gridPane=gridPane;

        }

        @Override
        public void run() {

            System.out.println("[ParentInfoThread]: " + classSelected);
            System.out.println("[ParentInfoThread]: " + parentname);
            System.out.println("[ParentInfoThread]: Thread Started");
            System.out.println("[ParentInfoThread]-->Processing request");

            System.out.println("[ParentInfoThread]: Setting Up client");
            OkHttpClient client=new OkHttpClient();
            System.out.println("[ParentInfoThread]: Making request");
            Request request=new Request.Builder()
                    .url("http://localhost:8080/getparentinformation/"+classSelected+"/"+parentname)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();

            try {
                Response response=client.newCall(request).execute();
                String raw=new String(response.body().bytes(),"UTF-8");
                System.out.println(raw);
                if (response.code()==200||response.code()==201||response.code()==212){
                    //Close the window
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                    });
                    System.out.println("[ParentInfoThread]: Converting json response to json Object");
                    GsonBuilder builder=new GsonBuilder();
                    builder.setPrettyPrinting();
                    builder.serializeNulls();
                    Gson gson=builder.create();
                    //Converting json to object
                    parentInformationResponseEntity=new ParentInformationResponseEntity();
                    parentInformationResponseEntity=gson.fromJson(raw,ParentInformationResponseEntity.class);
                    Platform.runLater(()->{
                        fatherlabel.setText(parentInformationResponseEntity.getFathername());
                        motherlabel.setText(parentInformationResponseEntity.getMothername());
                    });
                    //This lists get the list of childs pictures and list of their names
                    //these list have the same size
                    List<byte[]> childpictures=parentInformationResponseEntity.getAssociatechildpictures();
                    List<String> childnames=parentInformationResponseEntity.getAssociatechildnames();
                    Path path=Paths.get(System.getProperty("user.dir")+"/MyChildSchool");
                    if (Files.exists(path)){
                        System.out.println("[ParentInfoThread]: Processing father images");
                        File father=new File(System.getProperty("user.dir")+"/MyChildSchool/fatherimage");
                        File mother=new File(System.getProperty("user.dir")+"/MyChildSchool/motherimage");
                        FileOutputStream fout=new FileOutputStream(father);
                        FileOutputStream mout=new FileOutputStream(mother);
                        fout.write(parentInformationResponseEntity.getFatherpicture());
                        mout.write(parentInformationResponseEntity.getMotherpictures());
                        fatherBufferedImage=ImageIO.read(father);
                        motherBufferedImage=ImageIO.read(mother);
                        Image f=SwingFXUtils.toFXImage(fatherBufferedImage,null);
                        Image m=SwingFXUtils.toFXImage(motherBufferedImage,null);
                        //setting father and mother images
                        System.out.println("[ParentInfoThread]: Setting father image and mother image to image view");
                        FatherImage.setImage(f);
                        MotherImage.setImage(m);
                        System.out.println("[ParentInfoThread]: Processing childs data");
                        System.out.println("[ParentInfoThread]: Setting up gridpane layout");

                        //preparing gridpane children
                       Platform.runLater(()->{
                         for(int row=0;row<1;row++){
                             for (int column=0;column<childpictures.size();column++){
                                 ImageView imageView=new ImageView();
                                 imageView.setFitHeight(150);
                                 imageView.setFitWidth(150);
                                 imageView.setSmooth(true);
                                 Label label=new Label();
                                 label.setFont(Font.font("Algerian",FontWeight.MEDIUM,13));
                                 label.setStyle("-fx-text-fill:#499954");
                                 File child=new File(System.getProperty("user.dir")+"/MyChildSchool/child"+i);
                                 try {
                                     FileOutputStream cout = new FileOutputStream(child);
                                     cout.write(childpictures.get(column));
                                     BufferedImage bufferedImage=ImageIO.read(child);
                                     Image image=SwingFXUtils.toFXImage(bufferedImage,null);
                                     imageView.setImage(image);
                                     label.setText(childnames.get(column));
                                     label.isWrapText();
                                     gridPane.add(imageView,column,row);
                                     gridPane.setVgap(20);
                                     gridPane.add(label,column,row+2);
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                       });
                    }else {
                        Files.createDirectories(path);
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
                    response.close();
                }
                if (response.code()==404){
                    Platform.runLater(()->{
                        boolean error=new ConnectionError().Connection("Server:error"+response.code()+",Parent information not found");
                        if (error){
                            SelectParent.window1.close();
                            System.out.println("[SelectParent]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                //Display an Alert dialog
                Platform.runLater(()->{
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK YOUR INTERNET CONNECTION");
                    if (error){
                        SelectParent.window1.close();
                        System.out.println("[SelectParent]--> Connection Error,Window close");
                    }
                });
                e.printStackTrace();
            }
        }
    }


}
