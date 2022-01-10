package com.reins.bookstore.daoimpl;

import com.reins.bookstore.dao.VisitDao;
import com.reins.bookstore.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VisitDaoImpl implements VisitDao
{
    @Autowired
    VisitRepository visitRepository;

    @Override
    public Integer visitHomePage()
    {
        visitRepository.visitHomePage();
        return visitRepository.getVisitNumber();
    }

}
