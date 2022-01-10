package com.minbao.bookmicroservice.serviceimpl;


import com.minbao.bookmicroservice.dao.BookDao;
import com.minbao.bookmicroservice.entity.Book;
import com.minbao.bookmicroservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImplement implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book getAuthorByTitle(String title) {
        return bookDao.getAuthorByTitle(title);
    }
}
