package sample.Configuration;

public class Configuration {
    private static final double SoftwareVersion=1.0;
    public static String ipaddress;
    public static String port;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public double getSoftwareVersion() {
        return SoftwareVersion;
    }
}
