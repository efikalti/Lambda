package intrusiondetection.watchdirapi.Tasks;

import intrusiondetection.watchdirapi.Config;
import java.util.Properties;
import java.util.TimerTask;
import org.apache.hadoop.conf.Configuration;

/**
 *
 * @author efi kaltirimidou
 */
public class WriteToHDFSTask extends TimerTask{

    private final String namenode;
    private final Configuration conf;
    private final String dir;
    
    public WriteToHDFSTask()
    {
        Properties props = Config.getInstance().getProperties();
        this.dir = props.getProperty("dir");
        namenode = props.getProperty("namenode");
        conf =  new Configuration();
        conf.set("fs.default.name", namenode);
    }
    
    @Override
    public void run() {
        
    }
    
}
