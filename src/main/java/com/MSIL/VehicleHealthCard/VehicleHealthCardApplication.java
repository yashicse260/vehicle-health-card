
package com.MSIL.VehicleHealthCard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VehicleHealthCardApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VehicleHealthCardApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(VehicleHealthCardApplication.class, args);
    }
}
