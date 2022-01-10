package com.reins.bookstore.serviceimpl;

import com.reins.bookstore.dao.VisitDao;
import com.reins.bookstore.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    VisitDao visitDao;

    @Override
    public synchronized Integer visitHomePage()
    {
        /*visitDao.increment();
        return visitDao.getVisit();*/
        return visitDao.visitHomePage();
    }
}
