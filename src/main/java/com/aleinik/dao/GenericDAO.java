package com.aleinik.dao;

import com.aleinik.config.SessionCreator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class GenericDAO <T>{
    private final Class<T> clazz;

    public GenericDAO(Class<T> clazz){
        this.clazz = clazz;
    }


    public T getById(int id){
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int offset, int count){
        Query<T> query = getCurrentSession().createQuery("FROM " + clazz.getSimpleName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll(){
        return getCurrentSession().createQuery("FROM " + clazz.getSimpleName(), clazz).list();
    }

    public T save(T entity){
        getCurrentSession().persist(entity);
        return entity;
    }

    public T update(T entity){
        return (T) getCurrentSession().merge(entity);
    }

    public T delete(T entity){
        getCurrentSession().remove(entity);
        return entity;
    }

    public void deleteById(int id) {
        T entity = getById(id);
        if (entity != null) {
            getCurrentSession().remove(entity);
        }
    }



    protected Session getCurrentSession(){
        return SessionCreator.getSession();
    }


}
