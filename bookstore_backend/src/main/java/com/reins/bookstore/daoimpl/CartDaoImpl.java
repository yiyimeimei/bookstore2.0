package com.reins.bookstore.daoimpl;

import com.reins.bookstore.dao.CartDao;
import com.reins.bookstore.entity.Cart;
import com.reins.bookstore.repository.CartRepository;
import com.reins.bookstore.utils.sessionutils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    CartRepository cartRepository;
    @Autowired
    void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getCartByCustomerId(Integer customerId)
    {
        return cartRepository.getCartByCustomerId(customerId);
    }


    @Override
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCartItem(Integer cartId, Integer userId)
    {
        Cart c = cartRepository.getUserCartByCartId(cartId);

        if(c != null && c.getUserId() == userId)
        {
            cartRepository.deleteUserCartByCartId(cartId);
        }
    }

    @Override
    public void addCartItem(Integer bookId, Integer userId) {
        Cart cart = new Cart();
        cart.setBookId(bookId);
        cart.setBookNumber(1);
        cart.setUserId(userId);
        cartRepository.save(cart);
    }

    @Override
    public void addBookNumber(Integer bookId, Integer userId)
    {
        cartRepository.addBookNumber(bookId, userId);
    }

    @Override
    public List<Cart> getAllCartItems(Integer userId) {
        if (userId != null)
            return cartRepository.getAllCartItems(userId);
        else
            return null;
    }

    @Override
    public Boolean existsBook(Integer bookId, Integer userId) {
        return cartRepository.getCartByBookIdAndUserId(bookId, userId) != null;
    }

    @Override
    public List<Cart> searchCartItems(String keyword, Integer userId) {
        return cartRepository.searchCartItems(userId, keyword);
    }
}
