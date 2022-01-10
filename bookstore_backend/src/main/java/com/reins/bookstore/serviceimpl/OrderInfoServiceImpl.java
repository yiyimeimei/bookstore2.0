package com.reins.bookstore.serviceimpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reins.bookstore.dao.BookDao;
import com.reins.bookstore.dao.CartDao;
import com.reins.bookstore.dao.OrderInfoDao;
import com.reins.bookstore.dao.UserDao;
import com.reins.bookstore.entity.OrderInQueue;
import com.reins.bookstore.entity.OrderInfo;
import com.reins.bookstore.entity.OrderItem;
import com.reins.bookstore.entity.User;
import com.reins.bookstore.service.OrderInfoService;
import com.reins.bookstore.utils.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
//@Scope("session")
public class OrderInfoServiceImpl implements OrderInfoService {

    OrderInfoDao orderInfoDao;
    CartDao cartDao;
    UserDao userDao;
    BookDao bookDao;

    @Autowired
    void setOrderDao(OrderInfoDao orderInfoDao) {
        this.orderInfoDao = orderInfoDao;
    }


    @Autowired
    void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /*@Override
    public void addOrder(Integer userId)
    {
        orderInfoDao.addOrder(userId);
    }*/

    /*@JmsListener(destination = "orderQueue")//, containerFactory = "myFactory")
    public void receiveMessage(OrderInQueue orderInQueue) throws Exception {
        System.out.println("处理了订单" + orderInQueue.getOrderItems());
        placeOrder(orderInQueue.getOrderItems(), orderInQueue.getUserId());
    }*/

    @Override
    @JmsListener(destination = "orderQueue")
    @Transactional(propagation = Propagation.REQUIRED)
    public void placeOrder(OrderInQueue orderInQueue){
        JSONObject orderItems = orderInQueue.getOrderItems();
        Integer userId = orderInQueue.getUserId();

        if (orderItems != null)
        {
            JSONArray itemList = JSONArray.fromObject(orderItems.get("itemList"));
            Integer totalPrice = 0;
            String orderAddress = orderItems.getString("orderAddress");
            String orderTel = orderItems.getString("orderTel");
            String orderReceiver = orderItems.getString("orderReceiver");
            Integer orderId = orderInfoDao.placeOrder(orderReceiver, orderAddress, orderTel, userId);
            System.out.println("orderId" + orderId);
            for (Object listItem : itemList)
            {
                JSONObject orderItem = JSONObject.fromObject(listItem);
                Integer bookId = (Integer) (orderItem.get("bookId"));
                Integer bookInventory = bookDao.getBookInventoryById(bookId);
                Integer bookNumber = (Integer) (orderItem.get("bookNumber"));
                /*if(bookInventory < bookNumber)
                {
                    throw new Exception("购买书籍数量大于库存");
                }*/
                Integer bookPrice = bookDao.getBookPriceById(bookId);
                totalPrice += (bookPrice * bookNumber);
                Integer cartId = (Integer) (orderItem.get("cartId"));
                orderInfoDao.addOrderItem(orderId, bookId, bookNumber);
                bookDao.placeOrder(bookId, bookNumber);
                cartDao.deleteCartItem(cartId, userId);
            }
            if(userId != null)
            {
                userDao.addConsumptionByUserId(userId, totalPrice);
            }
            //return true;
        }
        //return false;
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return orderInfoDao.getUserOrdersByOrderId(orderId);
    }


    @Override
    public ArrayList<OrderInfo> getAllOrders(Integer userId)
    {
        return orderInfoDao.getAllOrders(userId);
    }

    @Override
    public ArrayList<OrderInfo> getAllOrdersForManager()
    {
        return orderInfoDao.getAllOrdersForManager();
    }

    @Override
    @Transactional
    public ArrayList<OrderInfo> getOrdersInRange(ArrayList<Date> time, Integer userId)
    {
        if (time != null && time.size() == 2)
        {
            Date start = (Date) time.get(0);
            Date end = (Date) time.get(1);
            System.out.println("start&end");
            System.out.println(start);
            System.out.println(end);
            if (start != null && end != null)
                return orderInfoDao.getOrdersInRange(start, end, userId);
        }
        return orderInfoDao.getAllOrders(userId);

    }

    @Override
    @Transactional
    public ArrayList<OrderInfo> getOrdersInRangeForManager(ArrayList<Date> time)
    {
        if (time != null && time.size() == 2)
        {
            Date start = (Date) time.get(0);
            Date end = (Date) time.get(1);
            System.out.println("start&end");
            System.out.println(start);
            System.out.println(end);
            if (start != null && end != null)
                return orderInfoDao.getOrdersInRangeForManager(start, end);
        }
        return orderInfoDao.getAllOrdersForManager();
    }

    @Override
    public ArrayList<OrderInfo> searchOrdersByKeyword(String keyword, Integer userId)
    {
        return orderInfoDao.searchOrdersByKeyword(keyword, userId);
    }

    @Override
    public ArrayList<OrderInfo> searchOrdersByKeywordForManager(String keyword)
    {
        return orderInfoDao.searchOrdersByKeywordForManager(keyword);
    }
}
