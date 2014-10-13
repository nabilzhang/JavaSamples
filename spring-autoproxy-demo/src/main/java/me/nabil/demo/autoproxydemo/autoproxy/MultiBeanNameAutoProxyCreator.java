package me.nabil.demo.autoproxydemo.autoproxy;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.aop.TargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

public class MultiBeanNameAutoProxyCreator extends MultiBeansAutoProxyCreator
        implements InitializingBean {

    /**
     * 
     */
    private static final long serialVersionUID = 5459930508545123655L;

    private Map<String, String> beanNamesMapping;

    /**
     * set multi bean names and intercepter mappings. <br>
     * multi bean name and intercepter use comma to split<br>
     * eg. <br>
     * &lt;Map&gt;<br>
     * &lt;entry key="*Service,*Facade"
     * value="transactionInterceptor,monitorInterceptor"/&gt;<br>
     * &lt;/Map&gt;<br>
     * 
     * @param beanNamesMapping
     */
    public void setBeanNamesMapping(Map<String, String> beanNamesMapping) {
        Assert.notEmpty(beanNamesMapping,
                "property 'beanNamesMapping' is empty");
        this.beanNamesMapping = beanNamesMapping;
    }

    public void afterPropertiesSet() throws Exception {
        super.setInterceptorNames(new String[0]); // dummy interceptor names
        Assert.notEmpty(beanNamesMapping,
                "property 'beanNamesMapping' is empty");
    }

    @Override
    public void setInterceptorNames(String[] interceptorNames) {
        throw new UnsupportedOperationException(
                "please use beanNamesMapping to specified interceptors");
    }

    @Override
    protected Map<String, String> getInterceptorsMapping() {
        return beanNamesMapping;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected boolean isMatch(Class beanClass, String beanName,
            String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass,
            String beanName, TargetSource targetSource) throws BeansException {
        Object[] result = super.getAdvicesAndAdvisorsForBean(beanClass,
                beanName, targetSource);
        if (!ArrayUtils.isEmpty(result)) {
            logger.info("Bean name '" + beanName + "' is intercepted by "
                    + Arrays.toString(result));
        }
        return result;
    }
}
