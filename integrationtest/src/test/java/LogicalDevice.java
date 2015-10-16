import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 16/10/15.
 */
public class LogicalDevice {

    private final String id;
    private final List<String> serials;

    public LogicalDevice(String id, List<String> serials) {
        this.id = id;
        this.serials = new ArrayList<>(serials);
    }

    public List<String> getSerials() {
        return serials;
    }

    public String getId() {
        return id;
    }
}
