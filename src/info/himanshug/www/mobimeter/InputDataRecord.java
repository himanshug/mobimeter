package info.himanshug.www.mobimeter;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/**
 * A record to store the inputs received from user. He
 * gets these prefilled when he starts the application
 * next time.
 * @author hgupta<g.himanshu@gmail.com>
 */
public class InputDataRecord {

    private String warmUpReqCount = "";
    private String testReqCount = "";
    private String thinkTime = "";
    private String url = "http://";

    private final static String RS_NAME = "mobile-meter-rs";
    
    private final static String SEPARATOR = "|:|:|";
    private final static int SEPARATOR_LENGTH = SEPARATOR.length();

    private static InputDataRecord instance;

    private InputDataRecord() {}

    private InputDataRecord(String warmUpReqCount, String testReqCount, String thinkTime, String url) {
        this.warmUpReqCount = warmUpReqCount;
        this.testReqCount = testReqCount;
        this.thinkTime = thinkTime;
        this.url = url;
    }
    
    public String getWarmUpReqCount() {
        return warmUpReqCount;
    }

    public String getTestReqCount() {
        return testReqCount;
    }

    public String getThinkTime() {
        return thinkTime;
    }

    public String getUrl() {
        return url;
    }

    public static InputDataRecord getPreviousRecord() {
        if(instance == null) {
            try {
                RecordStore rs = RecordStore.openRecordStore(RS_NAME, true);
                if(rs.getNumRecords() >= 1) {
                    instance = getRecordFromBytes(rs.enumerateRecords(null, null, false).nextRecord());
                }
                rs.closeRecordStore();
            }
            catch(Exception ex) {
                if(MobiMeter.DEBUG)
                    ex.printStackTrace();
            }
            
            if(instance == null) instance = new InputDataRecord();
        }
        return instance;
    }

    //update and save this instance
    public void update(String warmUpReqCount, String testReqCount, String thinkTime, String url) {
        boolean isChanged = false;

        if(!this.warmUpReqCount.equals(warmUpReqCount)) {
            this.warmUpReqCount = warmUpReqCount;
            isChanged = true;
        }

        if(!this.testReqCount.equals(testReqCount)) {
            this.testReqCount = testReqCount;
            isChanged = true;
        }

        if(!this.thinkTime.equals(thinkTime)) {
            this.thinkTime = thinkTime;
            isChanged = true;
        }

        if(!this.url.equalsIgnoreCase(url)) {
            this.url = url;
            isChanged = true;
        }

        if(isChanged)
            save();
    }

    //save this instance to RMS
    private void save() {
        try {
            byte[] data = getBytes();
            RecordStore rs = RecordStore.openRecordStore(RS_NAME, true);
            if(rs.getNumRecords() == 0)
                rs.addRecord(data, 0, data.length);
            else {
                RecordEnumeration re = rs.enumerateRecords(null, null, false);
                rs.setRecord(re.nextRecordId(), data, 0, data.length);
            }
            rs.closeRecordStore();
        }
        catch(Exception ex) {
            if(MobiMeter.DEBUG)
                ex.printStackTrace();
        }
    }

    private static InputDataRecord getRecordFromBytes(byte[] bytes) {
        String str = new String(bytes);

        if(MobiMeter.DEBUG)
            System.out.println("Parsing data record from : " + str);

        int pos1 = str.indexOf(SEPARATOR);
        String warmUpReqCount = str.substring(0,pos1);
        if(MobiMeter.DEBUG)
            System.out.println("warmUpReqCount = " + warmUpReqCount);

        int pos2 = str.indexOf(SEPARATOR, pos1+1);
        String testReqCount = str.substring(pos1+SEPARATOR_LENGTH, pos2);
        if(MobiMeter.DEBUG)
            System.out.println("testReqCount = " + testReqCount);

        int pos3 = str.indexOf(SEPARATOR, pos2+1);
        String thinkTime = str.substring(pos2+SEPARATOR_LENGTH, pos3);
        if(MobiMeter.DEBUG)
            System.out.println("thinkTime = " + thinkTime);

        String url = str.substring(pos3+SEPARATOR_LENGTH);
        if(MobiMeter.DEBUG)
            System.out.println("url = " + url);
        
        return new InputDataRecord(warmUpReqCount, testReqCount, thinkTime, url);
    }

    private byte[] getBytes() {
        StringBuffer sb = new StringBuffer();
        sb.append(warmUpReqCount);
        sb.append(SEPARATOR);
        sb.append(testReqCount);
        sb.append(SEPARATOR);
        sb.append(thinkTime);
        sb.append(SEPARATOR);
        sb.append(url);

        String result = sb.toString();

        if(MobiMeter.DEBUG)
            System.out.println("saving data record: " + result);

        return result.getBytes();
    }
}
