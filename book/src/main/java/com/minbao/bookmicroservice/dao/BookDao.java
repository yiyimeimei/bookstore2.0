package com.minbao.bookmicroservice.dao;

import com.minbao.bookmicroservice.entity.Book;

public interface BookDao {
    Book getAuthorByTitle(String title);
}
