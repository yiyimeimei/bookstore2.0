package com.minbao.bookmicroservice.controller;

import com.minbao.bookmicroservice.entity.Book;
import com.minbao.bookmicroservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/author/{title}")
    //@CrossOrigin(allowCredentials = "true")
    Book getAuthorByTitle(@PathVariable(name = "title") String title)
    {
        System.out.println(title);
        return bookService.getAuthorByTitle(title);
    }



    @RequestMapping("/available")
    String test()
    {
        return "SUCCESS";
    }
}
