package Settings;

import Model.BDD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingsProperties {
    String result = "";
    InputStream inputStream;
    private String DBUserName ;
    private String DBPasswd ;

    public String getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "./config.properties";

//            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }



            this.DBUserName = prop.getProperty("DATA_BASE_USERNAME");
            this.DBPasswd = prop.getProperty("DATA_BASE_PASSWORD");
            BDD.setProperties(this.DBUserName, this.DBPasswd);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

    public static void init(){
        SettingsProperties settingsProperties = new SettingsProperties();
        try {
            settingsProperties.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}