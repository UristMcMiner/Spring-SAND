package de.dhbw_mannheim.sand.aspects;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.service.SessionService;

@Aspect
@Component
@Scope("request")
public class LoggedInAspect {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SessionService sessionService;
	
	
	@Pointcut("within (@de.dhbw_mannheim.sand.annotations.LoggedIn *)")
	private void loggingType() {
	}

	@Pointcut("execution(@de.dhbw_mannheim.sand.annotations.LoggedIn * *.*(..))")
	private void loggingMethod() {
	}

	@Around("loggingMethod()")
	public Object doDemo1(ProceedingJoinPoint joinPoint) {
		System.out.println("######## Demo ++++++++"+joinPoint.toString());
		Object result = null;
		if (checkAuthorization()) {
			try {
				result = joinPoint.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
				result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			result = new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		return result;
	}
	
	private boolean checkAuthorization() {
		UUID uuid = UUID.fromString(request.getHeader("authorization"));
		Session session = sessionService.getSessionById(uuid);
		return (session != null);
	}

	@Before("loggingType()")
	public void doDemo2(JoinPoint joinPoint) {
		System.out.println("######## Demo --------"+joinPoint.toString());
	}
	
}
