package com.reins.bookstore.repository;

import com.reins.bookstore.entity.User;
import com.reins.bookstore.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VisitRepository extends JpaRepository<Visit,Integer> {

    @Modifying
    @Query(value = "update Visit set visitNumber = visitNumber + 1 where visitId = 1")
    Integer visitHomePage();

    @Query(value = "select visitNumber from Visit where visitId = 1")
    Integer getVisitNumber();
}
