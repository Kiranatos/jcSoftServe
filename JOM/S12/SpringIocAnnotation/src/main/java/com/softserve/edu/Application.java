package com.softserve.edu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.edu.service.EuclideanService;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        //EuclideanService euclideanService = (EuclideanService) annotationConfigContext.getBean("EuclideanService");
        EuclideanService euclideanService = annotationConfigContext.getBean(EuclideanService.class);
        annotationConfigContext.close();
        System.out.println("GCD(18, 30) = " + euclideanService.calculateGCD("18", "30"));
    }

}
