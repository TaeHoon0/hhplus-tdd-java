package io.hhplus.tdd.point.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "point-policy")
public class PointPolicy {

    private final long maxBalance;

    public void validateMaxBalance(long currentPoint, long amount) {

        if(currentPoint + amount > maxBalance) {
            throw new IllegalArgumentException("보유 포인트 한도를 초과합니다.");
        }
    }
}
