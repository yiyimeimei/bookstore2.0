package com.reins.bookstore.controller;


import com.reins.bookstore.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisitControllerTest {

    @Autowired
    VisitService visitService;

@Test
public void testVisitHomePage() throws Exception
{
    for(int i = 0; i < 100; ++i)
    {
        new Thread(() -> visitService.visitHomePage()).start();
    }

} 


} 
