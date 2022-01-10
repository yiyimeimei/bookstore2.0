package com.reins.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "visit")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Visit {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer visitId;
    private Integer visitNumber;

    public synchronized void increment() {
        visitNumber++;
    }

    /*public synchronized void decrement() {
        visitNumber--;
    }*/

    public synchronized int getVisitNumber()
    {
        return visitNumber;
    }
}