package com.reins.bookstore.dao;

import com.reins.bookstore.entity.BookType;

import java.util.List;

public interface BookTypeDao {

    List<BookType> searchDependentType(String type);
}
