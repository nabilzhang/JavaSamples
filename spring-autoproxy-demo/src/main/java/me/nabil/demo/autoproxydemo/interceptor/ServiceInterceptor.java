package me.nabil.demo.autoproxydemo.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author zhangbi
 * @date 2014年10月13日下午2:33:40
 */
public class ServiceInterceptor implements MethodInterceptor {

    private final static Log log = LogFactory.getLog(ServiceInterceptor.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String className = "unknowClassName";
        String methodName = "unknowMethodName";
        Object[] methodParams = {};

        try {
            className = methodInvocation.getMethod().getDeclaringClass()
                    .getName();
            methodName = methodInvocation.getMethod().getName();
            methodParams = methodInvocation.getArguments();

        } catch (Throwable e) {
            log.warn("can not get class and method names", e);
        }

        System.out.println("start " + this + "," + className + "." + methodName
                + methodInvocation.getThis());
        long startTimestamp = System.currentTimeMillis();
        try {
            Object result = methodInvocation.proceed();
            System.out.println("end " + this + "," + className + "."
                    + methodName);
            long endTimestamp = System.currentTimeMillis();
            System.out.println(className + "." + methodName + "  cost"
                    + (endTimestamp - startTimestamp));
            return result;
        } catch (Throwable t) {

            throw t;
        }
    }

}
