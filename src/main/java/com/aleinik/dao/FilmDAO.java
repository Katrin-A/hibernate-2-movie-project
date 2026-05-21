package com.aleinik.dao;

import com.aleinik.entity.Film;

public class FilmDAO extends GenericDAO<Film> {
    public FilmDAO() {
        super(Film.class);
    }
}
