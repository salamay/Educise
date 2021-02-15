package sample.LoginPage.DashBoard;


import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.Duration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.AdminWindow;
import sample.LoginPage.DashBoard.SelectWindows.AcademicSession.SelectAcademicSession;
import sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation.EditstudentInformationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionAndClassWindow;
import sample.LoginPage.DashBoard.SelectWindows.Parent.SelectParent;
import sample.LoginPage.DashBoard.SelectWindows.Score.StudentSelectAssessmentSessionWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.RegisterationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Teacher.NewTeacherWindow;
import sample.LoginPage.DashBoard.SelectWindows.Teacher.PreInformationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Teacher.retrieveTeacherWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public JFXButton studentinformationbutton;
    public JFXButton editStudentInformationButton;
    public JFXButton parentButton;
    public JFXButton teacherInfoButton;
    public JFXButton createSessionButton;
    public JFXButton adminButton;
    public JFXButton NewStudentButton;
    public JFXButton StudentAssessmentButton;
    public JFXButton ParentPortal;
    public JFXButton NewTeacherButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void StudentButtonClicked() throws IOException {
     new SelectInformationSesssionAndClassWindow();
    }
    public void EditStudentInformationButtonClicked(){
        new EditstudentInformationWindow();
    }

    public void NewStudentButtonClicked() throws IOException {
        new RegisterationWindow();
    }
    public void NewTeacherButtonClicked() throws IOException {
        new NewTeacherWindow();
    }
    public void NewTeacherButtonClickedtwo() throws IOException {
        new PreInformationWindow();
    }

    public void CreateStudentSessionClicked() throws IOException {
        new SelectAcademicSession().CreateWindow();
    }

    public void StudentAssessmentButtonClicked() throws IOException {
        new StudentSelectAssessmentSessionWindow();
    }

    public void ParentPortalClicked() throws IOException {
        new SelectParent();
    }
    public void ParentPortalClickedtwo() throws IOException {
        new SelectParent();
    }

    public void AdminButtonClicked() throws IOException {
        new AdminWindow();
    }






    //////////////////////////////////On Hover Methods////////////////////////////////////////////////////////
    public void studentInformationOnMouseEnter(){
        setColor(studentinformationbutton);ScaleTrans(studentinformationbutton);
    }
    public void studentInformationOnMouseExited(){
        disableColor(studentinformationbutton);RestoreScale(studentinformationbutton);
    }


    public void EditStudentInformationButtonEntered(){
        setColor(editStudentInformationButton);ScaleTrans(editStudentInformationButton);
    }
    public void EditStudentInformationButtonExited(){
        disableColor(editStudentInformationButton);RestoreScale(editStudentInformationButton);
    }
    public void ParentInfoButtonEntered(){
        setColor(parentButton);ScaleTrans(parentButton);
    }
    public void ParentInfoButtonExited(){
        disableColor(parentButton); RestoreScale(parentButton);
    }

    public void NewTeacherButtonEntered(){
        setColor(teacherInfoButton);ScaleTrans(teacherInfoButton);
    }
    public void NewTeacherButtonExited(){
        disableColor(teacherInfoButton); RestoreScale(teacherInfoButton);
    }

    public void CreateStudentSessionEntered(){
        setColor(createSessionButton); ScaleTrans(createSessionButton);
    }
    public void CreateStudentSessionExited(){
        disableColor(createSessionButton); RestoreScale(createSessionButton);
    }

    public void AdminButtonEntered(){
        setColor(adminButton);ScaleTrans(adminButton);
    }
    public void AdminButtonExited(){
        disableColor(adminButton);RestoreScale(adminButton);
    }

    public void NewStudentButtonEntered(){
        ScaleTrans(NewStudentButton);
    }
    public void NewStudentButtonExited(){
        RestoreScale(NewStudentButton);
    }
    public void StudentAssessmentButtonEntered(){
        ScaleTrans(StudentAssessmentButton);
    }
    public void StudentAssessmentButtonExited(){
        RestoreScale(StudentAssessmentButton);
    }

    public void ParentPortalEntered(){
        ScaleTrans(ParentPortal);
    }
    public void ParentPortalExited(){
         RestoreScale(ParentPortal);
    }

    public void NewTeacherButtonEntered2(){
        ScaleTrans(NewTeacherButton);
    }
    public void NewTeacherButtonExited2(){
        RestoreScale(NewTeacherButton);
    }



    public void setColor(JFXButton button){
        button.setStyle("-fx-background-color:#4C7B9E;");
    }
    public void disableColor(JFXButton button){
        button.setStyle("-fx-background-color: #1E306E;");
    }

    public void ScaleTrans(Button button){
        ScaleTransition scaleTransition=new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setFromY(1);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            scaleTransition.stop();
        });
    }
    public void RestoreScale(Button button){
        ScaleTransition scaleTransition=new ScaleTransition(new Duration(100));
        scaleTransition.setNode(button);
        scaleTransition.setFromX(1.2);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(1.2);
        scaleTransition.setToY(1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            scaleTransition.stop();
        });
    }
}
