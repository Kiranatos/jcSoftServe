package com.softserve.edu;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.softserve.edu.service.EuclideanService;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlConfigContext = new ClassPathXmlApplicationContext("config.xml");
        //EuclideanService euclideanService = (EuclideanServiceImpl) xmlConfigContext.getBean("euclideanService");
        EuclideanService euclideanService = xmlConfigContext.getBean(EuclideanService.class);
        xmlConfigContext.close();
        System.out.println("GCD(18, 30) = " + euclideanService.calculateGCD("18", "30"));
    }

}
