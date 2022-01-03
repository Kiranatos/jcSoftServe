package com.softserve.edu.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.softserve.edu.service.DigitalService;

@Component
@Scope("prototype")
public class DigitalServiceImpl implements DigitalService {
    private final String ERROR_MESSAGE = "Invalid argument %s, message %s";

    //private LocalDateTime currentTime;
    private long currentTime;
    
    public DigitalServiceImpl() {
        //currentTime = LocalDateTime.now();
        currentTime = System.currentTimeMillis();
    }
    
    public int toInt(String arg) {
        //System.out.println("currentTime = " + currentTime);
        int result = 0;
        try {
            result = Integer.valueOf(arg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE, arg, e.toString()));
        }
        return Math.abs(result);
    }
}
