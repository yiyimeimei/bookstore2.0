package com.reins.bookstore.repository;

import com.reins.bookstore.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
public interface CommentRepository extends MongoRepository<Comment, String>
{
    List<Comment> findByBookId(@Param("bookId") Integer bookId);
}