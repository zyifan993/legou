package com.zyf.legou.order.service;

import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.order.po.Order;

public interface OrderService extends ICrudService<Order> {

    /**
     * 添加订单
     */
    public void add(Order order);
}
