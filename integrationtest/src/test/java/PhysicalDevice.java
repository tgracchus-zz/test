/**
 * Created by ulises on 16/10/15.
 */
public class PhysicalDevice {

    private final String logicalId;
    private final int serial;

    public PhysicalDevice(int serial, String logicalId) {
        this.serial = serial;
        this.logicalId = logicalId;
    }

    public int getSerial() {
        return serial;
    }

    public String getLogicalId() {
        return logicalId;
    }
}
