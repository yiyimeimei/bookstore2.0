package com.reins.bookstore.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node
public class BookType {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private BookType() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    public BookType(String type) {
        this.type = type;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "RELATIVE")
    public Set<BookType> relatives;

    public void dependent(BookType bookType)
    {
        if (relatives == null) {
            relatives = new HashSet<>();
        }
        relatives.add(bookType);
    }

    public String toString()
    {
        return this.type + "'s relatives => "
                + Optional.ofNullable(this.relatives).orElse(
                Collections.emptySet()).stream()
                .map(BookType::getType)
                .collect(Collectors.toList());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
