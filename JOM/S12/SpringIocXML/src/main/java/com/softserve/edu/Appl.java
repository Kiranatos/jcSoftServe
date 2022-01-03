package com.softserve.edu;

import com.softserve.edu.service.DigitalService;
import com.softserve.edu.service.EuclideanService;
import com.softserve.edu.service.OperationService;
import com.softserve.edu.service.OrderService;
import com.softserve.edu.service.impl.ConvertServiseImpl;
import com.softserve.edu.service.impl.DigitalServiceImpl;
import com.softserve.edu.service.impl.EuclideanServiceImpl;
import com.softserve.edu.service.impl.OrderServiceImpl;
import com.softserve.edu.service.impl.ResOperationService;

public class Appl {
    public static void main(String[] args) {
        DigitalService digitalService = new DigitalServiceImpl();
        OperationService operationService = new ResOperationService(digitalService);
        OrderService orderService = new OrderServiceImpl();
        orderService.setDigitalService(digitalService);
        EuclideanService euclideanService = new EuclideanServiceImpl(operationService);
        euclideanService.setConvertServise(ConvertServiseImpl.getConvertServise());
        euclideanService.setOrderService(orderService);
        System.out.println("GCD(18, 30) = " + euclideanService.calculateGCD("18a", "30"));
    }
}
