package com.pes.mob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import com.pes.mob.controller.YourAnnotation;

@Aspect
public class YourAspect {

	String TAG = "[RUNTIME DOKIMOS INFO:]";
    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut will look for the @YourAnnotation
    @Pointcut("@annotation(yourAnnotationVariableName)")
    public void annotationPointCutDefinition(YourAnnotation yourAnnotationVariableName){
    }

    //Defines a pointcut that we can use in the @Before,@After, @AfterThrowing, @AfterReturning,@Around specifications
    //The pointcut is a catch-all pointcut with the scope of execution
    @Pointcut("execution(* *(..))")
    public void atExecution(){}

    @Before("annotationPointCutDefinition(yourAnnotationVariableName) && atExecution()")
    //JointPoint = the reference of the call to the method
    public void bef(JoinPoint pointcut, YourAnnotation yourAnnotationVariableName){
        //Just prints new lines after each method that's executed in
    	System.out.print(TAG);
    }
    
    //Defines a pointcut where the @YourAnnotation exists
    //and combines that with a catch-all pointcut with the scope of execution
    @Around("annotationPointCutDefinition(yourAnnotationVariableName) && atExecution()")
    //ProceedingJointPoint = the reference of the call to the method.
    //The difference between ProceedingJointPoint and JointPoint is that a JointPoint can't be continued (proceeded)
    //A ProceedingJointPoint can be continued (proceeded) and is needed for an Around advice
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, YourAnnotation yourAnnotationVariableName) throws Throwable {
        if(yourAnnotationVariableName.isRun()) {
            //Default Object that we can use to return to the consumer
            Object returnObject = null;

            try {
                //System.out.println("YourAspect's aroundAdvice's body is now executed Before yourMethodAround is called.");
                //We choose to continue the call to the method in question
                returnObject = joinPoint.proceed();
                //If no exception is thrown we should land here and we can modify the returnObject, if we want to.
            } catch (Throwable throwable) {
                //Here we can catch and modify any exceptions that are called
            	System.out.println(TAG);
            	System.out.println(TAG+"This class has thrown a NullPointerException!");
            	System.out.println(TAG);
            	System.out.println(TAG+"Trying to reload class");
            	replaceClass();
            	System.out.println(TAG+"Wait for 2 seconds(time to replace class) and proceed again");
            	Thread.sleep(2000);
            	returnObject = joinPoint.proceed();
            	

            } finally {
                //If we want to be sure that some of our code is executed even if we get an exception
                //System.out.println("YourAspect's aroundAdvice's body is now executed After yourMethodAround is called.");
            }
            return returnObject;
        }
        else{
            return joinPoint.proceed();
        }
    }

    private void replaceClass() {
    	File classactual = new File("C:/resilient/dynamic/mobservice/classes/com/pes/mob/controller/FourSquareController.class");
		File classnew = new File("C:/VersionRepository/v2/mobservice/classes/com/pes/mob/controller/FourSquareController.class");
		//System.out.println("Files created");
		if(classactual!=null && classnew != null){
			//System.out.println("Files not null");
			boolean isTwoEqual = false; //FileUtils.contentEquals(classactual, classnew);
			if(!isTwoEqual){
				//System.out.println("Files diferent");
				try {
					Files.copy(classnew.toPath(),classactual.toPath(),StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(TAG+"Class reloaded successfully");
			}
			else{
				System.out.println(TAG+"Files equals, no resilience applied");		
			}
		}
		
	}

	@After("annotationPointCutDefinition(yourAnnotationVariableName) && atExecution()")
    //JointPoint = the reference of the call to the method
    public void af(JoinPoint pointcut, YourAnnotation yourAnnotationVariableName){
        //Just prints new lines after each method that's executed in
		//System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.print(TAG);
    }
}
