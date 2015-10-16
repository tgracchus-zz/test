import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Created by ulises on 16/10/15.
 */
public class Accu {


    @Test
    public void testName() throws Exception {

        Collector<PhysicalDevice, PhysicalDeviceAccumulator, LogicalDevice> collector = Collector.of(
                () -> new PhysicalDeviceAccumulator(),
                (pa, pd) -> pa.accumulation(pd),
                (left, right) -> left.combine(right),
                (pa) -> pa.build()
        );

        LogicalDevice logicalDevice =
                Stream.iterate(0, (actual) -> actual + 1)
                        .map((serial) -> new PhysicalDevice(serial, "10"))
                        .limit(10)
                        .collect(collector);

        Assert.assertEquals("10", logicalDevice.getId());
        Assert.assertEquals(10, logicalDevice.getSerials().size());

    }
}
