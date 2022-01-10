package com.reins.bookstore.daoimpl;

import com.reins.bookstore.dao.BookTypeDao;
import com.reins.bookstore.entity.BookType;
import com.reins.bookstore.repository.BookTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookTypeDaoImpl implements BookTypeDao {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Override
    public List<BookType> searchDependentType(String type)
    {
        return bookTypeRepository.findRelatives(type);
    }
}
