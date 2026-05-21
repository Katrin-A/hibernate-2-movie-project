package com.aleinik.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.aleinik.entity.*;

public class SessionCreator {

    private static final SessionFactory sessionFactory;

    private SessionCreator() {}

   static  {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Country.class);
        configuration.addAnnotatedClass(City.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Store.class);
        configuration.addAnnotatedClass(Staff.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Inventory.class);
        configuration.addAnnotatedClass(Film.class);
        configuration.addAnnotatedClass(Rental.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(FilmText.class);
        configuration.addAnnotatedClass(Language.class);
        configuration.addAnnotatedClass(Category.class);

        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
