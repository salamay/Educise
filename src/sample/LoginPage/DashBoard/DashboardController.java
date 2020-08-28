package sample.LoginPage.DashBoard;


import javafx.fxml.Initializable;
import sample.LoginPage.DashBoard.SelectWindows.AcademicSession.SelectAcademicSession;
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

    public void NewStudentButtonClicked() throws IOException {
        new RegisterationWindow();

    }
    public void NewTeacherButtonClicked() throws IOException {
        new NewTeacherWindow();
    }

    public void Student_Score_Clicked() throws IOException {

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


}
