package sample.StudentInformation;

import com.jfoenix.controls.JFXSpinner;
import com.sun.javaws.progress.Progress;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

//this class create Student information window
//And start to get information from the ClassNameThread class
public class InformationWindow {
   static Stage window1;

    public InformationWindow(String clas,String NewValue) {

        window1=new Stage();

        window1.setTitle("Student Information");
        BorderPane borderPane=new BorderPane();
        String borderstyle=" -fx-background-color: #130F22;";
        borderPane.setStyle(borderstyle);
        //top Vbox
        VBox topVbox=new VBox();
        topVbox.setMinWidth(Control.USE_COMPUTED_SIZE);
        topVbox.setMinHeight(Control.USE_COMPUTED_SIZE);
        topVbox.setPadding(new Insets(10,10,10,10));
        //Vbox content
        HBox hbox=new HBox();
        Label label=new Label("Student Information.");
        label.setFont(Font.font("Verdana", FontWeight.MEDIUM,25));
        hbox.getChildren().add(label);
        HBox hBox2=new HBox();
        hBox2.setPrefWidth(1180);
        hBox2.setPrefHeight(197);
        ImageView image=new ImageView();
        ImageView FatherImage=new ImageView();
        FatherImage.setFitHeight(217);
        FatherImage.setFitWidth(206);
        ImageView MotherImage=new ImageView();
        MotherImage.setFitHeight(217);
        MotherImage.setFitWidth(206);
        image.setFitHeight(217);
        image.setFitWidth(206);
        Label NameLabel=new Label("Name");
        NameLabel.setFont(Font.font("verdana",FontWeight.MEDIUM,30));
        NameLabel.setStyle("-fx-text-fill: #ee1010 ");
        hBox2.setMinHeight(Control.USE_COMPUTED_SIZE);
        hBox2.setMinWidth(Control.USE_COMPUTED_SIZE);
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10,10,10,10));
        //Label Styles
        String labelstyle="-fx-text-fill: #FFFFFF;";

        //Father Name And MotherName
        Label FatherName=new Label("Name");
        FatherName.setStyle(labelstyle);
        Label MotherName=new Label("Name");
        MotherName.setStyle(labelstyle);

        // layout
        hBox2.getChildren().addAll(image,NameLabel,FatherImage,FatherName,MotherImage,MotherName);
        hBox2.setMinWidth(Control.USE_COMPUTED_SIZE);
        hBox2.setMinHeight(Control.USE_COMPUTED_SIZE);
        String styles="-fx-background-image: url('image/schoolback.jpg');"+"-fx-background-size:70%;";
        hBox2.setStyle(styles);
        topVbox.getChildren().addAll(hbox,hBox2);

        ///center

        BorderPane bdp=new BorderPane();
        bdp.setMinHeight(Control.USE_COMPUTED_SIZE);
        bdp.setMinHeight(Control.USE_COMPUTED_SIZE);
        HBox info=new HBox();
        Label label1=new Label("Personal details");
        Label label2=new Label("Score Chart");
        label1.setFont(Font.font("Verdana",FontWeight.BOLD,24));
        label2.setFont(Font.font("Verdana",FontWeight.BOLD,24));
        info.setMinHeight(Control.USE_COMPUTED_SIZE);
        info.setMinWidth(Control.USE_COMPUTED_SIZE);
        info.getChildren().addAll(label1,label2);
        bdp.setTop(info);


        //vbox containing node

        VBox infocontainer=new VBox();
        String infoStyle="-fx-background-color:#1A162B;";
        infocontainer.setStyle(infoStyle);
        infocontainer.setSpacing(10);
        infocontainer.setPadding(new Insets(5,5,5,5));
        infocontainer.setMinWidth(Control.USE_COMPUTED_SIZE);
        infocontainer.setMinHeight(Control.USE_COMPUTED_SIZE);
        Label AgeLabel=new Label("Age: ");
        AgeLabel.setStyle(labelstyle);
        Label PhoneNoLabel=new Label("Phone No: ");
        PhoneNoLabel.setStyle(labelstyle);
        Label FatherNameLabel=new Label("Father Name: ");
        FatherNameLabel.setStyle(labelstyle);
        Label MotherNameLabel=new Label("Mother Name: ");
        MotherNameLabel.setStyle(labelstyle);
        Label AddressLabel=new Label("Address: ");
        AddressLabel.setStyle(labelstyle);
        Label NextOfKinLabel=new Label("Next Of Kin: ");
        NextOfKinLabel.setStyle(labelstyle);
        Label GenderLabel=new Label("Gender: ");
        GenderLabel.setStyle(labelstyle);
        Label ClubLabel=new Label("Club: ");
        ClubLabel.setStyle(labelstyle);
        Label ROleModelLabel=new Label("RoleModel: ");
        ROleModelLabel.setStyle(labelstyle);
        Label AcademicPerformance=new Label("Academic Performance: ");
        AcademicPerformance.setStyle(labelstyle);
        infocontainer.getChildren().addAll(AgeLabel,PhoneNoLabel,FatherNameLabel,MotherNameLabel,AddressLabel,
                AcademicPerformance,NextOfKinLabel,GenderLabel,ClubLabel,ROleModelLabel);
        bdp.setLeft(infocontainer);

        //Vbox containing node
        VBox infocontainer2=new VBox();
        infocontainer2.setStyle(infoStyle);
        infocontainer2.setMinHeight(Control.USE_COMPUTED_SIZE);
        infocontainer2.setMinWidth(Control.USE_COMPUTED_SIZE);
        infocontainer2.setSpacing(10);
        infocontainer2.setPadding(new Insets(5,5,5,5));

        Label ageLabel=new Label();
        ageLabel.setStyle(labelstyle);
        Label phoneNoLabel=new Label();
        phoneNoLabel.setStyle(labelstyle);
        Label fatherNameLabel=new Label();
        fatherNameLabel.setStyle(labelstyle);
        Label motherNameLabel=new Label();
        motherNameLabel.setStyle(labelstyle);
        Label addressLabel=new Label();
        addressLabel.setStyle(labelstyle);
        Label nextOfKinLabel=new Label();
        NextOfKinLabel.setStyle(labelstyle);
        Label genderLabel=new Label();
        genderLabel.setStyle(labelstyle);
        Label clubLabel=new Label();
        clubLabel.setStyle(labelstyle);
        Label rOleModelLabel=new Label();
        rOleModelLabel.setStyle(labelstyle);
        Label academicPerformance=new Label();
        academicPerformance.setStyle(labelstyle);
        infocontainer2.getChildren().addAll(ageLabel,phoneNoLabel,fatherNameLabel,motherNameLabel,addressLabel,
                nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel,academicPerformance);
        bdp.setCenter(infocontainer2);


        //GridPane containing JFX node
        GridPane gridPane=new GridPane();
        gridPane.setMinHeight(Control.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Control.USE_COMPUTED_SIZE);
        gridPane.setHgap(30);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.TOP_LEFT);
        ProgressIndicator MathSpinner=new ProgressIndicator();
        MathSpinner.isIndeterminate();
        ProgressIndicator EnglishSpinner=new ProgressIndicator();
        EnglishSpinner.isIndeterminate();
        ProgressIndicator BasicScienceSpinner=new ProgressIndicator();
        BasicScienceSpinner.isIndeterminate();
        ProgressIndicator BasicTechnologySpinner=new ProgressIndicator();
        BasicTechnologySpinner.isIndeterminate();
        ProgressIndicator RnvSpinner=new ProgressIndicator();
        RnvSpinner.isIndeterminate();
        ProgressIndicator AgricSpinner=new ProgressIndicator();
        AgricSpinner.isIndeterminate();
        Label math=new Label("Mathematics");
        Label english=new Label("English Studies");
        Label basicscience=new Label("Basic Science");
        Label Basictech=new Label("Basic Technology");
        Label rnv=new Label("RNV");
        Label agric=new Label("Agricultural Studies");
        GridPane.setConstraints(math,1,1);
        GridPane.setConstraints(english,2,1);
        GridPane.setConstraints(basicscience,3,1);
        GridPane.setConstraints(MathSpinner,1,2);
        GridPane.setConstraints(EnglishSpinner,2,2);
        GridPane.setConstraints(BasicScienceSpinner,3,2);
        GridPane.setConstraints(Basictech,1,3);
        GridPane.setConstraints(rnv,2,3);
        GridPane.setConstraints(agric,3,3);
        GridPane.setConstraints(BasicTechnologySpinner,1,4);
        GridPane.setConstraints(RnvSpinner,2,4);
        GridPane.setConstraints(AgricSpinner,3,4);
        gridPane.getChildren().addAll(math,english,basicscience,Basictech,rnv,agric,
                MathSpinner,EnglishSpinner,BasicScienceSpinner,BasicTechnologySpinner,RnvSpinner,AgricSpinner);
        bdp.setRight(gridPane);
        Region region1=new Region();
        Region region2=new Region();
        region1.setMinHeight(302);
        region1.setMinWidth(108);
        region2.setMinHeight(302);
        region2.setMinWidth(108);
        borderPane.setTop(topVbox);
        borderPane.setCenter(bdp);
        borderPane.setLeft(region1);
        borderPane.setRight(region2);

        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setMaximized(true);
        window1.setResizable(true);
        Scene scene=new Scene(borderPane,1200.,650);
        window1.setScene(scene);
        window1.show();
        System.out.println("[InformationWindow]: "+NewValue);
        System.out.println("[InformationWindow]: "+clas);

        //this get the student information,the clas and new value is the class and the student name respectively
        new ClassInformationThread(clas,NewValue,NameLabel,ageLabel,phoneNoLabel,fatherNameLabel,motherNameLabel,
                addressLabel,nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel,academicPerformance,image,FatherImage,MotherImage,FatherName,MotherName).start();

    }

}
