package sample.LoginPage.DashBoard.SelectWindows.AcademicSession;


import com.jfoenix.controls.JFXSpinner;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.ConnectionError;
import sample.LoginPage.DashBoard.SelectWindows.Registeration.LoadingWindow;

import java.io.IOException;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AcademicSessionController {
    public TextField jss1SessionTextField;
    public TextField jss2SessionTextField;
    public TextField jss3SessionTextField;
    public TextField ss1SessionTextField;
    public TextField ss2SessionTextField;
    public TextField ss3SessionTextField;
    public TextField pr1SessionTextField;
    public TextField pr2SessionTextField;
    public TextField pr3SessionTextField;
    public TextField pr4SessionTextField;
    public TextField pr5SessionTextField;
    public TextField nur1SessionTextField;
    public TextField nur2SessionTextField;
    public Label Error;

    public void CreateSessionClicked() throws IOException {
        String jss1sessionName=jss1SessionTextField.getText();
        String jss2sessionName=jss2SessionTextField.getText();
        String jss3sessionName=jss3SessionTextField.getText();
        String ss1sessionName=ss1SessionTextField.getText();
        String ss2sessionName=ss2SessionTextField.getText();
        String ss3sessionName=ss3SessionTextField.getText();
        String pr1sessionName=pr1SessionTextField.getText();
        String pr2sessionName=pr2SessionTextField.getText();
        String pr3sessionName=pr3SessionTextField.getText();
        String pr4sessionName=pr4SessionTextField.getText();
        String pr5sessionName=pr5SessionTextField.getText();
        String nur1sessionName=nur1SessionTextField.getText();
        String nur2sessionName=nur2SessionTextField.getText();
        System.out.println(ss3sessionName);
        System.out.println(jss3sessionName);

            Error.setVisible(false);
            new LoadingWindow();
            new CreatingSessionThread(jss1sessionName, jss2sessionName, jss3sessionName, ss1sessionName,
                    ss2sessionName, ss3sessionName,pr1sessionName,pr2sessionName,pr3sessionName,pr4sessionName,pr5sessionName,nur1sessionName,nur2sessionName).start();

    }
}
