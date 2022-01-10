package com.minbao.bookmicroservice.daoimpl;

import com.minbao.bookmicroservice.dao.BookDao;
import com.minbao.bookmicroservice.entity.Book;
import com.minbao.bookmicroservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImplement implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getAuthorByTitle(String title)
    {
        Book b = bookRepository.getBookByTitle(title);
        if(b == null)
            return null;
        else
            return b;
    }
}
