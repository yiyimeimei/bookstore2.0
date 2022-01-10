package com.minbao.bookmicroservice.service;

import com.minbao.bookmicroservice.entity.Book;

public interface BookService {
    Book getAuthorByTitle(String title);
}
