package intrusiondetection.watchdirapi;

import intrusiondetection.watchdirapi.Tasks.WriteToFileTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author efi kaltirimidou
 */
public class UpdateBatchLayer implements Runnable{
    private Set<String> files;
    private final String statefile;
    private final Timer timer;
    
    public UpdateBatchLayer()
    {
        Properties props = Config.getInstance().getProperties();
        this.statefile = props.getProperty("statefile");
        if (!this.retrievePreviousState())
        {
            files = new HashSet<>();
        }
        for (String s : this.files)
        {
            System.out.println(s);
        }
        //start a new time, with true to state that is a daemon timer
        timer = new Timer(true);
    }
    
    public void add(String filename)
    {
        System.out.println("File " + filename + " added to the update set.");
        if (!files.contains(filename))
        {
            files.add(filename);
        }
    }
    
    public Iterator getIterator()
    {
        return files.iterator();
    }
    
    /**
     * Save the state of the watchdir tree to a file to be retrieved later or in
     * case of an unexpected termination of the program
     */
    public void saveState() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(statefile));
            oos.writeObject(this.files);
            oos.close();
        }
        catch (IOException ex) {
            Logger.getLogger(WatchDir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Retrieves the object keys from the file stated in the configuration from previous run
     *
     * @return false if there is no previous state or in case of an error with
     * the file handling
     */
    private boolean retrievePreviousState() {
        File f = new File(statefile);
        if (f.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(statefile));
                try {
                    this.files = (Set<String>) ois.readObject();
                    ois.close();
                    return true;
                }
                catch (ClassNotFoundException ex) {
                    Logger.getLogger(WatchDir.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            catch (IOException ex) {
                Logger.getLogger(WatchDir.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void run() {
        System.out.println("running...");
        WriteToFileTask task = new WriteToFileTask(this.files, this.statefile);
        timer.scheduleAtFixedRate(task, 1000, 6000);
        task.setObject(this.files);
    }
}
