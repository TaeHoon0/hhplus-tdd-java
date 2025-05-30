package io.hhplus.tdd;

import io.hhplus.tdd.point.domain.PointPolicy;
import io.hhplus.tdd.point.infrastructure.lock.LockManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({PointPolicy.class, LockManager.class})
public class TddApplication {

    public static void main(String[] args) {
        SpringApplication.run(TddApplication.class, args);
    }
}
