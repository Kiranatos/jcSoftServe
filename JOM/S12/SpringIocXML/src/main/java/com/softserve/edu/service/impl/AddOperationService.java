package com.softserve.edu.service.impl;

import com.softserve.edu.service.DigitalService;
import com.softserve.edu.service.OperationService;

public class AddOperationService implements OperationService {

    private DigitalService digitalService;
    
    public AddOperationService(DigitalService digitalService) {
        this.digitalService = digitalService;
    }
    
    public String operation(String arg0, String arg1) {
        return String.valueOf(digitalService.toInt(arg0) + digitalService.toInt(arg1));
    }

}
