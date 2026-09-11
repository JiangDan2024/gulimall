package com.gulimall.order;

import com.gulimall.order.domain.Order;
import com.gulimall.order.domain.OrderOperateHistory;
import com.gulimall.order.service.IOrderOperateHistoryService;
import com.gulimall.order.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallOrderApplicationTests {


    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderOperateHistoryService orderOperateHistoryService;

    @Test
    void contextLoads() {

    }

}
