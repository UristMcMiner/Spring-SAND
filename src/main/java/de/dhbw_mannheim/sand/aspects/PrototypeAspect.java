package de.dhbw_mannheim.sand.aspects;

import java.nio.file.AccessDeniedException;

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
	
	
	
	@Pointcut("within (@de.dhbw_mannheim.sand.annotations.Prototype *)")
	private void loggingType() {
	}

	@Pointcut("execution(* de.dhbw_mannheim.sand.controller.*.*(..))")
	private void loggingMethod() {
	}

	@Around("loggingMethod()")
	private Object aroundMethod(ProceedingJoinPoint joinpoint) throws Throwable{
		Object[] args = joinpoint.getArgs();
		logger.info("Calling Method: " + joinpoint.toShortString());
		
		boolean isAuthorized = false;
		
		User user = null;
		
		String userLogin = "";
		try{
			Login login = (Login)args[0];
			//Gets the User Object which is attached to the Request
			user = userService.getUserByLoginAndPassword(login.getLogin(), login.getPassword());
			userLogin = user.getLogin();
			
			if(user.isAdmin()){
				logger.info("User " + user.getLogin() + " is Admin");
				isAuthorized = AdminCheck(joinpoint.toShortString());//Check if admin is eligible
			}
			if(user.isSecretary()){
				logger.info("User "+ user.getLogin() +" is Secretary");
				if(!isAuthorized)isAuthorized = SecretaryCheck(joinpoint.toShortString());//Check if secretary is eligible
			}
			if(user.isStudent()){
				logger.info("User "+ user.getLogin() +" is Student");
				if(!isAuthorized)isAuthorized = StudentCheck(joinpoint.toShortString());;//Check if student is eligible
			}
			if(user.isSupervisor()){
				logger.info("User "+ user.getLogin() +" is Supervisor");
				if(!isAuthorized)isAuthorized = SupervisorCheck(joinpoint.toShortString());//Check if supervisor is eligible
			}
			if(user.isTeacher()){
				logger.info("User "+ user.getLogin() +" is Teacher");
				if(!isAuthorized)isAuthorized = TeacherCheck(joinpoint.toShortString());//Check if teacher is eligible 
			}
		}catch(Exception e){
			//e.printStackTrace();
			logger.info("No valid Credentials passed: "+args[0]);
		}
		if(!isAuthorized){
			if(!GenericCheck(joinpoint.toShortString())){//Checks if the method is publicly available
			throw new AccessDeniedException("User "+ userLogin +" not Authorized");}
			else {logger.info("Access to public method " + joinpoint.toShortString() + " by anonymous user");
			}
		}
		return joinpoint.proceed();
		
	}
	
	private boolean AdminCheck(String methodName){
		return true;
	}

	private boolean SecretaryCheck(String methodName){
		return true;
	}
	
	private boolean StudentCheck(String methodName){
		return true;
	}

	private boolean SupervisorCheck(String methodName){
		return true;
	}

	private boolean TeacherCheck(String methodName){
		return true;
	}
	
	private boolean GenericCheck(String methodName){
		return true;
	}
}
