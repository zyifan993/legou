package com.zyf.legou.order.service.impl;

import com.zyf.legou.item.po.Sku;
import com.zyf.legou.item.po.Spu;
import com.zyf.legou.order.client.SkuClient;
import com.zyf.legou.order.client.SpuClient;
import com.zyf.legou.order.po.OrderItem;
import com.zyf.legou.order.service.CartService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Long id, Integer num, String username) {
        //删除商品购物车信息
        if (num <= 0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            return;
        }

        //根据sku的id，获取sku的数据
        Sku sku = skuClient.edit(id);
        if (null != sku){
            //2.根据sku的数据对象，获取sku对象的spu数据
            Long spuId = sku.getSpuId();
            Spu spu = spuClient.edit(spuId);

            //3.将数据存储到 购物⻋对象(order_item)中
            OrderItem orderItem = new OrderItem();
            orderItem.setCategoryId1(spu.getCid1());
            orderItem.setCategoryId2(spu.getCid2());
            orderItem.setCategoryId3(spu.getCid3());
            orderItem.setSpuId(spu.getId());
            orderItem.setSkuId(id);
            orderItem.setName(sku.getTitle());//商品的名称 sku的名称
            orderItem.setPrice(sku.getPrice());
            orderItem.setNum(num);//购买的数量
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());//单价* 数量
            orderItem.setImage(sku.getImages());//图片地址
            //4.数据添加到redis中 key:⽤户名 field:sku的ID value:购物⻋数据(order_item)
            redisTemplate.boundHashOps("Cart_" + username).put(id,orderItem);/// hset key field value hget key field
        }

    }

    @Override
    public List<OrderItem> list(String username) {
        List<OrderItem> orderItemList = redisTemplate.boundHashOps("Cart_" + username).values();
        return orderItemList;
    }
}
