package info.himanshug.www.mobimeter;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * A requester that sends the requests and updates statistics
 * *WITH* reporting response data(first MAX_RESPONSE_STORED chars only)
 *
 * @author hgupta<g.himanshu@gmail.com>
 */
public class ComplexRequester implements IRequester {

    private static final int BUFFER_SIZE = 2000;
    private static final int MAX_RESPONSE_STORED = 2000;

    public void send(String url, Stats stats) throws IOException {
        //we can make all http thingie configurable e.g.
        //GET or POST, http or https, User-Agent etc
        long size = 0; //in bytes
        boolean isError = false;
        HttpConnection conn = null;
        InputStreamReader rdr = null;
        String response = null;

        long startTime = System.currentTimeMillis();
        long firstByteReceivedTime = startTime;

        try {
            conn = (HttpConnection)Connector.open(url);
            conn.setRequestMethod(HttpConnection.GET);
            if(conn.getResponseCode() != conn.HTTP_OK)
                isError = true;

            //read off any data
            rdr = new InputStreamReader(conn.openDataInputStream());

            int firstChar = rdr.read();
            firstByteReceivedTime = System.currentTimeMillis();

            if(firstChar > -1) {
                size += 1; //size of first char
                StringBuffer sb = new StringBuffer();
                sb.append((char)firstChar);

                char[] buffer = new char[BUFFER_SIZE];
                int n = -1;

                while((n = rdr.read(buffer)) > -1) {
                    size += n;
                    if(sb.length() < MAX_RESPONSE_STORED)
                        sb.append(buffer,0,n);
                }

                response = sb.toString();
            }

            //System.out.println(response);

        }
        finally {
            if(conn != null)
                conn.close();
            if(rdr != null)
                rdr.close();
        }

        if(stats != null)
            stats.addSample(firstByteReceivedTime - startTime, size, isError, response);
    }
}
