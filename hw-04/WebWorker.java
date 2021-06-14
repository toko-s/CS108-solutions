import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private String urlString;
    private WebFrame frame;
    private int index;
    private long time;
    private String status;

    public WebWorker(String urlString, WebFrame frame, int index) {
        this.urlString = urlString;
        this.frame = frame;
        this.index = index;
    }

    @Override
    public void run() {
        time = System.currentTimeMillis();
        download();
    }

    public void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);
            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                if(isInterrupted())
                    throw new InterruptedException();
                contents.append(array, 0, len);
                Thread.sleep(100);
            }
            status = new SimpleDateFormat("hh:mm:ss").format(new Date()) + "    " + (System.currentTimeMillis() - time) + "ms     " + contents.length() + " bytes";
        }
        // Otherwise control jumps to a catch...
        catch (IOException e){
            if(isInterrupted())
                status = "Interrupted";
            else
                status = "Err";
        } catch (InterruptedException exception) {
            status = "Interrupted";
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            frame.updateTable(index,status);
            frame.unlockThread();
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }


    }
}
