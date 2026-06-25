package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, String> {
    List<Actor> findByIdIn(List<String> ids);

    List<Actor> findByActorName(String actorName);
}
