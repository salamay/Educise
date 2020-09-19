package sample.LoginPage.DashBoard.SelectWindows.Information;

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

    public InformationWindow(String clas, String NewValue) {

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
        topVbox.setAlignment(Pos.CENTER);
        //Vbox content
        HBox hbox=new HBox();
        Label label=new Label("Student Information.");
        label.setFont(Font.font("Verdana", FontWeight.MEDIUM,25));
        label.setStyle("-fx-text-fill:#FFFFFF");
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
        borderPane.setTop(hbox);
        borderPane.setCenter(hBox2);
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

        infocontainer.getChildren().addAll(AgeLabel,PhoneNoLabel,FatherNameLabel,MotherNameLabel,AddressLabel,NextOfKinLabel,GenderLabel,ClubLabel,ROleModelLabel);
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
        infocontainer2.getChildren().addAll(ageLabel,phoneNoLabel,fatherNameLabel,motherNameLabel,addressLabel,
                nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel);
        bdp.setCenter(infocontainer2);
        borderPane.setBottom(bdp);
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setMaximized(true);
        window1.setResizable(true);
        window1.isResizable();
        window1.isMaximized();
        Scene scene=new Scene(borderPane,1200,700);
        window1.setScene(scene);
        window1.show();
        System.out.println("[InformationWindow]:"+NewValue);
        System.out.println("[InformationWindow]:"+clas);
        SelectInformationSesssionWindow.StudentWindow.close();
        //this get the student information,the clas and new value is the class and the student name respectively
        new ClassInformationThread(clas,NewValue,NameLabel,ageLabel,phoneNoLabel,fatherNameLabel,motherNameLabel,
                addressLabel,nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel,image,FatherImage,MotherImage,FatherName,MotherName).start();
    }

}
