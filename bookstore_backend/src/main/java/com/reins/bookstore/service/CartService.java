package com.reins.bookstore.service;

import com.reins.bookstore.entity.Cart;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartService {
/*
    void addCartProduct(Integer customerId, Integer bookId, String bookTitle, String bookImage1, Double bookPrice);
*/

    List<Cart> getCartByCustomerId(Integer customerId);

    Boolean addToCart(Integer bookId, Integer userId);

    void deleteCartItem(Integer cartId, Integer userId);

    List<Cart> getAllCartItems(Integer userId);

    List<Cart> searchCartItems(String keyword, Integer userId);
}
