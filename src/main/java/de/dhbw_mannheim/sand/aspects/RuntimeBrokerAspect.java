package de.dhbw_mannheim.sand.aspects;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.annotations.RuntimeBroker;
import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.service.SessionService;

@Aspect
@Component
@Scope("request")
public class RuntimeBrokerAspect {
	
	long beforeTime;
	private static final Logger logger = LogManager.getLogger(RuntimeBroker.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SessionService sessionService;
	
	
	@Pointcut("within (@de.dhbw_mannheim.sand.annotations.RuntimeBroker *)")
	private void loggingType() {
	}

	@Pointcut("execution(@de.dhbw_mannheim.sand.annotations.RuntimeBroker * *.*(..))")
	private void loggingMethod() {
	}
	@Before("loggingMethod()")
	private void beforeMethod(JoinPoint joinpoint){
		beforeTime = System.currentTimeMillis();
	}
	@After("loggingMethod()")
	private void afterMethod(JoinPoint joinpoint){
		long elapsed = System.currentTimeMillis()-beforeTime;
		logger.info(joinpoint.toShortString() + " Method Runtime: "+ elapsed + "ms");
		
	}
}
