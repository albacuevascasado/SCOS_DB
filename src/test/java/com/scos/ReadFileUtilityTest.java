package com.scos;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReadFileUtilityTest {

    @Test
    public void testReadFileUtility() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        ReadFileUtility readFileUtility = new ReadFileUtility(applicationContext);
    }

}
