package com.reins.bookstore.controller;

import com.reins.bookstore.entity.Book;
import com.reins.bookstore.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VisitController {

    @Autowired
    VisitService visitService;

    @RequestMapping("/visitHomePage")
    public Integer visitHomePage()
    {
        return visitService.visitHomePage();
    }



}
