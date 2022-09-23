package com.zyf.legou.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpand.starter.canal.annotation.*;
import com.zyf.legou.canal.client.CategoryClient;
import com.zyf.legou.item.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

//使用com.xpanl.canle构件监听canal服务，用来监听数据库的变化
@CanalEventListener
public class MyEventListener {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ListenPoint(destination = "example", schema = "legou", table = {"category_"}, eventType = {CanalEntry.EventType.UPDATE,CanalEntry.EventType.INSERT,CanalEntry.EventType.DELETE})
    public void onEvent4(CanalEntry.EventType eventType, CanalEntry.RowData rowData) throws JsonProcessingException {
        List<Category> list = categoryClient.list(new Category()); //数据库中最新的商品分类

        ObjectMapper objectMapper = new ObjectMapper();
        stringRedisTemplate.boundValueOps("cl").set(objectMapper.writeValueAsString(list));
    }



/*
    //当数据被添加的时候触发
    // CanalEntry.EventType eventType  监听到的操作的类型  INSERT  UPDATE ,DELETE ,CREATE INDEX ,GRAND
    // CanalEntry.RowData rowData 被修改的数据()
    @InsertListenPoint
    public void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        System.out.println("添加数据监听。。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }

    //当数据被更新的时候触发
    @UpdateListenPoint
    public void onEvent1(CanalEntry.RowData rowData) {
        //do something...
        System.out.println("修改数据监听。。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }
    // 当数据被删除的时候触发
    @DeleteListenPoint
    public void onEvent3(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        System.out.println("删除数据监听。。。。");
//        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        List<CanalEntry.Column> afterColumnsList = rowData.getBeforeColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }

    //自定义事件的触发
    // destination = "example" 指定某一个目的地 一定要和配置文件中的目录保持一致
    //schema = "canal-test" 要监听的数据库实例
    //table = {"t_user", "test_table"},   要监听的表
    // eventType = CanalEntry.EventType.UPDATE  要监听的类型
    @ListenPoint(destination = "example", schema = "legou", table = {"category_"}, eventType = {CanalEntry.EventType.UPDATE,CanalEntry.EventType.INSERT,CanalEntry.EventType.DELETE})
    public void onEvent4(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //do something...
        System.out.println("只监听分类。。。。");
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName()+":"+column.getValue());
        }
    }
*/

}
