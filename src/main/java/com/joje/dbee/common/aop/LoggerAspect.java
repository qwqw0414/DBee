package com.joje.dbee.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스 흐름을 로깅하기 위한 AOP
 *
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect {

	private static final int FILTER_SIZE = "com.joje.dbee.".length();

//	@Pointcut("execution(* com.joje.dbee..*.*(..))")
	@Pointcut("execution(* com.joje.dbee..*.*(..)) && !execution(* com.joje.dbee.common..*.*(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();
		String type = signature.getDeclaringTypeName();
		String methodName = signature.getName();

//		공통 경로 제외
		type = type.substring(FILTER_SIZE);

		log.debug(">> {}.{} >>", type, methodName);
		Object obj = joinPoint.proceed();
		log.debug("<< {}.{} <<", type, methodName);

		return obj;
	}

}
