package de.dhbw_mannheim.sand.aspects;

import java.nio.file.AccessDeniedException;
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

import de.dhbw_mannheim.sand.annotations.Prototype;
import de.dhbw_mannheim.sand.aspects.authorization.AuthorizationChecker;
import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.Login;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.service.RoleService;
import de.dhbw_mannheim.sand.service.RoleServiceImpl;
import de.dhbw_mannheim.sand.service.SessionService;
import de.dhbw_mannheim.sand.service.UserService;

/**

Prototype for a LoggedIn Aspect with Authorization Control

**/
@Aspect
@Component
@Scope("request")
public class PrototypeAspect {
	
    static final Logger logger = LogManager.getLogger(Prototype.class);
    
    @Autowired
    private UserService userService;
	

    private RoleService roleService = new RoleServiceImpl();
    
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private AuthorizationChecker userControllerAuthorizationChecker;
	@Autowired
	private AuthorizationChecker studentControllerAuthorizationChecker;
	@Autowired
	private AuthorizationChecker researchProjectOfferControllerAuthorizationChecker;
	@Autowired
	private AuthorizationChecker sessionControllerAuthorizationChecker;
	
	private AuthorizationChecker authorizationChecker;
	
	@Pointcut("within (@de.dhbw_mannheim.sand.annotations.Prototype *)")
	private void loggingType() {
	}

	@Pointcut("execution(@de.dhbw_mannheim.sand.annotations.Prototype * *.*(..))")
	//@Pointcut("execution(* de.dhbw_mannheim.sand.controller.*.*(..))")
	private void loggingMethod() {
	}

	@Around("loggingMethod()")
	private Object aroundMethod(ProceedingJoinPoint joinpoint) throws Throwable{
		String targetClass = joinpoint.getTarget().getClass().getSimpleName();
		if (targetClass.equals("SessionController")) {
			return joinpoint.proceed();
		}

		Object[] args = joinpoint.getArgs();
		String[] parts = joinpoint.toShortString().split("Controller");
		String targetMethod = parts[1].substring(1,parts[1].length() - 5);
		logger.info("Method / Class: "+ targetMethod + " " + targetClass);

		User user = null;
		
		String userLogin = "";
			
			String authorization = (String) args[0];
			try{
			user = sessionService.getSessionById(UUID.fromString(authorization)).getUser();
			}
			catch(Exception e){
				//Authorization String is not valid/No valid Session is found corresponding to that entity
				return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
			}
			//Gets the User Object which is attached to the Request
			userLogin = user.getLogin();
			
			if (targetClass.equals("UserController")) 
				authorizationChecker = userControllerAuthorizationChecker;
			else if ( targetClass.equals("ResearchProjectOfferController"))
				authorizationChecker = researchProjectOfferControllerAuthorizationChecker;
			else if ( targetClass.equals("SessionController"))
				authorizationChecker = sessionControllerAuthorizationChecker;
			else if ( targetClass.equals("StudentController"))
				authorizationChecker = studentControllerAuthorizationChecker;
			boolean authorized= true;//Change to false as soon as methods are implemented
			Object param = args[1];
			System.out.println(targetMethod);
			switch (targetMethod) {
			//CheckOther is going to be used by the methods itself
				case "getById": 
				case "getStudentById":
				case "getUserById":
					authorized = authorizationChecker.checkGetById(user, (Integer) param);
					break;
				case "add":
					authorized = authorizationChecker.checkAdd(user, (LazyObject)param);
					break;
				case "delete":
					authorized = authorizationChecker.checkDelete(user, (Integer) param);
					break;
				case "edit":
					authorized = authorizationChecker.checkUpdate(user, (LazyObject)param);
					break;
			}
			
		if (!authorized) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		return joinpoint.proceed();
		
	}
}
