package me.nabil.demo.aspectdemo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PerformenceAspect {

	@Pointcut("execution(* *.*(..))")
	public void pointMethod() {

	}

	@Around("pointMethod()")
	public Object excute(ProceedingJoinPoint pjp) throws Throwable {

		long startTime = System.currentTimeMillis();
		try {
			return pjp.proceed();
		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("consume " + (endTime - startTime) + " ms");
		}

	}
}
