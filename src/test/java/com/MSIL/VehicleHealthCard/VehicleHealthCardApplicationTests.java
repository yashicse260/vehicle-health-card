
package com.MSIL.VehicleHealthCard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = VehicleHealthCardApplication.class)
@ActiveProfiles("test")
class VehicleHealthCardApplicationTests {

    @Test
    void contextLoads() {
        // This will just verify that the application context starts
    }
}
