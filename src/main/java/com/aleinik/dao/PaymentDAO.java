package com.aleinik.dao;

import com.aleinik.entity.Payment;

public class PaymentDAO extends GenericDAO<Payment> {
    public PaymentDAO() {
        super(Payment.class);
    }
}
