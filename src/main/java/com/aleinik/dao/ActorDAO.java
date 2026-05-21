package com.aleinik.dao;

import com.aleinik.entity.Actor;

import java.util.Optional;

public class ActorDAO extends GenericDAO<Actor> {

    public ActorDAO() {
        super(Actor.class);
    }

    public Actor findOrCreate(String firstName, String lastName) {
        return getActorByName(firstName, lastName)
                .orElseGet(() -> {
                    Actor actor = Actor.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .build();
                    return save(actor);
                });
    }

    public Optional<Actor> getActorByName(String firstName, String lastName) {
        return getCurrentSession()
                .createQuery("SELECT a FROM Actor a WHERE a.firstName= :firstName AND a.lastName = :lastName", Actor.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResultOptional();
    }
}
