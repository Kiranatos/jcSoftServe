package com.softserve.edu.service.impl;

import com.softserve.edu.service.ConvertServise;
import com.softserve.edu.service.EuclideanService;
import com.softserve.edu.service.OperationService;
import com.softserve.edu.service.OrderService;

public class EuclideanServiceImpl implements EuclideanService {

    private OperationService operationService;
    private ConvertServise convertServise;
    private OrderService orderService;

    public EuclideanServiceImpl(OperationService operationService) {
        this.operationService = operationService;
    }

    public void setConvertServise(ConvertServise convertServise) {
        this.convertServise = convertServise;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    
    public String calculateGCD(String arg0, String arg1) {
        String max = orderService.max(convertServise.validateDigits(arg0), convertServise.validateDigits(arg1));
        String min = orderService.min(convertServise.validateDigits(arg0), convertServise.validateDigits(arg1));
        while (!orderService.isNull(min)) {
            String temp = operationService.operation(max, min);
            max = orderService.max(min, temp);
            min = orderService.min(min, temp);
        }
        return max;
    }

}
