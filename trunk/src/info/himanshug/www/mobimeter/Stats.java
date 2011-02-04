package info.himanshug.www.mobimeter;

/**
 * A class to keep the statistics.
 * @author hgupta<g.himanshu@gmail.com>
 */
public class Stats {

    private long min = Integer.MAX_VALUE;
    private long max = Integer.MIN_VALUE;
    private int errors = 0;
    private int count = 0;
    private long totalTime = 0;
    private long totalSize = 0;

    private ReportForm reportForm; //Form that is kept updated

    public Stats(ReportForm reportForm) {
        this.reportForm = reportForm;
    }

    public void addSample(long time, long size, boolean isError, String response) {
        totalTime += time;
        totalSize += size;
        count++;
        if(isError) errors++;
        if(time < min) min = time;
        if(time > max) max = time;

        updateMidlet(response != null ? response : "");
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public int getErrors() {
        return errors;
    }

    public long getAverageTime() {
        return totalTime/count;
    }

    public long getAverageSize() {
        return totalSize/count;
    }

    private void updateMidlet(String response) {
        reportForm.getCountStringItem().setText(Integer.toString(count));
        reportForm.getAverageTimeStringItem().setText(Long.toString(getAverageTime()));
        reportForm.getMinStringItem().setText(Long.toString(min));
        reportForm.getMaxStringItem().setText(Long.toString(max));
        reportForm.getAverageSizeStringItem().setText(Long.toString(getAverageSize()));
        reportForm.getErrorsStringItem().setText(Integer.toString(errors));
        reportForm.getResponseStringItem().setText(response);
    }
}
