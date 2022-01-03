package com.softserve.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.softserve.edu.service.DigitalService;
import com.softserve.edu.service.OperationService;

@Component
@Qualifier("ResOperationService")
public class ResOperationService implements OperationService {

    private DigitalService digitalService;

    @Autowired
    public ResOperationService(DigitalService digitalService) {
        this.digitalService = digitalService;
    }

    public String operation(String arg0, String arg1) {
        return String.valueOf(digitalService.toInt(arg0) % digitalService.toInt(arg1));
    }

}
