package sample.LoginPage.DashBoard.SelectWindows.AcademicSession;


import com.jfoenix.controls.JFXSpinner;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AcademicSessionController {
    public TextField jss1SessionTextField;
    public TextField jss2SessionTextField;
    public TextField jss3SessionTextField;
    public TextField ss1SessionTextField;
    public TextField ss2SessionTextField;
    public TextField ss3SessionTextField;
    public Label Error;
    public JFXSpinner LoadingSpinner;

    public void CreateSessionClicked(){
        String jss1sessionName=jss1SessionTextField.getText();
        String jss2sessionName=jss2SessionTextField.getText();
        String jss3sessionName=jss3SessionTextField.getText();
        String ss1sessionName=ss1SessionTextField.getText();
        String ss2sessionName=ss2SessionTextField.getText();
        String ss3sessionName=ss3SessionTextField.getText();

        if(jss1SessionTextField.getText().isEmpty() || jss2SessionTextField.getText().isEmpty()
                || jss3SessionTextField.getText().isEmpty() || ss1SessionTextField.getText().isEmpty()||
                ss2SessionTextField.getText().isEmpty() || ss3SessionTextField.getText().isEmpty()){
            Error.setVisible(true);
        }
        else {

            new CreatingSessionThread(jss1sessionName, jss2sessionName, jss3sessionName, ss1sessionName,
                    ss2sessionName, ss3sessionName,LoadingSpinner).start();
        }
    }
}
