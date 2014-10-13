package me.nabil.demo.autoproxydemo.autoproxy;

import org.springframework.beans.factory.BeanFactory;

/**
 *
 *
 * @author zhangbi
 * @date 2014年10月13日下午3:19:49
 */
public interface CustomProxyMatcher {
    /**
     * Get AOP intercepter instances according to given bean class and bean
     * name.
     * 
     * @param beanClass
     * @param beanName
     * @param beanFactory
     *            provide the intercepter instances
     * @return
     */
    @SuppressWarnings("rawtypes")
    Object[] proxy(Class beanClass, String beanName, BeanFactory beanFactory);
}
