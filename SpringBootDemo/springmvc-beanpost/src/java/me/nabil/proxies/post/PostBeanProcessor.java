package me.nabil.proxies.post;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class PostBeanProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object beanClass, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization" + beanName + ":" + beanClass);
		return beanClass;
	}

	@Override
	public Object postProcessBeforeInitialization(Object beanClass, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization" + beanName + ":" + beanClass);
		return beanClass;
	}

}
