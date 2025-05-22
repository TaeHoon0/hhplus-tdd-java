package io.hhplus.tdd.point.infrastructure.lock;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
@ConfigurationProperties(prefix = "lock")
public class LockManager {

    private final long timeOut;
    private final long retrySleep;
    private final ConcurrentMap<Long, ReentrantLock> pointLockManager = new ConcurrentHashMap<>();

    public void getLock(long userId) throws InterruptedException {

        ReentrantLock lock = pointLockManager.computeIfAbsent(userId, k -> new ReentrantLock());

        for (int i = 0; i < 3; i++ ) {

            if (lock.tryLock(timeOut, TimeUnit.MILLISECONDS))
                return;

            Thread.sleep(retrySleep);
        }

        throw new IllegalArgumentException("락 획득 실패");
    }

    public void releaseLock(long userId) {

        ReentrantLock lock = pointLockManager.get(userId);

        if (lock != null && lock.isHeldByCurrentThread())
            lock.unlock();

    }
}
