/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.himanshug.www.mobimeter;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author hgupta
 */
public class MobileMeterMidlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    private InputForm inputForm;
    private ReportForm reportForm;

    Alert invalidAlert;
    
    /**
     * The MainMIDlet constructor.
     */
    public MobileMeterMidlet() {
    }

    /**
     * Initializes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {
        // write pre-initialize user code here

        // write post-initialize user code here
    }

    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {
        // write pre-action user code here
        switchDisplayable(null, getInputForm().getForm());
        // write post-action user code here
    }

    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {
        // write pre-action user code here

        // write post-action user code here
    }

    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        // write pre-switch user code here
        Display display = getDisplay();
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
        // write post-switch user code here
    }

    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {
        // write pre-action user code here
        if (displayable == getInputForm().getForm()) {
            if (command == getInputForm().getExitCommand()) {
                // write pre-action user code here
                exitMIDlet();
                // write post-action user code here
            } else if (command == getInputForm().getOkCommand()) {
                //check if input form is valid
                if(getInputForm().isValid()) {
                    //update the input data record
                    InputDataRecord.getPreviousRecord().update(getInputForm().getWarmUpReqTextField().getString(),
                            getInputForm().getTestReqCountTextField().getString(),
                            getInputForm().getThinkTimeTextField().getString(),
                            getInputForm().getUrlTextField().getString().trim());

                    //do the load test and change display to report
                    LoadTester ltester = new LoadTester(Integer.parseInt(getInputForm().getWarmUpReqTextField().getString()),
                            Integer.parseInt(getInputForm().getTestReqCountTextField().getString()),
                            Long.parseLong(getInputForm().getThinkTimeTextField().getString()),
                            getInputForm().getUrlTextField().getString().trim(),getReportForm());
                    switchDisplayable(null, getReportForm().getForm());
                    // write post-action user code here
                    new Thread(ltester).start();
                }
                else {
                    //display Alert
                    switchDisplayable(getInvalidAlert(), getInputForm().getForm());
                }
            }
        } else if (displayable == getReportForm().getForm()) {
            if (command == getReportForm().getExitCommand()) {
                // write pre-action user code here
                exitMIDlet();
                // write post-action user code here
            }
        }
        // write post-action user code here
    }

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    private InputForm getInputForm() {
        if(inputForm == null)
            inputForm = new InputForm(this);

        return inputForm;
    }

    private ReportForm getReportForm() {
        if(reportForm == null)
            reportForm = new ReportForm(this);

        return reportForm;
    }

    private Alert getInvalidAlert() {
        if(invalidAlert == null) {
            invalidAlert = new Alert("INVALID", "Please input valid values.", null, AlertType.WARNING);
            invalidAlert.setTimeout(Alert.FOREVER);
        }
        return invalidAlert;
    }
}
