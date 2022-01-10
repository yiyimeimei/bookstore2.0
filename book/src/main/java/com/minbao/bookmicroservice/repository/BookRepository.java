package com.minbao.bookmicroservice.repository;

import com.minbao.bookmicroservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "from Book where title = :title")
    Book getBookByTitle(@Param("title") String title);

}
