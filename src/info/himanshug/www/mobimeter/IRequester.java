package info.himanshug.www.mobimeter;

import java.io.IOException;

/**
 *
 * @author hgupta<g.himanshu@gmail.com>
 */
public interface IRequester {

    /**
     * Send GET request to url and update the statistics in stats
     */
    public void send(String url, Stats stats) throws IOException;
}
