package com.choimory.helloelasticsearch.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class ExecuteTimeLogAop {
    @Around("@annotation(ExecuteTimeLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch(joinPoint.toShortString());

        stopWatch.start(joinPoint.toShortString());
        Object result = joinPoint.proceed();
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());

        return result;
    }
}
