package com.aleinik.dao;

import com.aleinik.config.SessionCreator;
import com.aleinik.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO() {
        super(Customer.class);
    }

    public void saveCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = SessionCreator.getSession()) {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
