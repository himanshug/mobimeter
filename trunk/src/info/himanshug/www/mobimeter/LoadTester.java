package info.himanshug.www.mobimeter;

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

    private IRequester requester;
    
    

    public LoadTester(int warmUpCount, int actualCount, long thinkTime,
            String url, ReportForm reportForm, boolean shouldPrintResponse) {
        this.warmUpCount = warmUpCount > 0 ? warmUpCount : 0;
        this.actualCount = actualCount > 0 ? actualCount : 0;
        this.thinkTime = thinkTime > 0 ? thinkTime : 0;
        this.url = url;
        this.reportForm = reportForm;

        if(shouldPrintResponse) {
            this.reportForm.getForm().append(this.reportForm.getResponseStringItem());
            requester = new ComplexRequester();
        }
        else {
            requester = new SimpleRequester();
        }
    }

    public void run() {
        try {
            //do the warmup
            reportForm.getStatusStringItem().setText("Warming-Up");
            for(int i = 0; i < warmUpCount; i++) {
                requester.send(url,null);
                Thread.currentThread().sleep(thinkTime);
            }

            //load and test
            reportForm.getStatusStringItem().setText("In-Progress");
            Stats stats = new Stats(reportForm);
            for(int i = 0; i < actualCount; i++) {
                requester.send(url, stats);

                if(i != actualCount-1)
                    Thread.currentThread().sleep(thinkTime);
            }

            reportForm.getStatusStringItem().setText("Finished");
        }
        catch(Exception ex) {
            reportForm.getStatusStringItem().setText("Failed." + ex.getClass().getName());
        }
    }
}
