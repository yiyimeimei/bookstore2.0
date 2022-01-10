package com.reins.bookstore.repository;

import com.reins.bookstore.entity.Book;
import com.reins.bookstore.entity.BookType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookTypeRepository  extends Neo4jRepository<BookType, Long>
{
    BookType findByType(String type);

    @Query(value = "match data=(na:BookType{type:$type})-[*1..2]->(nb:BookType) return data\n")
    List<BookType> findRelatives(String type);
}
