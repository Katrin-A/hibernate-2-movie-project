package com.aleinik.dao;

import com.aleinik.entity.City;
import com.aleinik.entity.Country;

import java.util.Optional;

public class CityDAO extends GenericDAO<City> {

    private final CountryDAO countryDAO;

    public CityDAO() {

        super(City.class);
        this.countryDAO = new CountryDAO();

    }


    public Optional<City> getCityByName(String name, String countryName) {
        return getCurrentSession()
                .createQuery("SELECT c FROM City c WHERE c.city = :name AND c.country.country = :country", City.class)
                .setParameter("name", name)
                .setParameter("country", countryName)
                .uniqueResultOptional();
    }

    public City findOrCreate(String cityName, String countryName) {
        return getCityByName(cityName, countryName)
                .orElseGet(() -> {
                    Country country = countryDAO.findOrCreate(countryName);
                    City newCity = City.builder()
                            .city(cityName)
                            .country(country)
                            .build();
                    return save(newCity);
                });
    }


}
