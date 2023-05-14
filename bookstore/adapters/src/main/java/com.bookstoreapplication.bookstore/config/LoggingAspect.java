package com.bookstoreapplication.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
class LoggingAspect {

    @SuppressWarnings("EmptyMethod")
    @Pointcut("execution(* bookstore..*(..))  " +
            "&& !execution(* bookstore.config..*(..))" +
            "&& !execution(* bookstore.auth..*(..))" +
            "&& !execution(* bookstore.exception_handler..*(..))" +
            "&& !execution(* *..*Config.*(..))" /*+
            "&& !execution(* *..*Repository.*(..))"*/)
    private void anyPublicMethod(){
    }

    @Before("anyPublicMethod()")
    public void beforeAnyPublicMethod(JoinPoint joinPoint){
        log.info("###### before :: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " :: "+ joinPoint.getSignature().getName());
    }

    @After("anyPublicMethod()")
    public void afterAnyPublicMethod(JoinPoint joinPoint){
        log.info("###### after :: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " :: "+ joinPoint.getSignature().getName());
    }

    @Around("execution(* *..*Controller.*(..))")
    public Object aroundControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed =  proceedingJoinPoint.proceed();
        log.info("###### time " + (System.currentTimeMillis() - start) +
                " ms :: " + proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName() +
                " :: " + proceedingJoinPoint.getSignature().getName());
        return proceed;
    }

}
