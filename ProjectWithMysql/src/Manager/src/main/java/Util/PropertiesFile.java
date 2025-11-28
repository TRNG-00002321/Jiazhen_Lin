package Util;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    public static Properties createManagerDBProperties() {
        Properties prop = new Properties();
        try {
            prop.setProperty("url", "jdbc:mysql://localhost:3306/employee");
            prop.setProperty("user", "root");
            prop.setProperty("password", "password");
            prop.store(new FileOutputStream("ManagerDB.properties"), "Added 11/25/2025");
            prop.load(new FileInputStream("ManagerDB.properties"));
            return prop;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Properties loadManagerDBProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("ManagerDB.properties"));
            return prop;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
