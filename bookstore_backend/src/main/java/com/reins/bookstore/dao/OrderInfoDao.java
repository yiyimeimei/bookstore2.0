package com.reins.bookstore.dao;

import com.reins.bookstore.entity.OrderInfo;
import com.reins.bookstore.entity.OrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface OrderInfoDao {
    //void addOrder(Integer userId);

    ArrayList<OrderInfo> getAllOrders(Integer userId);

    ArrayList<OrderInfo> getAllOrdersForManager();

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    Integer placeOrder(String receiver, String address, String tel, Integer userId);

    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);

    ArrayList<OrderInfo> getOrdersInRange(Date start, Date end, Integer userId);

    ArrayList<OrderInfo> getOrdersInRangeForManager(Date start, Date end);

    ArrayList<OrderInfo> searchOrdersByKeyword(String keyword, Integer userId);

    ArrayList<OrderInfo> searchOrdersByKeywordForManager(String keyword);

    List<OrderItem> searchOrderItemByBookId(Integer bookId);
}
