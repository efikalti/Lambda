package intrusiondetection.watchdirapi.Tasks;

import intrusiondetection.watchdirapi.WatchDir;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author efi kaltirimidou
 */
public class WriteToFileTask extends TimerTask {

    private Object obj;
    private final String statefile;
    
    public WriteToFileTask(Object obj, String file)
    {
        this.obj = obj;
        this.statefile = file;
    }
    
    public void setObject(Object obj)
    {
        System.out.println("Object set.");
        this.obj = obj;
    }
    
    public void WriteToFile()
    {
        System.out.println("Writing to file.");
         ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(statefile));
            oos.writeObject(this.obj);
            oos.close();
        }
        catch (IOException ex) {
            Logger.getLogger(WatchDir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.WriteToFile();
    }
    
}
