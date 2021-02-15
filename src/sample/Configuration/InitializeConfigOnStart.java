package sample.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class InitializeConfigOnStart {

    private static Path path = Paths.get(System.getProperty("user.dir") + "/MyChildSchool/co.ser");

    public static File getConfigurationFile() {
        if (!Files.exists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(String.valueOf(path.toAbsolutePath()));
        return file;
    }

    public String getIpAddress() {
        String ipAddress = null;
        if (Files.exists(path)) {
            File configFile = getConfigurationFile();
            try {
                FileInputStream fileInputStream = new FileInputStream(configFile);
                Properties properties = new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();
                ipAddress = properties.getProperty("ipaddress");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            ipAddress = null;
        }
        return ipAddress;
    }

    public String getPort(){
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