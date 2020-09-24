package sample.LoginPage.DashBoard;


import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.Admin.AdminWindow;
import sample.LoginPage.DashBoard.SelectWindows.AcademicSession.SelectAcademicSession;
import sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation.EditstudentInformationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Information.SelectInformationSesssionWindow;
import sample.LoginPage.DashBoard.SelectWindows.Parent.SelectParent;
import sample.LoginPage.DashBoard.SelectWindows.Score.StudentSelectAssessmentSessionWindow;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.RegisterationWindow;
import sample.LoginPage.DashBoard.SelectWindows.Teacher.NewTeacherWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void StudentButtonClicked() throws IOException {
     new SelectInformationSesssionWindow();

    }
    public void EditStudentInformationButtonClicked(){
        new EditstudentInformationWindow();
    }

    public void NewStudentButtonClicked() throws IOException {
        new RegisterationWindow();

    }
    public void NewTeacherButtonClicked() throws IOException {
        new ConnectionError().Connection("Not available");
        //new NewTeacherWindow();
    }
    public void NewTeacherButtonClickedtwo() throws IOException {
        new ConnectionError().Connection("Not available");
        //new NewTeacherWindow();
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
    ///on over method
    public void StundenInfoButtonOnDragExited(){
        System.out.println("Mouse status:On drag over");
       // scaleTransition(studentinfobutton);
    }

    public void StundenInfoButtonOnDragReleased(){

    }


    public void scaleTransition(JFXButton button){
        ScaleTransition scaleTransition=new ScaleTransition();
        scaleTransition.setDuration(new Duration(100));
        scaleTransition.setByX(1.5);
        scaleTransition.setByY(1.5);
        scaleTransition.setNode(button);
        scaleTransition.play();
    }

}
