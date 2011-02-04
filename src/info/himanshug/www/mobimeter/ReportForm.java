package info.himanshug.www.mobimeter;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 * Display form for displaying the test report.
 * @author hgupta<g.himanshu@gmail.com>
 */
public class ReportForm {

    private Form form;

    private StringItem countStringItem;
    private StringItem averageTimeStringItem;
    private StringItem averageSizeStringItem;
    private StringItem minStringItem;
    private StringItem maxStringItem;
    private StringItem errorsStringItem;
    private StringItem statusStringItem;
    private StringItem responseStringItem;

    private Command exitCommand;
    private Command aboutCommand;

    public ReportForm(CommandListener listener) {
        form = new Form("Report", new Item[] { getStatusStringItem(), getCountStringItem(),
            getAverageTimeStringItem(), getMinStringItem(), getMaxStringItem(),
            getAverageSizeStringItem(), getErrorsStringItem()});
        
        form.addCommand(getExitCommand());
        form.addCommand(getAboutCommand());
        form.setCommandListener(listener);
    }

   public StringItem getCountStringItem() {
        if (countStringItem == null) {
            countStringItem = new StringItem("Count:", "0");
        }
        return countStringItem;
    }

    public StringItem getAverageTimeStringItem() {
        if (averageTimeStringItem == null) {
            averageTimeStringItem = new StringItem("AverageTime(ms):", "0");
        }
        return averageTimeStringItem;
    }

    public StringItem getAverageSizeStringItem() {
        if (averageSizeStringItem == null) {
            averageSizeStringItem = new StringItem("AverageSize(bytes):", "0");
        }
        return averageSizeStringItem;
    }

    public StringItem getMinStringItem() {
        if (minStringItem == null) {
            minStringItem = new StringItem("Min(ms):", "0");
        }
        return minStringItem;
    }

    public StringItem getMaxStringItem() {
        if (maxStringItem == null) {
            maxStringItem = new StringItem("Max(ms):", "0");
        }
        return maxStringItem;
    }

    public StringItem getErrorsStringItem() {
        if (errorsStringItem == null) {
            errorsStringItem = new StringItem("Errors:", "0");
        }
        return errorsStringItem;
    }

     public StringItem getStatusStringItem() {
        if (statusStringItem == null) {
            statusStringItem = new StringItem("Status:", "In-Progress");
        }
        return statusStringItem;
    }

    public StringItem getResponseStringItem() {
        if (responseStringItem == null) {
            responseStringItem = new StringItem("Last Response:", "");
        }
        return responseStringItem;
    }

    public Command getExitCommand() {
        if (exitCommand == null) {
            exitCommand = new Command("Exit", Command.EXIT, 0);
        }
        return exitCommand;
    }

    public Command getAboutCommand() {
        if (aboutCommand == null) {
            aboutCommand = new Command("About", Command.SCREEN, 1);
        }
        return aboutCommand;
    }

    public Form getForm() {
        return form;
    }
}
