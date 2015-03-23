package intrusiondetection.watchdirapi;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


/**
 *
 * @author efi kaltirimidou
 */
public class Config {
    
    private String config_file = "config/configuration.xml";
    XMLConfiguration configRead;
    
    private Config() {
        try {
            configRead = new XMLConfiguration(config_file);
        }
        catch (ConfigurationException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
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
        /*
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
        /*
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
        /*
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
        */

    }
    
    public static Config getInstance() {
        return PropertiesHolder.INSTANCE;
    }
    
    private static class PropertiesHolder {

        private static final Config INSTANCE = new Config();
    }
    
    /*
    public String getProperty(String field)
    {
        return this.props.getProperty(field);
    }
    
    public Properties getProperties()
    {
        return this.props;
    }
    */
    
    public String getProperty(String key)
    {
        return configRead.getString(key);
    }
    
    public void writeConfig(String key, String value)
    {
        configRead.setFileName(this.config_file);
        configRead.addProperty(key, value);
        
        try {
            configRead.save();
        }
        catch (ConfigurationException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            /*
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
            
            /*
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
            
            /*
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
            
            /*
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
            */
    }
}
