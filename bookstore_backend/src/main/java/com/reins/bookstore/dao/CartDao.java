package com.reins.bookstore.dao;

import com.reins.bookstore.entity.Cart;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface CartDao {
/*
    void addCartProduct(Integer customerId, Integer bookId, String bookTitle, String bookImage1, Double bookPrice);
*/

    List<Cart> getCartByCustomerId(Integer customerId);

    void deleteCartItem(Integer cartId, Integer userId);

    void addBookNumber(Integer bookId, Integer userId);

    void addCartItem(Integer bookId, Integer userId);

    List<Cart> getAllCartItems(Integer userId);

    Boolean existsBook(Integer bookId, Integer userId);

    List<Cart> searchCartItems(String keyword, Integer userId);
}
