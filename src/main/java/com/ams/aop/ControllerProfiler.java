package com.ams.aop;

import com.athaydes.javanna.Javanna;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${ams.monitoring.enabled}")
public class ControllerProfiler {

    private static Map<String, Object> timedAnnotationData = new HashMap<>();

    static {
        // use percentile data of p90, p95, p99.99
        double[] percentiles = {0.90, 0.95, 0.9999};
        // set histogram to true
        timedAnnotationData.put("histogram", true);
        // set percentile
        timedAnnotationData.put("percentiles", percentiles);
    }

    @Autowired
    private MonitoringTimedAspect timedAspect;

    private static final MonitoringTimed timed =
            Javanna.createAnnotation(MonitoringTimed.class, timedAnnotationData);

    private static final Logger logger = LoggerFactory.getLogger(ControllerProfiler.class);

    @Pointcut("execution(* com.ams.controller..*.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        return timedAspect.timeThisMethod(pjp, timed);
    }
}
