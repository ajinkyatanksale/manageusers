package com.atcorp.manageusers.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.atcorp.manageusers.controller.*.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.atcorp.manageusers.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("ENTRY: [{}]", joinPoint.getSignature());
    }

    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("EXIT: [{}]", joinPoint.getSignature());
    }

    @Before("controllerMethods()")
    public void logBeforeC(JoinPoint joinPoint) {
        logger.info("ENTRY: [{}]", joinPoint.getSignature());
    }

    @After("controllerMethods()")
    public void logAfterC(JoinPoint joinPoint) {
        logger.info("EXIT: [{}]", joinPoint.getSignature());
    }
}

