package sample.LoginPage.DashBoard.SelectWindows.Information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

//this class create Student information window
//And start to get information from the ClassInformation thread class
public class InformationWindow {
   static Stage window1;

    public InformationWindow( String studentid) {

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
        //Student image
        ImageView image=new ImageView();
        image.setFitHeight(190);
        image.setFitWidth(190);
        //Father image
        ImageView FatherImage=new ImageView();
        FatherImage.setFitHeight(190);
        FatherImage.setFitWidth(190);
        //Mother image
        ImageView MotherImage=new ImageView();
        MotherImage.setFitHeight(190);
        MotherImage.setFitWidth(190);
        ImageView otherImage=new ImageView();
        otherImage.setFitHeight(190);
        otherImage.setFitWidth(190);

        Label NameLabel=new Label("Name");
        NameLabel.isWrapText();
        NameLabel.setFont(Font.font("verdana",FontWeight.MEDIUM,17));
        NameLabel.setStyle("-fx-text-fill: #ee1010;");

        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10,10,10,10));
        //Label Styles
        String labelstyle="-fx-text-fill: #FFFFFF;";

        //Father Name And MotherName and other name
        Label FatherName=new Label("Name");
        FatherName.setStyle("-fx-text-fill: #ee1010;");
        FatherName.setFont(Font.font("verdana",FontWeight.MEDIUM,17));
        FatherName.isWrapText();
        Label MotherName=new Label("Name");
        MotherName.setStyle("-fx-text-fill: #ee1010;");
        MotherName.setFont(Font.font("verdana",FontWeight.MEDIUM,17));
        MotherName.isWrapText();
        Label otherName=new Label("Name");
        otherName.setStyle("-fx-text-fill: #ee1010;");
        otherName.setFont(Font.font("verdana",FontWeight.MEDIUM,17));
        otherName.isWrapText();
        // layout
        hBox2.getChildren().addAll(image,NameLabel,FatherImage,FatherName,MotherImage,MotherName,otherImage,otherName);
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
        label1.setFont(Font.font("Verdana",FontWeight.BOLD,24));
        info.setMinHeight(Control.USE_COMPUTED_SIZE);
        info.setMinWidth(Control.USE_COMPUTED_SIZE);
        info.getChildren().addAll(label1);
        bdp.setTop(info);

        //vbox containing node

        VBox infocontainer=new VBox();
        String infoStyle="-fx-background-color:#1A162B;";
        infocontainer.setStyle(infoStyle);
        infocontainer.setSpacing(5);
        infocontainer.setPadding(new Insets(5,5,5,5));
        infocontainer.setMinWidth(Control.USE_COMPUTED_SIZE);
        infocontainer.setMinHeight(Control.USE_COMPUTED_SIZE);
        Label AgeLabel=new Label("Age: ");
        AgeLabel.setStyle(labelstyle);
        Label PhoneNoLabel=new Label("Phone No: ");
        PhoneNoLabel.setStyle(labelstyle);
        Label parentPhoneNumber=new Label("parent phone No: ");
        parentPhoneNumber.setStyle(labelstyle);
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
        Label Class=new Label("Class: ");
        Class.setStyle(labelstyle);
        Label Session=new Label("Session: ");
        Session.setStyle(labelstyle);
        Label Tag=new Label("Type: ");
        Tag.setStyle(labelstyle);
        Label ambition=new Label("Ambition: ");
        ambition.setStyle(labelstyle);
        infocontainer.getChildren().addAll(AgeLabel,PhoneNoLabel,parentPhoneNumber,FatherNameLabel,MotherNameLabel,AddressLabel,NextOfKinLabel,GenderLabel,ClubLabel,ROleModelLabel,Class,Session,Tag,ambition);
        bdp.setLeft(infocontainer);

        //Vbox containing node
        VBox infocontainer2=new VBox();
        infocontainer2.setStyle(infoStyle);
        infocontainer2.setMinHeight(Control.USE_COMPUTED_SIZE);
        infocontainer2.setMinWidth(Control.USE_COMPUTED_SIZE);
        infocontainer2.setSpacing(5);
        infocontainer2.setPadding(new Insets(5,5,5,5));

        Label ageLabel=new Label();
        ageLabel.setStyle(labelstyle);
        ageLabel.setWrapText(true);
        Label phoneNoLabel=new Label();
        phoneNoLabel.setStyle(labelstyle);
        Label parentPhoneNumberLabel=new Label();
        parentPhoneNumberLabel.setStyle(labelstyle);
        PhoneNoLabel.setWrapText(true);
        Label fatherNameLabel=new Label();
        fatherNameLabel.setStyle(labelstyle);
        fatherNameLabel.setWrapText(true);
        Label motherNameLabel=new Label();
        motherNameLabel.setStyle(labelstyle);
        motherNameLabel.setWrapText(true);
        Label addressLabel=new Label();
        addressLabel.setStyle(labelstyle);
        addressLabel.setWrapText(true);
        Label nextOfKinLabel=new Label();
        nextOfKinLabel.setStyle(labelstyle);
        nextOfKinLabel.setWrapText(true);
        Label genderLabel=new Label();
        genderLabel.setStyle(labelstyle);
        genderLabel.setWrapText(true);
        Label clubLabel=new Label();
        clubLabel.setStyle(labelstyle);
        clubLabel.setWrapText(true);
        Label rOleModelLabel=new Label();
        rOleModelLabel.setStyle(labelstyle);
        rOleModelLabel.setWrapText(true);
        Label Class2=new Label();
        Class2.setStyle(labelstyle);
        Class2.setWrapText(true);
        Label Session2=new Label();
        Session2.setStyle(labelstyle);
        Session2.setWrapText(true);
        Label Tag2=new Label();
        Tag2.setStyle(labelstyle);
        Tag2.setWrapText(true);
        Label ambition2=new Label();
        ambition2.setStyle(labelstyle);
        ambition2.setWrapText(true);
        infocontainer2.getChildren().addAll(ageLabel,phoneNoLabel,parentPhoneNumberLabel,fatherNameLabel,motherNameLabel,addressLabel,
                nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel,Class2,Session2,Tag2,ambition2);
        /// QR container
        VBox qrvbox=new VBox();
        ImageView qrImage=new ImageView();
        qrImage.setFitHeight(250);
        qrImage.setFitWidth(250);
        qrvbox.getChildren().add(qrImage);
        bdp.setRight(qrvbox);
        bdp.setCenter(infocontainer2);
        borderPane.setBottom(bdp);
        borderPane.setPadding(new Insets(10,10,10,10));
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setMaximized(true);
        window1.setResizable(true);
        window1.isResizable();
        window1.isMaximized();
        window1.getIcons().add(new Image("image/window_icon.png"));
        Scene scene=new Scene(borderPane,1200,700);
        window1.setScene(scene);
        window1.show();
        System.out.println("[InformationWindow] student id:"+studentid);
        SelectInformationSesssionAndClassWindow.StudentWindow.close();
        //this get the student information,the clas and new value is the class and the student name respectively
        new ClassInformationThread(studentid,NameLabel,ageLabel,phoneNoLabel,parentPhoneNumberLabel,fatherNameLabel,motherNameLabel,
                addressLabel,nextOfKinLabel,genderLabel,clubLabel,rOleModelLabel,image,FatherImage,MotherImage,otherImage,FatherName,MotherName,otherName,Class2,Tag2,ambition2,Session2,qrImage).start();
    }

}
