package com.softserve.edu.service.impl;

import com.softserve.edu.service.DigitalService;
import com.softserve.edu.service.OrderService;

public class OrderServiceImpl implements OrderService {

    private DigitalService digitalService;
    
    public void setDigitalService(DigitalService digitalService) {
        this.digitalService = digitalService;
    }
    
    public String max(String arg0, String arg1) {
        return digitalService.toInt(arg0) > digitalService.toInt(arg1) ? arg0 : arg1;
    }

    public String min(String arg0, String arg1) {
        return digitalService.toInt(arg0) < digitalService.toInt(arg1) ? arg0 : arg1;
    }
    
    public boolean isNull(String arg) {
        return digitalService.toInt(arg) == 0;
    }

}
