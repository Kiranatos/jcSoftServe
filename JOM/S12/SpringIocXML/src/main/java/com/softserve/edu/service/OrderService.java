package com.softserve.edu.service;

public interface OrderService {

    void setDigitalService(DigitalService digitalService);
    
    String max(String arg0, String arg1);

    String min(String arg0, String arg1);

    boolean isNull(String arg);
    
}
