package io.hhplus.tdd.point.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPoint {

    private Long id;
    private Long point;
    private Long updateMillis;

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0L, System.currentTimeMillis());
    }

    public void charge(long amount) {

        this.point += amount;
        this.updateMillis = System.currentTimeMillis();
    }
}
