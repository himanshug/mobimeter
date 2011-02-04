package info.himanshug.www.mobimeter;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * A requester that sends the requests and updates statistics
 * *withOUT* reporting response data
 * @author hgupta<g.himanshu@gmail.com>
 */
public class SimpleRequester implements IRequester {

    private static final int BUFFER_SIZE = 2000;

    public void send(String url, Stats stats) throws IOException {
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
                byte[] buffer = new byte[BUFFER_SIZE];
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
            stats.addSample(firstByteReceivedTime - startTime, size, isError,null);
    }


}
