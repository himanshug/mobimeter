package info.himanshug.www.mobimeter;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

/**
 * Display form to get various inputs from the user.
 * @author hgupta<g.himanshu@gmail.com>
 */
public class InputForm {

    private Form form;
    private TextField warmUpReqTextField;
    private TextField testReqCountTextField;
    private TextField thinkTimeTextField;
    private TextField urlTextField;

    private Command exitCommand;
    private Command okCommand;

    private InputDataRecord previousInputs;
    
    public InputForm(CommandListener listener) {
        previousInputs = InputDataRecord.getPreviousRecord();
        
        form = new Form("Mobi-Meter", new Item[] { getWarmUpReqTextField(),
            getTestReqCountTextField(), getThinkTimeTextField(), getUrlTextField() });
        form.addCommand(getExitCommand());
        form.addCommand(getOkCommand());
        form.setCommandListener(listener);
    }

    public TextField getWarmUpReqTextField() {
        if (warmUpReqTextField == null) {
            warmUpReqTextField = new TextField("WarmUp Req Count(>0)", previousInputs.getWarmUpReqCount(), 32, TextField.NUMERIC);
        }
        return warmUpReqTextField;
    }

    public TextField getTestReqCountTextField() {
        if (testReqCountTextField == null) {
            testReqCountTextField = new TextField("TestRequest Count(>0)", previousInputs.getTestReqCount(), 32, TextField.NUMERIC);
        }
        return testReqCountTextField;
    }

    public TextField getThinkTimeTextField() {
        if (thinkTimeTextField == null) {
            thinkTimeTextField = new TextField("Think Time (ms, >=0)", previousInputs.getThinkTime(), 32, TextField.NUMERIC);
        }
        return thinkTimeTextField;
    }

    public TextField getUrlTextField() {
        if (urlTextField == null) {
            urlTextField = new TextField("URL(non-empty)", previousInputs.getUrl(), 500, TextField.URL);
        }
        return urlTextField;
    }

    public Command getOkCommand() {
        if (okCommand == null) {
            okCommand = new Command("Start", Command.OK, 0);
        }
        return okCommand;
    }

    public Command getExitCommand() {
        if (exitCommand == null) {
            exitCommand = new Command("Exit", Command.EXIT, 0);
        }
        return exitCommand;
    }

    public boolean isValid() {
        return getWarmUpReqTextField().getString().length() > 0 && Integer.parseInt(getWarmUpReqTextField().getString()) > 0 &&
                getTestReqCountTextField().getString().length() > 0 && Integer.parseInt(getTestReqCountTextField().getString()) > 0 &&
                getThinkTimeTextField().getString().length() > 0 && Integer.parseInt(getThinkTimeTextField().getString()) >= 0 &&
                getUrlTextField().getString().trim().length() > 0;
    }

    public Form getForm() {
        return form;
    }
}
