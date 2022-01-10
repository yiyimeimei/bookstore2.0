package com.reins.bookstore.serviceimpl;


import com.reins.bookstore.dao.CartDao;
import com.reins.bookstore.entity.Cart;
import com.reins.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    CartDao cartDao;
    @Autowired
    void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /*@Override
    public void addCartProduct(Integer customerId, Integer bookId, String bookTitle, String bookImage1, Double bookPrice)
    {
        cartDao.addCartProduct(customerId, bookId, bookTitle, bookImage1, bookPrice);
    }*/

    @Override
    public List<Cart> getCartByCustomerId(Integer customerId)
    {
        return cartDao.getCartByCustomerId(customerId);
    }

    @Override
    public Boolean addToCart(Integer bookId, Integer userId) {
        if (!cartDao.existsBook(bookId, userId))
        {
            cartDao.addCartItem(bookId, userId);
            return true;
        } else
        {
            cartDao.addBookNumber(bookId, userId);
            return false;
        }
    }

    @Override
    public void deleteCartItem(Integer cartId, Integer userId) {
        cartDao.deleteCartItem(cartId, userId);
    }

    @Override
    public List<Cart> getAllCartItems(Integer userId) {
        return cartDao.getAllCartItems(userId);
    }

    @Override
    public List<Cart> searchCartItems(String keyword, Integer userId) {
        return cartDao.searchCartItems(keyword, userId);
    }
}
