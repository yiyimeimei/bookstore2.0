package com.reins.bookstore.controller;

import com.reins.bookstore.entity.Comment;
import com.reins.bookstore.service.BookService;
import com.reins.bookstore.utils.msgutils.Message;
import com.reins.bookstore.utils.msgutils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    BookService bookService;

    @RequestMapping("/commentBook")
    public Message commentBook(@RequestParam("bookId") Integer bookId, @RequestParam("userId") Integer userId, @RequestParam("comment") String comment)
    {
        if(bookService.commentBook(bookId, userId, comment))
        {
            return MessageUtil.createMessage(MessageUtil.GENERAL_SUCCESS_CODE, MessageUtil.GENERAL_SUCCESS_MSG);
        }
        else
        {
            return MessageUtil.createMessage(MessageUtil.GENERAL_FAIL_CODE, MessageUtil.GENERAL_FAIL_MSG);
        }
    }

    @RequestMapping("/searchCommentByBookId")
    public List<Comment> commentBook(@RequestParam("bookId") Integer bookId)
    {
        return bookService.searchCommentByBookId(bookId);
    }
}
