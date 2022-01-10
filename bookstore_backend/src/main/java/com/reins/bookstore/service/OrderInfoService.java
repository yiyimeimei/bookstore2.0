package com.reins.bookstore.service;

import com.reins.bookstore.entity.OrderInQueue;
import com.reins.bookstore.entity.OrderInfo;
import com.reins.bookstore.entity.OrderItem;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface OrderInfoService {
    //void addOrder(Integer userId);

    void placeOrder(OrderInQueue orderInQueue);

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    ArrayList<OrderInfo> getAllOrders(Integer userId);

    ArrayList<OrderInfo> getAllOrdersForManager();

    ArrayList<OrderInfo> getOrdersInRange(ArrayList<Date> time, Integer userId);

    ArrayList<OrderInfo> getOrdersInRangeForManager(ArrayList<Date> time);

    ArrayList<OrderInfo> searchOrdersByKeyword(String keyword, Integer userId);

    ArrayList<OrderInfo> searchOrdersByKeywordForManager(String keyword);
}
