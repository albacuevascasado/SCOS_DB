package com.scos;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
    }

    public static <T> T getSpringBean(Class<T> objectClass) {
        return applicationContext.getBean(objectClass);
    }
}

