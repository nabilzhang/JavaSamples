package me.nabil.demo.autoproxydemo.autoproxy;

/**
 *
 *
 * @author zhangbi
 * @email zhangbi@baidu.com
 * @date 2014年10月13日下午3:18:42
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

public abstract class MultiBeansAutoProxyCreator extends
        AbstractAutoProxyCreator {

    /**
     * 
     */
    private static final long serialVersionUID = -8314890857296261471L;

    private CustomProxyMatcher customProxyFinder;

    protected abstract Map<String, String> getInterceptorsMapping();

    /**
     * Return if the given bean name matches the mapped name.
     * <p>
     * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
     * as well as direct equality. Can be overridden in subclasses.
     * 
     * @param beanName
     *            the bean name to check
     * @param mappedName
     *            the name in the configured list of names
     * @return if the names match
     * @see org.springframework.util.PatternMatchUtils#simpleMatch(String,
     *      String)
     */
    @SuppressWarnings("rawtypes")
    protected abstract boolean isMatch(Class beanClass, String beanName,
            String mappedName);

    @SuppressWarnings("rawtypes")
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass,
            String beanName, TargetSource customTargetSource)
            throws BeansException {
        // wrap custom proxy support
        Object[] customerInterceptors = null;
        if (customProxyFinder != null) {
            customerInterceptors = customProxyFinder.proxy(beanClass, beanName,
                    getBeanFactory());
        }

        Object[] interceptors = getInterceptors(beanClass, beanName);
        if (ArrayUtils.isEmpty(customerInterceptors)
                && ArrayUtils.isEmpty(interceptors)) {
            // no intercepter available
            return DO_NOT_PROXY;
        }

        // no customer intercepters
        if (ArrayUtils.isEmpty(customerInterceptors)) {
            return interceptors;
        }

        if (ArrayUtils.isEmpty(interceptors)) {
            return customerInterceptors;
        }
        // else merge all
        return ArrayUtils.addAll(customerInterceptors, interceptors);
    }

    @SuppressWarnings("rawtypes")
    private Object[] getInterceptors(Class beanClass, String beanName) {
        List<Object> interceptors = new LinkedList<Object>();
        Set<Map.Entry<String, String>> entrySet = getInterceptorsMapping()
                .entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (StringUtils.isEmpty(entry.getValue())) {
                continue;
            }
            String[] beanNameMappings = StringUtils.split(entry.getKey(), ",");
            String[] interceptorBeanNames = StringUtils.split(entry.getValue(),
                    ",");

            List<Object> beans = getInterceptors(beanNameMappings,
                    interceptorBeanNames, beanClass, beanName);
            if (beans != null) {
                interceptors.addAll(beans);
            }
        }
        if (CollectionUtils.isEmpty(interceptors)) {
            return null;
        }

        return interceptors.toArray(new Object[interceptors.size()]);
    }

    private List<Object> getInterceptors(String[] beanNameMappings,
            String[] interceptorBeanNames, Class<?> beanClass, String beanName) {
        if (beanNameMappings != null) {
            for (String beanNameMapping : beanNameMappings) {
                if (FactoryBean.class.isAssignableFrom(beanClass)) {
                    if (!beanNameMapping
                            .startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
                        continue;
                    }
                    beanNameMapping = beanNameMapping
                            .substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
                }
                if (isMatch(beanClass, beanName, beanNameMapping)) {
                    List<Object> beans = getBeans(interceptorBeanNames);
                    if (CollectionUtils.isEmpty(beans)) {
                        return null;
                    }
                    return beans;
                }
            }
        }
        return null;
    }

    private List<Object> getBeans(String[] beanNames) {
        List<Object> beans = new ArrayList<Object>(beanNames.length);
        for (String beanName : beanNames) {
            Object bean = getBeanFactory().getBean(beanName);
            beans.add(bean);
        }
        return beans;
    }

    public void setCustomProxyFinder(CustomProxyMatcher customProxyFinder) {
        this.customProxyFinder = customProxyFinder;
    }
}
