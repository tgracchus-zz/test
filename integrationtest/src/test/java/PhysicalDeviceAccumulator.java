import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 16/10/15.
 */
public class PhysicalDeviceAccumulator {

    private String logicalId;
    private final List<String> serials;

    public PhysicalDeviceAccumulator() {
        this.serials = new ArrayList<>();
    }

    public PhysicalDeviceAccumulator combine(PhysicalDeviceAccumulator pa) {
        this.serials.addAll(pa.getSerials());
        return this;
    }

    protected List<String> getSerials() {
        return serials;
    }

    public void accumulation(PhysicalDevice physicalDevice) {
        logicalId = physicalDevice.getLogicalId();
        serials.add(String.valueOf(physicalDevice.getSerial()));
    }

    public LogicalDevice build() {
        return new LogicalDevice(logicalId, serials);
    }


}
