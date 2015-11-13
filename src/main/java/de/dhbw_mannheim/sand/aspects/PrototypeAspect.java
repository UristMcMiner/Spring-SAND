package de.dhbw_mannheim.sand.aspects;

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

import de.dhbw_mannheim.sand.annotations.Prototype;
import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.service.SessionService;

/**

Scheme for a general aspect, can be copied. 
You need the Annotation to use this, so you need to copy that too. (annotations/prototype)
On copy you need to change the Pointcuts to the name you gave it

**/
@Aspect
@Component
@Scope("request")
public class PrototypeAspect {
	
    static final Logger logger = LogManager.getLogger(Prototype.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SessionService sessionService;
	
	
	@Pointcut("within (@de.dhbw_mannheim.sand.annotations.Prototype *)")
	private void loggingType() {
	}

	@Pointcut("execution(@de.dhbw_mannheim.sand.annotations.Prototype * *.*(..))")
	private void loggingMethod() {
	}
	
	@Before("loggingMethod()")
	private void beforeMethod(JoinPoint joinpoint){
	}
	
	@Around("loggingMethod()")
	private void aroundMethod(ProceedingJoinPoint joinpoint){
		
	}
	@After("loggingMethod()")
	private void afterMethod(JoinPoint joinpoint){
		
	}
}
