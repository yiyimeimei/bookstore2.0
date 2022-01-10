package com.reins.bookstore.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.reins.bookstore.constant.Constant;
import com.reins.bookstore.entity.Book;
import com.reins.bookstore.entity.OrderInQueue;
import com.reins.bookstore.entity.OrderInfo;
import com.reins.bookstore.entity.OrderItem;
import com.reins.bookstore.service.OrderInfoService;
import com.reins.bookstore.utils.msgutils.Message;
import com.reins.bookstore.utils.msgutils.MessageUtil;
import com.reins.bookstore.utils.sessionutils.SessionUtil;
import net.sf.json.JSONObject;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Scope("session")
public class OrderInfoController {

    OrderInfoService orderInfoService;

    private Integer userId = SessionUtil.getUserId();

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    void setOrderService(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    @RequestMapping("/placeOrder")
    Message placeOrder(@RequestBody JSONObject orderItems)
    {
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        System.out.println("添加新的订单到待处理队列");
        OrderInQueue mOrderInQueue = new OrderInQueue(userId, orderItems);
        //System.out.println(mOrderInQueue);
        jmsTemplate.convertAndSend("orderQueue", mOrderInQueue);
        return MessageUtil.createMessage(MessageUtil.PURCHASE_SUCCESS_CODE, MessageUtil.PURCHASE_SUCCESS_MSG);
        //System.out.println(orderItems.toString());
        /*if (orderInfoService.placeOrder(orderItems, userId))
            return MessageUtil.createMessage(MessageUtil.PURCHASE_SUCCESS_CODE, MessageUtil.PURCHASE_SUCCESS_MSG);
        return MessageUtil.createMessage(MessageUtil.PURCHASE_FAIL_CODE, MessageUtil.PURCHASE_FAIL_MSG);*/

    }

    @RequestMapping("/getAllOrders")
    List<OrderInfo> getAllOrders()
    {
        String json = JSON.toJSONString(orderInfoService.getAllOrders(userId), SerializerFeature.DisableCircularReferenceDetect);
        List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
        return newList;
    }

    @RequestMapping("/admin/getAllOrders")
    List<OrderInfo> getAllOrdersForManager()
    {
        if(SessionUtil.getUserType() == Constant.MANAGER)
        {
            String json = JSON.toJSONString(orderInfoService.getAllOrdersForManager(), SerializerFeature.DisableCircularReferenceDetect);
            List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
            return newList;
        }
        else
            return null;

    }

    @RequestMapping("/searchOrders")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    List<OrderInfo> searchOrders(@RequestBody ArrayList<Date> time)
    {
        String json = JSON.toJSONString(orderInfoService.getOrdersInRange(time, userId), SerializerFeature.DisableCircularReferenceDetect);
        List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
        return newList;
    }

    @RequestMapping("/admin/searchOrders")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    List<OrderInfo> searchOrdersForManager(@RequestBody ArrayList<Date> time)
    {
        if(SessionUtil.getUserType() == Constant.MANAGER)
        {
            String json = JSON.toJSONString(orderInfoService.getOrdersInRangeForManager(time), SerializerFeature.DisableCircularReferenceDetect);
            List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
            return newList;
        }
        else
            return null;
    }



    @RequestMapping("/searchOrdersByKeyword")
    List<OrderInfo> searchOrdersByKeyword(@RequestParam("keyword") String keyword) {
        String json = JSON.toJSONString(orderInfoService.searchOrdersByKeyword(keyword, userId), SerializerFeature.DisableCircularReferenceDetect);
        List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
        return newList;
    }

    @RequestMapping("/admin/searchOrdersByKeyword")
    List<OrderInfo> searchOrdersByKeywordForManager(@RequestParam("keyword") String keyword)
    {
        if(SessionUtil.getUserType() == Constant.MANAGER)
        {
            String json = JSON.toJSONString(orderInfoService.searchOrdersByKeywordForManager(keyword), SerializerFeature.DisableCircularReferenceDetect);
            List<OrderInfo> newList = JSON.parseArray(json, OrderInfo.class);
            return newList;
        }
        else
            return null;
    }
}
