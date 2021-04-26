package sample.Configuration;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.ConnectionError;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigureApplication implements Initializable {

    public JFXTextField ipAddressTextField;
    public JFXTextField serverPort;
    public Label softwareVersion;
    private static Path path;
    public Configuration configuration;

    public ConfigureApplication (){
        configuration=new Configuration();
        path= Paths.get(System.getProperty("user.dir")+"/MyChildSchool/co.ser");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        softwareVersion.setText(String.valueOf(configuration.getSoftwareVersion()));
        if (Files.exists(path)){
            File configFile=getConfigurationFile();
            try {
                FileInputStream fileInputStream=new FileInputStream(configFile);
                Properties properties=new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();
                ipAddressTextField.setText(properties.getProperty("ipaddress"));
                serverPort.setText(properties.getProperty("port"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            ipAddressTextField.setText("");
            serverPort.setText("");
        }
    }

    public void onSaveButtonClicked(){
        if (!ipAddressTextField.getText().isEmpty()||!serverPort.getText().isEmpty()){
            String ipAddress=ipAddressTextField.getText();
            String port=serverPort.getText();
            configuration.setIpaddress(ipAddress);
            configuration.setPort(port);
            System.out.println("Ip address: "+ipAddress);
            System.out.println("Port: "+port);
            if (Files.exists(path)){
                saveConfig(configuration.getIpaddress(),configuration.getPort());
                new ConnectionError().Connection("Successfully saved");
            }else{
                createFile();
                saveConfig(configuration.getIpaddress(),configuration.getPort());
            }
        }else {
            new ConnectionError().Connection("Fields must not be empty");
        }
    }

    public void createFile(){
        try {
            Files.createFile(path);
        } catch (IOException e) {
            new ConnectionError().Connection("Unable to save");
            e.printStackTrace();
        }
    }
    public void saveConfig(String ipAddress, String port){
        String raw="ipaddress="+ipAddress+"\n port="+port;
        try {
            FileOutputStream fos=new FileOutputStream(path.toFile());
            fos.write(raw.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static File getConfigurationFile(){
        File file =new File(String.valueOf(path.toAbsolutePath()));
        return file;
    }

    public static String getIpAddress(){
        String ipAddress=null;
        if (Files.exists(path)){
            File configFile=getConfigurationFile();
            try {
                FileInputStream fileInputStream=new FileInputStream(configFile);
                Properties properties=new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();
                ipAddress=properties.getProperty("ipaddress");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            ipAddress=null;
        }
        return ipAddress;
    }
    public static String getPort(){
        String port=null;
        if (Files.exists(path)){
            File configFile=getConfigurationFile();
            try {
                FileInputStream fileInputStream=new FileInputStream(configFile);
                Properties properties=new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();
                port=properties.getProperty("port");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            port=null;
        }
        return port;
    }
}
