package sample.DashBoard;


import javafx.fxml.Initializable;
import sample.*;
import sample.SelectWindows.Information.StudentSelectClassInfo;
import sample.SelectWindows.Parent.SelectParent;
import sample.SelectWindows.Score.StudentAssessmentSession;
import sample.StudentRegistration.RegisterationWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void StudentButtonClicked() throws IOException {
     new StudentSelectClassInfo();

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
        new StudentAssessmentSession();
    }

    public void ParentPortalClicked() throws IOException {
        new SelectParent();


    }


}
