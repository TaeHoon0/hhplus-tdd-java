package io.hhplus.tdd.point.domain.repository;

import io.hhplus.tdd.point.domain.entity.UserPoint;

public interface UserPointRepository {

    UserPoint selectById(Long id);

    UserPoint insertOrUpdate(long id, long amount);
}
