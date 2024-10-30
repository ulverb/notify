package com.mend.metrics.annotations;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class CountRequestsAspect {

    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    @Autowired private MeterRegistry meterRegistry;

    @Around("@annotation(com.mend.metrics.annotations.CountRequests)")
    public Object countRequests(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        // Count requests and register metrics with method as a tag
        requestCounts.putIfAbsent(methodName, new AtomicInteger(0));
        requestCounts.get(methodName).incrementAndGet();

        meterRegistry.counter("http.requests.count", "method", methodName).increment();

        // Track execution time for each endpoint separately
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        meterRegistry.timer("http.endpoint.executionTime", "method", methodName)
                .record(executionTime, java.util.concurrent.TimeUnit.MILLISECONDS);

        return result;
    }

    // Method to retrieve individual request count
    public int getRequestCount(String methodName) {
        return requestCounts.getOrDefault(methodName, new AtomicInteger(0)).get();
    }
}
