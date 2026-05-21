package com.aleinik.dao;

import com.aleinik.config.SessionCreator;
import com.aleinik.entity.Rental;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Date;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO() {
        super(Rental.class);
    }

    public void returnItemById(int rentalId) {
        String hql = "UPDATE Rental r SET r.returnDate = :localDateTimeValue";
        Query query = SessionCreator.getSession().createQuery(hql, Rental.class);
        LocalDateTime localDateTime = LocalDateTime.now();
        query.setParameter("localDateTime", localDateTime);
        query.executeUpdate();
    }
}
