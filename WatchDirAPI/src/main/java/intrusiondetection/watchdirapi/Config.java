package intrusiondetection.watchdirapi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author efi kaltirimidou
 */
public class Config {
    
    private Properties props;
    private FileInputStream fis;
    private String config_file = "config/configuration.xml";
    
    private Config() {
        props = new Properties();
        try {
            fis = new FileInputStream(config_file);
            try {
                props.loadFromXML(fis);
            }
            catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static Config getInstance() {
        return PropertiesHolder.INSTANCE;
    }
    
    private static class PropertiesHolder {

        private static final Config INSTANCE = new Config();
    }
    
    public String getProperty(String field)
    {
        return this.props.getProperty(field);
    }
    
    public Properties getProperties()
    {
        return this.props;
    }
    
    public void close()
    {
        try {
            fis.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeConfig(String key, String value)
    {
        FileOutputStream out;
        try {
            this.props.setProperty(key, value);
            out = new FileOutputStream(this.config_file);
            try {
                props.store(out, null);
            }
            catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                out.close();
            }
            catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
