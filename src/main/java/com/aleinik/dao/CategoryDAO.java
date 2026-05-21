package com.aleinik.dao;

import com.aleinik.entity.Category;

import java.util.Optional;

public class CategoryDAO extends GenericDAO<Category> {

    public CategoryDAO() {
        super(Category.class);
    }

    public Optional<Category> findByName(String name) {
        return getCurrentSession().createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }
}
