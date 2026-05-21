package com.aleinik.dao;

import com.aleinik.entity.Country;

import java.util.Optional;

public class CountryDAO extends GenericDAO<Country> {

    public CountryDAO() {
        super(Country.class);
    }

    public Optional<Country> getCountryByName(String countryName) {
        return getCurrentSession()
                .createQuery("SELECT c FROM Country c WHERE c.country = :countryName", Country.class)
                .setParameter("countryName", countryName)
                .uniqueResultOptional();
    }


    public Country findOrCreate(String countryName) {
        return getCountryByName(countryName)
                .orElseGet(() -> {
                    Country country = Country
                            .builder()
                            .country(countryName)
                            .build();
                    return save(country);
                });

    }
}
