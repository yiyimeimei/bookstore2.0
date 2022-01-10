package com.reins.bookstore.entity;


import net.sf.json.JSONObject;

public class OrderInQueue {
    private Integer userId;
    private JSONObject orderItems;

    public OrderInQueue() {}

    public OrderInQueue(Integer userId, JSONObject orderItems)
    {
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public JSONObject getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(JSONObject orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return String.format("OrderInQueue{userId=%s, orderItems=%s}", getUserId(), getOrderItems());
    }
}
