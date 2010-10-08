package info.himanshug.www.mobimeter;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * Runnable that sends all the requests and collects/updates the statistics.
 * @author hgupta<g.himanshu@gmail.com>
 */
public class LoadTester implements Runnable {

    private int warmUpCount;
    private int actualCount;
    private long thinkTime;
    private String url;

    private ReportForm reportForm;
    private int bufferSize = 1234;

    public LoadTester(int warmUpCount, int actualCount, long thinkTime, String url, ReportForm reportForm) {
        this.warmUpCount = warmUpCount > 0 ? warmUpCount : 0;
        this.actualCount = actualCount > 0 ? actualCount : 0;
        this.thinkTime = thinkTime > 0 ? thinkTime : 0;
        this.url = url;
        this.reportForm = reportForm;
    }

    public void run() {
        try {
            //do the warmup
            reportForm.getStatusStringItem().setText("Warming-Up");
            for(int i = 0; i < warmUpCount; i++) {
                sendRequest(url,null);
                Thread.currentThread().sleep(thinkTime);
            }

            //load and test
            reportForm.getStatusStringItem().setText("In-Progress");
            Stats stats = new Stats(reportForm);
            for(int i = 0; i < actualCount; i++) {
                sendRequest(url,stats);

                if(i != actualCount-1)
                    Thread.currentThread().sleep(thinkTime);
            }

            reportForm.getStatusStringItem().setText("Finished");
        }
        catch(Exception ex) {
            reportForm.getStatusStringItem().setText("Failed." + ex.getClass().getName());
        }
    }

    //sends the request and updates statistics if needed
    private void sendRequest(String url, Stats stats) throws IOException {
        //we can make all http thingie configurable e.g.
        //GET or POST, http or https, User-Agent etc
        long startTime = System.currentTimeMillis();
        long firstByteReceivedTime = startTime;
        //long allBytesReceivedTime = startTime;
        long size = 0; //in bytes

        boolean isError = false;
        HttpConnection conn = null;
        DataInputStream dis = null;

        try {
            conn = (HttpConnection)Connector.open(url);
            conn.setRequestMethod(HttpConnection.GET);
            if(conn.getResponseCode() != conn.HTTP_OK)
                isError = true;

            //read off any data
            dis = conn.openDataInputStream();
            int ch = dis.read();
            firstByteReceivedTime = System.currentTimeMillis();

            if(ch != -1) {
                size = 1;
                byte[] buffer = new byte[bufferSize];
                while((ch = dis.read(buffer)) != -1) {
                  size += ch;
                }
            }
        }
        finally {
            if(conn != null)
                conn.close();
            if(dis != null)
                dis.close();
        }

        if(stats != null)
            stats.addSample(firstByteReceivedTime - startTime, size, isError);
    }
}
