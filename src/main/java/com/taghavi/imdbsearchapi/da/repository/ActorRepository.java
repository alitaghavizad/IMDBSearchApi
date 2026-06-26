package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, String> {
    List<Actor> findByIdIn(List<String> ids);

    List<Actor> findByActorName(String actorName);

    @Query(value = "SELECT NCONST FROM NAME_BASICS WHERE DEATHYEAR = '\\N'", nativeQuery = true)
    List<String> findAllLivingActors();
}
