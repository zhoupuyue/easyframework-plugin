package org.easyframework.plugin.spring;

import org.easyframework.web.mvc.DispatcherFilter;
import org.easyframework.web.mvc.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring对象工厂类
 * 
 * @author puyue.zhou
 *
 */
public class SpringObjectFactory implements ApplicationContextAware, ObjectFactory {

	private ApplicationContext applicationContext;

	public SpringObjectFactory() {
		DispatcherFilter.setObjectFactory(this);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Object getInstance(Class<?> clazz) {

		if (applicationContext.containsBean(clazz.getName())) {
			return applicationContext.getBean(clazz);
		}

		synchronized (clazz) {
			if (!applicationContext.containsBean(clazz.getName())) {
				DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
				BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
				beanFactory.registerBeanDefinition(clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
				return applicationContext.getBean(clazz);
			}
		}

		return applicationContext.getBean(clazz);

	}
}
