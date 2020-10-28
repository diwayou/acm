package com.diwayou.spring.ioc;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng
 * @date 2020/10/28
 */
@Aspect
@Configuration
@Slf4j
public class StoreAop {

    @Pointcut("execution(* com.diwayou.spring.ioc.StoreManager.*(..))")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void logStart(JoinPoint joinPoint) {
        log.info("除法运行....参数列表是:{}", joinPoint.getArgs());
    }

    @After("pointCut()")
    public void logEnd() {
        log.info("除法结束......");
    }

    @AfterReturning(pointcut = "pointCut()", returning = "retVal")
    public void logReturn(Object retVal) {
        log.info("除法正常返回......运行结果是:{}", retVal);
    }

    @AfterThrowing("pointCut()")
    public void logException() {
        log.info("运行异常......异常信息是:{}");
    }

    @Around("pointCut()")
    public Object Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("@Arount:执行目标方法之前...,args={}", proceedingJoinPoint.getArgs());
        Object obj = proceedingJoinPoint.proceed();//相当于开始调div地
        log.info("@Arount:执行目标方法之后...");
        return obj;
    }

}
