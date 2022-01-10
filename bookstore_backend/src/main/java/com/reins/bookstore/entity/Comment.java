package com.reins.bookstore.entity;

import org.springframework.data.annotation.Id;

public class Comment {
    /*@Id
    private Integer id;*/
    private Integer bookId;
    private Integer userId;
    private String comment;

    public Comment(Integer bookId, Integer userId, String comment){
        this.bookId = bookId;
        this.userId = userId;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId(){return userId;}

    public void setUserId(Integer userId){this.userId = userId;}

}
