package com.bookstoreapplication.bookstore.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.bookstoreapplication.bookstore..*(..))  " +
            "&& !execution(* com.bookstoreapplication.bookstore.config..*(..))" +
            "&& !execution(* com.bookstoreapplication.bookstore.aop..*(..))" +
            "&& !execution(* com.bookstoreapplication.bookstore.auth..*(..))")
    private void anyPublicMethod(){
    }

    @Before("anyPublicMethod()")
    public void beforeAnyPublicMethod(JoinPoint joinPoint){
        logger.info("###### before :: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " :: "+ joinPoint.getSignature().getName());
    }

    @After("anyPublicMethod()")
    public void afterAnyPublicMethod(JoinPoint joinPoint){
        logger.info("###### after :: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " :: "+ joinPoint.getSignature().getName());
    }

    @Around("execution(* *..*Controller.*(..))")
    public Object aroundControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.nanoTime();
        Object proceed =  proceedingJoinPoint.proceed();
        logger.info("###### time " + ((System.nanoTime() - start)/1000000) + " ms :: " + proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName() + " :: " + proceedingJoinPoint.getSignature().getName());
        return proceed;
    }

}
