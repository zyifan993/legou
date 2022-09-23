package com.zyf.legou.order.service;

import com.zyf.legou.order.po.OrderItem;

import java.util.List;

public interface CartService {

    /**
     * 添加购物车
     * @param id  sku的Id
     * @param num 购买的数量
     * @param username  购买商品的用户名
     */
    void add(Long id,Integer num,String username);

    /**
     * 从redis中获取当前的⽤户的购物⻋的列表数据
     * @param username
     * @return
     */
    List<OrderItem> list(String username);
}
